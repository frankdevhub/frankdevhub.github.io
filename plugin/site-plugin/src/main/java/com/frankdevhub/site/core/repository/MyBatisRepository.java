package com.frankdevhub.site.core.repository;

import com.github.pagehelper.PageHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MyBatisRepository {
    public void setPage(Integer pageNum, Integer pageSize) {
        if (pageSize != null && pageSize != null)
            PageHelper.startPage(pageNum, pageSize, true);
    }

    private void resetCurrentCalendarMilliSecond(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    public Long[] getTodayTimeStampRange() {
        Long range[] = new Long[2];
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        resetCurrentCalendarMilliSecond(c);

        Long zeroT = c.getTime().getTime();
        Long endT = zeroT + (24 * 3600 * 1000) - 1;

        range[0] = zeroT;
        range[1] = endT;

        System.out.println(
                "timeStamp = "
                        + date.getTime()
                        + ", zeroT = " + zeroT
                        + ", endT = " + endT + "");

        return range;
    }

    public Long[] getTimeStampRange(Integer year, Integer month, Integer day) {
        Long range[] = new Long[2];
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DATE, day);

        resetCurrentCalendarMilliSecond(c);

        Long zeroT = c.getTime().getTime();
        Long endT = zeroT + (24 * 3600 * 1000) - 1;

        range[0] = zeroT;
        range[1] = endT;

        System.out.println(
                "year = "
                        + year
                        + ", month= " + month
                        + ", day = " + day
                        + ", zeroT = " + zeroT
                        + ", endT = " + endT + "");

        return range;
    }

    public Long[] getTimeStampRange(Calendar c) {
        Long range[] = new Long[2];

        Date date = c.getTime();
        resetCurrentCalendarMilliSecond(c);

        Long zeroT = c.getTime().getTime();
        Long endT = zeroT + (24 * 3600 * 1000) - 1;
        range[0] = zeroT;
        range[1] = endT;

        System.out.println("timeStamp = " + date.getTime() + ", "
                + "zeroT = " + zeroT + ""
                + ", endT=" + endT + "");
        return range;
    }


    public Long[] getTimeStampRange(Long timeStamp) {
        if (timeStamp < 1000000000000L)
            throw new RuntimeException("please use timestamp format in millisecond");

        Long[] range = new Long[2];
        long zeroT = timeStamp - (timeStamp + TimeZone.getDefault().getRawOffset()) % (1000 * 3600 * 24);
        long endT = zeroT + (24 * 3600 * 1000) - 1;

        range[0] = zeroT;
        range[1] = endT;

        System.out.println(
                "timeStamp = "
                        + timeStamp
                        + ", zeroT = " + zeroT
                        + ", endT = " + endT + "");

        return range;
    }

}
