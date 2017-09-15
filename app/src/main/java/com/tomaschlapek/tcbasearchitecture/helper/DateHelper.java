package com.tomaschlapek.tcbasearchitecture.helper;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Provides methods to format dates and times.
 */
public class DateHelper {

  /* Private Constants ****************************************************************************/

  /**
   * Default date format used to format date and time.
   */
  private static final DateFormat DEFAULT_FORMAT = DateFormat.getInstance();

  /**
   * UTC time zone used to format unix timestamps.
   */
  private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

  // Time periods defined in milliseconds.
  private static final long MINUTE_MS = 60 * 1000;
  private static final long HOUR_MS = 60 * MINUTE_MS;
  private static final long DAY_MS = 24 * HOUR_MS;

  /* Public Types *********************************************************************************/

  /**
   * Desired output of formatting.
   */
  public enum Output {
    DATE,
    TIME,
    ALL,
  }

  /* Public Static Methods ************************************************************************/

  /**
   * Convenience method to {@link DateHelper#format(Output, long, int, Locale)}.
   */
  public static String format(Output output, long time) {
    return format(output, time, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#format(Output, long, int, Locale)}.
   */
  public static String format(Output output, long time, int style) {
    return format(output, time, style, Locale.getDefault());
  }

  /**
   * Creates formatted string from Unix timestamp.
   *
   * @param output Desired output of formatting.
   * @param time Unix timestamp in milliseconds.
   * @param style One of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String format(Output output, long time, int style, Locale locale) {
    return getDateFormat(output, style, locale, UTC).format(new Date(time));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#format(Output, Calendar, int, Locale)}
   */
  public static String format(Output output, Calendar calendar) {
    return format(output, calendar, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#format(Output, Calendar, int, Locale)}
   */
  public static String format(Output output, Calendar calendar, int style) {
    return format(output, calendar, style, Locale.getDefault());
  }

  /**
   * Creates formatted string from {@link Calendar} object.
   *
   * @param output Desired output of formatting.
   * @param calendar Defines point in time and timezone.
   * @param style One of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String format(Output output, Calendar calendar, int style, Locale locale) {
    return getDateFormat(output, style, locale, calendar.getTimeZone()).format(calendar.getTime());
  }


  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatWithPattern(String, long, Locale)}
   */
  public static String formatWithPattern(String pattern, long time) {
    return formatWithPattern(pattern, time, Locale.US);
  }

  /**
   * Creates formatted string from Unix timestamp using formatting pattern.
   *
   * @param pattern Pattern that defines the formatting.
   * @param time Unix timestamp in milliseconds.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatWithPattern(String pattern, long time, Locale locale) {
    DateFormat dateFormat = new SimpleDateFormat(pattern, locale);
    dateFormat.setTimeZone(UTC);
    return dateFormat.format(new Date(time));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatWithPattern(String, Calendar, Locale)}
   */
  public static String formatWithPattern(String pattern, Calendar calendar) {
    return formatWithPattern(pattern, calendar, Locale.US);
  }

  /**
   * Creates formatted string from Unix timestamp using formatting pattern.
   *
   * @param pattern Pattern that defines the formatting.
   * @param calendar Defines point in time and timezone.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatWithPattern(String pattern, Calendar calendar, Locale locale) {
    DateFormat dateFormat = new SimpleDateFormat(pattern, locale);
    dateFormat.setTimeZone(calendar.getTimeZone());
    return dateFormat.format(calendar.getTime());
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatMonth(long, int, Locale)}
   */
  public static String formatMonth(long time) {
    return formatMonth(time, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#formatMonth(long, int, Locale)}
   */
  public static String formatMonth(long time, int style) {
    return formatMonth(time, style, Locale.getDefault());
  }

  /**
   * Creates formatted string with name of month.
   *
   * @param time Unix timestamp in milliseconds.
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatMonth(long time, int style, Locale locale) {
    return getMonthDateFormat(style, locale, UTC).format(new Date(time));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatMonth(Calendar, int, Locale)}
   */
  public static String formatMonth(Calendar calendar) {
    return formatMonth(calendar, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#formatMonth(Calendar, int, Locale)}
   */
  public static String formatMonth(Calendar calendar, int style) {
    return formatMonth(calendar, style, Locale.getDefault());
  }

  /**
   * Creates formatted string with name of month.
   *
   * @param calendar Defines point in time and timezone.
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatMonth(Calendar calendar, int style, Locale locale) {
    return getMonthDateFormat(style, locale, calendar.getTimeZone()).format(calendar.getTime());
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatMonth(int, int, Locale)}
   */
  public static String formatMonth(int index) {
    return formatMonth(index, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#formatMonth(int, int, Locale)}
   */
  public static String formatMonth(int index, int style) {
    return formatMonth(index, style, Locale.getDefault());
  }

  /**
   * Creates formatted string with name of month.
   *
   * @param index Zero based index of month of year.
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatMonth(int index, int style, Locale locale) {
    Calendar calendar = Calendar.getInstance(UTC);
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    calendar.set(Calendar.MONTH, index);
    return formatMonth(calendar, style, locale);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatDay(long, int, Locale)}
   */
  public static String formatDay(long time) {
    return formatDay(time, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#formatDay(long, int, Locale)}
   */
  public static String formatDay(long time, int style) {
    return formatDay(time, style, Locale.getDefault());
  }

  /**
   * Creates formatted string with name of day.
   *
   * @param time Unix timestamp in milliseconds.
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatDay(long time, int style, Locale locale) {
    return getDayDateFormat(style, locale, UTC).format(new Date(time));
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatDay(Calendar, int, Locale)}
   */
  public static String formatDay(Calendar calendar) {
    return formatDay(calendar, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#formatDay(Calendar, int, Locale)}
   */
  public static String formatDay(Calendar calendar, int style) {
    return formatDay(calendar, style, Locale.getDefault());
  }

  /**
   * Creates formatted string with name of day.
   *
   * @param calendar Defines point in time and timezone.
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatDay(Calendar calendar, int style, Locale locale) {
    return getDayDateFormat(style, locale, calendar.getTimeZone()).format(calendar.getTime());
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatDay(int, int, Locale)}
   */
  public static String formatDay(int index) {
    return formatDay(index, DateFormat.SHORT, Locale.getDefault());
  }

  /**
   * Convenience method to {@link DateHelper#formatDay(int, int, Locale)}
   */
  public static String formatDay(int index, int style) {
    return formatDay(index, style, Locale.getDefault());
  }

  /**
   * Creates formatted string with name of day.
   *
   * @param index Zero based index of day of week.
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   *
   * @return Formatted string.
   */
  public static String formatDay(int index, int style, Locale locale) {
    Calendar calendar = Calendar.getInstance(UTC);
    calendar.set(Calendar.DAY_OF_WEEK, index + 1);
    return formatDay(calendar, style, locale);
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#formatDuration(long)}
   */
  public static String formatDuration(long time1, long time2) {
    return formatDuration(Math.abs(time1 - time2));
  }

  /**
   * Creates formatted string representing duration.
   *
   * @param duration Duration in milliseconds.
   *
   * @return Formatted string.
   */
  public static String formatDuration(long duration) {
    long days = duration / DAY_MS;
    duration %= DAY_MS;
    long hours = duration / HOUR_MS;
    duration %= HOUR_MS;
    long minutes = duration / MINUTE_MS;
    duration %= MINUTE_MS;
    StringBuilder builder = new StringBuilder();
    if ((days > 0) || (builder.length() > 0)) {
      builder.append(days);
      builder.append("d ");
    }
    if ((hours > 0) || (builder.length() > 0)) {
      builder.append(hours);
      builder.append("h ");
    }
    if ((minutes > 0) || (builder.length() > 0)) {
      builder.append(minutes);
      builder.append("m ");
    }
    return builder.toString().trim();
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Convenience method to {@link DateHelper#stringToMillis(String, String, TimeZone)}
   */
  public static Long stringToMillis(String string, String format) {
    return stringToMillis(string, format, UTC);
  }

  /**
   * Converts a string to milliseconds.
   *
   * @param string A string to be converted. Example: "1990-04-12".
   * @param format Format of the string. Example: "yyyy-MM-dd".
   * @param timeZone Output timezone.
   *
   * @return Milliseconds from January 1, 1970 or null if conversion failed.
   */
  public static Long stringToMillis(String string, String format, TimeZone timeZone) {
    DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
    dateFormat.setTimeZone(timeZone);
    try {
      return dateFormat.parse(string).getTime();
    } catch (ParseException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  /* Private Methods ******************************************************************************/

  /**
   * Constructor.
   */
  private DateHelper() {
    // Just hidden.
  }

  /**
   * Creates date format for date and time formatting.
   *
   * @param output Desired output of formatting.
   * @param style One of SHORT, MEDIUM, LONG, FULL, or DEFAULT.
   * @param locale Locale that determines formatting.
   * @param timeZone Time zone that shifts the result.
   *
   * @return Date format ready to be used for formatting.
   */
  private static DateFormat getDateFormat(Output output,
    int style, Locale locale, TimeZone timeZone) {
    DateFormat dateFormat = DEFAULT_FORMAT;
    switch (output) {
      case DATE:
        dateFormat = DateFormat.getDateInstance(style, locale);
        break;
      case TIME:
        dateFormat = DateFormat.getTimeInstance(style, locale);
        break;
      case ALL:
        dateFormat = DateFormat.getDateTimeInstance(style, style, locale);
        break;
    }
    dateFormat.setTimeZone(timeZone);
    return dateFormat;
  }

  /**
   * Creates date format for month formatting.
   *
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   * @param timeZone Time zone that shifts the result.
   *
   * @return Date format ready to be used for formatting.
   */
  private static DateFormat getMonthDateFormat(int style, Locale locale, TimeZone timeZone) {
    DateFormat dateFormat = new SimpleDateFormat((style == DateFormat.SHORT)
      ? "LLL" : "LLLL", locale);
    dateFormat.setTimeZone(timeZone);
    return dateFormat;
  }

  /**
   * Creates date format for month formatting.
   *
   * @param style One of SHORT, or LONG.
   * @param locale Locale that determines formatting.
   * @param timeZone Time zone that shifts the result.
   *
   * @return Date format ready to be used for formatting.
   */
  private static DateFormat getDayDateFormat(int style, Locale locale, TimeZone timeZone) {
    DateFormat dateFormat = new SimpleDateFormat((style == DateFormat.SHORT)
      ? "EEE" : "EEEE", locale);
    dateFormat.setTimeZone(timeZone);
    return dateFormat;
  }
}
