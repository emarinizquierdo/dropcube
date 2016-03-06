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

      user.normalized._id = user._id;
      user.normalized.email = user.email;
      user.normalized.lang = user.lang;

      if(user && user.provider == "google"){

        user.normalized.name = user.google.displayName;

        if(user.google.image){
          user.normalized.photo = user.google.image.url.replace("?sz=50", "?sz=100");
        }

        if(user.google.cover && user.google.cover.coverPhoto && user.google.cover.coverPhoto.url){
          user.normalized.cover = user.google.cover.coverPhoto.url;
        }

      }

      if(user && user.provider == "twitter"){

        user.normalized.name = user.twitter.name;

        if(user.twitter.profile_image_url){
          user.normalized.photo = user.twitter.profile_image_url;
        }

        if(user.twitter.profile_background_image_url_https){
          user.normalized.cover = user.twitter.profile_background_image_url_https;
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
