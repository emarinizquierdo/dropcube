'use strict';

describe('Directive: helpBlock', function () {

  // load the directive's module and view
  beforeEach(module('dropcubeApp'));
  beforeEach(module('components/help-block/help-block.html'));

  var element, scope;

  beforeEach(inject(function ($rootScope) {
    scope = $rootScope.$new();
  }));

  it('should make hidden element visible', inject(function ($compile) {
    element = angular.element('<help-block></help-block>');
    element = $compile(element)(scope);
    scope.$apply();
    expect(element.text()).toBe('this is the helpBlock directive');
  }));
});