package com.baeldung.temporaladjusters;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.temporaladjuster.TemporalAdjusterUtil;

public class TemporalAdjustersTest {

    @Test
    public void whenAdjust_thenNextSunday() {
        LocalDate localDate = LocalDate.now();
        LocalDate nextSunday = localDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

        String expected = "2017-07-09";

        Assert.assertEquals(expected, nextSunday.toString());
    }

}
