'use strict';

angular.module('dropcubeApp')
  .directive('subnavbar', function () {
    return {
      templateUrl: 'components/subnavbar/subnavbar.html',
      restrict: 'EA',
      scope : {
      	'name' : "="
      },
      link: function (scope, element, attrs) {
      }
    };
  });
