# Third part dependencies

import logging
from google.cloud import ndb
from flask_restplus import fields

# App dependencies
from backend.restplus import api

# App dependencies

log = logging.getLogger(__name__)

forecast_schema = api.model('Forecast', {
    'id': fields.Integer,
    'forecasts': fields.List(fields.Integer(required=True, ))
})

forecast_output_list_schema = api.model('ForecastOutputList', {
    'cursor': fields.String,
    'data': fields.List(fields.Nested(forecast_schema, skip_none=True)),
    'total': fields.Integer
})

class Forecast(ndb.Model):

  forecasts = ndb.IntegerProperty(indexed=True, repeated=True)

  @property
  def id(self):
    return self.key.id()