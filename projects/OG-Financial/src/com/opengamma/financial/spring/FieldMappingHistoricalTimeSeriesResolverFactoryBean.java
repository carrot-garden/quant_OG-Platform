/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.spring;

import java.util.Collection;

import org.joda.beans.BeanDefinition;
import org.joda.beans.PropertyDefinition;

import com.opengamma.core.config.ConfigSource;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesMaster;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesResolver;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesSelector;
import com.opengamma.master.historicaltimeseries.impl.DefaultHistoricalTimeSeriesSelector;
import com.opengamma.master.historicaltimeseries.impl.FieldMappingHistoricalTimeSeriesResolver;
import com.opengamma.master.historicaltimeseries.impl.HistoricalTimeSeriesFieldAdjustmentMap;
import com.opengamma.util.spring.SpringFactoryBean;
import java.util.Map;
import org.joda.beans.BeanBuilder;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

/**
 * Spring factory bean for {@link FieldMappingHistoricalTimeSeriesResolver} 
 */
@BeanDefinition
public class FieldMappingHistoricalTimeSeriesResolverFactoryBean extends SpringFactoryBean<HistoricalTimeSeriesResolver> {
  
  /**
   * The field adjustment maps.
   */
  @PropertyDefinition
  private Collection<HistoricalTimeSeriesFieldAdjustmentMap> _fieldAdjustmentMaps;
  /**
   * The HTS master.
   */
  @PropertyDefinition
  private HistoricalTimeSeriesMaster _historicalTimeSeriesMaster;
  /**
   * The config source.
   */
  @PropertyDefinition
  private ConfigSource _configSource;
  
  /**
   * Creates an instance.
   */
  public FieldMappingHistoricalTimeSeriesResolverFactoryBean() {
    super(HistoricalTimeSeriesResolver.class);
  }

  //-------------------------------------------------------------------------
  @Override
  protected HistoricalTimeSeriesResolver createObject() {
    HistoricalTimeSeriesSelector selector = new DefaultHistoricalTimeSeriesSelector(getConfigSource());
    return new FieldMappingHistoricalTimeSeriesResolver(getFieldAdjustmentMaps(), selector, getHistoricalTimeSeriesMaster());
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code FieldMappingHistoricalTimeSeriesResolverFactoryBean}.
   * @return the meta-bean, not null
   */
  @SuppressWarnings("unchecked")
  public static FieldMappingHistoricalTimeSeriesResolverFactoryBean.Meta meta() {
    return FieldMappingHistoricalTimeSeriesResolverFactoryBean.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(FieldMappingHistoricalTimeSeriesResolverFactoryBean.Meta.INSTANCE);
  }

  @Override
  public FieldMappingHistoricalTimeSeriesResolverFactoryBean.Meta metaBean() {
    return FieldMappingHistoricalTimeSeriesResolverFactoryBean.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 813766974:  // fieldAdjustmentMaps
        return getFieldAdjustmentMaps();
      case 173967376:  // historicalTimeSeriesMaster
        return getHistoricalTimeSeriesMaster();
      case 195157501:  // configSource
        return getConfigSource();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 813766974:  // fieldAdjustmentMaps
        setFieldAdjustmentMaps((Collection<HistoricalTimeSeriesFieldAdjustmentMap>) newValue);
        return;
      case 173967376:  // historicalTimeSeriesMaster
        setHistoricalTimeSeriesMaster((HistoricalTimeSeriesMaster) newValue);
        return;
      case 195157501:  // configSource
        setConfigSource((ConfigSource) newValue);
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
      FieldMappingHistoricalTimeSeriesResolverFactoryBean other = (FieldMappingHistoricalTimeSeriesResolverFactoryBean) obj;
      return JodaBeanUtils.equal(getFieldAdjustmentMaps(), other.getFieldAdjustmentMaps()) &&
          JodaBeanUtils.equal(getHistoricalTimeSeriesMaster(), other.getHistoricalTimeSeriesMaster()) &&
          JodaBeanUtils.equal(getConfigSource(), other.getConfigSource()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getFieldAdjustmentMaps());
    hash += hash * 31 + JodaBeanUtils.hashCode(getHistoricalTimeSeriesMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getConfigSource());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the field adjustment maps.
   * @return the value of the property
   */
  public Collection<HistoricalTimeSeriesFieldAdjustmentMap> getFieldAdjustmentMaps() {
    return _fieldAdjustmentMaps;
  }

  /**
   * Sets the field adjustment maps.
   * @param fieldAdjustmentMaps  the new value of the property
   */
  public void setFieldAdjustmentMaps(Collection<HistoricalTimeSeriesFieldAdjustmentMap> fieldAdjustmentMaps) {
    this._fieldAdjustmentMaps = fieldAdjustmentMaps;
  }

  /**
   * Gets the the {@code fieldAdjustmentMaps} property.
   * @return the property, not null
   */
  public final Property<Collection<HistoricalTimeSeriesFieldAdjustmentMap>> fieldAdjustmentMaps() {
    return metaBean().fieldAdjustmentMaps().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the HTS master.
   * @return the value of the property
   */
  public HistoricalTimeSeriesMaster getHistoricalTimeSeriesMaster() {
    return _historicalTimeSeriesMaster;
  }

  /**
   * Sets the HTS master.
   * @param historicalTimeSeriesMaster  the new value of the property
   */
  public void setHistoricalTimeSeriesMaster(HistoricalTimeSeriesMaster historicalTimeSeriesMaster) {
    this._historicalTimeSeriesMaster = historicalTimeSeriesMaster;
  }

  /**
   * Gets the the {@code historicalTimeSeriesMaster} property.
   * @return the property, not null
   */
  public final Property<HistoricalTimeSeriesMaster> historicalTimeSeriesMaster() {
    return metaBean().historicalTimeSeriesMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the config source.
   * @return the value of the property
   */
  public ConfigSource getConfigSource() {
    return _configSource;
  }

  /**
   * Sets the config source.
   * @param configSource  the new value of the property
   */
  public void setConfigSource(ConfigSource configSource) {
    this._configSource = configSource;
  }

  /**
   * Gets the the {@code configSource} property.
   * @return the property, not null
   */
  public final Property<ConfigSource> configSource() {
    return metaBean().configSource().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code FieldMappingHistoricalTimeSeriesResolverFactoryBean}.
   */
  public static class Meta extends SpringFactoryBean.Meta<HistoricalTimeSeriesResolver> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code fieldAdjustmentMaps} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Collection<HistoricalTimeSeriesFieldAdjustmentMap>> _fieldAdjustmentMaps = DirectMetaProperty.ofReadWrite(
        this, "fieldAdjustmentMaps", FieldMappingHistoricalTimeSeriesResolverFactoryBean.class, (Class) Collection.class);
    /**
     * The meta-property for the {@code historicalTimeSeriesMaster} property.
     */
    private final MetaProperty<HistoricalTimeSeriesMaster> _historicalTimeSeriesMaster = DirectMetaProperty.ofReadWrite(
        this, "historicalTimeSeriesMaster", FieldMappingHistoricalTimeSeriesResolverFactoryBean.class, HistoricalTimeSeriesMaster.class);
    /**
     * The meta-property for the {@code configSource} property.
     */
    private final MetaProperty<ConfigSource> _configSource = DirectMetaProperty.ofReadWrite(
        this, "configSource", FieldMappingHistoricalTimeSeriesResolverFactoryBean.class, ConfigSource.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "fieldAdjustmentMaps",
        "historicalTimeSeriesMaster",
        "configSource");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 813766974:  // fieldAdjustmentMaps
          return _fieldAdjustmentMaps;
        case 173967376:  // historicalTimeSeriesMaster
          return _historicalTimeSeriesMaster;
        case 195157501:  // configSource
          return _configSource;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends FieldMappingHistoricalTimeSeriesResolverFactoryBean> builder() {
      return new DirectBeanBuilder<FieldMappingHistoricalTimeSeriesResolverFactoryBean>(new FieldMappingHistoricalTimeSeriesResolverFactoryBean());
    }

    @Override
    public Class<? extends FieldMappingHistoricalTimeSeriesResolverFactoryBean> beanType() {
      return FieldMappingHistoricalTimeSeriesResolverFactoryBean.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code fieldAdjustmentMaps} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Collection<HistoricalTimeSeriesFieldAdjustmentMap>> fieldAdjustmentMaps() {
      return _fieldAdjustmentMaps;
    }

    /**
     * The meta-property for the {@code historicalTimeSeriesMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<HistoricalTimeSeriesMaster> historicalTimeSeriesMaster() {
      return _historicalTimeSeriesMaster;
    }

    /**
     * The meta-property for the {@code configSource} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ConfigSource> configSource() {
      return _configSource;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
