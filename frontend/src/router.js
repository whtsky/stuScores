import { store } from './store'
import { sync } from 'vuex-router-sync'

import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

import Students from 'pages/Students'
import Exams from 'pages/Exams'
import Subjects from 'pages/Subjects'
import Users from 'pages/Users'

import ExamDetail from 'pages/ExamDetail'

import FirstRun from 'pages/FirstRun'
import Login from 'pages/Login'

const routes = [
  { path: '/student', component: Students },
  { path: '/exam', component: Exams, alias: '/'},
  { path: '/exam/:id', component: ExamDetail, name: 'exam' },
  { path: '/subject', component: Subjects},
  { path: '/user', component: Users},
  {
    path: '/firstrun',
    component: FirstRun,
    beforeEnter: (to, from, next) => {
      if(!store.state.firstrun) {
        next('/')
        return
      }
      next()
    }
  },
  {
    path: '/login',
    component: Login,
    beforeEnter: (to, from, next) => {
      if(store.state.user.token !== '') {
        next('/')
      }
      next()
    }
  },
  { path: '*', redirect: '/'}
]



const router = new VueRouter({
  routes
})
router.beforeEach((to, from, next) => {
  if (store.state.firstrun === true && to.path !== '/firstrun') {
    next('/firstrun')
    return
  }
  if (store.state.firstrun === false && store.state.user.token === '' && to.path !== '/login') {
    next('/login')
    return
  }
  next()
})
sync(store, router, { moduleName: 'RouteModule' })

export default router
