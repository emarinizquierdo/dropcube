
from google.cloud import ndb

from backend.model.code import Code
from backend.model.generic_list import GenericList


def add(code):

    code['key'] = ndb.Key(Code, code['id'])
    del code['id']
    code = Code(**code)

    code.put()

    return code


def get_list():

    query = Code.query()

    codes = query.fetch()

    return GenericList(cursor=None, total=None, data=codes)
