# Third part dependencies
from flask_restplus import Resource
import logging

# App dependencies
from backend.restplus import api
from backend.controller import forecast_ctrl
from backend.model.forecast import Forecast, forecast_schema, forecast_output_list_schema

log = logging.getLogger(__name__)
ns = api.namespace('forecasts', description='forecasts endpoint')

@ns.route('', methods=['GET'])
class ForecastsWS(Resource):

    @api.doc(description='list forecasts')
    @ns.marshal_list_with(forecast_output_list_schema, skip_none=True)
    def get(self):
        """
        Retrieve a list of forecasts
        """
        return forecast_ctrl.get_forecasts()

@ns.route('/<int:id>', methods=['GET'])
class ForecastByIdWS(Resource):

    @api.doc(description='get forecast by id')
    def get(self, id):
        """
        Retrieve a list of forecasts
        """
        return forecast_ctrl.get_forecast(id)