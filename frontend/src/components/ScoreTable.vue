<template>
  <div>
    <md-dialog md-open-from="#add" md-close-to="#add" ref="addDialog">
      <md-dialog-title>录入新成绩</md-dialog-title>
      <md-dialog-content>
        <md-input-container>
          <label for="student">学生</label>
          <md-select required name="student" id="student" v-model="current.student">
            <md-option :value="student.id" v-for="student in uninputedStudents" :key="student.id">{{ student.name }}</md-option>
          </md-select>
        </md-input-container>
        <md-input-container :class="{'md-input-invalid': !scoreValid}">
          <label>得分</label>
          <md-input required v-model="current.score"></md-input>
          <span class="md-error">得分应是在0到100之间的正整数</span>
        </md-input-container>
      </md-dialog-content>
      <md-dialog-actions>
        <md-button class="md-primary" @click="closeAddDialog()">取消</md-button>
        <md-button class="md-primary" @click="addScore()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="adding"></md-spinner>
          录入新成绩
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <md-dialog ref="changeDialog">
      <md-dialog-title>修改成绩</md-dialog-title>
      <md-dialog-content>
        <md-input-container>
          <label>学生</label>
          <md-input required v-model="studentNames[current.student]" :disabled="true"></md-input>
        </md-input-container>
        <md-input-container :class="{'md-input-invalid': !scoreValid}">
          <label>得分</label>
          <md-input required v-model="current.score"></md-input>
          <span class="md-error">得分应是在0到100之间的正整数</span>
        </md-input-container>
      </md-dialog-content>
      <md-dialog-actions>
        <md-button class="md-primary" @click="closeChangeDialog()">取消</md-button>
        <md-button class="md-primary" @click="changeScore()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="changing"></md-spinner>
          修改成绩
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <md-table-card>
      <md-toolbar>
        <h1 class="md-title">成绩录入</h1>
        <h3 class="md-subhead">已录入{{ relatedScores.length }}条成绩。 还有 {{ uninputedStudents.length }} 名学生未录入成绩。</h3>
        <md-button class="md-icon-button" id="add" @click="openAddDialog()" :disabled="!uninputedStudents.length">
          <md-icon>add circle outline</md-icon>
        </md-button>
      </md-toolbar>

      <md-table-alternate-header md-selected-label="selected">
        <md-button class="md-icon-button" @click="remove()">
          <md-icon>delete</md-icon>
        </md-button>
      </md-table-alternate-header>

      <md-table @select="onSelect" @sort="onSort">
        <md-table-header>
          <md-table-row>
            <md-table-head md-sort-by="name">学生</md-table-head>
            <md-table-head md-sort-by="date">得分</md-table-head>
            <md-table-head>操作</md-table-head>
          </md-table-row>
        </md-table-header>

        <md-table-body>
          <md-table-row v-for="row in sortedData" :key="row.id" :md-item="row" md-selection>
            <md-table-cell>
              <span>{{ studentNames[row.student] }}</span>
            </md-table-cell>
            <md-table-cell>
              <span>{{ row.score }}</span>
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
  </page-content>
</template>

<script>
  import { mapGetters, mapMutations } from 'vuex'
  import { API } from 'src/utils'
  import { filter, map, sortBy, reverse } from 'lodash'
  import moment from 'moment'

  export default {
    name: 'ScoreTable',
    props: ['exam'],
    data() {
      return {
        adding: false,
        selectedData: [],
        current: {
          id: 0,
          student: 0,
          score: 0
        },
        changing: false,
        sort: {
          name: "score",
          type: "asc"
        }
      }
    },
    computed: {
      ...mapGetters([
        'studentNames',
        'students',
        'scores'
      ]),
      relatedScores() {
        return filter(this.scores, s => s.exam === this.exam.id)
      },
      inputedStudents() {
        return this.relatedScores.map(x => x.student)
      },
      uninputedStudents() {
        return this.students.filter(s => !this.inputedStudents.includes(s.id))
      },
      sortedData() {
        const result = sortBy(this.relatedScores, [this.sort.name])
        return this.sort.type == "desc" ? result : reverse(result)
      },
      currentScore() {
        return parseInt(this.current.score)
      },
      scoreValid() {
        if (!/^\d+$/.test(this.current.score)) {
          return false
        }
        return this.currentScore >= 0 && this.currentScore <= 100
      },
      canSubmit() {
        return this.current.student !== 0 && this.scoreValid && !(this.adding || this.changing)
      },
    },
    methods: {
      ...mapMutations([
        'updateScores'
      ]),
      onSelect(data) {
        this.selectedData = data;
      },
      onSort(sort) {
        this.sort = sort;
      },
      openAddDialog() {
        this.current = {
          id: 0,
          student: 0,
          score: 0
        },
        this.$refs['addDialog'].open()
      },
      openChangeDialog(score) {
        this.current = {...score}
        this.$refs['changeDialog'].open()
      },
      closeAddDialog() {
        this.$refs['addDialog'].close()
      },
      closeChangeDialog() {
        this.$refs['changeDialog'].close()
      },
      addScore() {
        if (!this.canSubmit) {
          return
        }
        this.adding = true
        API.post('/score', {
          score: this.current.score,
          student_id: this.current.student,
          exam_id: this.exam.id
        })
        .then((r) => {
          this.adding = false
          this.updateScores(r.data)
          this.closeAddDialog()
        })
        .catch((error) => {
          console.error(error)
          this.adding = false
        })
      },
      changeScore() {
        if (!this.canSubmit) {
          return
        }
        this.changing = true
        API.put(`/score/${this.current.id}`, {
          score: this.current.score
        })
        .then((r) => {
          this.changing = false
          this.closeChangeDialog()
          this.updateScores(this.scores.map(s => {
            if (s.id === this.current.id) {
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
      remove() {
        map(this.selectedData, s => API.delete(`/score/${s.id}`).then(r => this.updateScores(r.data)))
        this.selectedData = []
      }
    }
  }
</script>
