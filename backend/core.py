from flask import render_template, jsonify
from werkzeug.exceptions import HTTPException

from backend.restplus import api
from backend.ws.devices import ns as devices_namespace

def ndb_wsgi_middleware(wsgi_app, client):
    def middleware(environ, start_response):
        with client.context():
            return wsgi_app(environ, start_response)

    return middleware

def create_app(app, client):

  app.wsgi_app = ndb_wsgi_middleware(app.wsgi_app, client)  # Wrap the app in middleware.

  @app.route('/')
  def vue_client():
    return render_template('index.html')

  @app.errorhandler(Exception)
  def default_error_handler(e):
      '''Default error handler'''
      # pass through HTTP errors
      if isinstance(e, HTTPException):
          return jsonify(error=str(e)), getattr(e, 'code', 500)

      return jsonify(error=str(e)), 500
  

  api.init_app(app)
  api.add_namespace(devices_namespace)