package com.togest.domain;


public class C2PicIndex<T> extends CPicIndex<T> {

	protected Integer panoramisStartFrame;

	protected Integer panoramisEndFrame;

	protected String panoramisStartPath;

	protected String panoramisEndPath;

	protected Integer partStartFrame;

	protected Integer partEndFrame;

	protected String partStartPath;

	protected String partEndPath;

	public Integer getPanoramisStartFrame() {
		return panoramisStartFrame;
	}

	public void setPanoramisStartFrame(Integer panoramisStartFrame) {
		this.panoramisStartFrame = panoramisStartFrame;
	}

	public Integer getPanoramisEndFrame() {
		return panoramisEndFrame;
	}

	public void setPanoramisEndFrame(Integer panoramisEndFrame) {
		this.panoramisEndFrame = panoramisEndFrame;
	}

	public String getPanoramisStartPath() {
		return panoramisStartPath;
	}

	public void setPanoramisStartPath(String panoramisStartPath) {
		this.panoramisStartPath = panoramisStartPath;
	}

	public String getPanoramisEndPath() {
		return panoramisEndPath;
	}

	public void setPanoramisEndPath(String panoramisEndPath) {
		this.panoramisEndPath = panoramisEndPath;
	}

	public Integer getPartStartFrame() {
		return partStartFrame;
	}

	public void setPartStartFrame(Integer partStartFrame) {
		this.partStartFrame = partStartFrame;
	}

	public Integer getPartEndFrame() {
		return partEndFrame;
	}

	public void setPartEndFrame(Integer partEndFrame) {
		this.partEndFrame = partEndFrame;
	}

	public String getPartStartPath() {
		return partStartPath;
	}

	public void setPartStartPath(String partStartPath) {
		this.partStartPath = partStartPath;
	}

	public String getPartEndPath() {
		return partEndPath;
	}

	public void setPartEndPath(String partEndPath) {
		this.partEndPath = partEndPath;
	}

}