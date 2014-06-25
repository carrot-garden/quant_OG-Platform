/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.isda.credit;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableConstructor;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.threeten.bp.LocalDate;

import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Sets;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.Tenor;

/**
 * Data used to build an ISDA yield curve. The cash and swap data maps define the market data values and
 * imply the term structure of the curve. Other fields define the conventions which are applied 
 * when calibrating the curve. 
 */
@BeanDefinition
public class YieldCurveData implements ImmutableBean {
  
  /**
   * Currency of the yield curve.
   */
  @PropertyDefinition(validate = "notNull")
  private final Currency _currency;
  
  /**
   * Day count for the yield curve.
   */
  @PropertyDefinition(validate = "notNull")
  private final DayCount _curveDayCount;
  
  /**
   * Business day convention (aka bad day convention).
   */
  @PropertyDefinition(validate = "notNull")
  private final BusinessDayConvention _curveBusinessDayConvention;
  
  /**
   * Day count for the money market instruments.
   */
  @PropertyDefinition(validate = "notNull")
  private final DayCount _cashDayCount;
  
  /**
   * Day count for the swaps.
   */
  @PropertyDefinition(validate = "notNull")
  private final DayCount _swapDayCount;
  
  /**
   * Yield curve spot date. This is the start date for instruments
   * defined on the curve and used in calibration.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate _spotDate;
  
  /**
   * Calendar for use in calibration.
   */
  @PropertyDefinition(validate = "notNull")
  private final Calendar _calendar;
  
  /**
   * Contract payment interval for fixed legs on swap instruments.
   */
  @PropertyDefinition(validate = "notNull")
  private final Tenor _swapFixedLegInterval;
  
  /**
   * Tenor-rate mappings for cash data.
   * Rates should be in fractions (e.g. 0.01 = 1%).
   * The set of tenors must be distinct from the swap tenors.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSortedMap<Tenor, Double> _cashData;
  
  /**
   * Tenor-rate mappings for swap data.
   * Rates should be in fractions (e.g. 0.01 = 1%).
   * The set of tenors must be distinct from the cash tenors.
   */
  @PropertyDefinition(validate = "notNull")
  private final ImmutableSortedMap<Tenor, Double> _swapData;
  
  /**
   * Restricted constructor.
   * @param builder  the builder to copy from, not null
   */
  @ImmutableConstructor
  protected YieldCurveData(YieldCurveData.Builder builder) {
    JodaBeanUtils.notNull(builder._currency, "currency");
    JodaBeanUtils.notNull(builder._curveDayCount, "curveDayCount");
    JodaBeanUtils.notNull(builder._curveBusinessDayConvention, "curveBusinessDayConvention");
    JodaBeanUtils.notNull(builder._cashDayCount, "cashDayCount");
    JodaBeanUtils.notNull(builder._swapDayCount, "swapDayCount");
    JodaBeanUtils.notNull(builder._spotDate, "spotDate");
    JodaBeanUtils.notNull(builder._calendar, "calendar");
    JodaBeanUtils.notNull(builder._swapFixedLegInterval, "swapFixedLegInterval");
    JodaBeanUtils.notNull(builder._cashData, "cashData");
    JodaBeanUtils.notNull(builder._swapData, "swapData");
    this._currency = builder._currency;
    this._curveDayCount = builder._curveDayCount;
    this._curveBusinessDayConvention = builder._curveBusinessDayConvention;
    this._cashDayCount = builder._cashDayCount;
    this._swapDayCount = builder._swapDayCount;
    this._spotDate = builder._spotDate;
    this._calendar = builder._calendar;
    this._swapFixedLegInterval = builder._swapFixedLegInterval;
    this._cashData = ImmutableSortedMap.copyOfSorted(builder._cashData);
    this._swapData = ImmutableSortedMap.copyOfSorted(builder._swapData);
    
    //extra checks
    ArgumentChecker.isTrue(Sets.intersection(_cashData.keySet(), _swapData.keySet()).isEmpty(), 
                           "Tenors should not overlap");
  }

  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code YieldCurveData}.
   * @return the meta-bean, not null
   */
  public static YieldCurveData.Meta meta() {
    return YieldCurveData.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(YieldCurveData.Meta.INSTANCE);
  }

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static YieldCurveData.Builder builder() {
    return new YieldCurveData.Builder();
  }

  @Override
  public YieldCurveData.Meta metaBean() {
    return YieldCurveData.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets currency of the yield curve.
   * @return the value of the property, not null
   */
  public Currency getCurrency() {
    return _currency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets day count for the yield curve.
   * @return the value of the property, not null
   */
  public DayCount getCurveDayCount() {
    return _curveDayCount;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets business day convention (aka bad day convention).
   * @return the value of the property, not null
   */
  public BusinessDayConvention getCurveBusinessDayConvention() {
    return _curveBusinessDayConvention;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets day count for the money market instruments.
   * @return the value of the property, not null
   */
  public DayCount getCashDayCount() {
    return _cashDayCount;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets day count for the swaps.
   * @return the value of the property, not null
   */
  public DayCount getSwapDayCount() {
    return _swapDayCount;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets yield curve spot date. This is the start date for instruments
   * defined on the curve and used in calibration.
   * @return the value of the property, not null
   */
  public LocalDate getSpotDate() {
    return _spotDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets calendar for use in calibration.
   * @return the value of the property, not null
   */
  public Calendar getCalendar() {
    return _calendar;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets contract payment interval for fixed legs on swap instruments.
   * @return the value of the property, not null
   */
  public Tenor getSwapFixedLegInterval() {
    return _swapFixedLegInterval;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets tenor-rate mappings for cash data.
   * Rates should be in fractions (e.g. 0.01 = 1%).
   * The set of tenors must be distinct from the swap tenors.
   * @return the value of the property, not null
   */
  public ImmutableSortedMap<Tenor, Double> getCashData() {
    return _cashData;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets tenor-rate mappings for swap data.
   * Rates should be in fractions (e.g. 0.01 = 1%).
   * The set of tenors must be distinct from the cash tenors.
   * @return the value of the property, not null
   */
  public ImmutableSortedMap<Tenor, Double> getSwapData() {
    return _swapData;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public YieldCurveData clone() {
    return this;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      YieldCurveData other = (YieldCurveData) obj;
      return JodaBeanUtils.equal(getCurrency(), other.getCurrency()) &&
          JodaBeanUtils.equal(getCurveDayCount(), other.getCurveDayCount()) &&
          JodaBeanUtils.equal(getCurveBusinessDayConvention(), other.getCurveBusinessDayConvention()) &&
          JodaBeanUtils.equal(getCashDayCount(), other.getCashDayCount()) &&
          JodaBeanUtils.equal(getSwapDayCount(), other.getSwapDayCount()) &&
          JodaBeanUtils.equal(getSpotDate(), other.getSpotDate()) &&
          JodaBeanUtils.equal(getCalendar(), other.getCalendar()) &&
          JodaBeanUtils.equal(getSwapFixedLegInterval(), other.getSwapFixedLegInterval()) &&
          JodaBeanUtils.equal(getCashData(), other.getCashData()) &&
          JodaBeanUtils.equal(getSwapData(), other.getSwapData());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurrency());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurveDayCount());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCurveBusinessDayConvention());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCashDayCount());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSwapDayCount());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSpotDate());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCalendar());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSwapFixedLegInterval());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCashData());
    hash += hash * 31 + JodaBeanUtils.hashCode(getSwapData());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(352);
    buf.append("YieldCurveData{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("currency").append('=').append(JodaBeanUtils.toString(getCurrency())).append(',').append(' ');
    buf.append("curveDayCount").append('=').append(JodaBeanUtils.toString(getCurveDayCount())).append(',').append(' ');
    buf.append("curveBusinessDayConvention").append('=').append(JodaBeanUtils.toString(getCurveBusinessDayConvention())).append(',').append(' ');
    buf.append("cashDayCount").append('=').append(JodaBeanUtils.toString(getCashDayCount())).append(',').append(' ');
    buf.append("swapDayCount").append('=').append(JodaBeanUtils.toString(getSwapDayCount())).append(',').append(' ');
    buf.append("spotDate").append('=').append(JodaBeanUtils.toString(getSpotDate())).append(',').append(' ');
    buf.append("calendar").append('=').append(JodaBeanUtils.toString(getCalendar())).append(',').append(' ');
    buf.append("swapFixedLegInterval").append('=').append(JodaBeanUtils.toString(getSwapFixedLegInterval())).append(',').append(' ');
    buf.append("cashData").append('=').append(JodaBeanUtils.toString(getCashData())).append(',').append(' ');
    buf.append("swapData").append('=').append(JodaBeanUtils.toString(getSwapData())).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code YieldCurveData}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> _currency = DirectMetaProperty.ofImmutable(
        this, "currency", YieldCurveData.class, Currency.class);
    /**
     * The meta-property for the {@code curveDayCount} property.
     */
    private final MetaProperty<DayCount> _curveDayCount = DirectMetaProperty.ofImmutable(
        this, "curveDayCount", YieldCurveData.class, DayCount.class);
    /**
     * The meta-property for the {@code curveBusinessDayConvention} property.
     */
    private final MetaProperty<BusinessDayConvention> _curveBusinessDayConvention = DirectMetaProperty.ofImmutable(
        this, "curveBusinessDayConvention", YieldCurveData.class, BusinessDayConvention.class);
    /**
     * The meta-property for the {@code cashDayCount} property.
     */
    private final MetaProperty<DayCount> _cashDayCount = DirectMetaProperty.ofImmutable(
        this, "cashDayCount", YieldCurveData.class, DayCount.class);
    /**
     * The meta-property for the {@code swapDayCount} property.
     */
    private final MetaProperty<DayCount> _swapDayCount = DirectMetaProperty.ofImmutable(
        this, "swapDayCount", YieldCurveData.class, DayCount.class);
    /**
     * The meta-property for the {@code spotDate} property.
     */
    private final MetaProperty<LocalDate> _spotDate = DirectMetaProperty.ofImmutable(
        this, "spotDate", YieldCurveData.class, LocalDate.class);
    /**
     * The meta-property for the {@code calendar} property.
     */
    private final MetaProperty<Calendar> _calendar = DirectMetaProperty.ofImmutable(
        this, "calendar", YieldCurveData.class, Calendar.class);
    /**
     * The meta-property for the {@code swapFixedLegInterval} property.
     */
    private final MetaProperty<Tenor> _swapFixedLegInterval = DirectMetaProperty.ofImmutable(
        this, "swapFixedLegInterval", YieldCurveData.class, Tenor.class);
    /**
     * The meta-property for the {@code cashData} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSortedMap<Tenor, Double>> _cashData = DirectMetaProperty.ofImmutable(
        this, "cashData", YieldCurveData.class, (Class) ImmutableSortedMap.class);
    /**
     * The meta-property for the {@code swapData} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ImmutableSortedMap<Tenor, Double>> _swapData = DirectMetaProperty.ofImmutable(
        this, "swapData", YieldCurveData.class, (Class) ImmutableSortedMap.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "currency",
        "curveDayCount",
        "curveBusinessDayConvention",
        "cashDayCount",
        "swapDayCount",
        "spotDate",
        "calendar",
        "swapFixedLegInterval",
        "cashData",
        "swapData");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return _currency;
        case -1661418270:  // curveDayCount
          return _curveDayCount;
        case -796231010:  // curveBusinessDayConvention
          return _curveBusinessDayConvention;
        case -762266842:  // cashDayCount
          return _cashDayCount;
        case -1907625594:  // swapDayCount
          return _swapDayCount;
        case -1831990320:  // spotDate
          return _spotDate;
        case -178324674:  // calendar
          return _calendar;
        case 1403919506:  // swapFixedLegInterval
          return _swapFixedLegInterval;
        case 23596413:  // cashData
          return _cashData;
        case -318934051:  // swapData
          return _swapData;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public YieldCurveData.Builder builder() {
      return new YieldCurveData.Builder();
    }

    @Override
    public Class<? extends YieldCurveData> beanType() {
      return YieldCurveData.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Currency> currency() {
      return _currency;
    }

    /**
     * The meta-property for the {@code curveDayCount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DayCount> curveDayCount() {
      return _curveDayCount;
    }

    /**
     * The meta-property for the {@code curveBusinessDayConvention} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<BusinessDayConvention> curveBusinessDayConvention() {
      return _curveBusinessDayConvention;
    }

    /**
     * The meta-property for the {@code cashDayCount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DayCount> cashDayCount() {
      return _cashDayCount;
    }

    /**
     * The meta-property for the {@code swapDayCount} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<DayCount> swapDayCount() {
      return _swapDayCount;
    }

    /**
     * The meta-property for the {@code spotDate} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<LocalDate> spotDate() {
      return _spotDate;
    }

    /**
     * The meta-property for the {@code calendar} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Calendar> calendar() {
      return _calendar;
    }

    /**
     * The meta-property for the {@code swapFixedLegInterval} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Tenor> swapFixedLegInterval() {
      return _swapFixedLegInterval;
    }

    /**
     * The meta-property for the {@code cashData} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ImmutableSortedMap<Tenor, Double>> cashData() {
      return _cashData;
    }

    /**
     * The meta-property for the {@code swapData} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ImmutableSortedMap<Tenor, Double>> swapData() {
      return _swapData;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return ((YieldCurveData) bean).getCurrency();
        case -1661418270:  // curveDayCount
          return ((YieldCurveData) bean).getCurveDayCount();
        case -796231010:  // curveBusinessDayConvention
          return ((YieldCurveData) bean).getCurveBusinessDayConvention();
        case -762266842:  // cashDayCount
          return ((YieldCurveData) bean).getCashDayCount();
        case -1907625594:  // swapDayCount
          return ((YieldCurveData) bean).getSwapDayCount();
        case -1831990320:  // spotDate
          return ((YieldCurveData) bean).getSpotDate();
        case -178324674:  // calendar
          return ((YieldCurveData) bean).getCalendar();
        case 1403919506:  // swapFixedLegInterval
          return ((YieldCurveData) bean).getSwapFixedLegInterval();
        case 23596413:  // cashData
          return ((YieldCurveData) bean).getCashData();
        case -318934051:  // swapData
          return ((YieldCurveData) bean).getSwapData();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code YieldCurveData}.
   */
  public static class Builder extends DirectFieldsBeanBuilder<YieldCurveData> {

    private Currency _currency;
    private DayCount _curveDayCount;
    private BusinessDayConvention _curveBusinessDayConvention;
    private DayCount _cashDayCount;
    private DayCount _swapDayCount;
    private LocalDate _spotDate;
    private Calendar _calendar;
    private Tenor _swapFixedLegInterval;
    private SortedMap<Tenor, Double> _cashData = new TreeMap<Tenor, Double>();
    private SortedMap<Tenor, Double> _swapData = new TreeMap<Tenor, Double>();

    /**
     * Restricted constructor.
     */
    protected Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    protected Builder(YieldCurveData beanToCopy) {
      this._currency = beanToCopy.getCurrency();
      this._curveDayCount = beanToCopy.getCurveDayCount();
      this._curveBusinessDayConvention = beanToCopy.getCurveBusinessDayConvention();
      this._cashDayCount = beanToCopy.getCashDayCount();
      this._swapDayCount = beanToCopy.getSwapDayCount();
      this._spotDate = beanToCopy.getSpotDate();
      this._calendar = beanToCopy.getCalendar();
      this._swapFixedLegInterval = beanToCopy.getSwapFixedLegInterval();
      this._cashData = new TreeMap<Tenor, Double>(beanToCopy.getCashData());
      this._swapData = new TreeMap<Tenor, Double>(beanToCopy.getSwapData());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          return _currency;
        case -1661418270:  // curveDayCount
          return _curveDayCount;
        case -796231010:  // curveBusinessDayConvention
          return _curveBusinessDayConvention;
        case -762266842:  // cashDayCount
          return _cashDayCount;
        case -1907625594:  // swapDayCount
          return _swapDayCount;
        case -1831990320:  // spotDate
          return _spotDate;
        case -178324674:  // calendar
          return _calendar;
        case 1403919506:  // swapFixedLegInterval
          return _swapFixedLegInterval;
        case 23596413:  // cashData
          return _cashData;
        case -318934051:  // swapData
          return _swapData;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 575402001:  // currency
          this._currency = (Currency) newValue;
          break;
        case -1661418270:  // curveDayCount
          this._curveDayCount = (DayCount) newValue;
          break;
        case -796231010:  // curveBusinessDayConvention
          this._curveBusinessDayConvention = (BusinessDayConvention) newValue;
          break;
        case -762266842:  // cashDayCount
          this._cashDayCount = (DayCount) newValue;
          break;
        case -1907625594:  // swapDayCount
          this._swapDayCount = (DayCount) newValue;
          break;
        case -1831990320:  // spotDate
          this._spotDate = (LocalDate) newValue;
          break;
        case -178324674:  // calendar
          this._calendar = (Calendar) newValue;
          break;
        case 1403919506:  // swapFixedLegInterval
          this._swapFixedLegInterval = (Tenor) newValue;
          break;
        case 23596413:  // cashData
          this._cashData = (SortedMap<Tenor, Double>) newValue;
          break;
        case -318934051:  // swapData
          this._swapData = (SortedMap<Tenor, Double>) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setString(String propertyName, String value) {
      setString(meta().metaProperty(propertyName), value);
      return this;
    }

    @Override
    public Builder setString(MetaProperty<?> property, String value) {
      super.set(property, value);
      return this;
    }

    @Override
    public Builder setAll(Map<String, ? extends Object> propertyValueMap) {
      super.setAll(propertyValueMap);
      return this;
    }

    @Override
    public YieldCurveData build() {
      return new YieldCurveData(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the {@code currency} property in the builder.
     * @param currency  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder currency(Currency currency) {
      JodaBeanUtils.notNull(currency, "currency");
      this._currency = currency;
      return this;
    }

    /**
     * Sets the {@code curveDayCount} property in the builder.
     * @param curveDayCount  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder curveDayCount(DayCount curveDayCount) {
      JodaBeanUtils.notNull(curveDayCount, "curveDayCount");
      this._curveDayCount = curveDayCount;
      return this;
    }

    /**
     * Sets the {@code curveBusinessDayConvention} property in the builder.
     * @param curveBusinessDayConvention  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder curveBusinessDayConvention(BusinessDayConvention curveBusinessDayConvention) {
      JodaBeanUtils.notNull(curveBusinessDayConvention, "curveBusinessDayConvention");
      this._curveBusinessDayConvention = curveBusinessDayConvention;
      return this;
    }

    /**
     * Sets the {@code cashDayCount} property in the builder.
     * @param cashDayCount  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder cashDayCount(DayCount cashDayCount) {
      JodaBeanUtils.notNull(cashDayCount, "cashDayCount");
      this._cashDayCount = cashDayCount;
      return this;
    }

    /**
     * Sets the {@code swapDayCount} property in the builder.
     * @param swapDayCount  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder swapDayCount(DayCount swapDayCount) {
      JodaBeanUtils.notNull(swapDayCount, "swapDayCount");
      this._swapDayCount = swapDayCount;
      return this;
    }

    /**
     * Sets the {@code spotDate} property in the builder.
     * @param spotDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder spotDate(LocalDate spotDate) {
      JodaBeanUtils.notNull(spotDate, "spotDate");
      this._spotDate = spotDate;
      return this;
    }

    /**
     * Sets the {@code calendar} property in the builder.
     * @param calendar  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder calendar(Calendar calendar) {
      JodaBeanUtils.notNull(calendar, "calendar");
      this._calendar = calendar;
      return this;
    }

    /**
     * Sets the {@code swapFixedLegInterval} property in the builder.
     * @param swapFixedLegInterval  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder swapFixedLegInterval(Tenor swapFixedLegInterval) {
      JodaBeanUtils.notNull(swapFixedLegInterval, "swapFixedLegInterval");
      this._swapFixedLegInterval = swapFixedLegInterval;
      return this;
    }

    /**
     * Sets the {@code cashData} property in the builder.
     * @param cashData  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder cashData(SortedMap<Tenor, Double> cashData) {
      JodaBeanUtils.notNull(cashData, "cashData");
      this._cashData = cashData;
      return this;
    }

    /**
     * Sets the {@code swapData} property in the builder.
     * @param swapData  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder swapData(SortedMap<Tenor, Double> swapData) {
      JodaBeanUtils.notNull(swapData, "swapData");
      this._swapData = swapData;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(352);
      buf.append("YieldCurveData.Builder{");
      int len = buf.length();
      toString(buf);
      if (buf.length() > len) {
        buf.setLength(buf.length() - 2);
      }
      buf.append('}');
      return buf.toString();
    }

    protected void toString(StringBuilder buf) {
      buf.append("currency").append('=').append(JodaBeanUtils.toString(_currency)).append(',').append(' ');
      buf.append("curveDayCount").append('=').append(JodaBeanUtils.toString(_curveDayCount)).append(',').append(' ');
      buf.append("curveBusinessDayConvention").append('=').append(JodaBeanUtils.toString(_curveBusinessDayConvention)).append(',').append(' ');
      buf.append("cashDayCount").append('=').append(JodaBeanUtils.toString(_cashDayCount)).append(',').append(' ');
      buf.append("swapDayCount").append('=').append(JodaBeanUtils.toString(_swapDayCount)).append(',').append(' ');
      buf.append("spotDate").append('=').append(JodaBeanUtils.toString(_spotDate)).append(',').append(' ');
      buf.append("calendar").append('=').append(JodaBeanUtils.toString(_calendar)).append(',').append(' ');
      buf.append("swapFixedLegInterval").append('=').append(JodaBeanUtils.toString(_swapFixedLegInterval)).append(',').append(' ');
      buf.append("cashData").append('=').append(JodaBeanUtils.toString(_cashData)).append(',').append(' ');
      buf.append("swapData").append('=').append(JodaBeanUtils.toString(_swapData)).append(',').append(' ');
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
