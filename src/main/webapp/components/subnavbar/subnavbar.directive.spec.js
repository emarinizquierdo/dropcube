'use strict';

describe('Directive: subnavbar', function () {

  // load the directive's module and view
  beforeEach(module('dropcubeApp'));
  beforeEach(module('components/subnavbar/subnavbar.html'));

  var element, scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<subnavbar></subnavbar>');
    element = $compile(element)(scope);
    scope.$apply();
  }));
});
