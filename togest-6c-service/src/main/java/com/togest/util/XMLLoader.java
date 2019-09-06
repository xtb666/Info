package com.togest.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLLoader {
	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlParse(InputStream input) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(input);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		List<Element> childElements = root.elements();
		for (Element child : childElements) {
			List<Element> childElements1 = child.elements();
			for (Element element : childElements1) {
				List<Element> childElements2 = element.elements();
				for (Element element2 : childElements2) {
					map.put(element.getName() + element2.getName(),
							element2.getText());

				}
				map.put(element.getName(), element.getText());

			}
		}
		return map;

	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> xmlParse(String str) {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		File file = new File(str);
		Document document = null;
		try {
			document = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = document.getRootElement();
		List<Element> childElements = root.elements();
		for (Element child : childElements) {
			List<Element> childElements1 = child.elements();
			for (Element element : childElements1) {
				List<Element> childElements2 = element.elements();
				for (Element element2 : childElements2) {
					map.put(element.getName() + element2.getName(),
							element2.getText());

				}
				map.put(element.getName(), element.getText());

			}
		}
		return map;

	}
}
