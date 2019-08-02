package com.telecomyt.item.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 业务工具
 */
public class DateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 日期转换成：年-月
     */
    public static String dateToYm(LocalDate localDate){
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        StringBuilder ym = new StringBuilder();
        ym.append(year).append("-");
        if(String.valueOf(month).length() == 1){
            ym.append(0);
        }
        ym.append(month);
        return ym.toString();
    }

    /**
     * 日期转换成：年-月
     */
    public static String dateToYm(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        StringBuilder ym = new StringBuilder();
        ym.append(year).append("-");
        if(String.valueOf(month).length() == 1){
            ym.append(0);
        }
        ym.append(month);
        return ym.toString();
    }

    /**
     * 日期转换成：年-月-日
     */
    public static String dateToYmd(LocalDate localDate){
        return localDate.format(FORMATTER);
    }

    /**
     * 日期转换成：年-月-日
     */
    public static String dateToYmd(Date date){
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate.format(FORMATTER);
    }
    /**
     *  计算月份 ：之变月份，其他不变
     * @param date 日期
     * @param n 增加的月份
     */
    public static Date computingMonth(Date date , int n){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        return calendar.getTime();
    }

    /**
     *  获取 计算月份的 的第一天
     * @param date 日期
     * @param n 增加的月份
     */
    public static Date computingMonthFirstDay(Date date , int n){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     *  获取 计算月份的 的最后一天
     * @param date 日期
     * @param n 增加的月份
     */
    public static Date computingMonthLastDay(Date date , int n){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     *  获取两个日期之间相差的所有天
     */
    public static List<String> getDaysByDifference( LocalDateTime startTime , LocalDateTime endTime){
        List<String> days = Lists.newArrayList();
        Duration result = Duration.between(startTime, endTime);
        LocalDate startDate = startTime.toLocalDate();
        long dateDiff = result.toDays();
        for (int i = 0; i <= dateDiff ; i++) {
            LocalDate localDate = startDate.plusDays(i);
            days.add(localDate.toString());
        }
        return days;
    }

    /**
     * 获取两个日期之间相差的所有的号
     * @param startDayOfMonth 开始的号
     * @param endDayOfMonth 结束的号
     * @param lastDay 开始的时间当月最后一天
     */
    public static List<Integer> getDaysOfMonthByDifference(Integer startDayOfMonth , Integer endDayOfMonth , int lastDay){
        List<Integer> daysOfMonth = Lists.newArrayList();
        if(startDayOfMonth > endDayOfMonth ){
            for (int i = startDayOfMonth; i <= lastDay; i++) {
                daysOfMonth.add(i);
            }
            for (int i = 1; i <= endDayOfMonth; i++) {
                daysOfMonth.add(i);
            }
        }else {
            for (int i = startDayOfMonth; i <= endDayOfMonth; i++) {
                daysOfMonth.add(i);
            }
        }
        return daysOfMonth ;
    }

    /**
     *  求两个星期几之间的所有的星期数
     * @param startWeek 开始的星期几
     * @param endWeek 结束的星期几
     */
    public static List<Integer> getWeeksByDifference( Integer startWeek , Integer endWeek ){
        List<Integer> weeks = Lists.newArrayList();
        if(startWeek > endWeek ){
            for (int i = startWeek; i <= 7; i++) {
                weeks.add(i);
            }
            for (int i = 1; i <= endWeek; i++) {
                weeks.add(i);
            }
        }else {
            for (int i = startWeek; i <= endWeek; i++) {
                weeks.add(i);
            }
        }
        return weeks ;
    }

    /**
     *  从日期中取出多少号
     */
    public static Integer getDayByString(String date){
       return Integer.valueOf(date.split("-")[2]) ;
    }

    public static void main(String[] args) {
//        LocalDateTime start = LocalDateTime.of(2019,7,29,11,11,11);
//        LocalDateTime now = LocalDateTime.now();
//        List<Integer> daysOfMonth = DateUtil.getDaysOfMonthByDifference(start.getDayOfMonth(), now.getDayOfMonth(),
//                start.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
//        System.out.println(daysOfMonth);

        LocalDateTime startDate = LocalDateTime.of(2019,7,29,11,11,11);
        LocalDateTime endDate = LocalDateTime.of(2019,8,2,11,11,11);
        List<String> daysByDifference = DateUtil.getDaysByDifference(startDate, endDate);
        System.out.println(daysByDifference);
        Integer s = getDayByString(daysByDifference.get(3));
        System.out.println(s);

    }
}
