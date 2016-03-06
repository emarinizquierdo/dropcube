'use strict';

angular.module('dropcubeApp')
  .directive('note', function () {
    return {
      templateUrl: 'components/note/note.html',
      restrict: 'EA',
      scope: {
      	data : '='
      },
      link: function (scope, element, attrs) {
      }
    };
  });