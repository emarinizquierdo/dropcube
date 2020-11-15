"""Module describing default flask application configuration.

.. moduleauthor:: Cristian Davis <cristiandavis.asensio.contractor@bbva.com>
.. versionadded:: 0.1.0
"""

import os

def dev_env():
    return not os.getenv('GAE_ENV', '').startswith('standard')

class Config(object):

    """Class to describe the base configuration for the flask app."""

    DEBUG = False
    TESTING = False
    MAPPER_METHODS = ['GET', 'POST', 'PUT', 'DELETE']
    MAX_CONTENT_LENGTH = 20 * 1024 * 1024


class DevelopmentConfig(Config):

    """Class to describe the development environment configuration for the flask app."""

    DEBUG = True
    SECRET_KEY = 'dev'


class ProductionConfig(Config):

    """Class to describe the production environment configuration for the flask app."""

    pass


def get_config():
    """Gets the configuration base on the current enviroment.

    Returns:
        (:class:`subclass of Config`): The current environment configuration class.
    """

    if dev_env():
        return DevelopmentConfig
    else:
        return ProductionConfig
