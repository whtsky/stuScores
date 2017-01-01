<template>
  <page-content page-title="考试详情">
    <div class="main-content" v-if="exam">
      <md-card>
        <md-card-header>
          <div class="md-title">考试信息</div>
          <div class="md-subhead">{{ exam.name }}</div>
        </md-card-header>

        <md-card-content>
          <ul>
            <li>考试名称： {{ exam.name }}</li>
            <li>考试时间： {{ exam.date }}</li>
            <li>考试科目： {{ subjectNames[exam.subject] }}</li>
            <template v-if="relatedScores.length">
              <li>平均分： {{ avgScore }}</li>
              <li>最高分： {{highestScore.score}} （学生：{{ studentNames[highestScore.student] }}）</li>
              <li>最低分： {{lowestScore.score}}（学生：{{ studentNames[lowestScore.student] }}）</li>
            </template>
          </ul>
        </md-card-content>
      </md-card>
      <score-table :exam="exam"></score-table>
    </div>
    <div class="main-content" v-else>
      Loading
    </div>
  </page-content>
</template>

<script>
  import { mapGetters, mapMutations } from 'vuex'
  import ScoreTable from 'components/ScoreTable'
  import { filter, maxBy, minBy, sortBy, sumBy } from 'lodash'

  export default {
    name: 'ExamDetail',
    components: {
      ScoreTable
    },
    data() {
      return {
        adding: false,
        selectedData: [],
        currentExam: {
          id: 0,
          name: '',
          date: '',
          subject: 0
        },
        changing: false,
        sort: {
          name: "id",
          type: "desc"
        }
      }
    },
    computed: {
      ...mapGetters([
        'exams',
        'subjects',
        'subjectNames',
        'studentNames',
        'scores'
      ]),
      examID() {
        return parseInt(this.$route.params.id)
      },
      exam() {
        return this.exams.find(e => e.id === this.examID)
      },
      relatedScores() {
        return filter(this.scores, s => s.exam === this.exam.id)
      },
      highestScore() {
        return maxBy(this.scores, x => x.score)
      },
      lowestScore() {
        return minBy(this.scores, x => x.score)
      },
      avgScore() {
        return sumBy(this.scores, x => x.score) / this.scores.length
      },
      fieldsEmpty() {
        return this.currentExam.name === '' || this.currentExam.date === '' || this.currentExam.subject === 0
      },
      examExist() {
        if (this.fieldsEmpty) {
          return false
        }
        const current = this.currentExam
        return this.exams.some(x => (
          x.name === current.name.trim() && x.date === current.date && x.subject === current.subject &&
          x.id !== current.id
        ))
      },
      canSubmit() {
        return !(this.fieldsEmpty || this.examExist || this.adding)
      },
      sortedData() {
        const result = sortBy(this.exams, [this.sort.name])
        return this.sort.type == "desc" ? result : reverse(result)
      },
    },
    methods: {
      ...mapMutations([
        'updateExams'
      ]),
      onSelect(data) {
        this.selectedData = data;
      },
      onSort(sort) {
        this.sort = sort;
      },
      openAddDialog() {
        this.currentExam = {
          id: 0,
          name: '',
          date: '',
          subject: 0
        }
        this.$refs['addDialog'].open()
      },
      openChangeDialog(exam) {
        this.currentExam = {...exam}
        this.$refs['changeDialog'].open()
      },
      closeAddDialog() {
        this.$refs['addDialog'].close()
      },
      closeChangeDialog() {
        this.$refs['changeDialog'].close()
      },
      addExam() {
        if (!this.canSubmit) {
          return
        }
        this.adding = true
        API.post('/exam', {
          name: this.currentExam.name.trim(),
          date: this.currentExam.date,
          subject: this.currentExam.subject
        })
        .then((r) => {
          this.adding = false
          this.updateExams(r.data)
          this.closeAddDialog()
        })
        .catch((error) => {
          console.error(error)
          this.adding = false
        })
      },
      changeExam() {
        if (!this.canSubmit) {
          return
        }
        this.changing = true
        API.put(`/exam/${this.currentExam.id}`, {
          name: this.currentExam.name.trim(),
          date: this.currentExam.date
        })
        .then((r) => {
          this.changing = false
          this.closeChangeDialog()
          this.updateExams(this.exams.map(s => {
            if (s.id === this.currentExam.id) {
              return r.data
            }
            return s
          }))
        })
        .catch((error) => {
          console.error(error)
          this.changing = false
        });
      },
      removeExams() {
        map(this.selectedData, s => API.delete(`/exam/${s.id}`).then(r => this.updateExams(r.data)))
        this.selectedData = []
      }
    }
  }
</script>
