/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.convention;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;
import org.threeten.bp.LocalDate;

import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.calendar.MondayToFridayCalendar;
import com.opengamma.util.test.TestGroup;

/**
 * Test.
 */
@Test(groups = TestGroup.UNIT)
public class LiveCattleFutureExpiryCalculatorTest {

  private static final LiveCattleFutureExpiryCalculator EXPIRY_CALC = LiveCattleFutureExpiryCalculator.getInstance();

  static final Calendar WEEKEND_CALENDAR = new MondayToFridayCalendar("weekday");

  private static final LocalDate DATE = LocalDate.of(2013, 1, 1);

  @Test
  public void getExpiryDate() {
    assertEquals(LocalDate.of(2013, 2, 28), EXPIRY_CALC.getExpiryDate(1, DATE, WEEKEND_CALENDAR));
    assertEquals(LocalDate.of(2013, 4, 30), EXPIRY_CALC.getExpiryDate(2, DATE, WEEKEND_CALENDAR));
    assertEquals(LocalDate.of(2013, 6, 28), EXPIRY_CALC.getExpiryDate(3, DATE, WEEKEND_CALENDAR));
    assertEquals(LocalDate.of(2013, 8, 30), EXPIRY_CALC.getExpiryDate(4, DATE, WEEKEND_CALENDAR));
    assertEquals(LocalDate.of(2013, 10, 31), EXPIRY_CALC.getExpiryDate(5, DATE, WEEKEND_CALENDAR));
    assertEquals(LocalDate.of(2013, 12, 31), EXPIRY_CALC.getExpiryDate(6, DATE, WEEKEND_CALENDAR));
    assertEquals(LocalDate.of(2014, 2, 28), EXPIRY_CALC.getExpiryDate(7, DATE, WEEKEND_CALENDAR));
  }

}
