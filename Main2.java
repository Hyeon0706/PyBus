import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import java.util.Scanner;

//버스 번호 입력 -> 버스 차량번호 리스트 출력
public class Main2 {
	public static void main(String[] args) {
		
		String key = "46rvMskXxMWkqJuYkMxwx%2FZnnKSI0pO%2FOGiu3%2FCVSsUlEdoCAMWXfC%2Bk5DRGlrorCjbvJbb2ZcbPbWh8ZxNY6Q%3D%3D"; //이건 내가 받아놓은 서비스 인증키
		Scanner scanner = new Scanner(System.in);
		System.out.println("검색할 버스 번호를 입력하세요 : ");
		String bus_num = scanner.nextLine();
		String routeId = null;
		
		if(bus_num.equals("440")) {		//Api에서 입력값으로 버스 번호를 직접적으로 받지 않기 때문에 입력한 버스 번호와 맞는 노선 아이디를 설정함
			routeId = "100100459";
		}else if(bus_num.equals("302")){
			routeId = "100100052";
		}else {
			System.out.println("버스 번호를 확인하세요!");
		}
		
		try {
			String url = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey="
					+ key + "&" + URLEncoder.encode("busRouteId", "UTF-8") + "="
					+ URLEncoder.encode(routeId, "UTF-8");
			
			//xml 데이터 파싱하기
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(url);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("itemList");	//지금 우리가 사용하는 api에서는 row태그 안에 데이터가 있는게 아니라 itemList라는 태그 안에 있어서 바꿔줬어
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println(temp + ". 차량번호 : " + getTagValue("plainNo", eElement));
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
}