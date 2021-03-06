/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.future;

import java.io.Serializable;
import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.id.ExternalIdBundle;

/**
 * A deliverable element within a {@code BondFutureSecurity}.
 */
@BeanDefinition
public class BondFutureDeliverable extends DirectBean implements Serializable {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * The identifiers.
   */
  @PropertyDefinition(validate = "notNull")
  private ExternalIdBundle _identifiers = ExternalIdBundle.EMPTY;
  /**
   * The conversion factor.
   */
  @PropertyDefinition
  private double _conversionFactor;

  /**
   * Creates an empty instance.
   * <p>
   * The details should be set before use.
   */
  public BondFutureDeliverable() {
    super();
  }

  /**
   * Creates an instance.
   * 
   * @param bundle  the external identifier bundle, not null
   * @param conversionFactor  the conversion factor
   */
  public BondFutureDeliverable(ExternalIdBundle bundle, double conversionFactor) {
    setIdentifiers(bundle);
    setConversionFactor(conversionFactor);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BondFutureDeliverable}.
   * @return the meta-bean, not null
   */
  public static BondFutureDeliverable.Meta meta() {
    return BondFutureDeliverable.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BondFutureDeliverable.Meta.INSTANCE);
  }

  @Override
  public BondFutureDeliverable.Meta metaBean() {
    return BondFutureDeliverable.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1368189162:  // identifiers
        return getIdentifiers();
      case 1438876165:  // conversionFactor
        return getConversionFactor();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1368189162:  // identifiers
        setIdentifiers((ExternalIdBundle) newValue);
        return;
      case 1438876165:  // conversionFactor
        setConversionFactor((Double) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_identifiers, "identifiers");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BondFutureDeliverable other = (BondFutureDeliverable) obj;
      return JodaBeanUtils.equal(getIdentifiers(), other.getIdentifiers()) &&
          JodaBeanUtils.equal(getConversionFactor(), other.getConversionFactor());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getIdentifiers());
    hash += hash * 31 + JodaBeanUtils.hashCode(getConversionFactor());
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the identifiers.
   * @return the value of the property, not null
   */
  public ExternalIdBundle getIdentifiers() {
    return _identifiers;
  }

  /**
   * Sets the identifiers.
   * @param identifiers  the new value of the property, not null
   */
  public void setIdentifiers(ExternalIdBundle identifiers) {
    JodaBeanUtils.notNull(identifiers, "identifiers");
    this._identifiers = identifiers;
  }

  /**
   * Gets the the {@code identifiers} property.
   * @return the property, not null
   */
  public final Property<ExternalIdBundle> identifiers() {
    return metaBean().identifiers().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the conversion factor.
   * @return the value of the property
   */
  public double getConversionFactor() {
    return _conversionFactor;
  }

  /**
   * Sets the conversion factor.
   * @param conversionFactor  the new value of the property
   */
  public void setConversionFactor(double conversionFactor) {
    this._conversionFactor = conversionFactor;
  }

  /**
   * Gets the the {@code conversionFactor} property.
   * @return the property, not null
   */
  public final Property<Double> conversionFactor() {
    return metaBean().conversionFactor().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BondFutureDeliverable}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code identifiers} property.
     */
    private final MetaProperty<ExternalIdBundle> _identifiers = DirectMetaProperty.ofReadWrite(
        this, "identifiers", BondFutureDeliverable.class, ExternalIdBundle.class);
    /**
     * The meta-property for the {@code conversionFactor} property.
     */
    private final MetaProperty<Double> _conversionFactor = DirectMetaProperty.ofReadWrite(
        this, "conversionFactor", BondFutureDeliverable.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "identifiers",
        "conversionFactor");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1368189162:  // identifiers
          return _identifiers;
        case 1438876165:  // conversionFactor
          return _conversionFactor;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BondFutureDeliverable> builder() {
      return new DirectBeanBuilder<BondFutureDeliverable>(new BondFutureDeliverable());
    }

    @Override
    public Class<? extends BondFutureDeliverable> beanType() {
      return BondFutureDeliverable.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code identifiers} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ExternalIdBundle> identifiers() {
      return _identifiers;
    }

    /**
     * The meta-property for the {@code conversionFactor} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Double> conversionFactor() {
      return _conversionFactor;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
