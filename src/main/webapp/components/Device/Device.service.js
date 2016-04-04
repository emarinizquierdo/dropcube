'use strict';

angular.module('dropcubeApp')
  .factory('Device', function($rootScope, $resource) {

    var _Device = {};

    _Device.data = [];

    _Device.resource = $resource('/s/devices/:id', {
        id: '@_id'
    }, {
        get: {
            method: 'GET',
            isArray: true
        },
        save: {
            method: 'POST',
            isArray: false
        },
        delete: {
            method: 'DELETE',
            isArray: true
        },
        update: {
            method: 'PUT',
            isArray: false
        }
    });

    _Device.all = function() {

        _Device.resource.get(function(data) {
            if (data) {
                _Device.data = data;
            }
        }, function() {

        });
    }

    _Device.all();


    return _Device;

});
