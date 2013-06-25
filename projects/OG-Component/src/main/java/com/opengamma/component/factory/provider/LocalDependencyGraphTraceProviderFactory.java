/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.component.factory.provider;

import java.util.LinkedHashMap;
import java.util.Map;

import org.fudgemsg.FudgeContext;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.component.factory.ComponentInfoAttributes;
import com.opengamma.engine.function.CompiledFunctionService;
import com.opengamma.engine.function.exclusion.FunctionExclusionGroups;
import com.opengamma.engine.function.resolver.FunctionResolver;
import com.opengamma.engine.marketdata.resolver.MarketDataProviderResolver;
import com.opengamma.financial.depgraph.provider.DependencyGraphTraceProvider;
import com.opengamma.financial.depgraph.provider.LocalDependencyGraphTraceProvider;
import com.opengamma.financial.depgraph.provider.RemoteDependencyGraphTraceProvider;
import com.opengamma.financial.depgraph.rest.DependencyGraphBuilderResourceContextBean;
import com.opengamma.financial.depgraph.rest.DependencyGraphTraceBuilder;
import com.opengamma.financial.depgraph.rest.DependencyGraphTraceProviderResource;
import com.opengamma.util.fudgemsg.OpenGammaFudgeContext;

/**
 * Initializes the {@link LocalDependencyGraphTraceProvider} and its corresponding
 * REST interface if configured.
 */
@BeanDefinition
public class LocalDependencyGraphTraceProviderFactory extends AbstractComponentFactory {

  /**
   * The classifier that the factory should publish under. The Spring config must create this.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;

  /**
   * The flag determining whether the component should be published by REST (default true).
   */
  @PropertyDefinition
  private boolean _publishRest = true;

  /**
   * The fudge context.
   */
  @PropertyDefinition(validate = "notNull")
  private FudgeContext _fudgeContext = OpenGammaFudgeContext.getInstance();

  /**
   * The {@link CompiledFunctionService}
   */
  @PropertyDefinition(validate = "notNull")
  private CompiledFunctionService _compiledFunctionService;

  /**
   * The {@link FunctionResolver}
   */
  @PropertyDefinition(validate = "notNull")
  private FunctionResolver _functionResolver;

  /**
   * The {@link FunctionExclusionGroups}
   */
  @PropertyDefinition(validate = "notNull")
  private FunctionExclusionGroups _functionExclusionGroups;
  /**
   * The market data (for debugging).
   */
  @PropertyDefinition(validate = "notNull")
  private MarketDataProviderResolver _marketDataProviderResolver;

  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {

    DependencyGraphBuilderResourceContextBean builderContext = new DependencyGraphBuilderResourceContextBean();
    builderContext.setCompiledFunctionService(getCompiledFunctionService());
    builderContext.setFunctionResolver(getFunctionResolver());
    builderContext.setFunctionExclusionGroups(getFunctionExclusionGroups());
    builderContext.setMarketDataProviderResolver(getMarketDataProviderResolver());

    DependencyGraphTraceBuilder traceBuilder = new DependencyGraphTraceBuilder(builderContext);
    LocalDependencyGraphTraceProvider provider = new LocalDependencyGraphTraceProvider(traceBuilder);
    ComponentInfo info = new ComponentInfo(DependencyGraphTraceProvider.class, getClassifier());
    info.addAttribute(ComponentInfoAttributes.LEVEL, 1);
    info.addAttribute(ComponentInfoAttributes.REMOTE_CLIENT_JAVA, RemoteDependencyGraphTraceProvider.class);

    repo.registerComponent(info, provider);

    if (isPublishRest()) {
      //final ComponentInfo infoDGB = new ComponentInfo(DependencyGraphTraceProviderResource.class, getClassifier());
      //repo.registerComponent(infoDGB, restResource);
      repo.getRestComponents().publish(info, new DependencyGraphTraceProviderResource(provider, getFudgeContext()));
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code LocalDependencyGraphTraceProviderFactory}.
   * @return the meta-bean, not null
   */
  public static LocalDependencyGraphTraceProviderFactory.Meta meta() {
    return LocalDependencyGraphTraceProviderFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(LocalDependencyGraphTraceProviderFactory.Meta.INSTANCE);
  }

  @Override
  public LocalDependencyGraphTraceProviderFactory.Meta metaBean() {
    return LocalDependencyGraphTraceProviderFactory.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        return getClassifier();
      case -614707837:  // publishRest
        return isPublishRest();
      case -917704420:  // fudgeContext
        return getFudgeContext();
      case -1551147284:  // compiledFunctionService
        return getCompiledFunctionService();
      case 1517042686:  // functionResolver
        return getFunctionResolver();
      case 839813706:  // functionExclusionGroups
        return getFunctionExclusionGroups();
      case 56203069:  // marketDataProviderResolver
        return getMarketDataProviderResolver();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case -281470431:  // classifier
        setClassifier((String) newValue);
        return;
      case -614707837:  // publishRest
        setPublishRest((Boolean) newValue);
        return;
      case -917704420:  // fudgeContext
        setFudgeContext((FudgeContext) newValue);
        return;
      case -1551147284:  // compiledFunctionService
        setCompiledFunctionService((CompiledFunctionService) newValue);
        return;
      case 1517042686:  // functionResolver
        setFunctionResolver((FunctionResolver) newValue);
        return;
      case 839813706:  // functionExclusionGroups
        setFunctionExclusionGroups((FunctionExclusionGroups) newValue);
        return;
      case 56203069:  // marketDataProviderResolver
        setMarketDataProviderResolver((MarketDataProviderResolver) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_classifier, "classifier");
    JodaBeanUtils.notNull(_fudgeContext, "fudgeContext");
    JodaBeanUtils.notNull(_compiledFunctionService, "compiledFunctionService");
    JodaBeanUtils.notNull(_functionResolver, "functionResolver");
    JodaBeanUtils.notNull(_functionExclusionGroups, "functionExclusionGroups");
    JodaBeanUtils.notNull(_marketDataProviderResolver, "marketDataProviderResolver");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      LocalDependencyGraphTraceProviderFactory other = (LocalDependencyGraphTraceProviderFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          JodaBeanUtils.equal(isPublishRest(), other.isPublishRest()) &&
          JodaBeanUtils.equal(getFudgeContext(), other.getFudgeContext()) &&
          JodaBeanUtils.equal(getCompiledFunctionService(), other.getCompiledFunctionService()) &&
          JodaBeanUtils.equal(getFunctionResolver(), other.getFunctionResolver()) &&
          JodaBeanUtils.equal(getFunctionExclusionGroups(), other.getFunctionExclusionGroups()) &&
          JodaBeanUtils.equal(getMarketDataProviderResolver(), other.getMarketDataProviderResolver()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(isPublishRest());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFudgeContext());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCompiledFunctionService());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFunctionResolver());
    hash += hash * 31 + JodaBeanUtils.hashCode(getFunctionExclusionGroups());
    hash += hash * 31 + JodaBeanUtils.hashCode(getMarketDataProviderResolver());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under. The Spring config must create this.
   * @return the value of the property, not null
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier that the factory should publish under. The Spring config must create this.
   * @param classifier  the new value of the property, not null
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notNull(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the flag determining whether the component should be published by REST (default true).
   * @return the value of the property
   */
  public boolean isPublishRest() {
    return _publishRest;
  }

  /**
   * Sets the flag determining whether the component should be published by REST (default true).
   * @param publishRest  the new value of the property
   */
  public void setPublishRest(boolean publishRest) {
    this._publishRest = publishRest;
  }

  /**
   * Gets the the {@code publishRest} property.
   * @return the property, not null
   */
  public final Property<Boolean> publishRest() {
    return metaBean().publishRest().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the fudge context.
   * @return the value of the property, not null
   */
  public FudgeContext getFudgeContext() {
    return _fudgeContext;
  }

  /**
   * Sets the fudge context.
   * @param fudgeContext  the new value of the property, not null
   */
  public void setFudgeContext(FudgeContext fudgeContext) {
    JodaBeanUtils.notNull(fudgeContext, "fudgeContext");
    this._fudgeContext = fudgeContext;
  }

  /**
   * Gets the the {@code fudgeContext} property.
   * @return the property, not null
   */
  public final Property<FudgeContext> fudgeContext() {
    return metaBean().fudgeContext().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the {@link CompiledFunctionService}
   * @return the value of the property, not null
   */
  public CompiledFunctionService getCompiledFunctionService() {
    return _compiledFunctionService;
  }

  /**
   * Sets the {@link CompiledFunctionService}
   * @param compiledFunctionService  the new value of the property, not null
   */
  public void setCompiledFunctionService(CompiledFunctionService compiledFunctionService) {
    JodaBeanUtils.notNull(compiledFunctionService, "compiledFunctionService");
    this._compiledFunctionService = compiledFunctionService;
  }

  /**
   * Gets the the {@code compiledFunctionService} property.
   * @return the property, not null
   */
  public final Property<CompiledFunctionService> compiledFunctionService() {
    return metaBean().compiledFunctionService().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the {@link FunctionResolver}
   * @return the value of the property, not null
   */
  public FunctionResolver getFunctionResolver() {
    return _functionResolver;
  }

  /**
   * Sets the {@link FunctionResolver}
   * @param functionResolver  the new value of the property, not null
   */
  public void setFunctionResolver(FunctionResolver functionResolver) {
    JodaBeanUtils.notNull(functionResolver, "functionResolver");
    this._functionResolver = functionResolver;
  }

  /**
   * Gets the the {@code functionResolver} property.
   * @return the property, not null
   */
  public final Property<FunctionResolver> functionResolver() {
    return metaBean().functionResolver().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the {@link FunctionExclusionGroups}
   * @return the value of the property, not null
   */
  public FunctionExclusionGroups getFunctionExclusionGroups() {
    return _functionExclusionGroups;
  }

  /**
   * Sets the {@link FunctionExclusionGroups}
   * @param functionExclusionGroups  the new value of the property, not null
   */
  public void setFunctionExclusionGroups(FunctionExclusionGroups functionExclusionGroups) {
    JodaBeanUtils.notNull(functionExclusionGroups, "functionExclusionGroups");
    this._functionExclusionGroups = functionExclusionGroups;
  }

  /**
   * Gets the the {@code functionExclusionGroups} property.
   * @return the property, not null
   */
  public final Property<FunctionExclusionGroups> functionExclusionGroups() {
    return metaBean().functionExclusionGroups().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market data (for debugging).
   * @return the value of the property, not null
   */
  public MarketDataProviderResolver getMarketDataProviderResolver() {
    return _marketDataProviderResolver;
  }

  /**
   * Sets the market data (for debugging).
   * @param marketDataProviderResolver  the new value of the property, not null
   */
  public void setMarketDataProviderResolver(MarketDataProviderResolver marketDataProviderResolver) {
    JodaBeanUtils.notNull(marketDataProviderResolver, "marketDataProviderResolver");
    this._marketDataProviderResolver = marketDataProviderResolver;
  }

  /**
   * Gets the the {@code marketDataProviderResolver} property.
   * @return the property, not null
   */
  public final Property<MarketDataProviderResolver> marketDataProviderResolver() {
    return metaBean().marketDataProviderResolver().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code LocalDependencyGraphTraceProviderFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", LocalDependencyGraphTraceProviderFactory.class, String.class);
    /**
     * The meta-property for the {@code publishRest} property.
     */
    private final MetaProperty<Boolean> _publishRest = DirectMetaProperty.ofReadWrite(
        this, "publishRest", LocalDependencyGraphTraceProviderFactory.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code fudgeContext} property.
     */
    private final MetaProperty<FudgeContext> _fudgeContext = DirectMetaProperty.ofReadWrite(
        this, "fudgeContext", LocalDependencyGraphTraceProviderFactory.class, FudgeContext.class);
    /**
     * The meta-property for the {@code compiledFunctionService} property.
     */
    private final MetaProperty<CompiledFunctionService> _compiledFunctionService = DirectMetaProperty.ofReadWrite(
        this, "compiledFunctionService", LocalDependencyGraphTraceProviderFactory.class, CompiledFunctionService.class);
    /**
     * The meta-property for the {@code functionResolver} property.
     */
    private final MetaProperty<FunctionResolver> _functionResolver = DirectMetaProperty.ofReadWrite(
        this, "functionResolver", LocalDependencyGraphTraceProviderFactory.class, FunctionResolver.class);
    /**
     * The meta-property for the {@code functionExclusionGroups} property.
     */
    private final MetaProperty<FunctionExclusionGroups> _functionExclusionGroups = DirectMetaProperty.ofReadWrite(
        this, "functionExclusionGroups", LocalDependencyGraphTraceProviderFactory.class, FunctionExclusionGroups.class);
    /**
     * The meta-property for the {@code marketDataProviderResolver} property.
     */
    private final MetaProperty<MarketDataProviderResolver> _marketDataProviderResolver = DirectMetaProperty.ofReadWrite(
        this, "marketDataProviderResolver", LocalDependencyGraphTraceProviderFactory.class, MarketDataProviderResolver.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "publishRest",
        "fudgeContext",
        "compiledFunctionService",
        "functionResolver",
        "functionExclusionGroups",
        "marketDataProviderResolver");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
        case -614707837:  // publishRest
          return _publishRest;
        case -917704420:  // fudgeContext
          return _fudgeContext;
        case -1551147284:  // compiledFunctionService
          return _compiledFunctionService;
        case 1517042686:  // functionResolver
          return _functionResolver;
        case 839813706:  // functionExclusionGroups
          return _functionExclusionGroups;
        case 56203069:  // marketDataProviderResolver
          return _marketDataProviderResolver;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends LocalDependencyGraphTraceProviderFactory> builder() {
      return new DirectBeanBuilder<LocalDependencyGraphTraceProviderFactory>(new LocalDependencyGraphTraceProviderFactory());
    }

    @Override
    public Class<? extends LocalDependencyGraphTraceProviderFactory> beanType() {
      return LocalDependencyGraphTraceProviderFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    /**
     * The meta-property for the {@code publishRest} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> publishRest() {
      return _publishRest;
    }

    /**
     * The meta-property for the {@code fudgeContext} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<FudgeContext> fudgeContext() {
      return _fudgeContext;
    }

    /**
     * The meta-property for the {@code compiledFunctionService} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<CompiledFunctionService> compiledFunctionService() {
      return _compiledFunctionService;
    }

    /**
     * The meta-property for the {@code functionResolver} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<FunctionResolver> functionResolver() {
      return _functionResolver;
    }

    /**
     * The meta-property for the {@code functionExclusionGroups} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<FunctionExclusionGroups> functionExclusionGroups() {
      return _functionExclusionGroups;
    }

    /**
     * The meta-property for the {@code marketDataProviderResolver} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<MarketDataProviderResolver> marketDataProviderResolver() {
      return _marketDataProviderResolver;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
