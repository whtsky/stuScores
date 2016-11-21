import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import { API } from 'src/utils'

export const store = new Vuex.Store({
  state: {
    students: [],
    classes: [],
    subjects: [],
    exams: [],
    scores: []
  },
  mutations: {
    updateStudents(state, students) {
      state.students = students
    },
    updateClasses(state, classes) {
      state.classes = classes
    },
    updateSubjects(state, subjects) {
      state.subjects = subjects
    },
    updateExams(state, exams) {
      state.exams = exams
    },
    updateScores(state, scores) {
      state.scores = scores
    }
  },
  getters: {
    students: state => state.students,
    classes: state => state.classes,
    subjects: state => state.subjects,
    exams: state => state.exams,
    scores: staet => state.scores
  },
  actions: {
    fetchStudents(context) {
      API.get('/student').then(res => {
        console.log(res)
      }).catch(err => console.error(err))
    }
  }
})

API.get('/student').then(res => {
  console.log(res)
})