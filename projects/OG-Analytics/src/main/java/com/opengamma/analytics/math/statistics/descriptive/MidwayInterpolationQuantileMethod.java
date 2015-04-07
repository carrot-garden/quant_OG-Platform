/**
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.math.statistics.descriptive;

/**
 * Implementation of a quantile estimator.
 * The quantile is linearly interpolated between two sample values.
 * The probability dimension on which the interpolation take place (X axis) is the ratio of the sample index and the
 * number of elements in the sample ( <i>p<subscript>i</subscript> = (i-0.5) / n</i>). For each probability 
 * <i>p<subscript>i</subscript></i>, the distribution value is the sample value with same index. 
 * The index used above are the Java index plus 1.
 * <p> Reference: Value-At-Risk, OpenGamma Documentation 31, Version 0.1, April 2015.
 */
public class MidwayInterpolationQuantileMethod extends InterpolationQuantileMethod {
  
  /** Default implementation. */
  public static final MidwayInterpolationQuantileMethod DEFAULT = new MidwayInterpolationQuantileMethod();

  @Override
  protected double indexCorrection() {
    return 0.5d;
  }

}
