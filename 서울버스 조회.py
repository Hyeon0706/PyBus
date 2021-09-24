from bs4 import BeautifulSoup   #라이브러리 import
from urllib.request import urlopen  #url라이브러리 불러옴




busId = input("버스 노선 Id를 입력하시오 : ")
open_api_key = '46rvMskXxMWkqJuYkMxwx%2FZnnKSI0pO%2FOGiu3%2FCVSsUlEdoCAMWXfC%2Bk5DRGlrorCjbvJbb2ZcbPbWh8ZxNY6Q%3D%3D'
params = '&busRouteId=' + busId
open_url = urlopen('http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid?serviceKey=' + open_api_key + params)

num = 1



soup = BeautifulSoup(open_url, 'lxml')
for anchor in soup.select('gpsY, gpsX, plainNo, dataTm'):
    print(anchor.get_text())
    print("================")
    num = num + 1