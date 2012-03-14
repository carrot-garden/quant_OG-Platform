/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.production.viewer;

import java.net.URI;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.ws.rs.core.UriBuilder;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentServer;
import com.opengamma.component.factory.ComponentInfoAttributes;
import com.opengamma.component.rest.RemoteComponentServer;
import com.opengamma.engine.view.ViewDefinitionRepository;
import com.opengamma.engine.view.ViewProcessor;
import com.opengamma.financial.view.rest.RemoteViewDefinitionRepository;
import com.opengamma.financial.view.rest.RemoteViewProcessor;
import com.opengamma.util.jms.JmsConnector;
import com.opengamma.util.jms.JmsConnectorFactoryBean;

/**
 * Provides access to a ViewProcessor by starting an engine.
 */
public final class DemoViewProcessor {

  /**
   * The remote server.
   */
  private final ComponentServer _componentServer;
  /**
   * The remote server.
   */
  private final ScheduledExecutorService _scheduler = Executors.newSingleThreadScheduledExecutor();

  /**
   * Connects to a localhost server.
   */
  public DemoViewProcessor() {
    this(URI.create("http://localhost:8080/jax"));
  }

  /**
   * Connects to a server.
   * 
   * @param uri  the URI to connect to,not null
   */
  public DemoViewProcessor(URI uri) {
    RemoteComponentServer server = new RemoteComponentServer(uri);
    _componentServer = server.getComponentServer();
  }

  public ViewProcessor getViewProcessor() {
    ComponentInfo info = _componentServer.getComponentInfo(ViewProcessor.class, "main");
    
    URI jmsBrokerUri = URI.create(info.getAttribute(ComponentInfoAttributes.JMS_BROKER_URI));
    ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(jmsBrokerUri);
    JmsConnectorFactoryBean factory = new JmsConnectorFactoryBean();
    factory.setName(getClass().getSimpleName());
    factory.setConnectionFactory(cf);
    factory.setClientBrokerUri(jmsBrokerUri);
    JmsConnector jmsConnector = factory.getObjectCreating();
    
    URI uri = UriBuilder.fromUri(info.getUri()).build();
    
    return new RemoteViewProcessor(uri, jmsConnector, _scheduler);
  }

  public ViewDefinitionRepository getViewDefinitionRepository() {
    ComponentInfo info = _componentServer.getComponentInfo(ViewDefinitionRepository.class, "main");
    return new RemoteViewDefinitionRepository(info.getUri());
  }

}
