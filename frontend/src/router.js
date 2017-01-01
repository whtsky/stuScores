import { store } from './store'

import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import Students from 'pages/Students'
import Exams from 'pages/Exams'
import Subjects from 'pages/Subjects'
import Scores from 'pages/Scores'
import Analytics from 'pages/Analytics'
import FirstRun from 'pages/FirstRun'
import Login from 'pages/Login'

const routes = [
  { path: '/student', component: Students, alias: '/' },
  { path: '/exam', component: Exams},
  { path: '/score', component: Scores},
  { path: '/subject', component: Subjects},
  { path: '/analytics', component: Analytics},
  {
    path: '/firstrun',
    component: FirstRun,
    beforeEnter: (to, from, next) => {
      if(!store.state.firstrun) {
        next('/')
      } else {
        next()
      }
    }
  },
  {
    path: '/login',
    component: Login,
    beforeEnter: (to, from, next) => {
      if(store.state.user.token !== '') {
        next('/')
      } else {
        next()
      }
    }
  },
  { path: '*', redirect: '/'}
]



const router = new VueRouter({
  routes
})
router.beforeEach((to, from, next) => {
  console.log(store.state.user.token)
  window.t = store.state.user.token
  if (store.state.firstrun === true && to.path !== '/firstrun') {
    next('/firstrun')
  } else if (store.state.user.token === '' && to.path !== '/login') {
    next('/login')
  } else {
    next()
  }
})

export default router
