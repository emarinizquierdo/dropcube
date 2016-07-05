'use strict';

angular.module('dropcubeApp')
  .controller('CheckoutCtrl', function ($rootScope, $scope, $http, ngCart) {

    ngCart.setShipping(7);  

    $scope.payPalSettings = {
      paypal : {
        business : "e.marin.izquierdo@gmail.com",
        currency_code : "EUR",
      }
    };


  });
