package com.togest.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyUtils {
	public static final int ZERO = 0;
	public static final String ZERO_STR = "0";
	
	public static final int NUM_SCAL = 0;
	public static final int AMOUNT_SCAL = 2;
	public static final int WEIGHT_SCAL = 4;

	//private MoneyUtils() {}
	
	public static Integer cop(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2))
		{
			return null;
		}
		return new BigDecimal(StringUtils.msNumber(v1)).compareTo(new BigDecimal(StringUtils.msNumber(v2)));
	}
	
	public static Boolean eq(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2))
		{
			return null;
		}
		return cop(v1, v2) == 0;
	}
	
	public static Boolean bigger(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2))
		{
			return null;
		}
		return cop(v1, v2) == 1;
	}
	
	public static Boolean bigger(BigDecimal v1, BigDecimal v2) {
		return v1.compareTo(v2) == 1;
	}

	public static Boolean litter(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2))
		{
			return null;
		}
		return cop(v1, v2) == -1;
	}

	/**
	 * 实现浮点数的加法运算功能
	 * @param v1 加数1
	 * @param v2 加数2
	 * @return v1+v2的和
	 */
	public static double add(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || !NumberUtils.isDigit(v1))
		{
			v1 = ZERO_STR;
		}
		if (StringUtils.isEmpty(v2) || !NumberUtils.isDigit(v2))
		{
			v2 = ZERO_STR;
		}
		return new BigDecimal(v1).add(new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 实现浮点数的加法运算功能
	 * @param v1 加数1
	 * @param v2 加数2
	 * @param decimal 保留小数位数
	 * @return v1+v2的和
	 */
	public static String add(String v1, String v2, int decimal) {
		return add(v1, v2, decimal, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal add(BigDecimal v1, BigDecimal v2, int decimal) {
		return v1.add(v2).setScale(decimal, RoundingMode.HALF_UP);
	}

	public static String add(String v1, String v2, int decimal, RoundingMode roundingMode) {
		if (StringUtils.isEmpty(v1) || !NumberUtils.isDigit(v1))
		{
			v1 = ZERO_STR;
		}
		if (StringUtils.isEmpty(v2) || !NumberUtils.isDigit(v2))
		{
			v2 = ZERO_STR;
		}
		return new BigDecimal(v1).add(new BigDecimal(v2)).setScale(decimal, roundingMode).toString();
	}

	/**
	 * 实现浮点数的减法运算功能
	 * @param v1 被减数
	 * @param v2 减数
	 * @return v1-v2的差
	 */
	public static double sub(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || !NumberUtils.isDigit(v1))
		{
			v1 = ZERO_STR;
		}
		if (StringUtils.isEmpty(v2) || !NumberUtils.isDigit(v2))
		{
			v2 = ZERO_STR;
		}
		return new BigDecimal(v1).subtract(new BigDecimal(v2)).doubleValue();
	}

	/**
	 * 实现浮点数的减法运算功能
	 * @param v1 被减数
	 * @param v2 减数
	 * @return v1-v2的差
	 */
	public static String sub(String v1, String v2, int decimal) {
		return sub(v1, v2, decimal, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal sub(BigDecimal v1, BigDecimal v2, int decimal) {
		return v1.subtract(v2).setScale(decimal,RoundingMode.HALF_UP);
	}
	
	public static String sub(String v1, String v2, int decimal, RoundingMode roundingMode) {
		if (StringUtils.isEmpty(v1) || !NumberUtils.isDigit(v1))
		{
			v1 = ZERO_STR;
		}
		if (StringUtils.isEmpty(v2) || !NumberUtils.isDigit(v2))
		{
			v2 = ZERO_STR;
		}
		return new BigDecimal(v1).subtract(new BigDecimal(v2)).setScale(decimal, roundingMode).toString();
	}

	/**
	 * 实现浮点数的乘法运算功能
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return v1×v2的积
	 */
	public static double multi(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || !NumberUtils.isDigit(v1) || StringUtils.isEmpty(v2) || !NumberUtils.isDigit(v2))
		{
			return ZERO;
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 实现浮点数的乘法运算功能
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return v1×v2的积
	 */
	public static String multi(String v1, String v2, int decimal) {
		return multi(v1, v2, decimal, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal multi(BigDecimal v1, BigDecimal v2, int decimal) {
		return v1.multiply(v2).setScale(decimal,RoundingMode.HALF_UP);
	}

	public static String multi(String v1, String v2, int decimal, RoundingMode roundingMode) {
		if (StringUtils.isEmpty(v1) || !NumberUtils.isDigit(v1) || StringUtils.isEmpty(v2) || !NumberUtils.isDigit(v2))
		{
			return ZERO_STR;
		}
		return new BigDecimal(v1).multiply(new BigDecimal(v2)).setScale(decimal, roundingMode).toString();
	}

	public static String multiAmount(String v1, String v2) {
		DecimalFormat formater = new DecimalFormat();
		formater.setMaximumFractionDigits(2);
		formater.setGroupingSize(0);
		formater.setRoundingMode(RoundingMode.FLOOR);
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return formater.format(b1.multiply(b2)).toString();
	}

	/**
	 * 实现浮点数的除法运算功能 当发生除不尽的情况时，精确到小数点以后scale位，后面的位数进行四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @return v1/v2的商
	 */
	public static double div(String v1, String v2) {
		if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2)) {
			return 0;
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, 5, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 实现浮点数的除法运算功能 当发生除不尽的情况时，精确到小数点以后scale位，后面的位数进行四舍五入。
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示需要精确到小数点以后几位
	 * @return v1/v2的商
	 */
	public static String div(String v1, String v2, int scale) {
		if (StringUtils.isEmpty(v1) || StringUtils.isEmpty(v2)) {
			return "";
		}
		if(v2.equals("0"))
		{
			return "0";
		}
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(v1);
		BigDecimal b2 = new BigDecimal(v2);
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 提供精确的小数位四舍五入功能
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static String round(String v, int scale) {
		if (StringUtils.isEmpty(v)) {
			return "";
		}
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(v);
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 格式化银行账号取后四位
	 * @param account
	 * @return
	 */
	public static String formatAcount(String account)
	{
		String accountSub="";
		try {
			accountSub = account.substring(account.length()-4,account.length());
		} catch (Exception e) {
			e.printStackTrace();
			return accountSub="";
		}
		return accountSub;
	}
	
	/**
	 * @Description 取绝对值
	 */
	public static String getAbsValue(String number)
	{
		if (StringUtils.isEmpty(number))
		{
			return ZERO_STR;
		}
		return new BigDecimal(StringUtils.msNumber(number)).abs().toString();
	}
}