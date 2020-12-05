<template>
  <div id="app">
    <dropcube-header />
    <router-view />
  </div>
</template>

<script>
import '@/assets/colors.css';
import '@/assets/global.css';
import DropcubeHeader from '@/components/DropcubeHeader.vue';

import { SET_LANGUAGE } from '@/store';

export default {
  name: 'App',
  components: {
    DropcubeHeader
  },
  mounted () {
    var userLang = navigator.language || navigator.userLanguage;
    if (userLang.includes('es')) {
      this.$store.commit(SET_LANGUAGE, 'es_ES');
    } else {
      this.$store.commit(SET_LANGUAGE, 'en_US');
    }
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
