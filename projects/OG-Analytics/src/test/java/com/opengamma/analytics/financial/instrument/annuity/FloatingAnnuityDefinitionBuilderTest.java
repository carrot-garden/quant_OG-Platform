/**
 * Copyright (C) 2014 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.instrument.annuity;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.Test;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.datasets.CalendarUSD;
import com.opengamma.analytics.financial.instrument.NotionalProvider;
import com.opengamma.analytics.financial.instrument.index.GeneratorSwapFixedIbor;
import com.opengamma.analytics.financial.instrument.index.GeneratorSwapFixedIborMaster;
import com.opengamma.analytics.financial.instrument.index.IborIndex;
import com.opengamma.analytics.financial.instrument.index.IndexON;
import com.opengamma.analytics.financial.instrument.index.IndexONMaster;
import com.opengamma.analytics.financial.instrument.payment.CouponDefinition;
import com.opengamma.analytics.financial.instrument.payment.CouponIborSpreadDefinition;
import com.opengamma.analytics.financial.instrument.payment.CouponONArithmeticAverageSpreadDefinition;
import com.opengamma.analytics.financial.schedule.ScheduleCalculator;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.businessday.BusinessDayConventions;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.rolldate.RollConvention;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.DateUtils;

/**
 * Test the builder of floating annuities.
 */
public class FloatingAnnuityDefinitionBuilderTest {

  /** USD conventions */
  private static final Calendar NYC = new CalendarUSD("NYC");
  private static final GeneratorSwapFixedIborMaster GENERATOR_IRS_MASTER = GeneratorSwapFixedIborMaster.getInstance();
  private static final GeneratorSwapFixedIbor USD6MLIBOR3M = GENERATOR_IRS_MASTER.getGenerator("USD6MLIBOR3M", NYC);
  private static final IborIndex USDLIBOR3M = USD6MLIBOR3M.getIborIndex();
  private static final Currency USD = USDLIBOR3M.getCurrency();
  private static final AdjustedDateParameters ADJUSTED_DATE_LIBOR = new AdjustedDateParameters(NYC, USD6MLIBOR3M.getBusinessDayConvention());
  private static final AdjustedDateParameters ADJUSTED_DATE_FEDFUND = new AdjustedDateParameters(NYC, BusinessDayConventions.MODIFIED_FOLLOWING);
  private static final OffsetAdjustedDateParameters OFFSET_ADJ_LIBOR =
      new OffsetAdjustedDateParameters(-2, OffsetType.BUSINESS, NYC, USD6MLIBOR3M.getBusinessDayConvention());
  private static final OffsetAdjustedDateParameters OFFSET_FIXING_FEDFUND =
      new OffsetAdjustedDateParameters(0, OffsetType.BUSINESS, NYC, BusinessDayConventionFactory.of("Following"));
  private static final IndexON USDFEDFUND = IndexONMaster.getInstance().getIndex("FED FUND");
  /** Overnight Arithmetic Average - Leg details */
  private static final LocalDate EFFECTIVE_DATE_1 = LocalDate.of(2014, 7, 18);
  private static final int TENOR_YEAR_1 = 10;
  private static final LocalDate MATURITY_DATE_1 = EFFECTIVE_DATE_1.plus(Period.ofYears(TENOR_YEAR_1));
  private static final Period PAYMENT_PERIOD = Period.ofMonths(3);
  //  private static final int SPOT_OFFSET = 2;
  //  private static final int PAY_OFFSET = 0;
  //  private static final int CUT_OFF_OFFSET = 2;
  private static final double SPREAD_1 = 0.0010;
  private static final boolean PAYER_1 = false;
  private static final double NOTIONAL_1 = 1000000; // 1m
  /** Ibor - Leg details */

  private static final NotionalProvider NOTIONAL_PROV_1 = new NotionalProvider() {
    @Override
    public double getAmount(final LocalDate date) {
      return NOTIONAL_1;
    }
  };
  private static final double TOLERANCE_RATE = 1.0E-10;
  private static final double TOLERANCE_AMOUNT = 1.0E-2;

  private static final AnnuityDefinition<? extends CouponDefinition> ONAA_LEG_1_DEFINITION = (AnnuityDefinition<? extends CouponDefinition>) new FloatingAnnuityDefinitionBuilder().
      payer(PAYER_1).notional(NOTIONAL_PROV_1).startDate(EFFECTIVE_DATE_1).endDate(MATURITY_DATE_1).index(USDFEDFUND).
      accrualPeriodFrequency(PAYMENT_PERIOD).rollDateAdjuster(RollConvention.NONE.getRollDateAdjuster(0)).
      resetDateAdjustmentParameters(ADJUSTED_DATE_FEDFUND).accrualPeriodParameters(ADJUSTED_DATE_FEDFUND).
      dayCount(USDFEDFUND.getDayCount()).fixingDateAdjustmentParameters(OFFSET_FIXING_FEDFUND).currency(USD).spread(SPREAD_1).
      build();

  private static final AnnuityDefinition<? extends CouponDefinition> IBOR_LEG_1_DEFINITION = (AnnuityDefinition<? extends CouponDefinition>) new FloatingAnnuityDefinitionBuilder().
      payer(PAYER_1).notional(NOTIONAL_PROV_1).startDate(EFFECTIVE_DATE_1).endDate(MATURITY_DATE_1).index(USDLIBOR3M).
      accrualPeriodFrequency(PAYMENT_PERIOD).rollDateAdjuster(RollConvention.NONE.getRollDateAdjuster(0)).
      resetDateAdjustmentParameters(ADJUSTED_DATE_LIBOR).accrualPeriodParameters(ADJUSTED_DATE_LIBOR).
      dayCount(USDLIBOR3M.getDayCount()).fixingDateAdjustmentParameters(OFFSET_ADJ_LIBOR).currency(USD).spread(SPREAD_1).
      build();

  @Test
  public void arithmeticAverage() {
    int nbOnAaCpn = TENOR_YEAR_1 * 4;
    assertEquals("FloatingAnnuityDefinitionBuilderTest: arithmetic average", ONAA_LEG_1_DEFINITION.getNumberOfPayments(), nbOnAaCpn);
    assertEquals("FloatingAnnuityDefinitionBuilderTest: arithmetic average", ONAA_LEG_1_DEFINITION.getNthPayment(nbOnAaCpn - 1).getPaymentDate().toLocalDate(), MATURITY_DATE_1);
    ZonedDateTime effectiveDateTime = DateUtils.getUTCDate(EFFECTIVE_DATE_1.getYear(), EFFECTIVE_DATE_1.getMonthValue(), EFFECTIVE_DATE_1.getDayOfMonth());
    for (int loopcpn = 0; loopcpn < nbOnAaCpn; loopcpn++) {
      assertTrue("FloatingAnnuityDefinitionBuilderTest: arithmetic average", ONAA_LEG_1_DEFINITION.getNthPayment(loopcpn) instanceof CouponONArithmeticAverageSpreadDefinition);
      CouponONArithmeticAverageSpreadDefinition cpn = (CouponONArithmeticAverageSpreadDefinition) ONAA_LEG_1_DEFINITION.getNthPayment(loopcpn);
      ZonedDateTime expectedPaymentDate = ScheduleCalculator.getAdjustedDate(effectiveDateTime, PAYMENT_PERIOD.multipliedBy(loopcpn + 1),
          ADJUSTED_DATE_FEDFUND.getBusinessDayConvention(), NYC);
      assertEquals("FloatingAnnuityDefinitionBuilderTest: arithmetic average", cpn.getPaymentDate().toLocalDate(), expectedPaymentDate.toLocalDate());
      assertEquals("FloatingAnnuityDefinitionBuilderTest: arithmetic average", cpn.getSpread(), SPREAD_1, TOLERANCE_RATE);
      assertEquals("FloatingAnnuityDefinitionBuilderTest: arithmetic average", cpn.getNotional(), NOTIONAL_1, TOLERANCE_AMOUNT);
    }
  }

  @Test
  public void couponIbor() {
    int nbIborCpn = TENOR_YEAR_1 * 4;
    assertEquals("FloatingAnnuityDefinitionBuilderTest: coupon ibor", IBOR_LEG_1_DEFINITION.getNumberOfPayments(), nbIborCpn);
    assertEquals("FloatingAnnuityDefinitionBuilderTest: coupon ibor", IBOR_LEG_1_DEFINITION.getNthPayment(nbIborCpn - 1).getPaymentDate().toLocalDate(), MATURITY_DATE_1);
    ZonedDateTime effectiveDateTime = DateUtils.getUTCDate(EFFECTIVE_DATE_1.getYear(), EFFECTIVE_DATE_1.getMonthValue(), EFFECTIVE_DATE_1.getDayOfMonth());
    for (int loopcpn = 0; loopcpn < nbIborCpn; loopcpn++) {
      assertTrue("FloatingAnnuityDefinitionBuilderTest: coupon ibor", IBOR_LEG_1_DEFINITION.getNthPayment(loopcpn) instanceof CouponIborSpreadDefinition);
      CouponIborSpreadDefinition cpn = (CouponIborSpreadDefinition) IBOR_LEG_1_DEFINITION.getNthPayment(loopcpn);
      ZonedDateTime expectedPaymentDate = ScheduleCalculator.getAdjustedDate(effectiveDateTime, PAYMENT_PERIOD.multipliedBy(loopcpn + 1),
          ADJUSTED_DATE_FEDFUND.getBusinessDayConvention(), NYC);
      assertEquals("FloatingAnnuityDefinitionBuilderTest: coupon ibor", cpn.getPaymentDate().toLocalDate(), expectedPaymentDate.toLocalDate());
      assertEquals("FloatingAnnuityDefinitionBuilderTest: coupon ibor", cpn.getSpread(), SPREAD_1, TOLERANCE_RATE);
      assertEquals("FloatingAnnuityDefinitionBuilderTest: coupon ibor", cpn.getNotional(), NOTIONAL_1, TOLERANCE_AMOUNT);
    }
  }

}