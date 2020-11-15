from datetime import datetime
import urllib3
import json

from backend.model.property import Property

def get_weather(latitude, longitude):

  WEATHER_API_KEY = Property.get('weather_api_key').value

  http = urllib3.PoolManager()
  r = http.request('GET', 'https://api.openweathermap.org/data/2.5/onecall?units=metric&exclude=minutely,daily,current&lat={}&lon={}&appid={}'.format(latitude, longitude, WEATHER_API_KEY))
  
  return json.loads(r.data)

def extract_codes(forecast):

  return {
    'hour': datetime.fromtimestamp(int(forecast['dt'])).hour,
    'code': forecast['weather'][0]['id']
  }

def get_hourly_codes(latitude, longitude):

  forecasts = get_weather(latitude, longitude)['hourly'][1:25]
  return list(map(extract_codes, forecasts))