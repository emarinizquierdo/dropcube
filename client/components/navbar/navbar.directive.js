'use strict';

angular.module('dropcubeApp').directive('navbar', ['$location', 'Auth', function($location, Auth) {

    return {
        restrict: 'A',
        templateUrl: 'components/navbar/navbar.html',
        link: function($scope, $element, $attrs) {


            $scope.menu = [{
                'title': 'Home',
                'link': '/'
            }];

            $scope.isCollapsed = true;
            $scope.isLoggedIn = Auth.isLoggedIn;
            $scope.isAdmin = Auth.isAdmin;
            $scope.getCurrentUser = Auth.getCurrentUser;

            $scope.logout = function() {
                Auth.logout();
                $location.path('/login');
            };

            $scope.isActive = function(route) {
                return route === $location.path();
            };

            $element.find('.dropdown-button').dropdown();

            $element.find('.button-collapse').sideNav({
                closeOnClick: true // Closes side-nav on <a> clicks, useful for Angular/Meteor
            });

            $scope.signOut = function() {



            };

        }
    };

}]);