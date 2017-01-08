'use strict';

angular.module('dropcubeApp')
  .factory('Device', function($rootScope, $resource) {

    var _Device = {};

    _Device.data = [];

    _Device.resource = $resource('/s/devices/:id', {
        id: '@id'
    }, {
        get: {
            method: 'GET',
            isArray: false
        },
        save: {
            method: 'POST',
            isArray: false
        },
        delete: {
            method: 'DELETE',
            isArray: false
        },
        update: {
            method: 'PUT',
            isArray: false
        }
    });

    _Device.all = function() {

        _Device.resource.get(function(data) {
            if (data && data.data) {
                _Device.data = data.data;
            }
        }, function() {

        });
    }

    _Device.all();

    return _Device;

});
