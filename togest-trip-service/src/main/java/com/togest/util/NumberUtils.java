package com.togest.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.regex.Pattern;

public class NumberUtils {

	/**
	 * 判断当前值是否为整数
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInteger(Object value) {
		if (ObjectUtils.isNull(value)) {
			return false;
		}
		String mstr = value.toString();
		Pattern pattern = Pattern.compile("^-?\\d+{1}");
		return pattern.matcher(mstr).matches();
	}
	
	/**
	 * 百分号显示
	 * @param value
	 * @return
	 */
	public static String percent(String value) {
		String numberValue = StringUtils.msNumber(value);
		BigDecimal number = new BigDecimal("0.00");
		if (isDigit(numberValue)) {
			number = new BigDecimal(numberValue);
		}
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(1);
		return nf.format(number);
	}
	
	public static String format(String value, Integer precision) {
		String numberValue = StringUtils.msNumber(value);
		BigDecimal number = new BigDecimal("0.00");
		if (isDigit(numberValue)) {
			number = new BigDecimal(numberValue);
		}
		precision = (precision == null || precision < 0) ? 2 : precision;
		return number.setScale(precision, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 将数字格式化输出
	 * @param value
	 * @return
	 */
	public static String format(String value) {
		return format(value, 2);
	}

	public static boolean isDigit(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		Pattern pattern = Pattern.compile("^-?[0-9]*.?[0-9]*{1}");
		return pattern.matcher(value).matches();
	}

	/**
	 * 判断当前值是否为数字(包括小数)
	 * @param value
	 * @return
	 */
	public static boolean isDigit(Object value) {
		if (ObjectUtils.isNull(value)) {
			return false;
		}
		return isDigit(value.toString());
	}
	/**
	 * 将数字格式化输出
	 * @param value 需要格式化的值
	 * @param precision 精度(小数点后的位数)
	 * @return
	 */
	public static String format(Object value, Integer precision) {
		BigDecimal number = new BigDecimal("0.0");
		if (isDigit(value)) {
			number = new BigDecimal(value.toString());
		}
		precision = (precision == null || precision < 0) ? 2 : precision;
		return number.setScale(precision, BigDecimal.ROUND_HALF_UP).toString();
	}

	/**
	 * 将数字格式化输出
	 * 
	 * @param value
	 * @return
	 */
	public static String format(Object value) {
		return format(value, 2);
	}

	/**
	 * 将值转成Integer型，如果不是整数，则返回0
	 * 
	 * @param value
	 * @param replace
	 *            如果为0或者null，替换值
	 * @return
	 */
	public static Integer parseInteger(Object value, Integer replace) {
		if (!isInteger(value)) {
			return replace;
		}
		return new Integer(value.toString());
	}

	/**
	 * 将值转成Integer型，如果不是整数，则返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Integer parseInteger(Object value) {
		return parseInteger(value, 0);
	}

	/**
	 * 将值转成Long型
	 * 
	 * @param value
	 * @param replace
	 *            如果为0或者null，替换值
	 * @return
	 */
	public static Long parseLong(Object value, Long replace) {
		if (value instanceof String)
		{
			if(StringUtils.isEmpty((String)value))
			{
				return replace;
			}
			value = StringUtils.msNull((String)value);
		}
		if (!isInteger(value)) {
			return replace;
		}
		return new Long(value.toString());
	}

	/**
	 * 将值转成Long型，如果不是整数，则返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Long parseLong(Object value) {
		return parseLong(value, 0L);
	}

	/**
	 * 将值转成Double型
	 * 
	 * @param value
	 * @param replace
	 *            replace 如果为0或者null，替换值
	 * @return
	 */
	public static Double parseDouble(Object value, Double replace) {
		if (!isDigit(value)) {
			return replace;
		}
		return new Double(value.toString());
	}

	/**
	 * 将值转成Double型，如果不是整数，则返回0
	 * 
	 * @param value
	 * @return
	 */
	public static Double parseDouble(Object value) {
		return parseDouble(value, 0.0);
	}

	/**
	 * 将char型数据转成字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(char value) {
		byte[] bt = new byte[2];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}

	/**
	 * 将short型数据转成字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(short value) {
		byte[] bt = new byte[2];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}

	/**
	 * 将int型数据转成字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(int value) {
		byte[] bt = new byte[4];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}

	/**
	 * 将long型数据转成字节数组
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] toBytes(long value) {
		byte[] bt = new byte[8];
		for (int i = 0; i < bt.length; i++) {
			bt[i] = (byte) (value >>> (i * 8));
		}
		return bt;
	}
	
	/**
	 * @description:除法保留两位运算,并转换成正数(资金使用率出使用)
	 * @param v1 除数
	 * @param v2 被除数
	 * @return String
	 * @author: 钢银钱庄 祝煌寿
	 * @createTime:2016年6月29日 上午9:30:23
	 */
	public static String div(Object v1, Object v2)
	{
		if (v2 == null)
		{
			return "0.00";
		}

		double v11 = (null == v1)? 0.0 : NumberUtils.parseDouble(v1.toString().trim());
		double v22 = NumberUtils.parseDouble(v2.toString().trim());

		if (v22 == 0)
		{
			return "0.00";
		}

		BigDecimal b1 = new BigDecimal(Double.toString(v11));
		BigDecimal b2 = new BigDecimal(Double.toString(v22));
		BigDecimal multiplicand = new BigDecimal(100);

		String result = b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP).multiply(multiplicand).toString();

		return result.substring(0, result.indexOf(".") + 3);
	}
}