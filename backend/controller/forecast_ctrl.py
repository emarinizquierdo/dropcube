import logging
import pytz
from datetime import datetime
from google.cloud import ndb
from flask import abort
from timezonefinder import TimezoneFinder

from backend.model.forecast import Forecast
from backend.model.device import Device
from backend.model.code import Code
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

    hour_forecasts = openweather.get_hourly_codes(
        '40.47337182220572', '-3.6886051182626716')

    forecast = Forecast.get_by_id(device_id) or Forecast(
        key=ndb.Key(Forecast, device_id))
    forecast.forecasts = list(map(lambda item: Code.get_by_id(int(item['code'])).value, sorted(hour_forecasts, key=lambda x: x['hour'])))
    forecast.put()


def get_forecasts():

    query = Forecast.query()
    forecasts = query.fetch()

    return GenericList(cursor=None, total=None, data=forecasts)

def get_device_forecasts(deviceId):

    forecasts = Forecast.get_by_id(deviceId)
    return forecasts

def rotate(l, n):
   return l[n:] + l[:n]

def get_forecast(device_id):

    forecast = Forecast.get_by_id(device_id)
    query = Device.query()
    query = query.filter(Device.deviceId == device_id)
    device = query.get()

    if forecast is None:
        abort(404, 'Forecast doesnt exist')
    
    if device is None:
        abort(404, 'Device doesnt exist')

    tf = TimezoneFinder()
    timezone = tf.timezone_at(lng=device.lng, lat=device.lat)
    tz = pytz.timezone(timezone)
    local_hour = datetime.now(tz).hour

    try:
        local_index = rotate(device.hours, local_hour).index(True)
        return rotate(forecast.forecasts, local_hour)[local_index]
    except:
        pass

    return 1000
