package com.togest.util;

import java.util.Calendar;
import java.util.Date;

import com.togest.common.util.DateUtils;
import com.togest.common.util.string.StringUtil;

public class PicIndexUtil {

	public static Double convertGlb(String glb) {
		Double km = null;
		if (StringUtil.isBlank(glb)) {
			return km;
		}
		String dataRegx = "^K(\\d+)$";
		String dataRegx1 = "^K(\\d+)\\.(\\d+)$";
		String dataRegx2 = "^K(\\d+)\\+(\\d+)$";
		String dataRegx3 = "^K(\\d+)\\+(\\d+)\\.(\\d+)$";

		if (glb.matches(dataRegx)) {
			km = Double.valueOf(glb.replaceAll("K", ""));
		} else if (glb.matches(dataRegx1)) {
			km = Double.valueOf(glb.replaceAll("K", ""));
		} else if (glb.matches(dataRegx2)) {
			km = Double.valueOf(glb.replaceAll("K", "").replaceAll("\\+", "."));
		} else if (glb.matches(dataRegx3)) {
			km = Double.valueOf(glb.replaceAll("\\.", "").replaceAll("K", "")
					.replaceAll("\\+", "."));
		} else {
			km = Double.valueOf(glb);
		}
		return km;
	}

	public static Date processDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date convertDate(String dateStr) {
		Date date = null;
		if (StringUtil.isBlank(dateStr)) {
			return date;
		}
		String dateRegx = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		String dateRegx1 = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\s([0-5]\\d):([0-5]\\d)";
		String dateRegx2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\s([0-5]\\d):([0-5]\\d):([0-5]\\d)";
		String dateRegx3 = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\_([0-5]\\d):([0-5]\\d)";
		String dateRegx4 = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\_([0-5]\\d):([0-5]\\d):([0-5]\\d)";
		String dateRegx5 = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\_([0-5]\\d)-([0-5]\\d)";
		String dateRegx6 = "[0-9]{4}-[0-9]{2}-[0-9]{2}\\_([0-5]\\d)-([0-5]\\d)-([0-5]\\d)";
		String dateRegx7 = "[0-9]{4}[0-9]{2}[0-9]{2}";
		String dateRegx8 = "[0-9]{4}[0-9]{2}[0-9]{2}([0-5]\\d)([0-5]\\d)";
		String dateRegx9 = "[0-9]{4}[0-9]{2}[0-9]{2}\\s([0-5]\\d)([0-5]\\d)";
		String dateRegx10 = "[0-9]{4}[0-9]{2}[0-9]{2}([0-5]\\d)([0-5]\\d)([0-5]\\d)";
		String dateRegx11 = "[0-9]{4}[0-9]{2}[0-9]{2}\\s([0-5]\\d)([0-5]\\d)([0-5]\\d)";
		if (dateStr.matches(dateRegx)) {
			date = DateUtils.parseDatetime(dateStr, "yyyy-MM-dd");
		} else if (dateStr.matches(dateRegx1)) {
			date = DateUtils.parseDatetime(dateStr, "yyyy-MM-dd HH:mm");
		} else if (dateStr.matches(dateRegx2)) {
			date = DateUtils.parseDatetime(dateStr, "yyyy-MM-dd HH:mm:ss");
		} else if (dateStr.matches(dateRegx3)) {
			date = DateUtils.parseDatetime(dateStr, "yyyy-MM-dd_HH:mm");
		} else if (dateStr.matches(dateRegx4)) {
			date = DateUtils.parseDatetime(dateStr, "yyyy-MM-dd_HH:mm:ss");
		} else if (dateStr.matches(dateRegx5)) {
			date = DateUtils.parseDatetime(dateStr, "yyyy-MM-dd_HH-mm");
		} else if (dateStr.matches(dateRegx6)) {
			date = DateUtils.parseDatetime(dateStr, "yyyy-MM-dd_HH-mm-ss");
		} else if (dateStr.matches(dateRegx7)) {
			date = DateUtils.parseDatetime(dateStr, "yyyyMMdd");
		} else if (dateStr.matches(dateRegx8)) {
			date = DateUtils.parseDatetime(dateStr, "yyyyMMddHHmm");
		} else if (dateStr.matches(dateRegx9)) {
			date = DateUtils.parseDatetime(dateStr, "yyyyMMdd HHmm");
		} else if (dateStr.matches(dateRegx10)) {
			date = DateUtils.parseDatetime(dateStr, "yyyyMMddHHmmss");
		} else if (dateStr.matches(dateRegx11)) {
			date = DateUtils.parseDatetime(dateStr, "yyyyMMdd HHmmss");
		} else {
			return null;
		}
		return date;
	}

}
