/**
 * Using Rails-like standard naming convention for endpoints.
 * GET     /api/lights              ->  index
 */

'use strict';

var _ = require('lodash');
var timezoner = require('timezoner');
var Device = require('../device/device.model');
var config = require('../../config/environment');
var http = require("http");
var https = require("https");

// Gets a list of Lights
exports.show = function(req, res) {

    findDevice(req, res, function(device) {

        timezoner.getTimeZone(
            device.lat, device.lng,
            function (err, data) {
                if (err) {
                    console.log(err);
                } else {
                    getWeather(device.lat, device.lng, (toLocalTime(data.rawOffset) > device.maxHour), function(weather) {
                        return res.status(200).json(parseJson(weather, device));
                    });
                }
            }
        );

    });
};

// Gets a list of Lights
exports.particleShow = function(req, res) {

    findDevice(req, res, function(device) {
        timezoner.getTimeZone(
            device.lat, device.lng,
            function (err, data) {
                if (err) {
                    console.log(err);
                } else {
                    getWeather(device.lat, device.lng, (toLocalTime(data.rawOffset) > device.maxHour), function(weather) {
                        return res.status(200).send(parseParticleValue(weather, device));
                    });
                }
            }
        );
    });
};

function findDevice(req, res, onSuccess) {

    Device.find({
        deviceId: req.params.id
    }, function(err, device) {
        if (err) {
            return handleError(res, err);
        }
        if (!device) {
            return res.status(404).send('Not Found');
        } else {

            if (device && device[0] && (typeof device[0].minHour !== "undefined") && (typeof device[0].maxHour !== "undefined")) {

                onSuccess(device[0]);

            } else {
                return res.status(200).json({});
            }

        }
    });
}

function parseJson(weather, device) {

    var minHour = device.minHour,
        maxHour = device.maxHour,
        json = {
            time : 0,
            minHour: {
                probability: 0,
                intensity: 0,
            },
            maxHour: {
                probability: 0,
                intensity: 0,
            }
        };

    json.time = weather.currently.time;
    json.minHour.probability = weather.hourly.data[minHour].precipProbability;
    json.maxHour.probability = weather.hourly.data[maxHour].precipProbability;
    json.minHour.icon = weather.hourly.data[minHour].icon;
    json.maxHour.icon = weather.hourly.data[maxHour].icon;
    json.minHour.intensity = weather.hourly.data[minHour].precipIntensity;
    json.maxHour.intensity = weather.hourly.data[maxHour].precipIntensity;

    return json;
}

function parseParticleValue(weather, device){

	var minHour = device.minHour,
        maxHour = device.maxHour,
        minValue,
        maxValue,
        minProbability,
        minIntensity,
        maxProbability,
        maxIntensity;

    minProbability = weather.hourly.data[minHour].precipProbability;
    maxProbability = weather.hourly.data[maxHour].precipProbability;
    minIntensity = weather.hourly.data[minHour].precipIntensity;
    maxIntensity = weather.hourly.data[maxHour].precipIntensity;
    minIntensity = (minIntensity == 0) ? 0 : (minIntensity <= 2.5) ? 0.2 : (minIntensity < 7.6) ? 0.5 : 0.85;
    maxIntensity = (maxIntensity == 0) ? 0 : (maxIntensity <= 2.5) ? 0.2 : (maxIntensity < 7.6) ? 0.5 : 0.85;

    minValue = (minProbability + minIntensity) / 2;
    maxValue = (maxProbability + maxIntensity) / 2;

    return (minValue > maxValue) ? "" + minValue : "" + maxValue;

}

function getWeather(lat, lng, nextDay, callback) {

    //https://developer.forecast.io/docs/v2
    var options = {
        host: 'api.forecast.io',
        port: 443,
        path: '/forecast/' + config.weather.apiKey + '/',
        method: 'GET',
        json: true
    };

    var timestamp = (nextDay) ? new Date().getTime() + 86400000: new Date().getTime();
    options.path += lat + "," + lng + "," + Math.floor(timestamp / 1000) + config.weather.parameters;
    getJson(options, function(statusCode, result) {
        callback(result);
    });
}


function getJson(options, onResult) {

    var prot = options.port == 443 ? https : http;
    var req = prot.request(options, function(res) {

        // Buffer the body entirely for processing as a whole.
        var data = [];
        res.on('data', function(chunk) {
            data.push(chunk);
        }).on('end', function() {
            onResult(res.statusCode, JSON.parse(data.join('')));
        });

    });

    req.on('error', function(err) {
        //res.send('error: ' + err.message);
    });

    req.end();

};

var toLocalTime = function(offset) {
    var d = new Date();
    var offset = ((new Date().getTimezoneOffset() + (offset / 60)));
    var n = new Date(d.getTime() + offset * 60 * 1000).getHours();
    console.log(n);
    return n;

};