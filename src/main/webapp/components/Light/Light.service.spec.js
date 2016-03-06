'use strict';

describe('Service: Light', function () {

  // load the service's module
  beforeEach(module('dropcubeApp'));

  // instantiate service
  var Light;
  beforeEach(inject(function (_Light_) {
    Light = _Light_;
  }));

  it('should do something', function () {
    expect(!!Light).toBe(true);
  });

});
