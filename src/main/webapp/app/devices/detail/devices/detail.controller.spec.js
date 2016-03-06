'use strict';

describe('Controller: DevicesDetailCtrl', function () {

  // load the controller's module
  beforeEach(module('dropcubeApp'));

  var DevicesDetailCtrl, scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    DevicesDetailCtrl = $controller('DevicesDetailCtrl', {
      $scope: scope
    });
  }));

  it('should ...', function () {
  });
});
