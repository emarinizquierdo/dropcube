
from google.cloud import ndb

from backend.model.device import Device
from backend.model.generic_list import GenericList
from backend.controller import forecast_ctrl

def add( device ):

  device = Device(**device)

  device.put()

  return device

def get(id=None):

  query = Device.query(Device.deviceId == id)
  device = query.get()

  return device

def get_list( ):

  query = Device.query()

  devices = query.fetch()

  return GenericList(cursor=None, total=None, data=devices)


def config(id=None, hours=None):

  query = Device.query(Device.deviceId == id)
  device = query.get()
  device.hours = hours
  device.put()

  return device