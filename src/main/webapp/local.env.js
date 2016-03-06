'use strict';

// Use local.env.js for environment variables that grunt will set when the server starts locally.
// Use for your api keys, secrets, etc. This file should not be tracked by git.
//
// You will need to set these on the server you deploy to.

module.exports = {
  DOMAIN: 'http://localhost:9000',
  SESSION_SECRET: "dropcube-secret",

  FACEBOOK_ID: '1546768355641274',
  FACEBOOK_SECRET: '35f028d300d92c2adac2632be7d7441f',

  TWITTER_ID: 'UOVd3Kers5feQyGkJEqcWmTv8',
  TWITTER_SECRET: 'z4wJs6hQ13nrMEMwxGCapGLckZ07aqxdNHdvXeqmqbM7UzeBvD',

  GOOGLE_ID: '708634620251-1dfnfhs8hqico49b47ns9uk4fbcoichb.apps.googleusercontent.com',
  GOOGLE_SECRET: 'Mlc1EI9GnPMsWc2m1L1PsrN9',

  // Control debug level for modules using visionmedia/debug
  DEBUG: ''
};
