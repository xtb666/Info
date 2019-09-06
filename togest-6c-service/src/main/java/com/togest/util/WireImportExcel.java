package com.togest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFPictureData;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.togest.common.callback.MethodCallback;
import com.togest.common.util.DateUtils;
import com.togest.common.util.ListUtils;
import com.togest.common.util.ReflectHelper;
import com.togest.common.util.xls.ExcelField;
import com.togest.common.util.xls.ExcelFields;
import com.togest.common.util.xls.MyPictureData;

public class WireImportExcel {
	private static Logger log = LoggerFactory.getLogger(WireImportExcel.class);
	private static String DATATYPE = "date";
	private Workbook wb;
	private Sheet sheet;
	private int headerNum;
	private FormulaEvaluator evaluator;

	public WireImportExcel(String fileName, int headerNum) throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum);
	}

	public WireImportExcel(File file, int headerNum) throws InvalidFormatException, IOException {
		this(file, headerNum, 0);
	}

	public WireImportExcel(String fileName, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum, sheetIndex);
	}

	public WireImportExcel(File file, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
	}

	public WireImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
	}

	public WireImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		if (StringUtils.isBlank(fileName)) {
			throw new RuntimeException("导入文档为空!");
		}
		if (fileName.toLowerCase().endsWith("xls")) {
			try {
				wb = new HSSFWorkbook(is);
			} catch (Exception e) {
				wb = new XSSFWorkbook(is);
			}
		} else if (fileName.toLowerCase().endsWith("xlsx")) {
			try {
				wb = new XSSFWorkbook(is);
			} catch (Exception e) {
				wb = new HSSFWorkbook(is);
			}
		} else {
			throw new RuntimeException("文档格式不正确!");
		}
		if (wb.getNumberOfSheets() < sheetIndex) {
			throw new RuntimeException("文档中没有工作表!");
		}
		sheet = wb.getSheetAt(sheetIndex);
		evaluator = wb.getCreationHelper().createFormulaEvaluator();
		this.headerNum = headerNum;
		log.debug("Initialize success.");
	}

	public Row getRow(int rownum) {
		return sheet.getRow(rownum);
	}

	public int getDataRowNum() {
		return headerNum + 1;
	}

	public int getLastDataRowNum() {
		return sheet.getLastRowNum() + getDataRowNum();
	}

	public int getLastCellNum() {
		return getRow(headerNum).getLastCellNum();
	}

	public Object getCellValue(Row row, int column) {
		return getCellValue(row.getRowNum(), column);
	}

	public Object getCellValue(int rownum, int column) {
		return getCellValue(rownum, column, null);
	}

	public Object getCellValue(int rownum, int column, String dataType) {
		Object val = "";
		try {
			Cell cell = getRow(rownum).getCell(column);
			if (cell != null) {
				if (cell.getCellType() == 0) {
					short format = cell.getCellStyle().getDataFormat();
					if ((HSSFDateUtil.isCellDateFormatted(cell)) || (format == 14) || (format == 31) || (format == 57)
							|| (format == 58) || (format == 20) || (format == 32) || (DATATYPE.equals(dataType))) {
						Date date = cell.getDateCellValue();
						val = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
					} else {
						cell.setCellType(1);
						val = cell.getStringCellValue();
					}
				} else if (cell.getCellType() == 1) {
					val = cell.getStringCellValue();
				} else if (cell.getCellType() == 2) {
					val = evaluator.evaluate(cell).getNumberValue()+"";
				} else if (cell.getCellType() == 4) {
					val = Boolean.valueOf(cell.getBooleanCellValue());
				} else if (cell.getCellType() == 5) {
					val = Byte.valueOf(cell.getErrorCellValue());
				}
			}
		} catch (Exception e) {
			return val;
		}
		return val;
	}
	
	public int getAllSheets() {
		return wb.getNumberOfSheets();
	}

	public Workbook getWb() {
		return wb;
	}
}