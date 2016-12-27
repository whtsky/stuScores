import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import { API } from 'src/utils'

export const store = new Vuex.Store({
  state: {
    students: [],
    subjects: [],
    exams: [],
    scores: [],
    loading: {
      students: true,
      subjects: true,
      exams: true,
      scores: true
    }
  },
  mutations: {
    updateStudents(state, students) {
      state.students = students
      state.loading.students = false
    },
    updateSubjects(state, subjects) {
      state.subjects = subjects
      state.loading.subjects = false
    },
    updateExams(state, exams) {
      state.exams = exams
      state.loading.exams = false
    },
    updateScores(state, scores) {
      state.scores = scores
      state.loading.scores = false
    }
  },
  getters: {
    students: state => state.students,
    classes: state => state.classes,
    subjects: state => state.subjects,
    exams: state => state.exams,
    scores: state => state.scores,
    loading: state => Object.values(state.loading).some(v => v === true),
    studentNames: (state, getters) => {
      const studentMap = {}
      for(let student of getters.students) {
        studentMap[student.id] = student.name
      }
      return studentMap
    },
    examNames: (state, getters) => {
      const examMap = {}
      for(let exam of getters.exams) {
        examMap[exam.id] = exam.name
      }
      return examMap
    }
  },
  actions: {
    fetchStudents(context) {
      API.get('/student').then(res => {
        context.commit('updateStudents', res.data)
      }).catch(err => console.error(err))
    },
    fetchSubjects(context) {
      API.get('/subject').then(res => {
        context.commit('updateSubjects', res.data)
      }).catch(err => console.error(err))
    },
    fetchExams(context) {
      API.get('/exam').then(res => {
        context.commit('updateExams', res.data)
      }).catch(err => console.error(err))
    },
    fetchScores(context) {
      API.get('/score').then(res => {
        context.commit('updateScores', res.data)
      }).catch(err => console.error(err))
    }
  }
})
