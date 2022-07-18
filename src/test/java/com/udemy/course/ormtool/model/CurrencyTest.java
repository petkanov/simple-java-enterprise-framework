package com.udemy.course.ormtool.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyTest {
    @Test
    public void currencyConversionTest()
    {
        float usdAmount = 13;
        float euroAmount = 21;

        final float expectedEuroAmount = usdAmount / 2;
        final float expectedUsdAmount = euroAmount * 2;

        assertEquals(expectedUsdAmount, Currency.EURO.convertTo(Currency.USD, euroAmount), 0.001);
        assertEquals(expectedEuroAmount, Currency.USD.convertTo(Currency.EURO, usdAmount), 0.001);

        assertEquals(euroAmount, Currency.EURO.convertTo(Currency.EURO, euroAmount), 0.001);
        assertEquals(usdAmount, Currency.USD.convertTo(Currency.USD, usdAmount), 0.001);
    }
}
