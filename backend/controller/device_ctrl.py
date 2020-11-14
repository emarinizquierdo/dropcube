
from google.cloud import ndb

from backend.model.device import Device
from backend.model.property import Property
from backend.model.generic_list import GenericList
from backend.handlers import openweather

def add( device ):

  device = Device(**device)

  device.put()

  return device


def get_list( ):

  prop = Property.get('weather_api_key')

  print(openweather.get_weather(prop.value))

  query = Device.query()

  devices = query.fetch()

  return GenericList(cursor=None, total=None, data=devices)