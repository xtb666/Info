package com.togest.util;

import javax.servlet.http.HttpServletRequest;

import com.togest.common.util.string.StringUtil;
import com.togest.config.DataScope;

public class DataRightUtil {

	public static Data look(HttpServletRequest request, Data data) {
		UserInfo user = TokenUtil.getUser(request);
		if (user.getDeptTypeId().equals(DeptType.Gdc.getStatus())) {// 处登录
			return data;
		} else {
			data.setSectionId(user.getSectionId());
			return data;
		}
	}

	public static Data look(HttpServletRequest request, String code, Data data) {
		UserInfo user = TokenUtil.getUser(request);
		String dataScope = TokenUtil.getDataScopeTokenData(request, code);
		boolean isEmpty = StringUtil.isBlank(data.getDid());
		if (StringUtil.isBlank(dataScope)) {
			data.setSectionId(user.getSectionId());
			if (isEmpty) {
				data.setDid(user.getDeptId());// 默认本部门
			}
		} else if (DataScope.AllScope.getStatus().equals(dataScope)) {// 全局
			data.setIsChild(true);
			return data;
		} else {
			data.setSectionId(user.getSectionId());
			if (DataScope.SectionScope.getStatus().equals(dataScope)) {// 全段
				data.setIsChild(true);
			} else if (DataScope.DeptScope.getStatus().equals(dataScope)) {
				if (isEmpty) {
					data.setDid(user.getDeptId());
				}
			} else {// 本部门及下属部门
				data.setIsChild(true);
				if (isEmpty) {
					data.setDid(user.getDeptId());
				}
			}

		}

		return data;
	}

}
