/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.security.option;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * The vanilla payoff style.
 */
@BeanDefinition
public class VanillaPayoffStyle extends PayoffStyle {

  /** Serialization version. */
  private static final long serialVersionUID = 1L;

  /**
   * Creates an instance.
   */
  public VanillaPayoffStyle() {
  }

  //-------------------------------------------------------------------------
  @Override
  public <T> T accept(PayoffStyleVisitor<T> visitor) {
    return visitor.visitVanillaPayoffStyle(this);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code VanillaPayoffStyle}.
   * @return the meta-bean, not null
   */
  public static VanillaPayoffStyle.Meta meta() {
    return VanillaPayoffStyle.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(VanillaPayoffStyle.Meta.INSTANCE);
  }

  @Override
  public VanillaPayoffStyle.Meta metaBean() {
    return VanillaPayoffStyle.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code VanillaPayoffStyle}.
   */
  public static class Meta extends PayoffStyle.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap());

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    public BeanBuilder<? extends VanillaPayoffStyle> builder() {
      return new DirectBeanBuilder<VanillaPayoffStyle>(new VanillaPayoffStyle());
    }

    @Override
    public Class<? extends VanillaPayoffStyle> beanType() {
      return VanillaPayoffStyle.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
