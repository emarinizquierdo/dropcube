'use strict';

angular.module('dropcubeApp')
  .controller('SettingsCtrl', function ($scope, User, Auth, Note) {
    
    $scope.errors = {};
    $scope.note = Note;
    $scope.user = Auth.getCurrentUser().$promise.then(normalize);



    function __init__(){

    }

    function normalize(user){

      user.normalized = {};
      console.log(user);
      if(user && user.provider == "google"){

        user.normalized.name = user.google.displayName;
        user.normalized.email = user.email;
        
        if(user.google.image){
          user.normalized.photo = user.google.image.url.replace("?sz=50", "?sz=100");
        }

      }


      $scope.user.normalized = user.normalized;

    }

    __init__();

    $scope.showBig = function(user){
      debugger;
      return url.replace('?sz=50', '');
    }
  });
