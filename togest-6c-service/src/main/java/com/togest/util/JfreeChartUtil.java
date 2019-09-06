package com.togest.util;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.togest.common.util.PathUtil;
import com.togest.common.util.string.StringUtil;
import com.togest.response.statistics.PieChartInfo;

import Decoder.BASE64Encoder;

public class JfreeChartUtil {
	
	public static String set(DefaultPieDataset dataset, String title) {
		
		return null;
	}

	public static String getPieChart(String imageName, String title, List<PieChartInfo> list) {
		if(StringUtil.isEmpty(list)) {
			return null;
		}
		String imageFilePath = "";
		try {
			//设置饼图数据集  
	        DefaultPieDataset dataset = new DefaultPieDataset();
			for (PieChartInfo entity : list) {
				if(StringUtil.isNotEmpty(entity)) {
					dataset.setValue(entity.getName(), entity.getNum());
				}	
			}
	        //通过工厂类生成JFreeChart对象  
	        JFreeChart chart = ChartFactory.createPieChart(title, dataset, true, true, false);
	        chart.setTitle(new TextTitle(chart.getTitle().getText(),new Font("宋体", 1, 20)));  
            //加个副标题  
            PiePlot pieplot = (PiePlot) chart.getPlot();  
            pieplot.setLabelFont(new Font("宋体", 1, 15));
            //设置图表下方的图例说明字体
            chart.getLegend().setItemFont(new Font("宋体", 1, 15));
            //设置饼图是圆的
            pieplot.setCircular(true);
            StandardPieSectionLabelGenerator standarPieIG = new StandardPieSectionLabelGenerator("{0}:({1},{2})", 
            		NumberFormat.getNumberInstance(), NumberFormat.getPercentInstance());
            pieplot.setLabelGenerator(standarPieIG);  
            //没有数据的时候显示的内容  
            pieplot.setNoDataMessage("无数据显示");  
            pieplot.setLabelGap(0.02D);
            String path = PathUtil.getDirectoryPath() + File.separator + "template" + File.separator + "image\\";
            imageFilePath = path + imageName + ".jpg";
           //System.out.println("imageFilePath " + imageFilePath);
            File cTypeJpg = new File(imageFilePath);  
            if (!cTypeJpg.exists()) {  
            	cTypeJpg.createNewFile();  
            }
            ChartUtilities.saveChartAsJPEG(cTypeJpg, chart, //统计图表对象  
                    500, // 宽  
                    500  // 高  
            );
            imageFilePath = getImageStr(imageFilePath);
            cTypeJpg.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageFilePath;
	}
	
	
	public static String getImageStr(String imagePath) {//将图片转成base64码
       String imgFile = imagePath;  
       InputStream in = null;  
       byte[] data = null;  
       try {  
           in = new FileInputStream(imgFile);  
           data = new byte[in.available()];  
           in.read(data);  
           in.close();  
       } catch (IOException e) {  
           e.printStackTrace();  
       }  
       BASE64Encoder encoder = new BASE64Encoder();  
       return encoder.encode(data);  
	}  
}
