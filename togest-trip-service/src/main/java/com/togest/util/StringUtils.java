package com.togest.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtils
{
	public static final Logger logger = LoggerFactory.getLogger(StringUtils.class);
	
	public static final String EMPTY = "";
	public static final String ZERO_DOUBLE_STR = "0.00";
	public static final String ZERO_INTEGER_STR = "0";

	public static boolean isNull(String str)
	{
		return str == null;
	}

	public static boolean isNotNull(String str)
	{
		return !isNull(str);
	}

	public static String msNull(String str)
	{
		return isEmpty(str) ? EMPTY : str.trim();
	}

	public static String msNull(String str, String val)
	{
		return isEmpty(str) ? msNull(val) : str.trim();
	}

	public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(str.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

	public static String msInteger(String str)
	{
		if (isEmpty(str))
		{
			return ZERO_INTEGER_STR;
		}
		String s = str.replaceAll("\\,", "").trim();
		try
		{
			new BigDecimal(s);
		}
		catch (Exception e)
		{
			s = ZERO_INTEGER_STR;
			logger.error("[" + str + "]:::" + e.getMessage(), e);
		}
		return s;
	}

	public static String msNumber(String str)
	{
		if (isEmpty(str))
		{
			return ZERO_DOUBLE_STR;
		}
		String s = str.replaceAll("\\,", "").trim();
		try
		{
			new BigDecimal(s);
		}
		catch (Exception e)
		{
			s = ZERO_DOUBLE_STR;
			logger.error("[" + str + "]:::" + e.getMessage(), e);
		}
		return s;
	}

	public static String nullToEmpty(String str)
	{
		return isNull(str) ? EMPTY : str;
	}

	public static boolean isEmpty(String str)
	{
		return (isNull(str)) || (str.trim().isEmpty());
	}

	public static boolean isNotEmpty(String str)
	{
		return !isEmpty(str);
	}

	public static boolean isTrimEmpty(String str)
	{
		return (isNull(str)) || (str.trim().isEmpty());
	}

	public static boolean isNotTrimEmpty(String str)
	{
		return !isTrimEmpty(str);
	}

	public static String trim(String str)
	{
		return isTrimEmpty(str) ? EMPTY : str;
	}

	public static boolean isIn(String str, String... args)
	{
		boolean flag = false;
		if (StringUtils.isNotEmpty(str) && args != null && args.length > 0)
		{
			for (String arg : args)
			{
				if (msNull(str).equals(arg))
				{
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	public static String encode(String str)
	{
		try
		{
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			logger.error(e.getMessage() + "::" + str, e);
		}
		return str;
	}

	public static String decode(String str)
	{
		try
		{
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			logger.error(e.getMessage() + "::" + str, e);
		}
		return str;
	}

	public static String toObjectString(Object object)
	{
		return ToStringBuilder.reflectionToString(object);
	}

	public static boolean equals(String s1, String s2)
	{
		if (s1 == null)
		{
			return s2 == null;
		}

		return s1.equals(s2);
	}

	public static boolean equalsIgnoreCase(String s1, String s2)
	{
		if (s1 == null)
		{
			return s2 == null;
		}

		return s1.equalsIgnoreCase(s2);
	}

	public static String replace(String string, String oldString,
			String newString)
	{
		if (string == null)
		{
			return EMPTY;
		}

		int i = 0;

		if ((i = string.indexOf(oldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);

			int oldStrLength = oldString.length();
			i += oldStrLength;
			int j = i;

			while ((i = string.indexOf(oldString, i)) > 0)
			{
				buf.append(string2, j, i - j).append(newString2);
				i += oldStrLength;
				j = i;
			}

			return buf.append(string2, j, string2.length - j).toString();
		}

		return string;
	}

	public static String replace(String string, String oldString,
			String newString, int[] count)
	{
		if (string == null)
		{
			return EMPTY;
		}

		int i = 0;

		if ((i = string.indexOf(oldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);

			int counter = 1;
			int oldStrLength = oldString.length();
			i += oldStrLength;
			int j = i;

			while ((i = string.indexOf(oldString, i)) > 0)
			{
				counter++;
				buf.append(string2, j, i - j).append(newString2);
				i += oldStrLength;
				j = i;
			}

			count[0] = counter;
			buf.append(string2, j, string2.length - j);

			return buf.toString();
		}

		return string;
	}

	public static String replaceIgnoreCase(String string, String oldString,
			String newString)
	{
		if (string == null)
		{
			return EMPTY;
		}

		String lcString = string.toLowerCase();
		String lcOldString = oldString.toLowerCase();

		int i = 0;

		if ((i = lcString.indexOf(lcOldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);

			int oldStrLength = oldString.length();
			i += oldStrLength;
			int j = i;

			while ((i = lcString.indexOf(lcOldString, i)) > 0)
			{
				buf.append(string2, j, i - j).append(newString2);
				i += oldStrLength;
				j = i;
			}

			return buf.append(string2, j, string2.length - j).toString();
		}

		return string;
	}

	public static String replaceIgnoreCase(String string, String oldString,
			String newString, int[] count)
	{
		if (string == null)
		{
			return EMPTY;
		}

		String lcString = string.toLowerCase();
		String lcOldString = oldString.toLowerCase();

		int i = 0;

		if ((i = lcString.indexOf(lcOldString, i)) >= 0)
		{
			char[] string2 = string.toCharArray();
			char[] newString2 = newString.toCharArray();

			StringBuilder buf = new StringBuilder(string2.length);
			buf.append(string2, 0, i).append(newString2);

			int counter = 1;
			int oldStrLength = oldString.length();
			i += oldStrLength;
			int j = i;

			while ((i = lcString.indexOf(lcOldString, i)) > 0)
			{
				counter++;
				buf.append(string2, j, i - j).append(newString2);
				i += oldStrLength;
				j = i;
			}

			count[0] = counter;
			buf.append(string2, j, string2.length - j);

			return buf.toString();
		}

		return string;
	}

	public static int strLength(String s, String charsetName)
	{
		if (isEmpty(s))
		{
			return 0;
		}

		try
		{
			return s.getBytes(charsetName).length;
		} catch (UnsupportedEncodingException e)
		{
		}
		return s.getBytes().length;
	}

	public static String substring(String s, int length, String charsetName)
	{
		try
		{
			byte[] bytes = nullToEmpty(s).getBytes(charsetName);

			if (bytes.length <= length)
			{
				return s;
			}

			if (length < 1)
			{
				return EMPTY;
			}

			int len = s.length();

			for (int i = 0; i < len; i++)
			{
				int iDestLength = strLength(s.substring(0, i + 1), charsetName);

				if (iDestLength > length)
				{
					if (i == 0)
					{
						return EMPTY;
					}

					return s.substring(0, i);
				}

			}

			return s;
		} catch (UnsupportedEncodingException ex)
		{
		}
		return EMPTY;
	}

	public static String substring(String s, int length, String dot,
			String charsetName)
	{
		byte[] bytes = nullToEmpty(s).getBytes();

		if (bytes.length <= length)
		{
			return s;
		}

		int len = length - dot.length();

		if (len < 1)
		{
			len = 1;
		}

		return new StringBuilder().append(substring(s, len, charsetName))
				.append(dot).toString();
	}

	public static String filterSpec(String spec)
	{
		return msNull(spec).replaceAll("^Φ", "").replaceAll("\\*[A-Z]+$", "");
	}

	public static String upperMaterial(String material)
	{
		return msNull(material).toUpperCase();
	}

	public static String md5(String... ss)
	{
		if (ss != null) {
			StringBuffer buf = new StringBuffer();
			for (String s : ss) {
				buf.append(msNull(s));
			}
			return md5(buf.toString());
		}
		return EMPTY;
	}

	public static String md5Source(int type, String... ss)
	{
		if (ss != null) {
			StringBuffer buf = new StringBuffer();
			for (int i=0; i<ss.length; i++) {
				if (i == 1) {
					buf.append(filterSpec(ss[i]));
				} if (i == 2) {
					buf.append(upperMaterial(ss[i]));
				} else {
					buf.append(msNull(ss[i]));
				}
			}
			
			return buf.toString();
		}
		return EMPTY;
	}

	public static String md5(int type, String... ss)
	{
		String md5Source = md5Source(type, ss);
		if (StringUtils.isNotEmpty(md5Source)) {
			return md5(md5Source);
		}
		return EMPTY;
	}

	/**
	 * 获取当前时间的yyyy/MM/dd/字符串,eg:2016/11/29/
	 * @return
	 */
	public static String getYMDStr()
	{
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		return year + "/" + month + "/" + day + "/";
	}
}
