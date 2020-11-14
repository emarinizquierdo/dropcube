import urllib3
import json

def get_weather(weather_api):
  http = urllib3.PoolManager()
  r = http.request('GET', 'http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid={}'.format(weather_api))
  
  return json.loads(r.data)