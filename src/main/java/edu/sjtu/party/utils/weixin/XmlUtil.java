package edu.sjtu.party.utils.weixin;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class XmlUtil {
	public static Logger logger = LoggerFactory.getLogger(XmlUtil.class);

	public static String preOrderXml(Map<String, String> map, String sign) {
		String xml="<xml>"+
				"<appid>"+map.get("appid")+"</appid>"+
				"<mch_id>"+map.get("mch_id")+"</mch_id>"+
				"<nonce_str>"+map.get("nonce_str")+"</nonce_str>"+
				"<sign>"+sign+"</sign>"+
				"<body>"+map.get("body")+"</body>"+
				"<out_trade_no>"+map.get("out_trade_no")+"</out_trade_no>"+
				"<fee_type>"+map.get("fee_type")+"</fee_type>"+
				//������д��1 �ֵ�ʱ�޸�
				"<total_fee>"+Integer.parseInt(map.get("total_fee"))+"</total_fee>"+
				//"<total_fee>"+finalmoney+"</total_fee>"+
				"<spbill_create_ip>"+map.get("spbill_create_ip")+"</spbill_create_ip>"+
				"<notify_url>"+map.get("notify_url")+"</notify_url>"+
				"<trade_type>"+map.get("trade_type")+"</trade_type>"+
				"<openid>"+map.get("openid")+"</openid>"+
				"</xml>";
		
		return xml;
	}
	
	/**
	 * ֪ͨ��Ϣ����ת��Ϊxml
	 */
	public static String notifySetXml(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA["+return_code+"]]></return_code><return_msg><![CDATA["+return_msg+"]]></return_msg></xml>";
	}
	
	/**
	 * ����΢�ŷ���������XML��
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static SortedMap<String, String> payParseXml(String info) throws Exception {
		
		logger.info("���뵽����֮��");
		// ����������洢��HashMap��
		//Map<String, String> map = new HashMap<String, String>();
		SortedMap<String, String> map = new TreeMap<String, String>();

		// ��request��ȡ��������
		InputStream inputStream = String2Inputstream(info);
		// ��ȡ������
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// �õ�xml��Ԫ��
		Element root = document.getRootElement();
		// �õ���Ԫ�ص������ӽڵ�
		List<Element> elementList = root.elements();
		

		// ���������ӽڵ�
		for (Element e : elementList){
			if (e.getName()=="ScanCodeInfo")
			{
				List<Element> memberList = e.elements();
				for(Element sube : memberList)
				{
					map.put(sube.getName(),sube.getText());
				}
			}
			else
			    map.put(e.getName(), e.getText());
			logger.info(e.getName()+"----"+e.getText());
		}

		// �ͷ���Դ
		inputStream.close();
		inputStream = null;

		return map;
	}
		
	
	
    public static InputStream String2Inputstream(String str) {
    	return new ByteArrayInputStream(str.getBytes());
    }
    
    /**
     * map����תxml�ַ���
     * @param map
     * @return
     */
    public static String map2XmlString(Map<String, String> map) {
    	String xmlResult = "";

        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : map.keySet()) {
            sb.append("<" + key + ">" + map.get(key) + "</" + key + ">");
            
        }
        sb.append("</xml>");
        xmlResult = sb.toString();
        logger.info(xmlResult);
        return xmlResult;
    }
}
