
from google.cloud import ndb

from backend.model.forecast import Forecast
from backend.model.device import Device
from backend.model.generic_list import GenericList
from backend.handlers import openweather
from backend.utils import json_ok

def update_forecasts():

  query = Device.query()
  devices = query.fetch()

  for device in devices:
    update_forecast(device.deviceId, device.lat, device.lng)
  
  return json_ok(message='all devices updated')


def update_forecast(device_id, latitude, longitude):

  hour_forecasts = openweather.get_hourly_codes('40.47337182220572', '-3.6886051182626716')

  forecast = Forecast.get(device_id) or Forecast(key=ndb.Key(Forecast, device_id))
  forecast.forecasts = list(map(lambda item: int(item['code']), sorted(hour_forecasts, key=lambda x: x['hour'])))
  forecast.put()


def get_forecasts():

  query = Forecast.query()
  forecasts = query.fetch()

  return GenericList(cursor=None, total=None, data=forecasts)