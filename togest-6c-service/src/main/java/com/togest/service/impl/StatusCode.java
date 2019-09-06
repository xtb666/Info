package com.togest.service.impl;

import com.google.common.collect.ImmutableMap;
import com.togest.model.resposne.RestStatus;

public enum StatusCode implements RestStatus {
	/**
     * 参数类型非法，常见于SpringMVC中String无法找到对应的enum而抛出的异常
     */
    INVALID_PARAMS_CONVERSION(40002, "参数类型非法"),

    // 41xxx 请求方式出错
    /**
     * http media type not supported
     */
    HTTP_MESSAGE_NOT_READABLE(41001, "HTTP消息不可读"),

    /**
     * 请求方式非法
     */
    REQUEST_METHOD_NOT_SUPPORTED(41002, "不支持的HTTP请求方法"),
    

    // 成功接收请求, 但是处理失败
    /**
     * Duplicate Key
     */
    DUPLICATE_KEY(42001, "操作过快, 请稍后再试"),

    // 50xxx 服务端异常
    /**
     * 用于处理未知的服务端错误
     */
    SERVER_UNKNOWN_ERROR(50001, "服务器繁忙, 请稍后再试"),
    DATA_CONDITION_EMPTY(10002, "请求数据参数为空"),
	ID_DATA_EMPTY(10003, "通过ID查找数据为空"),
	PARAM_DATA_EMPTY(10004, "通过参数查找数据为空"), 
	NAME_REPEAT(10005, "名称重复"), 
	CODE_REPEAT(10006, "编码重复"), 
	NAME_EMPTY(10007, "名称为空"),
	CODE_EMPTY(10008, "编码为空"),
	START_PSA_EMPTY(10009, "上车地点为空"),
	START_PSA_NOT_EXIT(10010, "上车地点不存在"),
	END_PSA_EMPTY(10011, "下车地点为空"),
	END_PSA_EMPTY_NOT_EXIT(10012, "下车地点不存在"),
	AUDIT_EMPTY(10013, "审核人为空"),
	TRAINNUM_EMPTY(10014, "机车编号为空"),
	TRAINNUM_REPEAT(10015, "机车编号重复"),
	MILEAGE_ERROR(10016, "当前检测里程不等于合格里程与不合格里程之和"),
	LINE_REPEAT(10017, "计划中线路重复"),
	WORK_SHOP_RANGE_EMPTY(10018, "车间管辖范围为空"),
	DEPT_TYPE_ERROR(10019, "部门类型错误, 应选择车间"),
	ASSOCIATE_ERROR(10020, "当前配置为需要关联检测作业, 缺陷应当选择检测作业导入"),
	PLAN_ERROR(10021, "当前检测作业未找到对应计划"),
	NOTICE_ERROR(10022, "缺陷已与通知书关联"),
	NON_EXISTENT_TYPE(10023, "不存在的类型"),
	DEFECT_EXISTENT(10024, "缺陷已存在"),
	POINT_RELATION_ERROR(10025, "未找到到关联的监测点信息"),
	POINT_EXISTENT(10026, "监测点信息已存在"),
	FLOW_EMPTY(10027, "流程数据为空"),
	MILEAGE_ERROR2(10028, "合格里程不应小于优良里程"),
	NOTICE_NUM_EXIT(10029, "通知书编号已存在"),
	PLAN_EMPTY(10030, "计划执行情况为空"),
	PLAN_HANDLE_EMPTY(10031, "请填写本部门添乘计划！"),
	PLAN_ALREADLY_HANDLE_EMPTY(10032, "部门添乘计划已经办理！"),
	DATA_REPEAT(10033, "数据已存在！"),
	PLAN_REPEAT(10035, "计划已存在！"),
	STATION_NOT_FIND(10036,"找不到站场"),
	FAIL(-10005, "操作失败"),
	PLAN_RECORD_EMPTY(10034,"请提交本部门计划!");
	private final int errorCode;

	private final String message;

	private static final ImmutableMap<Integer, StatusCode> CACHE;

	static {
		final ImmutableMap.Builder<Integer, StatusCode> builder = ImmutableMap
				.builder();
		for (StatusCode statusCode : values()) {
			builder.put(statusCode.getErrorCode(), statusCode);
		}
		CACHE = builder.build();
	}

	private StatusCode(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public static StatusCode valueOfCode(int errorCode) {
		final StatusCode status = CACHE.get(errorCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for ["
					+ errorCode + "]");
		}
		return status;
	}

	@Override
	public Integer getErrorCode() {
		// TODO Auto-generated method stub
		return errorCode;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
}
