# Third part dependencies

import logging
from google.cloud import ndb
from flask_restplus import fields

# App dependencies
from backend.restplus import api

# App dependencies

log = logging.getLogger(__name__)

device_schema = api.model('Device', {
    'id': fields.String,
    'deviceId': fields.Integer,
    'lat': fields.Float,
    'lng': fields.Float,
    'hours': fields.List(fields.Boolean)
})

device_input_schema = api.model('DeviceInput', {
    'name': fields.String(required=True)
})

device_output_list_schema = api.model('DeviceOutputList', {
    'cursor': fields.String,
    'data': fields.List(fields.Nested(device_schema, skip_none=True)),
    'total': fields.Integer
})

device_input_config_schema = api.model('DeviceConfigInput', {
    'hours': fields.List(fields.Boolean())
})

class Device(ndb.Model):
    """
    Geography class
    """

    ''' basic geography info '''
    name = ndb.StringProperty()
    deviceId = ndb.IntegerProperty(indexed=True)
    lat = ndb.FloatProperty()
    lng = ndb.FloatProperty()
    hours = ndb.BooleanProperty(repeated=True)

    @property
    def id(self):
        return self.key.id()
