/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.batch.domain;

import org.joda.beans.*;
import org.joda.beans.impl.direct.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.time.Instant;
import java.util.Map;
import org.joda.beans.BeanBuilder;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

@BeanDefinition
public class RiskFailure extends DirectBean {

  @PropertyDefinition
  private long _id;

  @PropertyDefinition
  private long _calculationConfigurationId;

  @PropertyDefinition
  private String _name;

  @PropertyDefinition
  private long _valueRequirementId;

  @PropertyDefinition
  private long _valueSpecificationId;

  @PropertyDefinition
  private long _functionUniqueId;

  @PropertyDefinition
  private long _computationTargetId;

  @PropertyDefinition
  private long _runId;

  @PropertyDefinition
  private Instant _evalInstant;

  @PropertyDefinition
  private long _computeNodeId;

  public SqlParameterSource toSqlParameterSource() {
    MapSqlParameterSource source = new MapSqlParameterSource();
    source.addValue("id", getId());
    source.addValue("calculation_configuration_id", getCalculationConfigurationId());
    source.addValue("name", getName());
    source.addValue("value_requirement_id", getValueRequirementId());
    source.addValue("value_specification_id", getValueSpecificationId());
    source.addValue("function_unique_id", getFunctionUniqueId());
    source.addValue("computation_target_id", getComputationTargetId());
    source.addValue("run_id", getRunId());
    source.addValue("eval_instant", getEvalInstant());
    source.addValue("compute_node_id", getComputeNodeId());
    return source;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code RiskFailure}.
   * @return the meta-bean, not null
   */
  public static RiskFailure.Meta meta() {
    return RiskFailure.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(RiskFailure.Meta.INSTANCE);
  }

  @Override
  public RiskFailure.Meta metaBean() {
    return RiskFailure.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 3355:  // id
        return getId();
      case 1329751720:  // calculationConfigurationId
        return getCalculationConfigurationId();
      case 3373707:  // name
        return getName();
      case 24059597:  // valueRequirementId
        return getValueRequirementId();
      case -1127019571:  // valueSpecificationId
        return getValueSpecificationId();
      case 1563911364:  // functionUniqueId
        return getFunctionUniqueId();
      case -1362849421:  // computationTargetId
        return getComputationTargetId();
      case 108875014:  // runId
        return getRunId();
      case 820536741:  // evalInstant
        return getEvalInstant();
      case 398290388:  // computeNodeId
        return getComputeNodeId();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 3355:  // id
        setId((Long) newValue);
        return;
      case 1329751720:  // calculationConfigurationId
        setCalculationConfigurationId((Long) newValue);
        return;
      case 3373707:  // name
        setName((String) newValue);
        return;
      case 24059597:  // valueRequirementId
        setValueRequirementId((Long) newValue);
        return;
      case -1127019571:  // valueSpecificationId
        setValueSpecificationId((Long) newValue);
        return;
      case 1563911364:  // functionUniqueId
        setFunctionUniqueId((Long) newValue);
        return;
      case -1362849421:  // computationTargetId
        setComputationTargetId((Long) newValue);
        return;
      case 108875014:  // runId
        setRunId((Long) newValue);
        return;
      case 820536741:  // evalInstant
        setEvalInstant((Instant) newValue);
        return;
      case 398290388:  // computeNodeId
        setComputeNodeId((Long) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      RiskFailure other = (RiskFailure) obj;
      return JodaBeanUtils.equal(getId(), other.getId()) &&
          JodaBeanUtils.equal(getCalculationConfigurationId(), other.getCalculationConfigurationId()) &&
          JodaBeanUtils.equal(getName(), other.getName()) &&
          JodaBeanUtils.equal(getValueRequirementId(), other.getValueRequirementId()) &&
          JodaBeanUtils.equal(getValueSpecificationId(), other.getValueSpecificationId()) &&
          JodaBeanUtils.equal(getFunctionUniqueId(), other.getFunctionUniqueId()) &&
          JodaBeanUtils.equal(getComputationTargetId(), other.getComputationTargetId()) &&
          JodaBeanUtils.equal(getRunId(), other.getRunId()) &&
          JodaBeanUtils.equal(getEvalInstant(), other.getEvalInstant()) &&
          JodaBeanUtils.equal(getComputeNodeId(), other.getComputeNodeId());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCalculationConfigurationId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getValueRequirementId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getValueSpecificationId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFunctionUniqueId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getComputationTargetId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getRunId());
    hash += hash * 31 + JodaBeanUtils.hashCode(getEvalInstant());
    hash += hash * 31 + JodaBeanUtils.hashCode(getComputeNodeId());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the id.
   * @return the value of the property
   */
  public long getId() {
    return _id;
  }

  /**
   * Sets the id.
   * @param id  the new value of the property
   */
  public void setId(long id) {
    this._id = id;
  }

  /**
   * Gets the the {@code id} property.
   * @return the property, not null
   */
  public final Property<Long> id() {
    return metaBean().id().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the calculationConfigurationId.
   * @return the value of the property
   */
  public long getCalculationConfigurationId() {
    return _calculationConfigurationId;
  }

  /**
   * Sets the calculationConfigurationId.
   * @param calculationConfigurationId  the new value of the property
   */
  public void setCalculationConfigurationId(long calculationConfigurationId) {
    this._calculationConfigurationId = calculationConfigurationId;
  }

  /**
   * Gets the the {@code calculationConfigurationId} property.
   * @return the property, not null
   */
  public final Property<Long> calculationConfigurationId() {
    return metaBean().calculationConfigurationId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the name.
   * @return the value of the property
   */
  public String getName() {
    return _name;
  }

  /**
   * Sets the name.
   * @param name  the new value of the property
   */
  public void setName(String name) {
    this._name = name;
  }

  /**
   * Gets the the {@code name} property.
   * @return the property, not null
   */
  public final Property<String> name() {
    return metaBean().name().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the valueRequirementId.
   * @return the value of the property
   */
  public long getValueRequirementId() {
    return _valueRequirementId;
  }

  /**
   * Sets the valueRequirementId.
   * @param valueRequirementId  the new value of the property
   */
  public void setValueRequirementId(long valueRequirementId) {
    this._valueRequirementId = valueRequirementId;
  }

  /**
   * Gets the the {@code valueRequirementId} property.
   * @return the property, not null
   */
  public final Property<Long> valueRequirementId() {
    return metaBean().valueRequirementId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the valueSpecificationId.
   * @return the value of the property
   */
  public long getValueSpecificationId() {
    return _valueSpecificationId;
  }

  /**
   * Sets the valueSpecificationId.
   * @param valueSpecificationId  the new value of the property
   */
  public void setValueSpecificationId(long valueSpecificationId) {
    this._valueSpecificationId = valueSpecificationId;
  }

  /**
   * Gets the the {@code valueSpecificationId} property.
   * @return the property, not null
   */
  public final Property<Long> valueSpecificationId() {
    return metaBean().valueSpecificationId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the functionUniqueId.
   * @return the value of the property
   */
  public long getFunctionUniqueId() {
    return _functionUniqueId;
  }

  /**
   * Sets the functionUniqueId.
   * @param functionUniqueId  the new value of the property
   */
  public void setFunctionUniqueId(long functionUniqueId) {
    this._functionUniqueId = functionUniqueId;
  }

  /**
   * Gets the the {@code functionUniqueId} property.
   * @return the property, not null
   */
  public final Property<Long> functionUniqueId() {
    return metaBean().functionUniqueId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the computationTargetId.
   * @return the value of the property
   */
  public long getComputationTargetId() {
    return _computationTargetId;
  }

  /**
   * Sets the computationTargetId.
   * @param computationTargetId  the new value of the property
   */
  public void setComputationTargetId(long computationTargetId) {
    this._computationTargetId = computationTargetId;
  }

  /**
   * Gets the the {@code computationTargetId} property.
   * @return the property, not null
   */
  public final Property<Long> computationTargetId() {
    return metaBean().computationTargetId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the runId.
   * @return the value of the property
   */
  public long getRunId() {
    return _runId;
  }

  /**
   * Sets the runId.
   * @param runId  the new value of the property
   */
  public void setRunId(long runId) {
    this._runId = runId;
  }

  /**
   * Gets the the {@code runId} property.
   * @return the property, not null
   */
  public final Property<Long> runId() {
    return metaBean().runId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the evalInstant.
   * @return the value of the property
   */
  public Instant getEvalInstant() {
    return _evalInstant;
  }

  /**
   * Sets the evalInstant.
   * @param evalInstant  the new value of the property
   */
  public void setEvalInstant(Instant evalInstant) {
    this._evalInstant = evalInstant;
  }

  /**
   * Gets the the {@code evalInstant} property.
   * @return the property, not null
   */
  public final Property<Instant> evalInstant() {
    return metaBean().evalInstant().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the computeNodeId.
   * @return the value of the property
   */
  public long getComputeNodeId() {
    return _computeNodeId;
  }

  /**
   * Sets the computeNodeId.
   * @param computeNodeId  the new value of the property
   */
  public void setComputeNodeId(long computeNodeId) {
    this._computeNodeId = computeNodeId;
  }

  /**
   * Gets the the {@code computeNodeId} property.
   * @return the property, not null
   */
  public final Property<Long> computeNodeId() {
    return metaBean().computeNodeId().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code RiskFailure}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code id} property.
     */
    private final MetaProperty<Long> _id = DirectMetaProperty.ofReadWrite(
        this, "id", RiskFailure.class, Long.TYPE);
    /**
     * The meta-property for the {@code calculationConfigurationId} property.
     */
    private final MetaProperty<Long> _calculationConfigurationId = DirectMetaProperty.ofReadWrite(
        this, "calculationConfigurationId", RiskFailure.class, Long.TYPE);
    /**
     * The meta-property for the {@code name} property.
     */
    private final MetaProperty<String> _name = DirectMetaProperty.ofReadWrite(
        this, "name", RiskFailure.class, String.class);
    /**
     * The meta-property for the {@code valueRequirementId} property.
     */
    private final MetaProperty<Long> _valueRequirementId = DirectMetaProperty.ofReadWrite(
        this, "valueRequirementId", RiskFailure.class, Long.TYPE);
    /**
     * The meta-property for the {@code valueSpecificationId} property.
     */
    private final MetaProperty<Long> _valueSpecificationId = DirectMetaProperty.ofReadWrite(
        this, "valueSpecificationId", RiskFailure.class, Long.TYPE);
    /**
     * The meta-property for the {@code functionUniqueId} property.
     */
    private final MetaProperty<Long> _functionUniqueId = DirectMetaProperty.ofReadWrite(
        this, "functionUniqueId", RiskFailure.class, Long.TYPE);
    /**
     * The meta-property for the {@code computationTargetId} property.
     */
    private final MetaProperty<Long> _computationTargetId = DirectMetaProperty.ofReadWrite(
        this, "computationTargetId", RiskFailure.class, Long.TYPE);
    /**
     * The meta-property for the {@code runId} property.
     */
    private final MetaProperty<Long> _runId = DirectMetaProperty.ofReadWrite(
        this, "runId", RiskFailure.class, Long.TYPE);
    /**
     * The meta-property for the {@code evalInstant} property.
     */
    private final MetaProperty<Instant> _evalInstant = DirectMetaProperty.ofReadWrite(
        this, "evalInstant", RiskFailure.class, Instant.class);
    /**
     * The meta-property for the {@code computeNodeId} property.
     */
    private final MetaProperty<Long> _computeNodeId = DirectMetaProperty.ofReadWrite(
        this, "computeNodeId", RiskFailure.class, Long.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "id",
        "calculationConfigurationId",
        "name",
        "valueRequirementId",
        "valueSpecificationId",
        "functionUniqueId",
        "computationTargetId",
        "runId",
        "evalInstant",
        "computeNodeId");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3355:  // id
          return _id;
        case 1329751720:  // calculationConfigurationId
          return _calculationConfigurationId;
        case 3373707:  // name
          return _name;
        case 24059597:  // valueRequirementId
          return _valueRequirementId;
        case -1127019571:  // valueSpecificationId
          return _valueSpecificationId;
        case 1563911364:  // functionUniqueId
          return _functionUniqueId;
        case -1362849421:  // computationTargetId
          return _computationTargetId;
        case 108875014:  // runId
          return _runId;
        case 820536741:  // evalInstant
          return _evalInstant;
        case 398290388:  // computeNodeId
          return _computeNodeId;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends RiskFailure> builder() {
      return new DirectBeanBuilder<RiskFailure>(new RiskFailure());
    }

    @Override
    public Class<? extends RiskFailure> beanType() {
      return RiskFailure.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code id} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> id() {
      return _id;
    }

    /**
     * The meta-property for the {@code calculationConfigurationId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> calculationConfigurationId() {
      return _calculationConfigurationId;
    }

    /**
     * The meta-property for the {@code name} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> name() {
      return _name;
    }

    /**
     * The meta-property for the {@code valueRequirementId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> valueRequirementId() {
      return _valueRequirementId;
    }

    /**
     * The meta-property for the {@code valueSpecificationId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> valueSpecificationId() {
      return _valueSpecificationId;
    }

    /**
     * The meta-property for the {@code functionUniqueId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> functionUniqueId() {
      return _functionUniqueId;
    }

    /**
     * The meta-property for the {@code computationTargetId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> computationTargetId() {
      return _computationTargetId;
    }

    /**
     * The meta-property for the {@code runId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> runId() {
      return _runId;
    }

    /**
     * The meta-property for the {@code evalInstant} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Instant> evalInstant() {
      return _evalInstant;
    }

    /**
     * The meta-property for the {@code computeNodeId} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Long> computeNodeId() {
      return _computeNodeId;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
