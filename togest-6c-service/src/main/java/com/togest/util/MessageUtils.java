package com.togest.util;

public class MessageUtils {

	public static final String NAME = "name";
	public static final String ID = "id";
	
	public static final String TIPS_NOT_INPUT_LINE = "行没有填写线路信息";
	public static final String TIPS_NOT_FIND_LINE = "行没有找到线路信息";
	
	public static final String TIPS_NOT_INPUT_CHECK_DATE = "行没有填写检测日期信息";
	
	public static final String TIPS_NOT_INPUT_WORK_AREA = "行没有填写工区信息";
	public static final String TIPS_NOT_FIND_WORK_AREA = "行没有找到工区信息";
	
	public static final String TIPS_NOT_INPUT_WORK_SHOP = "行没有填写车间信息";
	public static final String TIPS_NOT_FIND_WORK_SHOP = "行没有找到车间信息";
	
	public static final String TIPS_NOT_INPUT_DIRECTION = "行没有方向信息";
	public static final String TIPS_NOT_FIND_DIRECTION = "行没有找到方向信息";
	
	public static final String TIPS_NOT_INPUT_SUPPLY_ARM = "行没有填写站区信息";
	public static final String TIPS_NOT_FIND_SUPPLY_ARM = "行没有找到站区信息";
	
	public static final String TIPS_NOT_INPUT_SECTION = "行没有填写段别信息";
	public static final String TIPS_NOT_FIND_SECTION = "行没有找到段别信息";
	
	public static final String TIPS_NOT_INPUT_PILLAR = "行没有填写支柱信息";
	public static final String TIPS_NOT_FIND_PILLAR = "行没有找到支柱信息";
	
	public static final String TIPS_NOT_INPUT_REVIEW_VALUE = "行没有填写最新测量值信息";
	public static final String TIPS_NOT_INPUT_CURRENT_REVIEW_RAIL_HEIGHT_VALUE = "行没有填写本次复核轨面高差信息";
	public static final String TIPS_NOT_INPUT_ADJUST_WIRE_HEIGHT = "行没有填写调整后导高信息";
	
	public static final String TIPS_NOT_STAND_LINE_HEIGHT = "行没有找到导高标准值信息";
	
	public static final String TIPS_NOT_INPUT_YEAR = "行没有找到年度信息";
	public static final String TIPS_NOT_INPUT_YEAR_REVIEW_RAIL_HEIGHT_VALUE = "行没有找到年度复核轨面高差信息";
	
	public static final String TIPS_NOT_INPUT_STANDAR_LINE_HEIGHT = "行没有填写导高标准值信息";
	public static final String TIPS_NOT_FIND_STANDAR_LINEHEIGHT_UP = "行没有找到上限值信息";
	public static final String TIPS_NOT_FIND_STANDAR_LINEHEIGHT_DOWN = "行没有找到下限值信息";
	
	public static final String TIPS_NOT_FIND_LINE_MIN_VALUE = "行没有找到允许最低值信息";
	
	public static final String DEFECT_GRADE = "defect_grade";
	
	/**动态检测值 高铁及普速 缺陷一级、二级 参数 */
	public interface DymCheck{
		//高铁一级缺陷 参数
		public static final int DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_ONE = 6600;   //1.H≥6600
		public static final int DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_TWO = 6500;   //1.6500≤H＜6600
		public static final int DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_THREE = 6600; //1.6500≤H＜6600
		public static final int DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_FOUR = 150;   //2.H≥标准值+150
		public static final int DYM_CHECK_VALUE_GAO_SU_ONE_LEVEL_FIVE = 100;   //3.H＜标准值-100
		
		//高铁二级缺陷参数
		public static final int DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_ONE = 100;    //1.标准值+100≤H ＜标准值+150
		public static final int DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_TWO= 150;     //1.标准值+100≤H ＜标准值+150
		public static final int DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_THREE= 100;   //2.标准值-100≤H＜标准值－50
		public static final int DYM_CHECK_VALUE_GAO_SU_TWO_LEVEL_FOUR= 50;     //2.标准值-100≤H＜标准值－50
		
		//普速一级缺陷 参数
		public static final int DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_ONE = 6600;    //1.H≥6600
		public static final int DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_TWO = 6500;    //1.6500≤H＜6600
		public static final int DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_THREE = 6600;  //1.6500≤H＜6600
		public static final int DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_FOUR = 250;    //2.H≥标准值+250
		public static final int DYM_CHECK_VALUE_PU_SU_ONE_LEVEL_FIVE = 150;    //3.H＜标准值-150
		
		//普速 二级缺陷参数
		public static final int DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_ONE = 150;     //1.标准值+150≤H＜标准值+250
		public static final int DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_TWO = 250;     //1.标准值+150≤H＜标准值+250
		public static final int DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_THREE = 150;   //2.标准值-150≤H＜标准值－100
		public static final int DYM_CHECK_VALUE_PU_SU_TWO_LEVEL_FOUR = 100;    //2.标准值-150≤H＜标准值－100
	}
}
