import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import App from './App'
import Students from 'pages/Students'
import Classes from 'pages/Classes'
import Exams from 'pages/Exams'
import Subjects from 'pages/Subjects'
import Scores from 'pages/Scores'
import Analytics from 'pages/Analytics'

const routes = [
  { path: '/student', component: Students, alias: '/' },
  { path: '/exam', component: Exams},
  { path: '/score', component: Scores},
  { path: '/class', component: Classes},
  { path: '/subject', component: Subjects},
  { path: '/analytics', component: Analytics},
  { path: '*', redirect: '/'}
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
