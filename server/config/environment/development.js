'use strict';

// Development specific configuration
// ==================================
module.exports = {
  // MongoDB connection options
  mongo: {
    uri: 'mongodb://localhost/dropcube-dev'
  },
  
  weather: {
    apiKey : process.env.WEATHER_API || 'b290219f260ca3a384400c3a019b21fd',
    parameters : "?units=si&exclude=daily,flags"
  },

  seedDB: false,
};
