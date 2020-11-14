# Third part dependencies

import logging
from google.cloud import ndb


# App dependencies

class Properties(ndb.Model):

    ''' basic geography info '''
    id = ndb.StringProperty()

    ''' State info'''
    value = ndb.String(indexed=True)
