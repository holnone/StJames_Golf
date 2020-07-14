package com.stj.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static boolean isEqualOrAfterToday(Date date) {
		if (date == null) {
			return false;
		}
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		Calendar compareDate = Calendar.getInstance();
		compareDate.setTime(date);
		compareDate.set(Calendar.HOUR, 0);
		compareDate.set(Calendar.MINUTE, 0);
		compareDate.set(Calendar.SECOND, 0);
		compareDate.set(Calendar.MILLISECOND, 0);
		
		return !compareDate.before(now);
	}
	
	public static boolean isBefore(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		Calendar now = Calendar.getInstance();
		now.setTime(date1);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MILLISECOND, 0);
		Calendar compareDate = Calendar.getInstance();
		compareDate.setTime(date2);
		compareDate.set(Calendar.SECOND, 0);
		compareDate.set(Calendar.HOUR, 0);
		compareDate.set(Calendar.MILLISECOND, 0);
		
		return now.before(compareDate);
	}
}
