package com.togest.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ExpressionUtil {

	public static boolean calculator(String formau){
		Boolean callbackvalue = false;
		try {
			ScriptEngineManager sem = new ScriptEngineManager();
			ScriptEngine se = sem.getEngineByName("javascript");
			se.eval(" var strname =" + formau);
			se.eval("function calculator( ) { " + " return strname}");
			Invocable invocableEngine = (Invocable) se;
			callbackvalue = (Boolean) invocableEngine
					.invokeFunction("calculator");
		} catch (NoSuchMethodException | ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return callbackvalue;
	}
}
