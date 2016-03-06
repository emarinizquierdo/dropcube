'use strict';

angular.module('dropcubeApp')
  .directive('helpBlock', function () {
    return {
      templateUrl: 'components/help-block/help-block.html',
      restrict: 'EA',
      scope: {
      	'source' : "="
      },
      link: function (scope, element, attrs) {
      }
    };
  });