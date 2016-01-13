'use strict';

angular.module('dropcubeApp')
	.directive('scroll', function ($rootScope, $window) {
		return {
			restrict: 'EA',
			link: function (scope, element, attrs) {
				angular.element($window).bind("scroll", function() {
					$rootScope.hideNavLogo = (($('body')[0].scrollTop < 200) && $rootScope.inMain) ? true : false;
					scope.$apply();
				});
			}
		};
});