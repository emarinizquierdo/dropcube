from flask_restplus import Api, Resource, fields

api = Api(version='1.0', title='Main API', 
    doc='/doc', base_url="/")