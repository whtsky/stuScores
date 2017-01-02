import { store } from './store'

import Vue from 'vue'
import VueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.css'
import { API } from './utils'

Vue.use(VueMaterial)

Vue.material.registerTheme('sidebar', {
  primary: 'white'
})

import PageContent from 'components/PageContent';
Vue.component('page-content', PageContent);

import App from './App'

import router from './router.js'


const Instance = Vue.component('app', App)

API.get('firstrun').then(r => {
  store.state.firstrun = r.data
  new Instance({
    el: '#app',
    router,
    store,
  })
}).catch(e => console.error(e))

