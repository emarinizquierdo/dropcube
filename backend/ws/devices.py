# Third part dependencies
from flask_restplus import Resource
import logging

# App dependencies
from backend.restplus import api
from backend.controller import device_ctrl
from backend.model.device import Device, device_schema, device_input_schema, device_output_list_schema, device_input_config_schema

log = logging.getLogger(__name__)
ns = api.namespace('devices', description='devices endpoint')


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


@ns.route('/<int:id>', methods=['GET', 'PUT'])
class DevicesIdWS(Resource):

    @api.doc('get device config')
    @ns.marshal_with(device_schema, skip_none=True)
    def get(self, id):
        """
        GET a device by id
        """
        device = device_ctrl.get(id)
        return device

    @api.doc('update device config')
    @ns.expect(device_input_config_schema, validate=True)
    @ns.marshal_with(device_schema, skip_none=True)
    def put(self, id):
        """
        Create a new device
        """
        args = api.payload
        device = device_ctrl.config(id, args.get('hours'))
        return device
