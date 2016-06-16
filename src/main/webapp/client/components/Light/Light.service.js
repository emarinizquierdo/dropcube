'use strict';

angular.module('dropcubeApp')
  .factory('Light', function($rootScope, $resource) {

    var _Light = {};

    _Light.data = [];

    _Light.resource = $resource('/s/lights/:id', {
        id: '@_id'
    }, {
        get: {
            method: 'GET',
            isArray: false
        }
    });

    return _Light;

});