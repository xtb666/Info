package com.togest.domain;

import com.togest.util.Data;



public class BdCorresStandDetail extends Data {

	private static final long serialVersionUID = 1L;

    //主表id
    private String standId;
    //区间或站场Id
    private String psaId;
    //区间或站场
    private String psaName;
    //杆号
    private String pillar;
    //公里标
    private String glb;
    //跨距（m）
    private String span;
    //距所距离（公里）
    private String distance;
    //电抗值
    private String issInductance;
    //
    private String note;
    
    private boolean flag;
    
    
    public BdCorresStandDetail() {
		super();
	}

	public BdCorresStandDetail(String standId, String note) {
    	this.standId = standId;
    	this.note = note;
	}

	public BdCorresStandDetail(String standId, String psaId, String psaName, String pillar, String glb, String span,
			String distance, String issInductance, String note, boolean flag) {
		super();
		this.standId = standId;
		this.psaId = psaId;
		this.psaName = psaName;
		this.pillar = pillar;
		this.glb = glb;
		this.span = span;
		this.distance = distance;
		this.issInductance = issInductance;
		this.note = note;
		this.flag = flag;
	}

	/**
     * 设置：主表id
     */
    public void setStandId(String standId) {
        this.standId = standId;
    }

    /**
     * 获取：主表id
     */
    public String getStandId() {
        return standId;
    }
    
    public String getPsaId() {
		return psaId;
	}

	public void setPsaId(String psaId) {
		this.psaId = psaId;
	}

	/**
     * 设置：杆号
     */
    public void setPillar(String pillar) {
        this.pillar = pillar;
    }

    /**
     * 获取：杆号
     */
    public String getPillar() {
        return pillar;
    }
    /**
     * 设置：公里标
     */
    public void setGlb(String glb) {
        this.glb = glb;
    }

    /**
     * 获取：公里标
     */
    public String getGlb() {
        return glb;
    }
    /**
     * 设置：跨距（m）
     */
    public void setSpan(String span) {
        this.span = span;
    }

    /**
     * 获取：跨距（m）
     */
    public String getSpan() {
        return span;
    }
    /**
     * 设置：距所距离（公里）
     */
    public void setDistance(String distance) {
        this.distance = distance;
    }

    /**
     * 获取：距所距离（公里）
     */
    public String getDistance() {
        return distance;
    }
    /**
     * 设置：电抗值
     */
    public void setIssInductance(String issInductance) {
        this.issInductance = issInductance;
    }

    /**
     * 获取：电抗值
     */
    public String getIssInductance() {
        return issInductance;
    }
    /**
     * 设置：
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * 获取：
     */
    public String getNote() {
        return note;
    }

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getPsaName() {
		return psaName;
	}

	public void setPsaName(String psaName) {
		this.psaName = psaName;
	}
	//校验公里标和距所距离
	public boolean check() {
//		return (PatternUtils.pattern(this.distance) || PatternUtils.pattern(this.glb));
		return true;
	}
}
