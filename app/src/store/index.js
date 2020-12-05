import Vue from 'vue'
import Vuex from 'vuex'
// import axios from 'axios'

import i18n from '../lang';

Vue.use(Vuex)

export const SET_LANGUAGE = 'SET_LANGUAGE'

export default new Vuex.Store({
  state: {
    language: null
  },
  getters: {
    language: state => state.language
  },
  mutations: {
    [SET_LANGUAGE] (state, language) {
      if (language === 'es_ES') {
        i18n.locale = 'es'
      } else {
        i18n.locale = 'en'
      }
      state.language = language
    }
  },
  actions: {

  }
})
