<template>
  <page-content page-title="考试管理">
    <md-dialog md-open-from="#add" md-close-to="#add" ref="addDialog">
      <md-dialog-title>添加考试</md-dialog-title>
      <md-dialog-content>
        <md-input-container :class="{'md-input-invalid': examExist}">
          <label>考试名称</label>
          <md-input required v-model="currentExam.name"></md-input>
          <span class="md-error">该考试已存在</span>
        </md-input-container>
        <md-input-container :class="{'md-input-invalid': examExist}">
          <label>考试时间</label>
          <md-input required v-model="currentExam.date" type="date" placeholder="请选择考试时间"></md-input>
          <span class="md-error">该考试已存在</span>
        </md-input-container>
        <md-input-container>
          <label for="subject">考试科目</label>
          <md-select required name="subject" id="subject" v-model="currentExam.subject">
            <md-option :value="subject.id" v-for="subject in subjects" :key="subject.id">{{ subject.name }}</md-option>
          </md-select>
        </md-input-container>
      </md-dialog-content>
      <md-dialog-actions>
        <md-button class="md-primary" @click="closeAddDialog()">取消</md-button>
        <md-button class="md-primary" @click="addExam()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="adding"></md-spinner>
          添加考试
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <md-dialog ref="changeDialog">
      <md-dialog-title>修改考试</md-dialog-title>
      <md-dialog-content>
          <md-input-container :class="{'md-input-invalid': examExist}">
            <label>考试名称</label>
            <md-input required v-model="currentExam.name"></md-input>
            <span class="md-error">该考试已存在</span>
          </md-input-container>
          <md-input-container :class="{'md-input-invalid': examExist}">
            <label>考试时间</label>
            <md-input required v-model="currentExam.date" type="date"></md-input>
            <span class="md-error">该考试已存在</span>
          </md-input-container>
          <md-input-container>
            <label>考试科目</label>
            <md-input required v-model="subjectNames[currentExam.subject]" :disabled="true"></md-input>
          </md-input-container>
        </form>
      </md-dialog-content>
      <md-dialog-actions>
        <md-button class="md-primary" @click="closeChangeDialog()">取消</md-button>
        <md-button class="md-primary" @click="changeExam()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="changing"></md-spinner>
          修改考试
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <div class="main-content">
      <md-table-card>
        <md-toolbar>
          <h1 class="md-title">考试管理</h1>
          <md-button class="md-icon-button" id="add" @click="openAddDialog()">
            <md-icon>add circle outline</md-icon>
          </md-button>
        </md-toolbar>

        <md-table-alternate-header md-selected-label="selected">
          <md-button class="md-icon-button" @click="removeExams()">
            <md-icon>delete</md-icon>
          </md-button>
        </md-table-alternate-header>

        <md-table @select="onSelect" @sort="onSort">
          <md-table-header>
            <md-table-row>
              <md-table-head md-numeric md-sort-by="id">ID</md-table-head>
              <md-table-head md-sort-by="name">考试名称</md-table-head>
              <md-table-head md-sort-by="date">考试时间</md-table-head>
              <md-table-head md-sort-by="subject">考试科目</md-table-head>
              <md-table-head md-sort-by="scores">已录入成绩数</md-table-head>
              <md-table-head>操作</md-table-head>
            </md-table-row>
          </md-table-header>

          <md-table-body>
            <md-table-row v-for="row in sortedData" :key="row.id" :md-item="row" md-selection>
              <md-table-cell :md-numeric="true">
                <span>{{ row.id }}</span>
              </md-table-cell>
              <md-table-cell :md-numeric="false">
                <router-link :to="{ name: 'exam', params: { id: row.id }}">{{ row.name }}</router-link>
              </md-table-cell>
              <md-table-cell>
                <span>{{ row.date }}</span>
              </md-table-cell>
              <md-table-cell>
                <span>{{ subjectNames[row.subject] }}</span>
              </md-table-cell>
              <md-table-cell>
                <span>{{ row.scores }} / {{ students.length }}</span>
              </md-table-cell>
              <md-table-cell>
                <md-button class="md-icon-button" @click="openChangeDialog(row)">
                  <md-icon>edit</md-icon>
                </md-button>
              </md-table-cell>
            </md-table-row>
          </md-table-body>
        </md-table>
      </md-table-card>
    </div>
  </page-content>
</template>

<script>
  import { mapGetters, mapMutations } from 'vuex'
  import { API } from 'src/utils'
  import { map, sortBy, reverse, groupBy } from 'lodash'
  import moment from 'moment'

  export default {
    name: 'Exams',
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
        'scores',
        'students',
        'subjectNames'
      ]),
      scoreCounts() {
        const groupedScores = groupBy(this.scores, 'exam')
        const counts = {}
        for(const k in groupedScores) {
          counts[k] = groupedScores[k].length
        }
        return counts
      },
      examsWithCount() {
        return this.exams.map(x => {
          x.scores = this.scoreCounts[x.id] || 0
          return x
        })
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
        const result = sortBy(this.examsWithCount, [this.sort.name])
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
