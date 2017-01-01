<template>
  <page-content page-title="学生管理">
    <md-dialog md-open-from="#add" md-close-to="#add" ref="addDialog">
      <md-dialog-title>添加学生</md-dialog-title>
      <md-dialog-content>
        <md-input-container :class="{'md-input-invalid': !validStudentID}">
          <label>学号</label>
          <md-input required v-model="newStudentID"></md-input>
          <span class="md-error">学号不合法（学号应为数字）或学号已存在</span>
        </md-input-container>
        <md-input-container>
          <label>学生名称</label>
          <md-input required v-model="newStudent"></md-input>
        </md-input-container>
      </md-dialog-content>

      <md-dialog-actions>
        <md-button class="md-primary" @click="closeAddDialog()">取消</md-button>
        <md-button class="md-primary" @click="addStudent()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="adding"></md-spinner>
          添加学生
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <md-dialog ref="changeDialog">
      <md-dialog-title>修改学生</md-dialog-title>
      <md-dialog-content>
        <md-input-container>
          <label>学号</label>
          <md-input v-model="currentStudentID" :disabled="true"></md-input>
        </md-input-container>
        <md-input-container>
          <label>学生名称</label>
          <md-input required v-model="currentStudentName"></md-input>
        </md-input-container>
      </md-dialog-content>

      <md-dialog-actions>
        <md-button class="md-primary" @click="closeChangeDialog()">取消</md-button>
        <md-button class="md-primary" @click="changeStudent()" :disabled="!canChange">
          <md-spinner md-indeterminate :md-size="10" v-if="changing"></md-spinner>
          修改学生
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <div class="main-content">
      <md-table-card>
        <md-toolbar>
          <h1 class="md-title">学生管理</h1>
          <md-button class="md-icon-button" id="add" @click="openAddDialog()">
            <md-icon>add circle outline</md-icon>
          </md-button>
        </md-toolbar>

        <md-table-alternate-header md-selected-label="selected">
          <md-button class="md-icon-button" @click="removeStudents()">
            <md-icon>delete</md-icon>
          </md-button>
        </md-table-alternate-header>

        <md-table @select="onSelect" @sort="onSort">
          <md-table-header>
            <md-table-row>
              <md-table-head md-numeric md-sort-by="id">学号</md-table-head>
              <md-table-head md-sort-by="name">学生名称</md-table-head>
            </md-table-row>
          </md-table-header>

          <md-table-body>
            <md-table-row v-for="row in sortedStudents" :key="row.id" :md-item="row" md-selection>
              <md-table-cell :md-numeric="true">
                <span>{{ row.id }}</span>
              </md-table-cell>
              <md-table-cell :md-numeric="false">
                <span>{{ row.name }}</span>
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
  import { map, sortBy, reverse } from 'lodash'

  export default {
    name: 'Students',
    data() {
      return {
        newStudent: '',
        newStudentID: '',
        adding: false,
        selectedData: [],
        currentStudentName: "",
        currentStudentID: 0,
        changing: false,
        sort: {
          name: "id",
          type: "desc"
        }
      }
    },
    computed: {
      ...mapGetters([
        'students'
      ]),
      validStudentID() {
        if (this.newStudentID === '') {
          return true
        }
        if (!/^\d+$/.test(this.newStudentID)) {
          return false
        }
        const id = parseInt(this.newStudentID);
        return !this.students.some(x => x.id == id)
      },
      canSubmit() {
        return this.validStudentID && this.newStudent !== '' && !(this.newStudentID === '' || this.adding)
      },
      canChange() {
        return !this.changing
      },
      sortedStudents() {
        const result = sortBy(this.students, [this.sort.name])
        return this.sort.type == "desc" ? result : reverse(result)
      }
    },
    methods: {
      ...mapMutations([
        'updateStudents'
      ]),
      onSelect(data) {
        this.selectedData = data;
      },
      onSort(sort) {
        this.sort = sort;
      },
      openAddDialog() {
        this.newStudent = ''
        this.newStudentID = ''
        this.$refs['addDialog'].open()
      },
      addStudent() {
        if (!this.canSubmit) {
          return
        }
        this.adding = true
        API.post('/student', {
          name: this.newStudent.trim(),
          id: this.newStudentID
        })
        .then((r) => {
          this.adding = false
          this.updateStudents(r.data)
          this.closeAddDialog()
        })
        .catch((error) => {
          console.error(error)
          this.adding = false
        });
      },
      openChangeDialog(student) {
        this.currentStudentName = student.name
        this.currentStudentID = student.id
        this.$refs['changeDialog'].open()
      },
      closeAddDialog() {
        this.$refs['addDialog'].close()
      },
      closeChangeDialog() {
        this.$refs['changeDialog'].close()
      },
      changeStudent() {
        if (!this.canChange) {
          return
        }
        this.changing = true
        API.put(`/student/${this.currentStudentID}`, {
          name: this.currentStudentName.trim()
        })
        .then((r) => {
          this.changing = false
          this.closeChangeDialog()
          this.updateStudents(this.students.map(s => {
            if (s.id === this.currentStudentID) {
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
      removeStudents() {
        map(this.selectedData, s => API.delete(`/student/${s.id}`).then(
          r => this.updateStudents(r.data)
        ))
        this.selectedData = []
      }
    }
  }
</script>
