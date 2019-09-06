package com.togest.config;

public enum NoticeStatus {

	Issued("1"), // 下发
	Sign("2"), // 签收
	Feedback("3"), // 反馈
	Cancel("4"),// 结束
	// 处级使用
	WaitIssued("1"), // 待下发
	AlreadyIssued("2"), // 已下发
	AlreadySign("3"), // 已签收
	AlreadyFeedback("4"); // 已反馈

	private final String status;

	NoticeStatus(String status) {
		this.status = status;

	}

	public String getStatus() {
		return status;
	}
}
