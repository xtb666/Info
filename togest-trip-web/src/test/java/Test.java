import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;
import com.togest.common.util.LocalDateUtils;
import com.togest.common.util.string.StringUtil;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) throws ParseException {
//		testDateTime();
//		System.out.println(NumUtils.toChineseLower("1010"));
//		String aaaa = new String("其他");
//		System.out.println("其他".equals(aaaa));
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(simpleDateFormat.parse("2018-08-01 00:00:00").getTime());
//		System.out.println("abbc.dd".lastIndexOf("."));
//		testJDK8();
//		System.out.println(StringUtil.toHungarianCase("processInstanceId"));
//		System.out.println(StringUtil.getHostIP());
		Map<String,Department> map = new HashMap<String, Department>();
		Department department = new Department();
		map.put("111", department);
		department.setDeptId("deptId_1111");
		department.setDeptName("deptName_張三");
		for(Department department2 : map.values()) {
			System.out.println(department2.getDeptId());
			System.out.println(department2.getDeptName());
		}
	}
	
	void testARM_Before_Java9() throws IOException{
		 BufferedReader reader1 = new BufferedReader(new FileReader("journaldev.txt"));
		 try (BufferedReader reader2 = reader1) {
		   System.out.println(reader2.readLine());
		 }
	}

	private static void testDateTime() {
//		System.out.println(new SimpleDateFormat("yyyy年MM月dd日").format(LocalDateUtils.toLocalDate(new Date())));
		LocalDate localDate = LocalDateUtils.toLocalDate(new Date());
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		String format = localDate.format(formatters);
		System.out.println(format);
	}
	
	public static void testJDK8(){
		List<Department> deptList = new ArrayList<>();
		deptList.add(new Department("11111","系统开发部", 8765.43));
		deptList.add(new Department(null,"系统开发部2", 9876.54));
		deptList.add(new Department("33333","系统开发部3", 7654.32));
		Map<String, List<Department>> map = deptList.stream().filter(dept->StringUtil.isNotBlank(dept.getDeptId())).collect(Collectors.groupingBy(Department::getDeptId));;
				
		Map<String, Double> map2 = deptList.stream().filter(dept->StringUtil.isNotBlank(dept.getDeptId())).collect(Collectors.groupingBy(Department::getDeptId, Collectors.summingDouble(Department::getSalary)));
		String deptStr = deptList.stream().filter(dept->StringUtil.isNotBlank(dept.getDeptId())).map(Department::getDeptId).map(String::toString).collect(Collectors.joining(","));
		System.out.println(map);
		System.out.println(map2);
		System.out.println(deptStr);
		map.forEach((key,value)->{
			
		});
	}
}

class Department{
	private String deptId;
	private String deptName;
	private Double salary;
	
	public Department() {
		super();
	}
	
	public Department(String deptId, String deptName, Double salary) {
		super();
		this.deptId = deptId;
		this.deptName = deptName;
		this.salary = salary;
	}

	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
}
