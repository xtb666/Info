package com.togest.util;

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
	NOTICE_ERROR(10009, "缺陷已与通知书关联"),
	DEFECT_EMPTY(10010, "通知书没有选择数据"),
	EXCEPTION(-10001, "操作异常"), 
	FAIL(-10005, "操作失败"),
	UPLOAD_DATA_EMPTY(10012, "上传数据为空"),
	TYPE_EMPTY(10013, "类型为空"),
	LINE_NAME_EMPTY(100014, "线路不能为空"),
	LINE_NAME_REPEAT(100015, "线路重复"),
	PAVILION_NAME_EMPTY(100016, "变电所不能为空"),
	PS_PD_NAME_EMPTY(100017, "供电臂不能为空"),
	LINE_PAVILION_PSPD_NAME_REPEAT(100018, "线路、变电所、供电臂重复"),
	TEMPLATEID_EMPTY(100019, "模板不能为空"),
	SPAN_NOT_A_NUMBER(100020, "间距必须为数字"),
	GLB_NOT_A_NUMBER(100021, "公里标格式不合法"),
	LINE_PAVILION_PSPD_TIME_NAME_REPEAT(100022, "此供电臂下已录入了该时间的跳闸数据"),
	START_GLB_NOT_A_NUMBER(100023, "开始公里标格式不合法"),
	END_GLB_NOT_A_NUMBER(100024, "结束公里标格式不合法");
	

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
