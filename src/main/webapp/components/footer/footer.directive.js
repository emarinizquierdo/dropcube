'use strict';

angular.module('dropcubeApp')
  .directive('footer', function () {
    return {
      templateUrl: 'components/footer/footer.html',
      restrict: 'A',
      link: function (scope, element, attrs) {
      }
    };
  });
