import Vue from 'vue'
import Router from 'vue-router'

const DropcubeMain = () =>
  import(/* webpackChunkName: "DropcubeMain" */ '@/views/DropcubeMain');

const DropcubeAdmin = () =>
  import(/* webpackChunkName: "DropcubeAdmin" */ '@/views/DropcubeAdmin');

Vue.use(Router)

export const routes = [{
  path: '/',
  name: 'DropcubeMain',
  component: DropcubeMain
},
{
  path: '/admin',
  name: 'DropcubeAdmin',
  component: DropcubeAdmin
}
];

const router = new Router({
  mode: 'history',
  routes
});

export default router
