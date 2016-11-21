import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import App from './App'
import Students from 'pages/Students'

const routes = [
  { path: '/', component: Students },
]

const router = new VueRouter({
  routes
})

import { store } from './store'

new Vue({
  el: '#app',
  template: '<App/>',
  router,
  store,
  components: { App }
})
