package com.tiktok.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    /**
     * 获取当前日期，格式 mm-dd
     * @return
     */
    public static String getCurrentDate() {
        // 创建 SimpleDateFormat 对象，指定日期格式为 "MMdd"
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd");

        // 获取当前时间
        Date currentDate = new Date();

        // 使用 SimpleDateFormat 格式化日期
        String formattedDate = dateFormat.format(currentDate);

        // 返回 String 类型的日期
        return formattedDate;
    }

    /**
     * 获取当前时间，格式 yyyy-MM-dd HH:mm
     * @return
     */
    public static String getCurrentTimeString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(new Date());
    }
}