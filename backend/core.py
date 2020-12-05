import logging
import os
from flask import render_template, jsonify
from werkzeug.exceptions import HTTPException

from backend.restplus import api
from backend.ws.devices import ns as devices_namespace
from backend.ws.codes import ns as codes_namespace
from backend.ws.forecasts import ns as forecasts_namespace
from backend.tasks import tasks
from backend.config import dev_env


def set_log_level():
    logging.getLogger().setLevel(logging.DEBUG if dev_env() else logging.INFO)


def ndb_wsgi_middleware(wsgi_app, client):
    def middleware(environ, start_response):
        with client.context():
            return wsgi_app(environ, start_response)

    return middleware


def create_app(app, client):

    # Wrap the app in middleware.
    app.wsgi_app = ndb_wsgi_middleware(app.wsgi_app, client)

    @app.route('/', defaults={'path': ''})
    @app.route('/<path:path>')
    def vue_client(path):
        return render_template('index.html')

    @app.errorhandler(Exception)
    def default_error_handler(e):
        '''Default error handler'''
        # pass through HTTP errors
        if isinstance(e, HTTPException):
            return jsonify(error=str(e)), getattr(e, 'code', 500)

        return jsonify(error=str(e)), 500

    set_log_level()

    api.init_app(app)
    api.add_namespace(devices_namespace)
    api.add_namespace(forecasts_namespace)
    app.register_blueprint(tasks)
