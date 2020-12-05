# Third part dependencies

import logging
from google.cloud import ndb
from flask_restplus import fields

# App dependencies
from backend.restplus import api

# App dependencies

log = logging.getLogger(__name__)

code_schema = api.model('Code', {
    'id': fields.Integer(required=True),
    'main': fields.String,
    'description': fields.String,
    'icon': fields.String,
    'value': fields.String,
})

code_output_list_schema = api.model('CodeOutputList', {
    'cursor': fields.String,
    'data': fields.List(fields.Nested(code_schema, skip_none=True)),
    'total': fields.Integer
})

class Code(ndb.Model):

    main = ndb.StringProperty()
    description = ndb.StringProperty()
    icon = ndb.StringProperty()
    value = ndb.StringProperty()

    @property
    def id(self):
        return self.key.id()