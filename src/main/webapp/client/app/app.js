'use strict';

angular.module('dropcubeApp', [
        'ngCookies',
        'ngResource',
        'ngSanitize',
        'ngRoute',
        'pascalprecht.translate',
        'ngCart'
    ])
    .config(function($routeProvider, $locationProvider, $httpProvider, $translateProvider, $sceProvider, properties) {
        $routeProvider
            .otherwise({
                redirectTo: '/'
            });

        $locationProvider.html5Mode(true);
        $httpProvider.interceptors.push('authInterceptor');

        $translateProvider.useLoader('customLoader', {});
        $translateProvider.use(properties.customLanguage);

        //Disable SCE Provider
        $sceProvider.enabled(false);

    })

.factory('customLoader', ['$http', '$q', function($http, $q) {

    return function(options) {
        var deferred = $q.defer();

        $http({
                method: 'GET',
                url: '/lang/' + options.key + '.json'
            })
            .success(function(data) {
                deferred.resolve(data);
            })
            .error(function() {
                deferred.reject(options.key);
            });

        return deferred.promise;
    }
}])

.factory('authInterceptor', function($rootScope, $q, $cookies, $location) {
    return {
        // Add authorization token to headers
        request: function(config) {
            config.headers = config.headers || {};

            if ($cookies.get('token')) {
                config.headers.Authorization = 'Bearer ' + $cookies.get('token');
            }
            return config;
        },

        // Intercept 401s and redirect you to login
        responseError: function(response) {
            if (response.status === 401) {
                $location.path('/login');
                // remove any stale tokens
                $cookies.remove('token');
                return $q.reject(response);
            } else {
                return $q.reject(response);
            }
        }
    };
})

.run(function($rootScope, $location, Auth, $timeout, $translate) {
    $rootScope.hideNavLogo = true;

    var getUser = Auth.getCurrentUser();

    if(getUser && getUser.$promise){
        getUser.$promise.then(function(user){
            $translate.use(user.data.lang);
        }, function(){});
    }else{
        if(navigator.language.replace('_ES') == 'es'){
            $translate.use('es_ES');
        }else{
            $translate.use('en_US');
        }
    }

    // Redirect to login if route requires auth and you're not logged in
    $rootScope.$on('$routeChangeStart', function(event, next) {

        Auth.isLoggedInAsync(function(loggedIn) {
            if (next.authenticate && !loggedIn) {
                event.preventDefault();
                $location.path('/login');
            }
        });
        hideNavLogo();

    });



    function hideNavLogo() {

        if ($location.path() == '/') {
            $rootScope.inMain = true;
            $rootScope.hideNavLogo = true;
        } else {
            $rootScope.inMain = false;
            $rootScope.hideNavLogo = false;
        }
    }

});
