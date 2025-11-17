package com.base.common.util;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author : AnhNT
 * @since : 10/11/2021, Wed
 */
public class DateUtil {

	public static final String ICT_ZONE_STR="Asia/Jakarta";
	public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_DATE_HH_MM_DDMMYYYY = "HH:mm - dd/MM/yyyy";
	public static final String FORMAT_DATE_DD_MM_YY_HH_MM_SS = "dd/MM/yy HH:mm:ss";
	public static final String FORMAT_DATE_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";
	public static final String FORMAT_DATE_YYYY_MM_DD_HH_MM_SS = "yyyy-mm-dd hh:mm:ss:SSS";
	public static final String FORMAT_DATE_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String FORMAT_DATE_DDMMYY = "ddMMyy";
	public static final String FORMAT_DATE_DDMMYYYY = "ddMMyyyy";
	public static final ZoneId ZONE_ID = ZoneId.of("UTC");
	public static final ZoneId ICT_ZONE_ID = ZoneId.of(ICT_ZONE_STR);
	public static final ZoneId SYS_ZONE_ID = ZoneId.systemDefault();
	public static final Clock offsetClockICT = Clock.offset(Clock.systemUTC(), Duration.ofHours(+7));

	/**
	 * Get current date time
	 *
	 * @return Local Date Time
	 */
	public static LocalDateTime getCurrentDateTime() {
		final long timestamp = System.currentTimeMillis();
		return convertLongToLdtUTC(timestamp);
	}

	/**
	 * Get current date time by ICT
	 *
	 * @return Local Date Time
	 */
	public static LocalDateTime getCurrentDateTimeICT() {
		final long timestamp = System.currentTimeMillis();
		return convertLongToLdtICT(timestamp);
	}

	/**
	 * Get current date by ICT
	 *
	 * @return Local Date
	 */
	public static LocalDate getCurrentDateICT() {
		final long timestamp = System.currentTimeMillis();
		return convertLongToLdICT(timestamp);
	}

	/**
	 * Get Current Millis
	 *
	 * @return Long
	 */
	public static Long getCurrentMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * Convert Ldt To Long UTC
	 *
	 * @param ldt LocalDateTime
	 * @return Long
	 */
	public static Long convertLdtToLongUTC(LocalDateTime ldt) {
		if (ldt == null) {
			return null;
		} else {
			return ldt.atZone(ZONE_ID).toInstant().toEpochMilli();
		}
	}

	/**
	 * Convert Ldt To Long ICT
	 *
	 * @param ldt LocalDateTime
	 * @return Long
	 */
	public static Long convertLdtToLongICT(LocalDateTime ldt) {
		if (ldt == null) {
			return null;
		} else {
			return ldt.atZone(ICT_ZONE_ID).toInstant().toEpochMilli();
		}
	}

	/**
	 * Convert Ldt To Long
	 *
	 * @param ldt LocalDateTime
	 * @return Long
	 */
	public static Long convertLdtToLong(LocalDateTime ldt) {
		if (ldt == null) {
			return null;
		} else {
			return ldt.atZone(SYS_ZONE_ID).toInstant().toEpochMilli();
		}
	}

	/**
	 * Convert Long To Ldt UTC
	 *
	 * @param longDateTime Long
	 * @return LocalDateTime
	 */
	public static LocalDateTime convertLongToLdtUTC(Long longDateTime) {
		if (longDateTime == null) {
			return null;
		} else {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(longDateTime), ZONE_ID);
		}
	}

	/**
	 * Convert Long To Ld ICT
	 *
	 * @param epochMilli Long
	 * @return LocalDate
	 */
	public static LocalDate convertLongToLdICT(Long epochMilli) {
		if (epochMilli == null) {
			return null;
		} else {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ICT_ZONE_ID).toLocalDate();
		}
	}

	/**
	 * Convert Long To Ldt ICT
	 *
	 * @param epochMilli Long
	 * @return LocalDate
	 */
	public static LocalDateTime convertLongToLdtICT(Long epochMilli) {
		if (epochMilli == null) {
			return null;
		} else {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ICT_ZONE_ID);
		}
	}
	
	/**
	 * Convert Long To Ldt
	 *
	 * @param epochMilli Long
	 * @return LocalDate
	 */
	public static LocalDateTime convertLongToLdt(Long epochMilli) {
		if (epochMilli == null) {
			return null;
		} else {
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), SYS_ZONE_ID);
		}
	}

	/**
	 * Argument.Datetime is compared to see if it is a future datetime.
	 *
	 * @param longDateTime Long
	 * @return Current date and time < argument.date and time
	 */
	public static boolean isFuture(Long longDateTime) {
		if (longDateTime == null) {
			throw new IllegalArgumentException();
		}
		return getCurrentMillis() < longDateTime;
	}

	/**
	 * Argument.Date and time are compared to see if they are in the past.
	 *
	 * @param longDateTime Long
	 * @return argument.datetime < current datetime
	 */
	public static boolean isPast(Long longDateTime) {
		return !isFuture(longDateTime);
	}

	/**
	 * Argument.Datetime is compared to see if it is a future datetime.
	 *
	 * @param datetime
	 * @return Current date and time < argument.date and time
	 */
	public static boolean isFuture(LocalDateTime datetime) {
		if (datetime == null) {
			throw new IllegalArgumentException();
		}
		return datetime.isAfter(getCurrentDateTime());
	}

	/**
	 * Argument.Date and time are compared to see if they are in the past.
	 *
	 * @param datetime
	 * @return argument.datetime < current datetime
	 */
	public static boolean isPast(LocalDateTime datetime) {
		return !isFuture(datetime);
	}

	/**
	 * Argument.Date and time is compared with the current date and time (ICT) to determine if it is in the future.
	 *
	 * @param datetime
	 * @return Current date and time < argument.date and time
	 */
	public static boolean isFutureInICT(LocalDateTime datetime) {
		if (datetime == null) {
			throw new IllegalArgumentException();
		}
		return datetime.isAfter(getCurrentDateTimeICT());
	}

	/**
	 * Argument.Date and time is compared with the current date and time (ICT) to determine if it is in the past.
	 *
	 * @param datetime
	 * @return argument.datetime < current datetime
	 */
	public static boolean isPastInICT(LocalDateTime datetime) {
		return !isFutureInICT(datetime);
	}

	/**
	 * Convert from utc time to ict time.
	 *
	 * @param utcDateTime utc time
	 * @return ict time
	 */
	public static LocalDateTime convertUtcToIct(LocalDateTime utcDateTime) {
		if (utcDateTime == null) {
			throw new IllegalArgumentException();
		}
		ZonedDateTime utc = utcDateTime.atZone(ZONE_ID);
		return utc.withZoneSameInstant(ICT_ZONE_ID).toLocalDateTime();
	}

	/**
	 * Convert from ict time to utc time.
	 *
	 * @param ictDateTime ict time
	 * @return ict time
	 */
	public static LocalDateTime convertIctToUtc(LocalDateTime ictDateTime) {
		if (ictDateTime == null) {
			throw new IllegalArgumentException();
		}
		ZonedDateTime utc = ictDateTime.atZone(ICT_ZONE_ID);
		return utc.withZoneSameInstant(ZONE_ID).toLocalDateTime();
	}

	/**
	 * sub day
	 * 
	 * @param d1 date 1
	 * @param d2 date 2
	 * @return long
	 */
	public static long subDay(Date d1, Date d2) {
		LocalDate day1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate day2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		Duration diff = Duration.between(day1.atStartOfDay(), day2.atStartOfDay());
		return diff.toDays();
	}

	/**
	 * Convert util date to LocalDateTime
	 *
	 * @param dateToConvert Date
	 * @return LocalDateTime
	 */
	public static LocalDateTime convertUtilDateToLocalDateTime(Date dateToConvert) {
		return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
	}

	public static Instant getCurrentInstantUTC() {
		return Instant.now();
	}

	public static Instant getCurrentInstantICT() {
		return Instant.now(offsetClockICT);
	}

	/**
	 * sub seconds
	 *
	 * @param startDate LocalDateTime 1
	 * @param endDate LocalDateTime 2
	 * @return long
	 */
	public static Integer subSeconds(LocalDateTime startDate, LocalDateTime endDate) {
		if(endDate.isBefore(startDate)){
			return 0;
		}
		Long startTime = startDate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
		Long endTime = endDate.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
		Long result = endTime - startTime;
		return result.intValue();
	}

	/**
	 * convert LocalDateTime to String
	 *
	 * @param localDateTime LocalDateTime
	 * @param format String
	 * @return long
	 */
	public static String convertToString(LocalDateTime localDateTime, String format) {
		if(localDateTime == null){
			return null;
		}
		if(StringUtils.isEmpty(format)) {
			format = FORMAT_DATE_DD_MM_YYYY;
		}
		return localDateTime.format(DateTimeFormatter.ofPattern(format));
	}

	/**
	 * convert Timestamp to LocalDateTime with ZONE_ID
	 * @param timestamp
	 * @return
	 */
	public static LocalDateTime convertTSToLdt(Timestamp timestamp) {
		if(timestamp == null) {
			return null;
		}
		return LocalDateTime.ofInstant(timestamp.toInstant(), ZONE_ID);
	}

	/**
	 * convert Timestamp to LocalDateTime with ICT_ZONE_ID
	 * @param timestamp
	 * @return
	 */
	public static LocalDateTime convertTSToLdtICT(Timestamp timestamp) {
		if(timestamp == null) {
			return null;
		}
		return LocalDateTime.ofInstant(timestamp.toInstant(), ICT_ZONE_ID);
	}

	/**
	 * convert Timestamp to Date
	 * @param timestamp
	 * @return
	 */
	public static Date convertTSToDate(Timestamp timestamp) {
		if(timestamp == null) {
			return null;
		}
		return Date.from(timestamp.toInstant());
	}

	/**
	 * get current Timestamp UTC
	 * @return
	 */
	public static Timestamp getCurrentTimeStampUTC() {
		return Timestamp.from(Instant.now().atZone(ZONE_ID).toInstant());
	}

	/**
	 * get current Timestamp ITC
	 * @return
	 */
	public static Timestamp getCurrentTimeStampICT() {
		return Timestamp.from(Instant.now(offsetClockICT));
	}


	/**
	 * get current Timestamp UTC
	 * @return
	 */
	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}


	/**
	 * get current Timestamp ITC from LocalDateTime
	 * @param localDateTime
	 * @return
	 */
	public static Timestamp convertLdtToTs(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return null;
		}
		return Timestamp.valueOf(localDateTime);
	}

	/**
	 * convert String to LocalDateTime
	 * @param str
	 * @param format
	 * @return
	 */
	public static LocalDateTime convertStringToLdt(String str, String format) {
		if(StringUtils.isEmpty(str) || StringUtils.isEmpty(format)) {
			return null;
		}
		return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(format));
	}

	/**
	 * get today UTC
	 * @return
	 */
	public static LocalDateTime getTodayUTC() {
		return LocalDate.now().atStartOfDay(ZONE_ID).toLocalDateTime();
	}

	/**
	 * get today ICT
	 * @return
	 */
	public static LocalDateTime getTodayICT() {
		return LocalDate.now().atStartOfDay(ICT_ZONE_ID).toLocalDateTime();
	}

	/**
	 * get start of day ICT
	 * @return
	 */
	public static LocalDateTime getStartOfToDayICT() {
		return LocalDateTime.now().toLocalDate().atStartOfDay(ICT_ZONE_ID).toLocalDateTime();
	}

	/**
	 * get start of day ICT
	 * @return
	 */
	public static LocalDateTime getStartOfToDayUTC() {
		return LocalDateTime.now().toLocalDate().atStartOfDay(ZONE_ID).toLocalDateTime();
	}

	/**
	 * get start of day ICT
	 * @return
	 */
	public static LocalDateTime getStartOfDayICT(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return null;
		}
		return localDateTime.toLocalDate().atStartOfDay(ICT_ZONE_ID).toLocalDateTime();
	}

	/**
	 * get start of day UTC
	 * @return
	 */
	public static LocalDateTime getStartOfDayUTC(LocalDateTime startDate) {
		if(startDate == null) {
			return null;
		}
		return startDate.toLocalDate().atStartOfDay(ZONE_ID).toLocalDateTime();
	}

	/**
	 * get end of day ICT
	 * @return
	 */
	public static LocalDateTime getEndOfDayICT(LocalDateTime startDate) {
		if(startDate == null) {
			return null;
		}
		return startDate.toLocalDate().plusDays(1).atStartOfDay(ICT_ZONE_ID).toLocalDateTime().minusSeconds(1);
	}

	/**
	 * get end of day UTC
	 * @return
	 */
	public static LocalDateTime getEndOfDayUTC(LocalDateTime startDate) {
		if(startDate == null) {
			return null;
		}
		return startDate.toLocalDate().plusDays(1).atStartOfDay(ZONE_ID).toLocalDateTime().minusSeconds(1);
	}

	/**
	 * convert LocalDate to Long seconds
	 * @param localDate
	 * @return
	 */
	public static Long convertLdToLongUTC(LocalDate localDate) {
		if(localDate == null) {
			return null;
		}
		return localDate.atStartOfDay().atZone(ZONE_ID).toInstant().getEpochSecond();
	}

	/**
	 * convert LocalDate to Long seconds
	 * @param localDate
	 * @return
	 */
	public static Long convertLdToLongICT(LocalDate localDate) {
		if(localDate == null) {
			return null;
		}
		return localDate.atStartOfDay().atZone(ICT_ZONE_ID).toInstant().getEpochSecond();
	}

	/**
	 *
	 * @param localDateTime
	 * @return
	 */
	public static Long convertLdtToLongSecond(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return null;
		}
		return localDateTime.atZone(ZONE_ID).toInstant().getEpochSecond();
	}

	/**
	 *
	 * @param localDateTime
	 * @return
	 */
	public static Long convertLdtToLongSecondICT(LocalDateTime localDateTime) {
		if(localDateTime == null) {
			return null;
		}
		return localDateTime.atZone(ICT_ZONE_ID).toInstant().getEpochSecond();
	}

	/**
	 *
	 * @return
	 */
	public static Date getCurrentDate() {
		LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
		return Date.from(currentDate.atStartOfDay().atZone(ZONE_ID).toInstant());
	}

	/**
	 *
	 * @return
	 */
	public static Date minusDays(Long days) {
		LocalDate currentDate = Instant.now().atZone(ZoneId.systemDefault()).toLocalDate();
		return Date.from(currentDate.atStartOfDay().minusDays(days).atZone(ZONE_ID).toInstant());
	}

	public static Long generatingRandom(LocalDateTime dateTime) {
		return convertLdtToLongUTC(dateTime) + randBetween(100l, 999l);
	}

	public static Long randBetween(Long start, Long end) {
		return start + Math.round(Math.random() * (end - start));
	}


	/**
	 * get Duration Time with startTime and endTime
	 * @param fromDateTime
	 * @param toDateTime
	 * @return
	 */
	public static String getDuration(LocalDateTime fromDateTime, LocalDateTime toDateTime) {

		if(fromDateTime == null || toDateTime == null) {
			return "00:00";
		}


		LocalDateTime tempDateTime = LocalDateTime.from( fromDateTime );
		long years = tempDateTime.until( toDateTime, ChronoUnit.YEARS );
		tempDateTime = tempDateTime.plusYears( years );
		String duration = "";
		if(years > 0) {
			duration += String.format("%02d", years) + ":";
		}
		long months = tempDateTime.until( toDateTime, ChronoUnit.MONTHS );
		tempDateTime = tempDateTime.plusMonths( months );
		if(months > 0) {
			duration += String.format("%02d", months) + ":";
		}
		long days = tempDateTime.until( toDateTime, ChronoUnit.DAYS );
		tempDateTime = tempDateTime.plusDays( days );
		if(days > 0) {
			duration += String.format("%02d", days) + ":";
		}
		long hours = tempDateTime.until( toDateTime, ChronoUnit.HOURS );
		tempDateTime = tempDateTime.plusHours( hours );
		if(hours > 0) {
			duration += String.format("%02d", hours) + ":";
		}
		long minutes = tempDateTime.until( toDateTime, ChronoUnit.MINUTES );
		tempDateTime = tempDateTime.plusMinutes( minutes );
		if(minutes < 0) minutes = 0l;
		duration += String.format("%02d", minutes) + ":";
		long seconds = tempDateTime.until( toDateTime, ChronoUnit.SECONDS );
		if(seconds < 0) seconds = 0l;
		duration += String.format("%02d", seconds);
		return duration;
	}
}
