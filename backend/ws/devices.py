# Third part dependencies
from flask_restplus import Resource
import logging

# App dependencies
from backend.restplus import api
from backend.controller import device_ctrl
from backend.model.device import Device, device_schema, device_input_schema, device_output_list_schema

log = logging.getLogger(__name__)
ns = api.namespace('devices', description='users endpoint')

@ns.route('', methods=['GET', 'POST'])
class DevicesWS(Resource):

    @api.doc('create a new device')
    @ns.expect(device_input_schema, validate=True)
    @ns.marshal_with(device_schema, skip_none=True)
    def post(self):
        """
        Create a new device
        """
        args = api.payload
        device = device_ctrl.add(args)
        return device

    @api.doc(description='list devices')
    @ns.marshal_list_with(device_output_list_schema, skip_none=True)
    def get(self):
        """
        Retrieve a list of devices
        """
        return device_ctrl.get_list()