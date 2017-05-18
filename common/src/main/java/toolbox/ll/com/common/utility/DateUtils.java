package toolbox.ll.com.common.utility;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class DateUtils {

    private static final String FROMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String FROMAT_DEF="yyyy-MM-dd HH:mm:ss";
    private static final String FROMAT_WITHOUT_SENCOND="yyyy-MM-dd HH:mm";
    private static final String FROMAT_YEAR_AND_MONTH="yyyy/MM";

    /**
     * "yyyy.MM"
     *
     * @param
     * @return
     */
    public static String cutYearAndMonth(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(time);
    }

    /**
     * "yyyy.MM"
     *
     * @param
     * @return
     */
    public static String cutYearAndMonth(long time, String fromat) {
        SimpleDateFormat sdf = new SimpleDateFormat(fromat);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(time);
    }

    /**
     * "MM"
     *
     * @param
     * @return
     */
    public static String cutMonth(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(time);
    }

    /**
     * "yyyy.MM.dd"
     *
     * @param
     * @return
     */
    public static String cutYearAndMonthAndDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(time);
    }

    /**
     * "yy.MM.dd"
     *
     * @param seconds
     * @return
     */
    public static String cutYearAndMonthAndDay1(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        Date date = new Date(seconds);
        return sdf.format(date);
    }

    /**
     * "MM.DD"
     *
     * @param
     * @return
     */
    public static String cutMonthAndDay(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(time);
    }

    /**
     * "MM月dd日"
     *
     * @param seconds
     * @return
     */
    public static String cutMonthAndDay1(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        sdf.setTimeZone(TimeZone.getDefault());
        Date date = new Date(seconds);
        return sdf.format(date);
    }

    /**
     * "HH.mm"
     *
     * @param
     * @return
     */
    public static String cutHourAndMintues(long time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(time);
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * "HH.mm"
     *
     * @param
     * @return
     */
    public static String cutMintuesAndSecond(long time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(time);
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * "HH:mm / 昨天 / 前天 / MM-dd"
     *
     * @param seconds
     * @return
     */
    /*public static String formatShortTime(Context context, long seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		long daySecond = calendar.get(Calendar.HOUR_OF_DAY) * 60 * 60
				+ calendar.get(Calendar.MINUTE) * 60
				+ calendar.get(Calendar.SECOND);
		long dayMillis = System.currentTimeMillis() - daySecond * 1000;
		String formatStr = "MM-dd";
		if (seconds*1000 >= dayMillis) {
			formatStr = "HH:mm";
		} else if ((dayMillis - seconds*1000) <= 24 * 60 * 60 * 1000) {
			return context.getString(R.string.common_time_yest);
		} else if ((dayMillis - seconds*1000) <= 2 * 24 * 60 * 60 * 1000) {
			return context.getString(R.string.common_time_before_yest);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		sdf.setTimeZone(TimeZone.getDefault());
		Date date = new Date(seconds * 1000);
		return sdf.format(date);
	}*/

    /**
     * "yyyy.MM.dd~yyyy.MM.dd"
     *
     * @param startTime 输入开始的时间，和时间跨度，输入如：“2014.03.01~2014.03.31”
     * @param endTime
     * @return
     */
    public static String formatMonthAndDay(long startTime, long endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        Date date1 = new Date(startTime);
        if ((endTime - startTime) > 24 * 60 * 60) {
            Date date2 = new Date(endTime);
            return sdf.format(date1) + "~" + sdf.format(date2);
        }
        return sdf.format(date1);
    }

    /**
     * "call duration"
     *
     * @param seconds
     * @return
     */
    public static String formatCallDuration(long seconds) {

        long hourValue = seconds / 3600;
        long minuteValue = (seconds - (hourValue * 3600)) / 60;
        long secondValue = (seconds - (hourValue * 3600) - (minuteValue * 60)) % 60;

        return String.format("%1$02d:%2$02d:%3$02d", hourValue, minuteValue, secondValue);
    }

    /**
     * "yyyy.MM.dd HH:mm"
     *
     * @param
     * @return
     */
    public static String cutTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(FROMAT_WITHOUT_SENCOND);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(time);
    }

    /**
     * "yyyy-MM-dd_HH-mm-ss"
     *
     * @param
     * @return
     */
    public static String formatCurrentTimeFilename(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(FROMAT_DEF);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(time);
    }


    /**
     * Returns number of milliseconds since Jan. 1, 1970, midnight.
     * Returns -1 if the date time information if not available.
     *
     * @hide
     */
    public static long getDateTime(String dateTimeString) {
        if (dateTimeString == null)
            return -1;
        ParsePosition pos = new ParsePosition(0);
        try {
            SimpleDateFormat sFormatter = new SimpleDateFormat(FROMAT_DEF);
            sFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date datetime = sFormatter.parse(dateTimeString, pos);
            if (datetime == null) return -1;
            return datetime.getTime();
        } catch (IllegalArgumentException ex) {
            return -1;
        }
    }

    public static long getDateTimeFromISO8601(String dateTimeString) {
        return getDateTimeFromat(dateTimeString, FROMAT_ISO8601);
    }

    public static long getDateTimeFromYearAndMonth(String dateTimeString) {
        return getDateTimeFromat(dateTimeString, FROMAT_YEAR_AND_MONTH);
    }


    /**
     * Returns number of milliseconds since Jan. 1, 1970, midnight.
     * Returns -1 if the date time information if not available.
     *
     * @hide
     */
    public static long getDateTimeFromat(String dateTimeString, String fromat) {
        try {
            if(dateTimeString==null)
                return 0;
            return new SimpleDateFormat(fromat).parse(dateTimeString).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * unix 时间戳转化成 ISO8601 格式标准时间
     *
     * @param timeMs time ms
     * @return ISO8601 time
     */
    public static String formatISO8601(long timeMs) {
        return formateTime(new Date(timeMs), FROMAT_ISO8601);
    }

    /**
     * date 对象转化成格式化的字符串
     *
     * @param date   date
     * @param format format string
     * @return format time
     */
    public static String formateTime(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * date 对象转化成格式化的字符串
     *
     * @param date   date
     * @param format format string
     * @return format time
     */
    public static String formateTime(long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据年 月 获取对应的月份 天数
     */
    public static int getDaysByYearMonth(long starTime) {
        Calendar a = Calendar.getInstance();
        a.setTimeInMillis(starTime);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 此方法输入所要转换的时间输入, 返回时间戳
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param sec
     * @return
     */
    public static long dateToTime(int year, int month, int day, int hour, int min, int sec) {
        SimpleDateFormat sdr = new SimpleDateFormat(FROMAT_DEF);
        Date date;
        try {
            date = sdr.parse(String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day)
                    + " " + String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec));
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static final int DAY_TIME_WEE_HOURS = 0;
    public static final int DAY_TIME_MORNING = 1;
    public static final int DAY_TIME_BEFORENOON = 2;
    public static final int DAY_TIME_NOONTIME = 3;
    public static final int DAY_TIME_AFTERNOON = 4;
    public static final int DAY_TIME_EVENING = 5;
    public static final int DAY_TIME_NIGHT = 6;
    public static final int DAY_TIME_MIDNIGHT = 7;

    public static int getDayTimeRange() {
        Date curDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTime(curDate);
        int hour = new Date(calendar.getTimeInMillis()).getHours();
        switch (hour) {
            case 3:
            case 4:
            case 5:
                return DAY_TIME_WEE_HOURS;
            case 6:
            case 7:
                return DAY_TIME_MORNING;
            case 8:
            case 9:
            case 10:
                return DAY_TIME_BEFORENOON;
            case 11:
            case 12:
                return DAY_TIME_NOONTIME;
            case 13:
            case 14:
            case 15:
            case 16:
                return DAY_TIME_AFTERNOON;
            case 17:
            case 18:
                return DAY_TIME_EVENING;
            case 19:
            case 20:
            case 21:
            case 22:
                return DAY_TIME_NIGHT;
            case 23:
            case 24:
            case 0:
            case 1:
            case 2:
            default:
                return DAY_TIME_MIDNIGHT;
        }
    }

    private static final int MonitorInterval = 2;

    public static long getMonitorTimestamp() {
        long currentTime = System.currentTimeMillis();
        //除以1000换算成秒，再除以60换算成分钟，再除以MonitorInterval，表示间隔的分钟数
        long cutTime = currentTime / 1000 / 60 / MonitorInterval;
        return cutTime;
    }


    /**
     * "yyyy-MM-dd_HH-mm-ss"
     *
     * @param seconds
     * @return
     */
    public static String formatFilename(long seconds) {
        SimpleDateFormat sdf = new SimpleDateFormat(FROMAT_DEF);
        sdf.setTimeZone(TimeZone.getDefault());
        Date date = new Date(seconds);
        return sdf.format(date);
    }


    public static boolean isAfterDate(long leftTime,long rigthTime){
        Calendar left= Calendar.getInstance();
        left.setTimeInMillis(leftTime);
        left.set(Calendar.MILLISECOND,0);
        left.set(Calendar.SECOND,0);
        left.set(Calendar.MINUTE,0);
        left.set(Calendar.HOUR_OF_DAY,0);
        Calendar right= Calendar.getInstance();
        right.setTimeInMillis(rigthTime);
        right.set(Calendar.MILLISECOND,0);
        right.set(Calendar.SECOND,0);
        right.set(Calendar.MINUTE,0);
        right.set(Calendar.HOUR_OF_DAY,0);
        return left.getTimeInMillis()>right.getTimeInMillis();
    }

    public static boolean isTheSameYear(long leftTime,long rigthTime){
        Calendar left= Calendar.getInstance();
        left.setTimeInMillis(leftTime);
        Calendar right= Calendar.getInstance();
        right.setTimeInMillis(rigthTime);
        return left.get(Calendar.YEAR)==right.get(Calendar.YEAR);
    }






}
