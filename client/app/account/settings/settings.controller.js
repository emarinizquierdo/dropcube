'use strict';

angular.module('dropcubeApp')
  .controller('SettingsCtrl', function ($scope, $translate, User, Auth, Note) {
    
    $scope.errors = {};
    $scope.note = Note;
    $scope.user = Auth.getCurrentUser(true).$promise.then(normalize);



    function __init__(){

    }

    function normalize(user){

      user.normalized = {};
      console.log(user);
      if(user && user.provider == "google"){

        user.normalized._id = user._id;
        user.normalized.name = user.google.displayName;
        user.normalized.email = user.email;
        user.normalized.lang = user.lang;

        if(user.google.image){
          user.normalized.photo = user.google.image.url.replace("?sz=50", "?sz=100");
        }

      }

      $scope.user.normalized = user.normalized;

    }

    __init__();

    $scope.showBig = function(user){
      return url.replace('?sz=50', '');
    }

    $scope.updateLang = function(lang) {

        User.update({lang : lang}, function(user) {
            normalize(user);
            $translate.use(user.lang);
            $scope.edit = false;
        }, function() {
            $scope.edit = false;
        });

    };

  });
