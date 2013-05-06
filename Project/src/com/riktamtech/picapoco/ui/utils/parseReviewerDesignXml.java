package com.riktamtech.picapoco.ui.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

import com.riktamtech.picapoco.beans.ReviewerDesignBean;
import com.riktamtech.picapoco.beans.ReviewerDesignLayoutGroups;
import com.riktamtech.picapoco.beans.ReviewerImageDetailsBean;
import com.riktamtech.picapoco.beans.ReviewerPageBeanDetails;
import com.riktamtech.picapoco.beans.ReviewerTextDetailsBean;

public class parseReviewerDesignXml {

	public ReviewerDesignBean parseReviewerDesignXml(String xmlFile) {

		ReviewerDesignBean designBean = new ReviewerDesignBean();

		try {
			InputStream is = new ByteArrayInputStream(xmlFile.getBytes());
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(is);
			// TODO create Design details bean
			Element designelement = (Element)doc.getElementsByTagName("designRequest").item(0);
			designBean.externalId = XMLFunctionalities.getValue(designelement,
					"externalId");
			designBean.designOwnerId = XMLFunctionalities.getValue(
					designelement, "designOwnerId");
			designBean.designId = XMLFunctionalities.getValue(designelement,
					"designId");
			designBean.designResolution = XMLFunctionalities.getValue(
					designelement, "designResolution");
			designBean.bookTitle = XMLFunctionalities.getValue(designelement,
					"bookTitle");

			// TODO create pagedetails beans
			NodeList nodes = doc.getElementsByTagName("designPage");
			designBean.reviewerPageBeanDetailsArrayList = new ArrayList<ReviewerPageBeanDetails>();
			for (int i = 0; i < nodes.getLength(); i++) {
				// TODO Handle each page
				ReviewerPageBeanDetails pageBeanDetails = new ReviewerPageBeanDetails();
				Element e = (Element) nodes.item(i);
				pageBeanDetails.designPageId = Integer
						.parseInt(XMLFunctionalities
								.getValue(e, "designPageId"));
				pageBeanDetails.designPageApiId = XMLFunctionalities.getValue(
						e, "designPageApiId");
				pageBeanDetails.position = Integer.parseInt(XMLFunctionalities
						.getValue(e, "position"));
				pageBeanDetails.redesign = Boolean
						.parseBoolean(XMLFunctionalities.getValue(e,
								"designPageId"));

				pageBeanDetails.designLayoutGroupsArrayList = new ArrayList<ReviewerDesignLayoutGroups>();

				// TODO Handle layer groups in page
				NodeList layerNodes = e
						.getElementsByTagName("designPageLayerGroup");
				for (int j = 0; j < layerNodes.getLength(); j++) {
					ReviewerDesignLayoutGroups designLayoutGroups = new ReviewerDesignLayoutGroups();
					Element layerElements = (Element) layerNodes.item(j);
					designLayoutGroups.designPageLayerGroupId = Integer
							.parseInt(XMLFunctionalities.getValue(

							layerElements, "designPageLayerGroupId"));
					designLayoutGroups.zIndex = Integer
							.parseInt(XMLFunctionalities.getValue(
									layerElements, "zIndex"));
					// TODO Image details
					designLayoutGroups.reviewerImageDetailsArrayList = new ArrayList<ReviewerImageDetailsBean>();
					NodeList imageDetailsNodes = layerElements
							.getElementsByTagName("designPageImageLayer");
					for (int k = 0; k < imageDetailsNodes.getLength(); k++) {
						ReviewerImageDetailsBean imageDetailsBean = new ReviewerImageDetailsBean();
						Element imageDetailsElements = (Element) imageDetailsNodes
								.item(k);
						imageDetailsBean.designPageImageLayerId = XMLFunctionalities
								.getValue(imageDetailsElements,
										"designPageImageLayerId");
						imageDetailsBean.designPageImageLayerApiId = XMLFunctionalities
								.getValue(imageDetailsElements,
										"designPageImageLayerApiId");

						imageDetailsBean.type = Integer
								.parseInt(XMLFunctionalities.getValue(
										imageDetailsElements, "type"));
						imageDetailsBean.zIndex = Integer
								.parseInt(XMLFunctionalities.getValue(
										imageDetailsElements, "zIndex"));
						imageDetailsBean.width = Integer
								.parseInt(XMLFunctionalities.getValue(
										imageDetailsElements, "width"));
						imageDetailsBean.height = Integer
								.parseInt(XMLFunctionalities.getValue(
										imageDetailsElements, "height"));
						imageDetailsBean.positionTop = Integer
								.parseInt(XMLFunctionalities.getValue(
										imageDetailsElements, "positionTop"));
						imageDetailsBean.positionLeft = Integer
								.parseInt(XMLFunctionalities.getValue(
										imageDetailsElements, "positionLeft"));

						imageDetailsBean.rotationAngle = Float
								.parseFloat(XMLFunctionalities.getValue(
										imageDetailsElements, "rotationAngle"));
						imageDetailsBean.photoCenterX = Float
								.parseFloat(XMLFunctionalities.getValue(
										imageDetailsElements, "photoCenterX"));
						imageDetailsBean.photoCenterY = Float
								.parseFloat(XMLFunctionalities.getValue(
										imageDetailsElements, "photoCenterY"));
						imageDetailsBean.photoScalePercentX = Float
								.parseFloat(XMLFunctionalities.getValue(
										imageDetailsElements,
										"photoScalePercentX"));
						imageDetailsBean.photoScalePercentY = Float
								.parseFloat(XMLFunctionalities.getValue(
										imageDetailsElements,
										"photoScalePercentY"));
						imageDetailsBean.photoRotationAngle = Float
								.parseFloat(XMLFunctionalities.getValue(
										imageDetailsElements,
										"photoRotationAngle"));
						designLayoutGroups.reviewerImageDetailsArrayList
								.add(imageDetailsBean);
					}// End of Image nodes for loop
						// TODO Text details
					designLayoutGroups.reviewerTextDetailsArrayList = new ArrayList<ReviewerTextDetailsBean>();
					NodeList TextDetailsNodes = layerElements
							.getElementsByTagName("designPageTextLayer");
					for (int k = 0; k < TextDetailsNodes.getLength(); k++) {
						ReviewerTextDetailsBean textDetailsBean = new ReviewerTextDetailsBean();
						Element textDetailsElements = (Element) TextDetailsNodes
								.item(k);
						textDetailsBean.designPageTextLayerId = XMLFunctionalities
								.getValue(textDetailsElements,
										"designPageTextLayerId");
						textDetailsBean.fontColor = XMLFunctionalities
								.getValue(textDetailsElements, "fontColor");
						textDetailsBean.text = XMLFunctionalities.getValue(
								textDetailsElements, "text");
						textDetailsBean.zIndex = Integer
								.parseInt(XMLFunctionalities.getValue(
										textDetailsElements, "zIndex"));
						textDetailsBean.fontFamily = Integer
								.parseInt(XMLFunctionalities.getValue(
										textDetailsElements, "fontFamily"));
						textDetailsBean.fontSize = Integer
								.parseInt(XMLFunctionalities.getValue(
										textDetailsElements, "fontSize"));
						textDetailsBean.positionTop = Integer
								.parseInt(XMLFunctionalities.getValue(
										textDetailsElements, "positionTop"));
						textDetailsBean.positionLeft = Integer
								.parseInt(XMLFunctionalities.getValue(
										textDetailsElements, "positionLeft"));
						textDetailsBean.rotationAngle = Float
								.parseFloat(XMLFunctionalities.getValue(
										textDetailsElements, "rotationAngle"));
						designLayoutGroups.reviewerTextDetailsArrayList
								.add(textDetailsBean);
					}// End of text views loop
					pageBeanDetails.designLayoutGroupsArrayList
							.add(designLayoutGroups);
				}// End of layer groups loop
				designBean.reviewerPageBeanDetailsArrayList
						.add(pageBeanDetails);
			}// End of page Details loop

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return designBean;
	}
}
