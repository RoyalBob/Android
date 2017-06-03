package com.example.weatherxml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomWeatherParser implements WeatherParser{

	@Override
	public List<Weather> parse(InputStream is) throws Exception {
		List<Weather> weathers = new ArrayList<Weather>();  
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //ȡ��DocumentBuilderFactoryʵ��  
        DocumentBuilder builder = factory.newDocumentBuilder(); //��factory��ȡDocumentBuilderʵ��  
        Document doc = builder.parse(is);   //���������� �õ�Documentʵ��  
        Element rootElement = doc.getDocumentElement();  
        NodeList items = rootElement.getElementsByTagName("weather_data");  
        //File sdCardDir = Environment.getExternalStorageDirectory();//��ȡSDCardĿ¼
        
        for (int i = 0; i < items.getLength(); i++) {  
        	Weather weather = null;
        	Node item = items.item(i);
            NodeList properties = item.getChildNodes();  
            
            for (int j = 0; j < properties.getLength(); j++) { 
            	Node property = properties.item(j);  
                String nodeName = property.getNodeName();  
                if (nodeName.equals("date")) { 
                	if(weather!=null)
                		weathers.add(weather);
                	weather = new Weather();
                    weather.setDate(property.getFirstChild().getNodeValue());  
                } else if (nodeName.equals("wind")) {  
                	weather.setWind(property.getFirstChild().getNodeValue());  
                } else if (nodeName.equals("weather")) {  
                	weather.setWeather(property.getFirstChild().getNodeValue());  
                } else if (nodeName.equals("temperature")) {  
                	weather.setTemperature(property.getFirstChild().getNodeValue());  
                } 
            }  
            weathers.add(weather);  
        }  
        return weathers;
	}

	@Override
	public String serialize(List<Weather> weathers) throws Exception {
		/*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document doc = builder.newDocument();   //��builder�������ĵ�  
          
        Element rootElement = doc.createElement("weather_data");  
  
        for (Weather weather : weathers) {                
            Element dateElement = doc.createElement("date");  
            dateElement.setTextContent(weather.getDate());  
            bookElement.appendChild(dateElement);  
              
            Element windElement = doc.createElement("date");  
            windElement.setTextContent(weather.getWind());  
            bookElement.appendChild(windElement); 
              
            rootElement.appendChild(bookElement);  
        }  
          
        doc.appendChild(rootElement);  
          
        TransformerFactory transFactory = TransformerFactory.newInstance();//ȡ��TransformerFactoryʵ��  
        Transformer transformer = transFactory.newTransformer();    //��transFactory��ȡTransformerʵ��  
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");            // ����������õı��뷽ʽ  
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");                // �Ƿ��Զ���Ӷ���Ŀհ�  
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");   // �Ƿ����XML����  
          
        StringWriter writer = new StringWriter();  
          
        Source source = new DOMSource(doc); //�����ĵ���Դ��doc  
        Result result = new StreamResult(writer);//����Ŀ����Ϊwriter  
        transformer.transform(source, result);  //��ʼת��  
          
        return writer.toString();  */
		return null;
	}

}
