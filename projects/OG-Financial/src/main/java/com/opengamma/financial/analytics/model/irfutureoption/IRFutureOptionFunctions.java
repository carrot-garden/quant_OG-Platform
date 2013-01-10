/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.irfutureoption;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.opengamma.engine.function.config.AbstractRepositoryConfigurationBean;
import com.opengamma.engine.function.config.FunctionConfiguration;
import com.opengamma.engine.function.config.RepositoryConfigurationSource;
import com.opengamma.financial.analytics.model.irfutureoption.IRFutureOptionFunctions.Defaults.CurrencyInfo;
import com.opengamma.financial.analytics.model.volatility.SmileFittingProperties;
import com.opengamma.financial.property.DefaultPropertyFunction.PriorityClass;
import com.opengamma.util.ArgumentChecker;

/**
 * Function repository configuration source for the functions contained in this package.
 */
public class IRFutureOptionFunctions extends AbstractRepositoryConfigurationBean {

  /**
   * Default instance of a repository configuration source exposing the functions from this package.
   *
   * @return the configuration source exposing functions from this package
   */
  public static RepositoryConfigurationSource instance() {
    return new IRFutureOptionFunctions().getObjectCreating();
  }

  public static RepositoryConfigurationSource deprecated() {
    return new Deprecated().getObjectCreating();
  }

  /**
   * Function repository configuration source for the deprecated functions contained in this package.
   */
  public static class Deprecated extends AbstractRepositoryConfigurationBean {

    @Override
    protected void addAllConfigurations(final List<FunctionConfiguration> functions) {
      // TODO: add functions
    }

  }

  public static RepositoryConfigurationSource defaults(final Map<String, CurrencyInfo> perCurrencyInfo) {
    final Defaults factory = new Defaults();
    factory.setPerCurrencyInfo(perCurrencyInfo);
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  /**
   * Function repository configuration source for the default functions contained in this package.
   */
  public static class Defaults extends AbstractRepositoryConfigurationBean {

    /**
     * Currency specific data.
     */
    public static class CurrencyInfo implements InitializingBean {

      private String _curveConfiguration;
      private String _surfaceName;
      private String _smileFittingMethod = SmileFittingProperties.NON_LINEAR_LEAST_SQUARES;

      public CurrencyInfo() {
      }

      public CurrencyInfo(final String curveConfiguration, final String surfaceName) {
        setCurveConfiguration(curveConfiguration);
        setSurfaceName(surfaceName);
      }

      public CurrencyInfo(final String curveConfiguration, final String surfaceName, final String smileFittingMethod) {
        this(curveConfiguration, surfaceName);
        setSmileFittingMethod(smileFittingMethod);
      }

      public String getCurveConfiguration() {
        return _curveConfiguration;
      }

      public void setCurveConfiguration(final String curveConfiguration) {
        _curveConfiguration = curveConfiguration;
      }

      public String getSurfaceName() {
        return _surfaceName;
      }

      public void setSurfaceName(final String surfaceName) {
        _surfaceName = surfaceName;
      }

      public String getSmileFittingMethod() {
        return _smileFittingMethod;
      }

      public void setSmileFittingMethod(final String smileFittingMethod) {
        _smileFittingMethod = smileFittingMethod;
      }

      @Override
      public void afterPropertiesSet() {
        ArgumentChecker.notNullInjected(getCurveConfiguration(), "curveConfiguration");
        ArgumentChecker.notNullInjected(getSurfaceName(), "surfaceName");
        ArgumentChecker.notNullInjected(getSmileFittingMethod(), "smileFittingMethod");
      }

    }

    private final Map<String, CurrencyInfo> _perCurrencyInfo = new HashMap<String, CurrencyInfo>();

    public void setPerCurrencyInfo(final Map<String, CurrencyInfo> perCurrencyInfo) {
      _perCurrencyInfo.clear();
      _perCurrencyInfo.putAll(perCurrencyInfo);
    }

    public Map<String, CurrencyInfo> getPerCurrencyInfo() {
      return _perCurrencyInfo;
    }

    public void setCurrencyInfo(final String currency, final CurrencyInfo info) {
      _perCurrencyInfo.put(currency, info);
    }

    public CurrencyInfo getCurrencyInfo(final String currency) {
      return _perCurrencyInfo.get(currency);
    }

    protected void addIRFutureOptionBlackDefaults(final List<FunctionConfiguration> functions) {
      final String[] args = new String[1 + getPerCurrencyInfo().size() * 3];
      int i = 0;
      args[i++] = PriorityClass.NORMAL.name();
      for (final Map.Entry<String, CurrencyInfo> e : getPerCurrencyInfo().entrySet()) {
        args[i++] = e.getKey();
        args[i++] = e.getValue().getCurveConfiguration();
        args[i++] = e.getValue().getSurfaceName();
      }
      functions.add(functionConfiguration(InterestRateFutureOptionBlackDefaults.class, args));
    }

    protected void addIRFutureOptionSABRDefaults(final List<FunctionConfiguration> functions) {
      final String[] args = new String[1 + getPerCurrencyInfo().size() * 4];
      int i = 0;
      args[i++] = PriorityClass.NORMAL.name();
      for (final Map.Entry<String, CurrencyInfo> e : getPerCurrencyInfo().entrySet()) {
        args[i++] = e.getKey();
        args[i++] = e.getValue().getCurveConfiguration();
        args[i++] = e.getValue().getSurfaceName();
        args[i++] = e.getValue().getSmileFittingMethod();
      }
      functions.add(functionConfiguration(IRFutureOptionSABRDefaults.class, args));
    }

    protected void addIRFutureOptionHestonDefaults(final List<FunctionConfiguration> functions) {
      final String[] args = new String[getPerCurrencyInfo().size() * 3];
      int i = 0;
      for (final Map.Entry<String, CurrencyInfo> e : getPerCurrencyInfo().entrySet()) {
        args[i++] = e.getKey();
        args[i++] = e.getValue().getCurveConfiguration();
        args[i++] = e.getValue().getSurfaceName();
      }
      functions.add(functionConfiguration(InterestRateFutureOptionHestonDefaults.class, args));
    }

    @Override
    protected void addAllConfigurations(final List<FunctionConfiguration> functions) {
      if (!getPerCurrencyInfo().isEmpty()) {
        addIRFutureOptionBlackDefaults(functions);
        addIRFutureOptionSABRDefaults(functions);
        addIRFutureOptionHestonDefaults(functions);
      }
    }

  }

  @Override
  protected void addAllConfigurations(final List<FunctionConfiguration> functions) {
    functions.add(functionConfiguration(InterestRateFutureOptionMarketUnderlyingPriceFunction.class));
    functions.add(functionConfiguration(InterestRateFutureOptionBlackPresentValueFunction.class));
    functions.add(functionConfiguration(InterestRateFutureOptionBlackVolatilitySensitivityFunction.class));
    functions.add(functionConfiguration(InterestRateFutureOptionBlackImpliedVolatilityFunction.class));
    functions.add(functionConfiguration(InterestRateFutureOptionBlackPV01Function.class));
    functions.add(functionConfiguration(InterestRateFutureOptionBlackYieldCurveNodeSensitivitiesFunction.class));
    functions.add(functionConfiguration(InterestRateFutureOptionBlackGammaFunction.class));
    functions.add(functionConfiguration(InterestRateFutureOptionBlackPriceFunction.class));
    functions.add(functionConfiguration(InterestRateFutureOptionHestonPresentValueFunction.class));
    functions.add(functionConfiguration(IRFutureOptionSABRPresentValueFunction.class));
    functions.add(functionConfiguration(IRFutureOptionSABRSensitivitiesFunction.class));
    functions.add(functionConfiguration(IRFutureOptionSABRYCNSFunction.class));
    // TODO: add functions from package
  }

}
