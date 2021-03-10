package webServices;

import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import common.Constant;
import common.RijndaelCrypt;
import common.WebService;

import db.DAO.ImageDescriptionDAO;

import db.DTO.ImageDescriptionDTO;

import android.content.Context;
import android.util.Log;

public class ImageDescriptionWebServices {

	public void callImageDescriptionWebServices(Context context) {
		
		RijndaelCrypt rc = new RijndaelCrypt(Constant.fixKey);
		String reponse = WebService.callWebService(Constant.fixedURL,
				Constant.webServiceGetImageDescription);
		
		String output = rc.decrypt(reponse);
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory
					.newInstance();

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource(new StringReader(output));

			XPathFactory factory = XPathFactory.newInstance();
			XPath xPath = factory.newXPath();
			NodeList nl;

			nl = (NodeList) xPath.evaluate(
					"/ArrayOfQUALITY_QM_IMAGE_DESCRIPTION", is,
					XPathConstants.NODESET);

			Element el = (Element) nl.item(0);
			NodeList table = el
					.getElementsByTagName("QUALITY_QM_IMAGE_DESCRIPTION");

			ArrayList<ImageDescriptionDTO> ImageDescriptionDTOArrayList = new ArrayList<ImageDescriptionDTO>();
			for (int i = 0; i < table.getLength(); i++) {
				ImageDescriptionDTO imageDescriptionDTOObj = new ImageDescriptionDTO();

				Element tableitem = (Element) table.item(i);
				NodeList descriptionIdNodeList = tableitem.getElementsByTagName("DescriptionId");
				NodeList descriptionNodeList = tableitem.getElementsByTagName("Description");
				

				Element descriptionIdElement = (Element) descriptionIdNodeList.item(0);
				Element descriptionElement = (Element) descriptionNodeList.item(0);
				

				imageDescriptionDTOObj.setDescriptionId(Integer.parseInt(descriptionIdElement.getFirstChild().getNodeValue()));
				imageDescriptionDTOObj.setDescription(descriptionElement.getFirstChild().getNodeValue());
				

				ImageDescriptionDTOArrayList.add(imageDescriptionDTOObj);

			} // for loop ends here
			ImageDescriptionDAO imageDescriptionDAO = new ImageDescriptionDAO();
			imageDescriptionDAO.updateImageDescription(ImageDescriptionDTOArrayList,context);

		}catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(Constant.tag, e.toString());
			//new ExceptionHandler(e, NotificationEnum.unhandledException, context, parent);
			e.printStackTrace();
		}
		
		
	}
}
