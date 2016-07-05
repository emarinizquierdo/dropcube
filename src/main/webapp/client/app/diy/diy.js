'use strict';

angular.module('dropcubeApp')
  .config(function ($routeProvider) {
    $routeProvider
      .when('/diy', {
        templateUrl: 'app/diy/diy.html',
        controller: 'DiyCtrl'
      });
  });
