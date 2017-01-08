'use strict';

angular.module('dropcubeApp').controller('DevicesCtrl', ["$scope", "$location", "Device", "Help", "Note", function($scope, $location, Device, Help, Note) {

    $scope.Device = Device;
    $scope.newDevice;
    $scope.help = Help;
    $scope.note = Note;

    var toDelete = null;

    var collapsible = $('.collapsible').collapsible({
        accordion: false // A setting that changes the collapsible behavior to expandable instead of the default accordion style
    });

    $scope.addNew = function(newDevice) {

        Device.resource.save(newDevice, function(data) {
            if(data && data.data){
                Device.data.push(data.data);
            }
        }, function() {

        });

        $scope.newDevice = {};
        collapsible.find(".collapsible-header").click();
    }

    $scope.goToDetail = function(id) {
        $location.path("devices/detail/" + id);
    }

    $scope.beforeToDelete = function(device) {
        toDelete = device;
        $('#modal1').openModal();

    }

    $scope.deleteDevice = function() {
        $('#modal1').closeModal();
        Device.resource.delete({id : toDelete.id}, function(data) {
            localDelete(Device.data, toDelete);
        }, function(e) {

        })
    }

    function localDelete(array, data) {
        angular.forEach(array, function(item, index) {
            if (item.id === data.id) {
                array.splice(index, 1);
                return false;
            }
        });
    }

}]);