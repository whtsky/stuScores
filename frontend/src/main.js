import { store } from './store'

store.dispatch('fetchStudents')
store.dispatch('fetchSubjects')
store.dispatch('fetchExams')
store.dispatch('fetchScores')

import Vue from 'vue'
import VueRouter from 'vue-router'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.css'

Vue.use(VueRouter)
Vue.use(VueMaterial)


Vue.material.theme.register('sidebar', {
  primary: 'white'
})

import PageContent from 'components/PageContent';
Vue.component('page-content', PageContent);

import App from './App'
import Students from 'pages/Students'
import Exams from 'pages/Exams'
import Subjects from 'pages/Subjects'
import Scores from 'pages/Scores'
import Analytics from 'pages/Analytics'

const routes = [
  { path: '/student', component: Students, alias: '/' },
  { path: '/exam', component: Exams},
  { path: '/score', component: Scores},
  { path: '/subject', component: Subjects},
  { path: '/analytics', component: Analytics},
  { path: '*', redirect: '/'}
]

const router = new VueRouter({
  mode: 'history',
  routes
})

const Instance = Vue.component('app', App);
new Instance({
  el: '#app',
  router,
  store,
})

