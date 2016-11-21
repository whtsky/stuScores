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
    scores: staet => state.scores,
    classNames: (state, getters) => {
      const classMap = {}
      for(let studentClass of getters.classes) {
        classMap[studentClass.id] = studentClass.name
      }
      return classMap
    },
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
    fetchClasses(context) {
      API.get('/class').then(res => {
        context.commit('updateClasses', res.data)
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
