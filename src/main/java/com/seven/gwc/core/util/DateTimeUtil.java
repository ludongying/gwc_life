package com.seven.gwc.core.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期时间工具类
 */
public class DateTimeUtil {

	/** 日期格式: ddMMyy */
	public static final String DDMMYY = "ddMMyy";

	/** 日期格式: MMddyy */
	public static final String MMDDYY = "MMddyy";

	/** 日期格式: yyMMdd */
	public static final String YYMMDD = "yyMMdd";

	/** 日期格式: yy-MM-dd */
	public static final String YY_MM_DD = "yy-MM-dd";

	/** 日期格式: yyyyMMdd */
	public static final String YYYYMMDD = "yyyyMMdd";

	/** 日期格式: yyyy-MM-dd */
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	/** 日期格式: yyyy-MM-dd HH:mm:ss */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/** 日期格式: yyyy-MM-dd HH:mm:ss.SSS */
	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

	/** 日期格式: yyyyMMddHHmmss */
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/** 日期格式: yyyyMMddHHmm */
	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

	/** 日期格式: yyyyMMddHHmmssSSS */
	public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	/** 时间格式: HHmm */
	public static final String HHMM = "HHmm";

	/** 时间格式: HHmmss */
	public static final String HHMMSS = "HHmmss";

	/** 时间格式: HH:mm:ss */
	public static final String HH_MM_SS = "HH:mm:ss";

	/** 时间格式: HH:mm:ss.SSS */
	public static final String HH_MM_SS_SSS = "HH:mm:ss.SSS";

	/**
	 * 获取日期中的某数值, 如获取月份
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @param calendarDateType
	 *            日期格式
	 * @return 数值
	 * @author ML
	 * @date 2018年6月26日 上午9:03:37
	 */
	public static int getInteger(Date date, int calendarDateType) {
		if (date == null) {
			return 0;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(calendarDateType);
	}

	/**
	 * 将日期按yyyy-MM-dd格式转换为指定的字符串格式
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @return 格式化后的日期字符串
	 * @author ML
	 * @date 2018年6月26日 上午9:05:06
	 */
	public static String parse2String(Date date) {
		return parse2String(date, YYYY_MM_DD);
	}

	/**
	 * 将日期按yyyy-MM-dd格式转换为指定的字符串格式
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return 格式化后的日期字符串
	 * @author ML
	 * @date 2018年6月26日 上午9:08:06
	 */
	public static String parse2String(Date date, String format) {
		if (StringUtils.isBlank(format)) {
			format = YYYY_MM_DD;
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return date != null ? formater.format(date) : "";
	}

	/**
	 * 将指定的日期时间字符串转化为指定格式日期字符串
	 * 
	 * @description
	 * @param dateStr
	 *            日期字符串
	 * @param dateFormat
	 *            日期格式
	 * @param timeStr
	 *            时间字符串
	 * @param timeFormat
	 *            时间格式
	 * @param format
	 *            格式
	 * @return 转换后的日期时间字符串
	 * @author ML
	 * @date 2018年6月26日 上午9:08:51
	 */
	public static String parse2String(String dateStr, String dateFormat, String timeStr, String timeFormat,
			String format) {
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = YYYY_MM_DD;
		}
		if (StringUtils.isBlank(timeFormat)) {
			format = HH_MM_SS;
		}
		if (StringUtils.isBlank(format)) {
			format = YYYY_MM_DD_HH_MM_SS;
		}
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(timeStr)) {
			return "";
		}
		Date date = parse2Date(dateStr, dateFormat);
		Date time = parse2Date(timeStr, timeFormat);
		Calendar timeal = Calendar.getInstance();
		timeal.setTime(time);
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		dateCal.set(Calendar.HOUR_OF_DAY, timeal.get(Calendar.HOUR_OF_DAY));
		dateCal.set(Calendar.MINUTE, timeal.get(Calendar.MINUTE));
		dateCal.set(Calendar.SECOND, timeal.get(Calendar.SECOND));
		return parse2String(dateCal.getTime(), format);
	}

	/**
	 * 将指定的日期对象串转化为yyyy-MM-dd格式的日期对象
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:09:49
	 */
	public static Date parse2Date(Date date) {
		return parse2Date(date, YYYY_MM_DD);
	}

	/**
	 * 将指定的日期对象字符串转化为yyyy-MM-dd格式的日期对象
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:10:25
	 */
	public static Date parse2Date(String date) {
		return parse2Date(date, YYYY_MM_DD);
	}

	/**
	 * 将当前系统时间按照指定的日期字符串格式转化为日期对象
	 * 
	 * @description
	 * @param format
	 *            日期格式
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:11:30
	 */
	public static Date parse2DateByFormat(String format) {
		return parse2Date(new Date(), format);
	}

	/**
	 * 将指定的日期字符串转化为日期对象
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @param format
	 *            日期格式
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:12:05
	 */
	public static Date parse2Date(Date date, String format) {
		if (StringUtils.isBlank(format)) {
			format = YYYY_MM_DD;
		}
		if (date == null) {
			return null;
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			date = formater.parse(formater.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将指定的日期字符串转化为日期对象
	 * 
	 * @description
	 * @param dateStr
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:13:36
	 */
	public static Date parse2Date(String dateStr, String format) {
		if (StringUtils.isBlank(format)) {
			format = YYYY_MM_DD;
		}
		if (StringUtils.isBlank(dateStr)) {
			dateStr = getDateTimeStr(format);
		}
		SimpleDateFormat formater = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = formater.parse(dateStr);
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * 将指定的日期时间字符串转化为指定格式Date
	 * 
	 * @description
	 * @param dateStr
	 *            日期字符串
	 * @param timeStr
	 *            时间字符串
	 * @param format
	 *            格式
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:14:06
	 */
	public static Date parse2Date(String dateStr, String timeStr, String format) {
		if (StringUtils.isBlank(format)) {
			format = YYYY_MM_DD_HH_MM_SS;
		}
		if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(timeStr)) {
			return null;
		}
		Date date = parse2Date(dateStr, DDMMYY);
		Date time = parse2Date(timeStr, HHMMSS);
		Calendar timeal = Calendar.getInstance();
		timeal.setTime(time);
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		dateCal.set(Calendar.HOUR_OF_DAY, timeal.get(Calendar.HOUR_OF_DAY));
		dateCal.set(Calendar.MINUTE, timeal.get(Calendar.MINUTE));
		dateCal.set(Calendar.SECOND, timeal.get(Calendar.SECOND));
		SimpleDateFormat formater = new SimpleDateFormat(format);
		try {
			date = formater.parse(parse2String(dateCal.getTime(), format));
		} catch (ParseException e) {
		}
		return date;
	}

	/**
	 * 获取yyyyMMdd"格式的日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:15:23
	 */
	public static Date getDate_YYYYMMDD() {
		return parse2DateByFormat(YYYYMMDD);
	}

	/**
	 * 获取yyyyMMdd"格式的日期字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:15:45
	 */
	public static String getDateStr_YYYYMMDD() {
		return getDateTimeStr(YYYYMMDD);
	}

	/**
	 * 获取yyyy-MM-dd"格式的日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:16:04
	 */
	public static Date getDate_YYYY_MM_DD() {
		return parse2DateByFormat(YYYY_MM_DD);
	}

	/**
	 * 获取yyyy-MM-dd"格式的日期字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:16:24
	 */
	public static String getDateStr_YYYY_MM_DD() {
		return getDateTimeStr(YYYY_MM_DD);
	}

	/**
	 * 获取HHmm格式的时间字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:18:57
	 */
	public static String getTimeStr_HHMM() {
		return getDateTimeStr(HHMM);
	}

	/**
	 * 获取HHmmSS格式的时间字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:19:14
	 */
	public static String getTimeStr_HHMMSS() {
		return getDateTimeStr(HHMMSS);
	}

	/**
	 * 获取HH:mm:ss格式的时间字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:19:36
	 */
	public static String getTimeStr_HH_MM_SS() {
		return getDateTimeStr(HH_MM_SS);
	}

	/**
	 * 获取HH:mm:ss.SSS格式的时间字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:19:51
	 */
	public static String getTimeStr_HH_MM_SS_SSS() {
		return getDateTimeStr(HH_MM_SS_SSS);
	}

	/**
	 * 获取yyyyMMddHHmmss格式的日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:21:04
	 */
	public static Date getDateTime_YYYYMMDDHHMMSS() {
		return parse2DateByFormat(YYYYMMDDHHMMSS);
	}

	/**
	 * 获取yyyyMMddHHmmss格式的日期字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:21:20
	 */
	public static String getDateTimeStr_YYYYMMDDHHMMSS() {
		return getDateTimeStr(YYYYMMDDHHMMSS);
	}

	/**
	 * 获取yyyyMMddHHmmssSSS格式的日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:21:37
	 */
	public static Date getDateTime_YYYYMMDDHHMMSSSSS() {
		return parse2DateByFormat(YYYYMMDDHHMMSSSSS);
	}

	/**
	 * 获取yyyyMMddHHmmssSSS格式的日期字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:21:56
	 */
	public static String getDateTimeStr_YYYYMMDDHHMMSSSSS() {
		return getDateTimeStr(YYYYMMDDHHMMSSSSS);
	}

	/**
	 * 获取yyyy-MM-dd HH:mm:ss格式的日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:22:16
	 */
	public static Date getDateTime_YYYY_MM_DD_HH_MM_SS() {
		return parse2DateByFormat(YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取yyyy-MM-dd HH:mm:ss格式的日期字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:23:27
	 */
	public static String getDateTimeStr_YYYY_MM_DD_HH_MM_SS() {
		return getDateTimeStr(YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取yyyy-MM-dd HH:mm:ss.SSS格式的日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:23:42
	 */
	public static Date getDateTime_YYYY_MM_DD_HH_MM_SS_SSS() {
		return parse2DateByFormat(YYYY_MM_DD_HH_MM_SS_SSS);
	}

	/**
	 * 获取yyyy-MM-dd HH:mm:ss.SSS格式的日期字符串
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:24:00
	 */
	public static String getDateTimeStr_YYYY_MM_DD_HH_MM_SS_SSS() {
		return getDateTimeStr(YYYY_MM_DD_HH_MM_SS_SSS);
	}

	/**
	 * 获取yyyyMMddHHmmss格式的日期字符串
	 * 
	 * @description
	 * @param format
	 *            格式
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:24:15
	 */
	public static String getDateTimeStr(String format) {
		return parse2String(new Date(), format);
	}

	/**
	 * 获取本月第一天日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:24:36
	 */
	public static String getFirstDayByCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		return parse2String(calendar.getTime(), YYYY_MM_DD);
	}

	/**
	 * 获取本月最后一天日期
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:25:04
	 */
	public static String getLastDayByCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.roll(Calendar.DATE, -1);
		return parse2String(calendar.getTime(), YYYY_MM_DD);
	}

	/**
	 * 获取当年年份, 失败返回0.
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:26:56
	 */
	public static int getYear() {
		return getYear(new Date());
	}

	/**
	 * 获取日期的年份, 失败返回0.
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:27:30
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取当月月份, 失败返回0.
	 * 
	 * @description
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:27:44
	 */
	public static int getMonth() {
		return getMonth(new Date());
	}

	/**
	 * 获取日期的月份, 失败返回0.
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:27:58
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH);
	}

	/**
	 * 获取两个日期相差的天数(date-otherDate)
	 * 
	 * @description
	 * @param smallDate
	 *            减数日期
	 * @param bigDate
	 *            被减数日期
	 * @return 两个日期相差的天数(date-otherDate)
	 * @throws ParseException
	 * @author ML
	 * @date 2018年6月26日 上午9:28:27
	 */
	public static int getIntervalDays(Date smallDate, Date bigDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		smallDate = simpleDateFormat.parse(simpleDateFormat.format(smallDate));
		bigDate = simpleDateFormat.parse(simpleDateFormat.format(bigDate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(smallDate);
		long time1 = calendar.getTimeInMillis();
		calendar.setTime(bigDate);
		long time2 = calendar.getTimeInMillis();
		long between_days = Long.parseLong("0");
		if (time2 > time1) {
			between_days = (time2 - time1) / (1000 * 3600 * 24);
		} else {
			between_days = (time1 - time2) / (1000 * 3600 * 24);
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 比较两个日期相差天数的大小(bigDate-smallDate)<br>
	 * (1.结果大于0,bigDate>smallDate;2.结果等于0,bigDate=smallDate;3.结果小于0,bigDate<smallDate)
	 * 
	 * @description
	 * @param smallDate
	 *            减数日期
	 * @param bigDate
	 *            被减数日期
	 * @return
	 * @throws ParseException
	 * @author ML
	 * @date 2018年6月26日 上午9:29:17
	 */
	public static int getDaysBetween(Date smallDate, Date bigDate) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		smallDate = simpleDateFormat.parse(simpleDateFormat.format(smallDate));
		bigDate = simpleDateFormat.parse(simpleDateFormat.format(bigDate));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(smallDate);
		long time1 = calendar.getTimeInMillis();
		calendar.setTime(bigDate);
		long time2 = calendar.getTimeInMillis();
		long between_days = Long.parseLong("0");
		between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取两个日期相差月数
	 * 
	 * @description
	 * @param startDate
	 *            靠前的一天
	 * @param endDate
	 *            靠后的一天
	 * @return
	 * @throws ParseException
	 * @author ML
	 * @date 2018年6月26日 上午9:30:37
	 */
	public static int getMonthDiff(Date startDate, Date endDate) throws ParseException {
		int monthday;
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate);
		int sYear = starCal.get(Calendar.YEAR);
		int sMonth = starCal.get(Calendar.MONTH) + 1;
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		int eYear = endCal.get(Calendar.YEAR);
		int eMonth = endCal.get(Calendar.MONTH) + 1;
		monthday = (eYear - sYear) * 12 + eMonth - sMonth;
		// 这里计算零头的情况，根据实际确定是否要加1 还是要减1
		starCal.add(Calendar.MONTH, monthday);
		if (starCal.compareTo(endCal) > 0) {
			monthday = monthday - 1;
		}
		return monthday;
	}

	/**
	 * 增加日期
	 * 
	 * @description
	 * @param value
	 *            加减日期数(正数加, 负数减)
	 * @param calendarDateType
	 *            日期格式
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:31:07
	 */
	public static Date addDateTime(int value, int calendarDateType) {
		return addDateTime(new Date(), value, calendarDateType);
	}

	/**
	 * 增加日期
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @param value
	 *            加减日期数(正数加, 负数减)
	 * @param calendarDateType
	 *            日期格式
	 * @author ML
	 * @date 2018年6月26日 上午9:31:47
	 */
	public static Date addDateTime(Date date, int value, int calendarDateType) {
		if (date == null) {
			return new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendarDateType, value);
		return calendar.getTime();
	}

	/**
	 * 计算传入日期的指定月数后同一天
	 * 
	 * @description
	 * @param date
	 *            日期
	 * @param months
	 *            指定月份数
	 * @return
	 * @throws ParseException
	 * @author ML
	 * @date 2018年6月26日 上午9:32:26
	 */
	public static Date getDateInSomeMonths(Date date, int months) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar nextDate = Calendar.getInstance();
		nextDate.setTime(simpleDateFormat.parse(simpleDateFormat.format(date)));
		nextDate.add(Calendar.MONTH, months);
		return nextDate.getTime();
	}

	/**
	 * 判断两个日期是否整好相差整数月
	 * 
	 * @description
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 * @throws ParseException
	 * @author ML
	 * @date 2018年6月26日 上午9:33:22
	 */
	public static boolean checkTwoDateIsEqual(Date startDate, Date endDate) throws ParseException {
		startDate = parse2Date(startDate);
		endDate = parse2Date(endDate);
		int monthDistance = getMonthDiff(startDate, endDate);
		Calendar starCal = Calendar.getInstance();
		starCal.setTime(startDate);
		starCal.add(Calendar.MONTH, monthDistance);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		return starCal.compareTo(endCal) == 0;
	}

	/**
	 * 获得起始日期与结束日期之间的日期集合（不包含结束日期）
	 * 
	 * @description
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:34:14
	 */
	public static List<Date> getAllTheDateOftheMonth(Date startDate, Date endDate) {
		List<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		while (startDate.before(endDate)) {
			list.add(startDate);
			cal.add(Calendar.DATE, 1);
			startDate = cal.getTime();
		}
		return list;
	}

	/**
	 * 判断两个日期是否是同一月份
	 * 
	 * @description
	 * @param date1
	 * @param date2
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:35:13
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		return isSameMonth;
	}

	/**
	 * 判断两个日期是否是同一天
	 * 
	 * @description
	 * @param date1
	 * @param date2
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:35:43
	 */
	public static boolean isSameday(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
		return isSameDate;
	}

	/**
	 * 获取指定日期N天后的日期
	 * 
	 * @description
	 * @param date
	 *            指定日期
	 * @param day
	 *            指定天数
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:36:01
	 */
	public static Date getDateAfter(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 返回指定日期月份的最后一天
	 * 
	 * @description
	 * @param date
	 *            指定日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:39:37
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期前N天的时间
	 * 
	 * @description
	 * @param date
	 *            指定日期
	 * @param day
	 *            指定天数
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:39:58
	 */
	public static Date getDateBefore(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 判断日期是否为月末最后一天
	 * 
	 * @description
	 * @param date
	 *            指定日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:40:23
	 */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}

	/**
	 * 获得指定日期的所有月份
	 * 
	 * @description
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            结束日期
	 * @return
	 * @throws ParseException
	 * @author ML
	 * @date 2018年6月26日 上午9:41:02
	 */
	public static List<String> getMonthBetween(String startDate, String endDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月
		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();
		min.setTime(sdf.parse(startDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);
		max.setTime(sdf.parse(endDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);
		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}
		return result;
	}

	/**
	 * 获取指定月份的上一个月 格式:yyyy-MM
	 * 
	 * @description
	 * @param date
	 *            指定日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:42:05
	 */
	public static String getMonthBeforThisMonth(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		Calendar c = Calendar.getInstance();
		// 过去一月
		c.setTime(date);
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = format.format(m);
		return mon;
	}

	/**
	 * 判断日期是否为月末最后一天
	 * 
	 * @description
	 * @param date
	 *            指定日期
	 * @return
	 * @author ML
	 * @date 2018年6月26日 上午9:42:28
	 */
	public static boolean isFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		if (nowDay == 1) {
			return true;
		}
		return false;
	}

}
