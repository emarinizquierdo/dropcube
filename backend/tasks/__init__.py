# -*- coding: utf-8 -*-
# third part dependencies
from flask import Blueprint, request
import base64
import json
import logging

# app dependencies
from backend.controller import forecast_ctrl

log = logging.getLogger(__name__)

tasks = Blueprint('tasks', __name__, url_prefix='/tasks')

@tasks.route('/refresh', methods=['GET'])
def refresh_forecasts():
  return forecast_ctrl.update_forecasts()