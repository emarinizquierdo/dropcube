'use strict';

angular.module('dropcubeApp')
  .service('Help', function () {
    

    this.sections = {
    	devices : {
    		title : "HELP.HELP_00",
    		items :[{
    			link : "/help/01",
    			literal : "HELP.HELP_01"
    		}]
    	}
    }


  });
