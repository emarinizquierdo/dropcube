from flask import jsonify
from http import HTTPStatus

def json_ok(message=None):
    response = dict()
    response["status"] = HTTPStatus.OK
    if message is not None:
        response["data"] = message
    return jsonify(response)


def json_ko(message=None):
    response = dict()
    response["status"] = HTTPStatus.INTERNAL_SERVER_ERROR
    if message is not None:
        response["data"] = message
    return jsonify(response)