import Vue from 'vue'
import App from './App.vue'

import router from './router'
import store from './store'
import i18n from './lang'
import 'vue-awesome/icons'
import Icon from 'vue-awesome/components/Icon'

// globally (in your main .js file)
Vue.component('v-icon', Icon)
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store,
  i18n
}).$mount('#app')
