/*
 * INSTALL @gcp/cookie-webpack-plugin AND UNCOMMENT NEXT
 * LINES IF YOU WANT TO USE THE CookieWebpackPlugin
 * See https://docs.google.com/document/d/1jSIk0rhvWPb6y1l4KfRupVINC_PrpmFlQXKh_fJBIiA/edit for more info
 */
// const CookieWebpackPlugin = require('@gcp/cookie-webpack-plugin')
// const { ProxyFactory } = require('@gcp/cookie-webpack-plugin')
// const URL_PROXY = 'your url'
// const cookieWebpackPlugin = new CookieWebpackPlugin({
//   url: URL_PROXY
// })
const path = require('path')
const CopyWebpackPlugin = require('copy-webpack-plugin')

module.exports = {
  devServer: {
    port: 3000,
    historyApiFallback: {
      disableDotRule: true
    },
    noInfo: true,
    overlay: true,
    /*
     * UNCOMMENT NEXT LINES IF YOU WANT TO USE THE ProxyFactory
     */
    proxy: {
      '^/v1': {
        target: 'http://localhost:8080',
        ws: false,
        changeOrigin: false
      }
    }
  }
}
