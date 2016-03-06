'use strict';

describe('Service: Help', function () {

  // load the service's module
  beforeEach(module('dropcubeApp'));

  // instantiate service
  var Help;
  beforeEach(inject(function (_Help_) {
    Help = _Help_;
  }));

  it('should do something', function () {
    expect(!!Help).toBe(true);
  });

});
