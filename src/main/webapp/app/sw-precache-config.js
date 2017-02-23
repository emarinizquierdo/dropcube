module.exports = {
  staticFileGlobs: [
    '/index.html',
    '/manifest.json',
    '/images/**.*',
    '/bower_components/**.html',
    '/src/**.html'
  ],
  navigateFallback: '/',
  navigateFallbackWhitelist: [/^(?!.*\.html$|\/data\/).*/],
  runtimeCaching: [
    {
      urlPattern: /\/images\/.*/,
      handler: 'cacheFirst',
      options: {
        cache: {
          maxEntries: 200,
          name: 'items-cache'
        }
      }
    },
    {
      urlPattern: /\/fonts.googleapis.com\/.*/,
      handler: 'fastest',
      options: {
        cache: {
          maxEntries: 200,
          name: 'data-cache'
        }
      }
    },
    {
      urlPattern: /\/font\/roboto\/.*/,
      handler: 'fastest',
      options: {
        cache: {
          maxEntries: 200,
          name: 'data-cache'
        }
      }
    },
    {
      urlPattern: /\/src\/.*json/,
      handler: 'fastest',
      options: {
        cache: {
          maxEntries: 100,
          name: 'data-cache'
        }
      }
    }
  ]
};
