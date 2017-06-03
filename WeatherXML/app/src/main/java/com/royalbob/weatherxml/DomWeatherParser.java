package com.royalbob.weatherxml;

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
        //取得DocumentBuilderFactory实例
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //从factory获取DocumentBuilder实例
        DocumentBuilder builder = factory.newDocumentBuilder();
        //解析输入流 得到Document实例
        Document doc = builder.parse(is);
        Element rootElement = doc.getDocumentElement();  
        NodeList items = rootElement.getElementsByTagName("weather_data");
        //获取SDCard目录
        //File sdCardDir = Environment.getExternalStorageDirectory();
        
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
        //由builder创建新文档
        Document doc = builder.newDocument();
          
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

        //取得TransformerFactory实例
        TransformerFactory transFactory = TransformerFactory.newInstance();
        //从transFactory获取Transformer实例
        Transformer transformer = transFactory.newTransformer();
        // 设置输出采用的编码方式
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        // 是否自动添加额外的空白
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        // 是否忽略XML声明
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
          
        StringWriter writer = new StringWriter();  

        //表明文档来源是doc
        Source source = new DOMSource(doc);
        //表明目标结果为writer
        Result result = new StreamResult(writer);
        //开始转换
        transformer.transform(source, result);
          
        return writer.toString();  */
		return null;
	}

}
