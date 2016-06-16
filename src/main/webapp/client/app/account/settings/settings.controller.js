'use strict';

angular.module('dropcubeApp')
  .controller('SettingsCtrl', function ($scope, $translate, User, Auth, Note) {

    $scope.errors = {};
    $scope.note = Note;
    $scope.user = Auth.getCurrentUser(true).$promise.then(normalize);

    function __init__(){

    }

    function normalize(user){

      user = user.data;

      user.normalized = {};

      user.normalized._id = user._id;
      user.normalized.email = user.email;
      user.normalized.lang = user.lang;

      if(user && user.provider == "google"){

        user.normalized.name = user.name;

        if(user.profileCover){
          user.normalized.photo = user.profileCover.replace("?sz=50", "?sz=100");
        }

        if(user.backgroundCover){
          user.normalized.cover = user.backgroundCover;
        }

      }

      if(user && user.provider == "twitter"){

        user.normalized.name = user.name;

        if(user.profileCover){
          user.normalized.photo = user.profileCover;
        }

        if(user.backgroundCover){
          user.normalized.cover = user.backgroundCover;
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
            $translate.use(user.data.lang);
            $scope.edit = false;
        }, function() {
            $scope.edit = false;
        });

    };

  });
