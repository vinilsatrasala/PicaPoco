package com.riktamtech.picapoco.ui.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * This class is used to parse xml responses
 * 
 * @author Suneel
 * 
 */
public class XMLFunctionalities {

	public final static Document XMLfromString(String xml) {

		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}

		return doc;

	}

	/**
	 * This method will parse xml file and returns Document format
	 * 
	 * @param filePath
	 * @return
	 */
	public final static Document XMLfromFile(String filePath) {

		Document doc = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			// String filename = filePath;
			File file = new File(filePath);

			FileInputStream fis;
			fis = new FileInputStream(file);
			InputSource is = new InputSource();
			is.setCharacterStream(new InputStreamReader(fis));
			doc = db.parse(is);

		} catch (ParserConfigurationException e) {
			System.out.println("XML parse error: " + e.getMessage());
			return null;
		} catch (SAXException e) {
			System.out.println("Wrong XML file structure: " + e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println("I/O exeption: " + e.getMessage());
			return null;
		}

		return doc;

	}

	/**
	 * Returns element value
	 * 
	 * @param elem
	 *            element (it is XML tag)
	 * @return Element value otherwise empty String
	 */
	public final static String getElementValue(Node elem) {
		Node kid;
		if (elem != null) {
			if (elem.hasChildNodes()) {
				for (kid = elem.getFirstChild(); kid != null; kid = kid
						.getNextSibling()) {
					if (kid.getNodeType() == Node.TEXT_NODE) {

						return kid.getNodeValue() != null ? kid.getNodeValue()
								: "";
					}
				}
			}
		}
		return "";
	}

	/**
	 * This method will get xml from server
	 * 
	 * @return
	 */
	public static String getXML() {
		String line = null;

		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://10.42.43.85/sample.xml");

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			line = EntityUtils.toString(httpEntity);
			System.out.println(line);

		} catch (UnsupportedEncodingException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (MalformedURLException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		} catch (IOException e) {
			line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
		}

		return line;

	}

	/**
	 * This will return result count
	 * 
	 * @param doc
	 * @param tag
	 * @return
	 */
	public static int numResults(Document doc, String tag) {
		NodeList nl = doc.getElementsByTagName(tag);
		return nl.getLength();
	}

	/**
	 * This will return the value of given element
	 * 
	 * @param item
	 * @param str
	 * @return
	 */
	public static String getValue(Element item, String str) {
		NodeList n;
		try {
			n = item.getElementsByTagName(str);
		} catch (NullPointerException e) {
			return "";
		}
		return XMLFunctionalities.getElementValue(n.item(0));
	}
}
