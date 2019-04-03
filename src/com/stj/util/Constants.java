package com.stj.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public interface Constants {

	public final static String PUBLIC_MESSAGE = "public_message";

	public final static Integer CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

	public final static DateFormat DATE_FORMAT = new SimpleDateFormat("MM-dd");
	
	public final static DateFormat DATE_FORMAT_MMDDYYYY = new SimpleDateFormat("MM/dd/yyyy");
	
//	public final static String TEE_TIME_1_GROUP_1 = "5:00";
//	public final static String TEE_TIME_1_GROUP_2 = "5:08";
//	public final static String TEE_TIME_2_GROUP_1 = "5:16";
//	public final static String TEE_TIME_2_GROUP_2 = "5:24";
//	public final static String TEE_TIME_3_GROUP_1 = "5:32";
//	public final static String TEE_TIME_3_GROUP_2 = "5:40";
	
	public final static String TEE_TIME_1_GROUP_1 = "4:45";
	public final static String TEE_TIME_1_GROUP_2 = "4:53";
	public final static String TEE_TIME_2_GROUP_1 = "5:01";
	public final static String TEE_TIME_2_GROUP_2 = "5:09";
	public final static String TEE_TIME_3_GROUP_1 = "5:17";
	public final static String TEE_TIME_3_GROUP_2 = "5:25";
}
