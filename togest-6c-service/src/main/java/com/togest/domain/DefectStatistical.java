package com.togest.domain;

import java.text.DecimalFormat;
import java.util.List;

import com.github.pagehelper.util.StringUtil;


public class DefectStatistical {
	public static final String STR = "0" ;
	
	private static DecimalFormat df = new DecimalFormat("0.00");
	private String  C1=STR;
	private String  C2=STR;
	private String  C3=STR;
	private String  C4=STR;
	private String  C5=STR;
	private String  C6=STR;
	private String  RDD=STR;
	private String  L1=STR;
	private String  L2=STR;
	private String wI;
	private String  RDDP;
	private int ARDP;
	private int BRDP;
	
	
	public String getwI() {
		return wI;
	}
	public void setwI(String wI) {
		this.wI = wI;
	}
	private int  L2C ;
	private int  L2D ;
	private int  L1C ;
	private int  L1D ;
	
	public int getL2D() {
		return L2D;
	}
	public void setL2D(int l2d) {
		L2D += l2d;
	}
	public int getL1D() {
		return L1D;
	}
	public void setL1D(int l1d) {
		L1D += l1d;
	}
	public int getL2C() {
		return L2C;
	}
	public void setL2C(int l2c) {
		L2C += l2c;
	}
	public int getL1C() {
		return L1C;
	}
	public void setL1C(int l1c) {
		L1C += l1c;
	}
	public int getARDP() {
		return ARDP;
	}
	public int getBRDP() {

		return BRDP;
	}
	public void setARDP(int aRDP) {
		ARDP += aRDP;
	}
	public void setBRDP(int bRDP) {
		BRDP += bRDP;
	}
	public String getC1() {
		return C1;
	}
	public String getC2() {
		return C2;
	}
	public String getC3() {
		return C3;
	}
	public String getC4() {
		return C4;
	}
	public String getC5() {
		return C5;
	}
	public String getC6() {
		return C6;
	}
	public String getRDD() {
		return ARDP+"/"+BRDP;
	}
	public String getL1() {
		return L1;
	}
	public String getL2() {
		return L2;
	}
	public String getRDDP() {
		
		
		return df.format(ARDP*100.00/BRDP)+"%"; 
	}
	public void setC1(String c1) {
		C1 = c1;
	}
	public void setC2(String c2) {
		C2 = c2;
	}
	public void setC3(String c3) {
		C3 = c3;
	}
	public void setC4(String c4) {
		C4 = c4;
	}
	public void setC5(String c5) {
		C5 = c5;
	}
	public void setC6(String c6) {
		C6 = c6;
	}
	public void setRDD(String rDD) {
		RDD = rDD;
	}
	public void setL1(String l1) {
		L1 = l1;
	}
	public void setL2(String l2) {
		L2 = l2;
	}
	public void setRDDP(String rDDP) {
		RDDP = rDDP;
	}

}
