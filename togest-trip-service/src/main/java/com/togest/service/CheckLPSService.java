package com.togest.service;

import java.util.Map;
import java.util.Set;

import com.togest.util.Data;

public interface CheckLPSService{
	
	boolean check(Data entity, Map<String, String> map, String name, 
			int i, StringBuilder errorMsg, int status, String notFindTips, String notInputTips);
	
	public boolean checkAlias(Data entity, String name, int status);
	
	void isNotFind(boolean fg, Set<String> isNotFindSet, String name);
}
