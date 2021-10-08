import java.util.Arrays;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;

import java.util.Scanner;

//버스 번호 입력 -> 버스 차량번호 리스트 출력
public class DOMParser {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("검색할 버스 번호를 입력하세요 : ");
		String bus_num = scanner.nextLine();
		
		try {
			//url 불러오기  미완성
			String url = "https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15000332"
					+ "xml/Grid_20150827000000000227_1/1/1" + "?" + URLEncoder.encode("vehId", "UTF-8") + "="
					+ URLEncoder.encode(bus_num, "UTF-8");
			
			//xml 데이터 파싱하기
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();
			System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("row");
			for (int temp = 0; temp < nList.getLength(); temp++) { // 리스트의 개수만큼 데이터를 끌어와서 Array라는 배열에 저장
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String b = getTagValue("vehId", eElement);
					test(b);  //해당 버스의 차량번호 출력
				}
			}
		}catch(Exception e){
			System.out.println("에러");
			System.out.println("에러 종류 : " + e);
		}
	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		
		Node nValue = (Node) nlList.item(0);
		if(nValue == null) {
			return null;
		}
		return nValue.getNodeValue();
	}
	public static void test(String get) {
		try {
			//url 불러오기    미완성
			String url = "http://211.237.50.150:7080/openapi/b53b81c76f8bc68b7fe1b656852cd6f0e5a50bfc1466e8f222272725be332249/"
					+ "xml/Grid_20150827000000000228_1/1/10" + "?" + URLEncoder.encode("vehId", "UTF-8") + "="
					+ URLEncoder.encode(get, "UTF-8");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();
			System.out.println("Root element : " + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("row");
			for (int temp = 0; temp < nList.getLength(); temp++) { // 리스트의 개수만큼 데이터를 끌어와서 Array라는 배열에 저장
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println(temp + ". 차량번호 : " + getTagValue("plainNo", eElement));
				}
			}
		}catch(Exception e) {
			System.out.println("에러");
			System.out.println("에러 종류 : " + e);
		}
	}
}