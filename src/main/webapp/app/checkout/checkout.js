'use strict';

angular.module('dropcubeApp')
  .config(function ($routeProvider) {
    $routeProvider
      .when('/checkout', {
        templateUrl: 'app/checkout/checkout.html',
        controller: 'CheckoutCtrl'
      });
  });
