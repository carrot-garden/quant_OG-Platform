/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.interestrate.swaption.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opengamma.analytics.financial.interestrate.InstrumentDerivative;
import com.opengamma.analytics.financial.interestrate.InterestRateCurveSensitivity;
import com.opengamma.analytics.financial.interestrate.ParRateCalculator;
import com.opengamma.analytics.financial.interestrate.ParRateCurveSensitivityCalculator;
import com.opengamma.analytics.financial.interestrate.PresentValueBlackSwaptionSensitivity;
import com.opengamma.analytics.financial.interestrate.YieldCurveBundle;
import com.opengamma.analytics.financial.interestrate.annuity.derivative.AnnuityCouponFixed;
import com.opengamma.analytics.financial.interestrate.method.PricingMethod;
import com.opengamma.analytics.financial.interestrate.swap.method.SwapFixedCouponDiscountingMethod;
import com.opengamma.analytics.financial.interestrate.swaption.derivative.SwaptionCashFixedIbor;
import com.opengamma.analytics.financial.model.option.definition.YieldCurveWithBlackSwaptionBundle;
import com.opengamma.analytics.financial.model.option.pricing.analytic.formula.BlackFunctionData;
import com.opengamma.analytics.financial.model.option.pricing.analytic.formula.BlackPriceFunction;
import com.opengamma.analytics.financial.model.volatility.BlackFormulaRepository;
import com.opengamma.analytics.math.function.Function1D;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.money.CurrencyAmount;
import com.opengamma.util.tuple.DoublesPair;

/**
 *  Class used to compute the price and sensitivity of a cash-settled swaption with the Black model.
 *  @deprecated Use {@link com.opengamma.analytics.financial.interestrate.swaption.provider.SwaptionCashFixedIborBlackMethod}
 */
@Deprecated
public final class SwaptionCashFixedIborBlackMethod implements PricingMethod {

  /**
   * The method unique instance.
   */
  private static final SwaptionCashFixedIborBlackMethod INSTANCE = new SwaptionCashFixedIborBlackMethod();

  /**
   * Return the unique instance of the class.
   * @return The instance.
   */
  public static SwaptionCashFixedIborBlackMethod getInstance() {
    return INSTANCE;
  }

  /**
   * Private constructor.
   */
  private SwaptionCashFixedIborBlackMethod() {
  }

  /**
   * The par rate sensitivity calculator.
   */
  private static final ParRateCurveSensitivityCalculator PRSC = ParRateCurveSensitivityCalculator.getInstance();
  /**
   * The par rate calculator.
   */
  private static final ParRateCalculator PRC = ParRateCalculator.getInstance();
  /**
   * The swap method.
   */
  private static final SwapFixedCouponDiscountingMethod METHOD_SWAP = SwapFixedCouponDiscountingMethod.getInstance();

  /**
   * Computes the present value of a cash-settled European swaption in the Black model.
   * @param swaption The swaption.
   * @param curveBlack The curves with Black volatility data.
   * @return The present value.
   */
  public CurrencyAmount presentValue(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    final AnnuityCouponFixed annuityFixed = swaption.getUnderlyingSwap().getFixedLeg();
    final double tenor = swaption.getMaturityTime();
    final double forward = swaption.getUnderlyingSwap().accept(PRC, curveBlack);
    final double pvbp = METHOD_SWAP.getAnnuityCash(swaption.getUnderlyingSwap(), forward);
    // Implementation comment: cash-settled swaptions make sense only for constant strike, the computation of coupon equivalent is not required.
    final BlackPriceFunction blackFunction = new BlackPriceFunction();
    final double volatility = curveBlack.getBlackParameters().getVolatility(swaption.getTimeToExpiry(), tenor);
    final double discountFactorSettle = curveBlack.getCurve(annuityFixed.getNthPayment(0).getFundingCurveName()).getDiscountFactor(swaption.getSettlementTime());
    final BlackFunctionData dataBlack = new BlackFunctionData(forward, discountFactorSettle * pvbp, volatility);
    final Function1D<BlackFunctionData, Double> func = blackFunction.getPriceFunction(swaption);
    final double price = func.evaluate(dataBlack) * (swaption.isLong() ? 1.0 : -1.0);
    return CurrencyAmount.of(swaption.getCurrency(), price);
  }

  @Override
  public CurrencyAmount presentValue(final InstrumentDerivative instrument, final YieldCurveBundle curves) {
    ArgumentChecker.isTrue(instrument instanceof SwaptionCashFixedIbor, "Physical delivery swaption");
    ArgumentChecker.isTrue(curves instanceof YieldCurveWithBlackSwaptionBundle, "Bundle should contain Black Swaption data");
    return presentValue((SwaptionCashFixedIbor) instrument, (YieldCurveWithBlackSwaptionBundle) curves);
  }

  /**
   * Computes the present value rate sensitivity to rates of a cash-settled European swaption in the Black model.
   * @param swaption The swaption.
   * @param curveBlack The curves with Black volatility data.
   * @return The present value curve sensitivity.
   */
  public InterestRateCurveSensitivity presentValueCurveSensitivity(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    final AnnuityCouponFixed annuityFixed = swaption.getUnderlyingSwap().getFixedLeg();
    final double tenor = swaption.getMaturityTime();
    final double forward = swaption.getUnderlyingSwap().accept(PRC, curveBlack);
    // Derivative of the forward with respect to the rates.
    final InterestRateCurveSensitivity forwardDr = new InterestRateCurveSensitivity(swaption.getUnderlyingSwap().accept(PRSC, curveBlack));
    final double pvbp = METHOD_SWAP.getAnnuityCash(swaption.getUnderlyingSwap(), forward);
    // Derivative of the cash annuity with respect to the forward.
    final double pvbpDf = METHOD_SWAP.getAnnuityCashDerivative(swaption.getUnderlyingSwap(), forward);
    // Implementation note: strictly speaking, the strike equivalent is curve dependent; that dependency is ignored.
    final BlackPriceFunction blackFunction = new BlackPriceFunction();
    final double volatility = curveBlack.getBlackParameters().getVolatility(swaption.getTimeToExpiry(), tenor);
    final double discountFactorSettle = curveBlack.getCurve(annuityFixed.getNthPayment(0).getFundingCurveName()).getDiscountFactor(swaption.getSettlementTime());
    final BlackFunctionData dataBlack = new BlackFunctionData(forward, 1.0, volatility);
    final double[] bsAdjoint = blackFunction.getPriceAdjoint(swaption, dataBlack);
    final double sensiDF = -swaption.getSettlementTime() * discountFactorSettle * pvbp * bsAdjoint[0];
    final List<DoublesPair> list = new ArrayList<>();
    list.add(new DoublesPair(swaption.getSettlementTime(), sensiDF));
    final Map<String, List<DoublesPair>> resultMap = new HashMap<>();
    resultMap.put(annuityFixed.getNthPayment(0).getFundingCurveName(), list);
    InterestRateCurveSensitivity result = new InterestRateCurveSensitivity(resultMap);
    result = result.plus(forwardDr.multipliedBy(discountFactorSettle * (pvbpDf * bsAdjoint[0] + pvbp * bsAdjoint[1])));
    if (!swaption.isLong()) {
      result = result.multipliedBy(-1);
    }
    return result;
  }

  /**
   * Computes the present value sensitivity to the Black volatility (also called vega) of a cash-settled European swaption in the Black swaption model.
   * @param swaption The swaption.
   * @param curveBlack The curves with Black volatility data.
   * @return The present value Black sensitivity.
   */
  public PresentValueBlackSwaptionSensitivity presentValueBlackSensitivity(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    final AnnuityCouponFixed annuityFixed = swaption.getUnderlyingSwap().getFixedLeg();
    final double forward = swaption.getUnderlyingSwap().accept(PRC, curveBlack);
    final double pvbp = METHOD_SWAP.getAnnuityCash(swaption.getUnderlyingSwap(), forward);
    final double discountFactorSettle = curveBlack.getCurve(annuityFixed.getNthPayment(0).getFundingCurveName()).getDiscountFactor(swaption.getSettlementTime());
    final DoublesPair point = new DoublesPair(swaption.getTimeToExpiry(), swaption.getMaturityTime());
    final BlackPriceFunction blackFunction = new BlackPriceFunction();
    final double volatility = curveBlack.getBlackParameters().getVolatility(point);
    final BlackFunctionData dataBlack = new BlackFunctionData(forward, 1.0, volatility);
    final double[] bsAdjoint = blackFunction.getPriceAdjoint(swaption, dataBlack);
    final Map<DoublesPair, Double> sensitivity = new HashMap<>();
    sensitivity.put(point, bsAdjoint[2] * pvbp * discountFactorSettle * (swaption.isLong() ? 1.0 : -1.0));
    return new PresentValueBlackSwaptionSensitivity(sensitivity, curveBlack.getBlackParameters().getGeneratorSwap());
  }

  /**
   * Computes the implied Black volatility of the vanilla swaption.
   * @param swaption The swaption.
   * @param curves The yield curve bundle.
   * @return The implied volatility.
   */
  public double impliedVolatility(final SwaptionCashFixedIbor swaption, final YieldCurveBundle curves) {
    ArgumentChecker.notNull(curves, "Curves");
    ArgumentChecker.isTrue(curves instanceof YieldCurveWithBlackSwaptionBundle, "Yield curve bundle should contain Black swaption data");
    final YieldCurveWithBlackSwaptionBundle curvesBlack = (YieldCurveWithBlackSwaptionBundle) curves;
    ArgumentChecker.notNull(swaption, "Forex option");
    final double tenor = swaption.getMaturityTime();
    final double volatility = curvesBlack.getBlackParameters().getVolatility(swaption.getTimeToExpiry(), tenor);
    return volatility;
  }

  public double deltaTheoretical(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    final AnnuityCouponFixed annuityFixed = swaption.getUnderlyingSwap().getFixedLeg();
    final double tenor = swaption.getMaturityTime();
    final double forward = swaption.getUnderlyingSwap().accept(PRC, curveBlack);
    final double pvbp = METHOD_SWAP.getAnnuityCash(swaption.getUnderlyingSwap(), forward);
    // Implementation comment: cash-settled swaptions make sense only for constant strike, the computation of coupon equivalent is not required.
    final double volatility = curveBlack.getBlackParameters().getVolatility(swaption.getTimeToExpiry(), tenor);
    final double discountFactorSettle = curveBlack.getCurve(annuityFixed.getNthPayment(0).getFundingCurveName()).getDiscountFactor(swaption.getSettlementTime());

    final double strike = swaption.getStrike();
    final double expiry = swaption.getTimeToExpiry();
    final boolean isCall = swaption.isCall();
    final double df = discountFactorSettle * pvbp;
    return df * BlackFormulaRepository.delta(forward, strike, expiry, volatility, isCall) * (swaption.isLong() ? 1.0 : -1.0);
  }

  public CurrencyAmount delta(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    return CurrencyAmount.of(swaption.getCurrency(), deltaTheoretical(swaption, curveBlack));
  }

  public double gammaTheoretical(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    final AnnuityCouponFixed annuityFixed = swaption.getUnderlyingSwap().getFixedLeg();
    final double tenor = swaption.getMaturityTime();
    final double forward = swaption.getUnderlyingSwap().accept(PRC, curveBlack);
    final double pvbp = METHOD_SWAP.getAnnuityCash(swaption.getUnderlyingSwap(), forward);
    // Implementation comment: cash-settled swaptions make sense only for constant strike, the computation of coupon equivalent is not required.
    final double volatility = curveBlack.getBlackParameters().getVolatility(swaption.getTimeToExpiry(), tenor);
    final double discountFactorSettle = curveBlack.getCurve(annuityFixed.getNthPayment(0).getFundingCurveName()).getDiscountFactor(swaption.getSettlementTime());

    final double strike = swaption.getStrike();
    final double expiry = swaption.getTimeToExpiry();
    final double df = discountFactorSettle * pvbp;
    return df * BlackFormulaRepository.gamma(forward, strike, expiry, volatility) * (swaption.isLong() ? 1.0 : -1.0);
  }

  public CurrencyAmount gamma(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    return CurrencyAmount.of(swaption.getCurrency(), gammaTheoretical(swaption, curveBlack));
  }

  public double thetaTheoretical(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    final AnnuityCouponFixed annuityFixed = swaption.getUnderlyingSwap().getFixedLeg();
    final double tenor = swaption.getMaturityTime();
    final double forward = swaption.getUnderlyingSwap().accept(PRC, curveBlack);
    final double pvbp = METHOD_SWAP.getAnnuityCash(swaption.getUnderlyingSwap(), forward);
    // Implementation comment: cash-settled swaptions make sense only for constant strike, the computation of coupon equivalent is not required.
    final double volatility = curveBlack.getBlackParameters().getVolatility(swaption.getTimeToExpiry(), tenor);
    final double discountFactorSettle = curveBlack.getCurve(annuityFixed.getNthPayment(0).getFundingCurveName()).getDiscountFactor(swaption.getSettlementTime());

    final double strike = swaption.getStrike();
    final double expiry = swaption.getTimeToExpiry();
    final boolean isCall = swaption.isCall();
    final double df = discountFactorSettle * pvbp;
    return forward * df * BlackFormulaRepository.delta(forward, strike, expiry, volatility, isCall) * (swaption.isLong() ? 1.0 : -1.0) + df *
        BlackFormulaRepository.driftlessTheta(forward, strike, expiry, volatility) * (swaption.isLong() ? 1.0 : -1.0);
  }

  public CurrencyAmount theta(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    return CurrencyAmount.of(swaption.getCurrency(), thetaTheoretical(swaption, curveBlack));
  }

  public double vegaTheoretical(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    final AnnuityCouponFixed annuityFixed = swaption.getUnderlyingSwap().getFixedLeg();
    final double tenor = swaption.getMaturityTime();
    final double forward = swaption.getUnderlyingSwap().accept(PRC, curveBlack);
    final double pvbp = METHOD_SWAP.getAnnuityCash(swaption.getUnderlyingSwap(), forward);
    // Implementation comment: cash-settled swaptions make sense only for constant strike, the computation of coupon equivalent is not required.
    final double volatility = curveBlack.getBlackParameters().getVolatility(swaption.getTimeToExpiry(), tenor);
    final double discountFactorSettle = curveBlack.getCurve(annuityFixed.getNthPayment(0).getFundingCurveName()).getDiscountFactor(swaption.getSettlementTime());

    final double strike = swaption.getStrike();
    final double expiry = swaption.getTimeToExpiry();
    final double df = discountFactorSettle * pvbp;
    return df * BlackFormulaRepository.vega(forward, strike, expiry, volatility) * (swaption.isLong() ? 1.0 : -1.0);
  }

  public CurrencyAmount vega(final SwaptionCashFixedIbor swaption, final YieldCurveWithBlackSwaptionBundle curveBlack) {
    ArgumentChecker.notNull(swaption, "Swaption");
    ArgumentChecker.notNull(curveBlack, "Curves with Black volatility");
    return CurrencyAmount.of(swaption.getCurrency(), vegaTheoretical(swaption, curveBlack));
  }
}
