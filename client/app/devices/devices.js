'use strict';

angular.module('dropcubeApp')
  .config(function ($routeProvider) {
    $routeProvider
      .when('/devices', {
        templateUrl: 'app/devices/devices.html',
        controller: 'DevicesCtrl',
        authenticate: true
      });
  });
