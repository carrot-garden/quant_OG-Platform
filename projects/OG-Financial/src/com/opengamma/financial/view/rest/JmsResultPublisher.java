/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.view.rest;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import javax.time.Instant;

import org.fudgemsg.FudgeContext;
import org.fudgemsg.MutableFudgeMsg;
import org.fudgemsg.mapping.FudgeSerializationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.opengamma.engine.view.ViewComputationResultModel;
import com.opengamma.engine.view.ViewDeltaResultModel;
import com.opengamma.engine.view.client.ViewClient;
import com.opengamma.engine.view.compilation.CompiledViewDefinition;
import com.opengamma.engine.view.execution.ViewCycleExecutionOptions;
import com.opengamma.engine.view.listener.CycleCompletedCall;
import com.opengamma.engine.view.listener.CycleExecutionFailedCall;
import com.opengamma.engine.view.listener.ProcessCompletedCall;
import com.opengamma.engine.view.listener.ProcessTerminatedCall;
import com.opengamma.engine.view.listener.ViewDefinitionCompilationFailedCall;
import com.opengamma.engine.view.listener.ViewDefinitionCompiledCall;
import com.opengamma.engine.view.listener.ViewResultListener;
import com.opengamma.transport.jms.QueueingJmsByteArrayMessageSender;

/**
 * Publishes asynchronous results from a view client over JMS.
 * <p>
 * Always call {@link #stopPublishingResults()} when this result publisher is no longer required to ensure that
 * associated resources are tidied up.
 */
public class JmsResultPublisher implements ViewResultListener {

  private static final Logger s_logger = LoggerFactory.getLogger(JmsResultPublisher.class);
  private static final String SEQUENCE_NUMBER_FIELD_NAME = "#";
  
  private final ViewClient _viewClient;
  private final FudgeContext _fudgeContext;
  private final FudgeSerializationContext _fudgeSerializationContext;
  private final String _destinationName;
  private final JmsTemplate _jmsTemplate;
  private final ReentrantLock _lock = new ReentrantLock();
  private final AtomicLong _sequenceNumber = new AtomicLong();
  
  private volatile QueueingJmsByteArrayMessageSender _sender;

  public JmsResultPublisher(ViewClient viewClient, FudgeContext fudgeContext, String destinationPrefix, JmsTemplate jmsTemplate) {
    _viewClient = viewClient;
    _fudgeContext = fudgeContext;
    _fudgeSerializationContext = new FudgeSerializationContext(fudgeContext);
    _jmsTemplate = jmsTemplate;
    _destinationName = getDestinationName(viewClient, destinationPrefix);
  }
  
  public void startPublishingResults() {
    _lock.lock();
    try {
      s_logger.debug("Setting listener {} on view client {}'s results", this, _viewClient);
      if (_sender == null) {
        _sender = new QueueingJmsByteArrayMessageSender(_destinationName, _jmsTemplate);
      }
      _viewClient.setResultListener(this);
    } finally {
      _lock.unlock();
    }
  }
  
  public void stopPublishingResults() {
    _lock.lock();
    try {
      s_logger.debug("Removing listener {} from view client {}'s results", this, _viewClient);
      _viewClient.setResultListener(null);
      if (_sender != null) {
        _sender.shutdown();
        _sender = null;
      }
    } finally {
      _lock.unlock();
    }
  }

  public String getDestinationName() {
    return _destinationName;
  }
  
  //-------------------------------------------------------------------------
  @Override
  public void viewDefinitionCompiled(CompiledViewDefinition compiledViewDefinition) {
    send(new ViewDefinitionCompiledCall(compiledViewDefinition));
  }  
  
  @Override
  public void viewDefinitionCompilationFailed(Instant valuationTime, Exception exception) {
    send(new ViewDefinitionCompilationFailedCall(valuationTime, exception));
  }

  @Override
  public void cycleCompleted(ViewComputationResultModel fullResult, ViewDeltaResultModel deltaResult) {
    send(new CycleCompletedCall(fullResult, deltaResult));
  }

  @Override
  public void cycleExecutionFailed(ViewCycleExecutionOptions executionOptions, Exception exception) {
    send(new CycleExecutionFailedCall(executionOptions, exception));
  }

  @Override
  public void processCompleted() {
    send(new ProcessCompletedCall());
  }

  @Override
  public void processTerminated(boolean executionInterrupted) {
    send(new ProcessTerminatedCall(executionInterrupted));
  }

  //-------------------------------------------------------------------------
  private String getDestinationName(ViewClient viewClient, String prefix) {
    return prefix + "-" + viewClient.getUniqueId();
  }
  
  private void send(Object result) {
    s_logger.debug("Result received to forward over JMS: {}", result);
    MutableFudgeMsg resultMsg = _fudgeSerializationContext.objectToFudgeMsg(result);
    FudgeSerializationContext.addClassHeader(resultMsg, result.getClass());
    long sequenceNumber = _sequenceNumber.getAndIncrement();
    resultMsg.add(SEQUENCE_NUMBER_FIELD_NAME, sequenceNumber);
    s_logger.debug("Sending result as fudge message with sequence number {}: {}", sequenceNumber, resultMsg);
    byte[] resultMsgByteArray = _fudgeContext.toByteArray(resultMsg);
    _sender.send(resultMsgByteArray);
  }
  
}
