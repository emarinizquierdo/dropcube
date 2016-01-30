'use strict';

angular.module('dropcubeApp')
    .controller('DevicesDetailCtrl', ["$scope", "$location", "$routeParams", "Device", "Light", function($scope, $location, $routeParams, Device, Light) {


        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night


        var marker;
        var map;

        $scope.Device = Device;
        $scope.deviceData;
        $scope.predictions;

        function __init__() {
            if ($routeParams.id) {
                Device.resource.get({
                    id: $routeParams.id
                }, function(data) {
                    $scope.deviceData = data[0];
                    if (!$scope.deviceData) {
                        $location.path("/devices");
                    } else {
                        getLight($scope.deviceData.deviceId);
                        initMap();
                    }
                }, function() {
                    $location.path("/");
                });
            }
        }

        __init__();

        $scope.edit = function(status) {
            if (status) {
                marker.setDraggable(true);
                $scope.editing = true;
                map.setOptions({
                    draggable : true,
                    zoomControl: true,
                    scrollwheel: true,
                    disableDoubleClickZoom: false
                });
                initMap();
            } else {
                marker.setDraggable(false);
                map.setOptions({
                    draggable : false,
                    zoomControl: false,
                    scrollwheel: false,
                    disableDoubleClickZoom: true
                });
                $scope.editing = false;
                centerMark();
            }
        };

        $scope.updateDevice = function(device) {

            Device.resource.update(device, function(data) {
                $scope.deviceData = data;
                $scope.edit(false);
                getLight($scope.deviceData.deviceId);
                centerMark();
            }, function() {
                $scope.edit(false);
            });

        };

        function setPosition(position) {
            $scope.deviceData.lat = position.coords.latitude;
            $scope.deviceData.lng = position.coords.longitude;
            drawMark();
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        }

        $scope.centerMe = function() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(setPosition);
            }
        }

        function centerMark() {
            var bounds = new google.maps.LatLngBounds(new google.maps.LatLng($scope.deviceData.lat || 59.325, $scope.deviceData.lng || 18.070));
            map.fitBounds(bounds);
            map.setZoom(15);
        }

        function drawMark() {
            map = new google.maps.Map(document.getElementById('map'), {
                zoom: 15,
                center: {
                    lat: $scope.deviceData.lat,
                    lng: $scope.deviceData.lng
                },
                draggable : false,
                zoomControl: false,
                scrollwheel: false,
                disableDoubleClickZoom: true
            });

            marker = new google.maps.Marker({
                map: map,
                animation: google.maps.Animation.DROP,
                position: {
                    lat: $scope.deviceData.lat,
                    lng: $scope.deviceData.lng
                }
            });

            marker.addListener('dragend', getLatLong);
        }

        function initMap() {

            if (map) {
                centerMark()
                return;
            }

            if (!($scope.deviceData.lat && $scope.deviceData.lng)) {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(setPosition);
                } else {
                    $scope.deviceData.lat = 59.325;
                    $scope.deviceData.lng = 18.070;
                    drawMark();
                }
            } else {
                drawMark();
            }


        }

        function getLight( id, onSuccess ){
            Light.resource.get({
                    id: id
                }, function(data) {
                    $scope.predictions = data;
                }, function() {
                    
                });
        }

        function getLatLong(event) {
            $scope.deviceData.lat = event.latLng.lat();
            $scope.deviceData.lng = event.latLng.lng();
            if (!$scope.$$phase) {
                $scope.$apply();
            }
        }


    }]);