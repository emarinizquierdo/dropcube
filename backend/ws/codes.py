# Third part dependencies
from flask_restplus import Resource
import logging

# App dependencies
from backend.restplus import api
from backend.controller import code_ctrl
from backend.model.code import Code, code_schema, code_output_list_schema

log = logging.getLogger(__name__)
ns = api.namespace('codes', description='codes endpoint')

@ns.route('', methods=['GET', 'POST'])
class CodesWS(Resource):

    @api.doc('create a new code')
    @ns.expect(code_schema, validate=True)
    @ns.marshal_with(code_schema, skip_none=True)
    def post(self):
        """
        Create a new code
        """
        args = api.payload
        code = code_ctrl.add(args)
        return code

    @api.doc(description='list codes')
    @ns.marshal_list_with(code_output_list_schema, skip_none=True)
    def get(self):
        """
        Retrieve a list of codes
        """
        return code_ctrl.get_list()