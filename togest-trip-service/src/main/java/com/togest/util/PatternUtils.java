package com.togest.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.togest.common.util.string.StringUtil;

public class PatternUtils {
	public static final String REGEX_PATTERN = "^(\\w)?(\\d+((\\.|\\+)\\d+)?)$";
	
	public static boolean pattern(String str) {
		if (StringUtil.isNotBlank(str)) {
			Pattern p = Pattern.compile(REGEX_PATTERN);
			Matcher m = p.matcher(str);
			return m.find();
		}
		return false;
	}
	
	public static String kilometerStandard(String str) {
		if (StringUtil.isNotBlank(str)) {
			str = str.replace("K", "").replace("k", "").replace("+", ".");
		}
		return str;
	}
	
	//k232+42.235转为数字
	public static String kilometerStandardToNumber(String str) {
		if (StringUtil.isNotBlank(str)) {
			str = str.replace("K", "").replace("k", "").replace("+", ".");
			if(str.indexOf(".") != -1 && str.indexOf(".",str.indexOf(".")+1) != -1) {
				String digitNumber = str.substring(0, str.indexOf("."));
				String xsNumberStr = str.substring(str.indexOf(".") + 1);
				String xsNumber = MoneyUtils.div(xsNumberStr, ""+Math.pow(10, xsNumberStr.length() - 1), 10);
				return MoneyUtils.add(digitNumber, xsNumber, 10);
			}
		}
		return str;
	}
	
	public static String returnGlb(String glb) {
		if (StringUtil.isNotBlank(glb)) {
			glb = glb.replaceAll("[^\\d.+]", "");
			if(-1 == glb.indexOf("+")) {
				glb = glb.replace(".", "+");
			}
			glb = new StringBuffer("K").append(glb).toString();
		}
		return glb;
	}
	
	public static String keepThreeXS(String str) {
		if(str.lastIndexOf(".") != -1) {
			if(str.length() -1 - str.lastIndexOf(".") > 3) {
				str = new BigDecimal(str).setScale(3, BigDecimal.ROUND_HALF_UP).toString();
			}
		}
		return new BigDecimal(str).toString();
	}
	
	public static void main(String[] args) {
//		System.out.println(pattern("K1361.666"));
//		System.out.println(pattern("K112+568"));
//		System.out.println(pattern("0.32"));
//		System.out.println(pattern("0"));
		System.out.println(returnGlb("k1308.953"));
		System.out.println(returnGlb("k1308+953"));
		System.out.println(keepThreeXS("234.5567"));
		System.out.println(keepThreeXS("234.57"));
		System.out.println(kilometerStandardToNumber("k1234+56.78"));
		System.out.println(kilometerStandardToNumber("k1234+567.8"));
		System.out.println(kilometerStandardToNumber("k1234+5678"));
		System.out.println(kilometerStandardToNumber("k234+567"));
	}
}
