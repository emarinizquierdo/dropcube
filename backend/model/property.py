# Third part dependencies

import logging
from google.cloud import ndb


# App dependencies

class Property(ndb.Model):

  ''' basic geography info '''
  id = ndb.StringProperty(indexed=True, required=True)

  ''' State info'''
  value = ndb.StringProperty()

  @staticmethod
  def get(key):
    prop_key = ndb.Key(Property, key)
    return prop_key.get()
