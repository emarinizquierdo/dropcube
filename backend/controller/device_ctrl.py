
from backend.model.device import Device
from backend.model.generic_list import GenericList


def add( device ):

  device = Device(**device)

  device.put()

  return device


def get_list( ):

  query = Device.query()

  devices = query.fetch()

  return GenericList(cursor=None, total=None, data=devices)