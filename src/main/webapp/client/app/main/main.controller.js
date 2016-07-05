'use strict';
var aux;

angular.module('dropcubeApp')
  .controller('MainCtrl', function ($rootScope, $scope, $http, $location) {

    $(document).ready(function(){
      $('.parallax').parallax();
    });

    $scope.goTo = function( path ){
    	$location.path(path);
    }

  });
