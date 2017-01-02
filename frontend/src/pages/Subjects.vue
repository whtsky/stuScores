<template>
  <page-content page-title="学科管理">
    <md-dialog md-open-from="#add" md-close-to="#add" ref="addDialog">
      <md-dialog-title>添加学科</md-dialog-title>
      <md-dialog-content>
        <md-input-container :class="{'md-input-invalid': subjectExist}">
          <label>学科名称</label>
          <md-input required v-model="newSubject"></md-input>
          <span class="md-error">该学科已存在</span>
        </md-input-container>
      </md-dialog-content>

      <md-dialog-actions>
        <md-button class="md-primary" @click="closeAddDialog()">取消</md-button>
        <md-button class="md-primary" @click="addSubject()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="adding"></md-spinner>
          添加学科
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <md-dialog ref="changeDialog">
      <md-dialog-title>修改学科</md-dialog-title>
      <md-dialog-content>
        <md-input-container :class="{'md-input-invalid': changeSubjectExist}">
          <label>学科名称</label>
          <md-input required v-model="currentSubjectName"></md-input>
          <span class="md-error">该学科已存在</span>
        </md-input-container>
      </md-dialog-content>

      <md-dialog-actions>
        <md-button class="md-primary" @click="closeChangeDialog()">取消</md-button>
        <md-button class="md-primary" @click="changeSubject()" :disabled="!canChange">
          <md-spinner md-indeterminate :md-size="10" v-if="changing"></md-spinner>
          修改学科
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <div class="main-content">
      <md-table-card>
        <md-toolbar>
          <h1 class="md-title">学科管理</h1>
          <md-button class="md-icon-button" id="add" @click="openAddDialog()">
            <md-icon>add circle outline</md-icon>
          </md-button>
        </md-toolbar>

        <md-table-alternate-header md-selected-label="selected">
          <md-button class="md-icon-button" @click="removeSubjects()">
            <md-icon>delete</md-icon>
          </md-button>
        </md-table-alternate-header>

        <md-table @select="onSelect" @sort="onSort">
          <md-table-header>
            <md-table-row>
              <md-table-head md-numeric md-sort-by="id">ID</md-table-head>
              <md-table-head md-sort-by="name">学科名称</md-table-head>
            </md-table-row>
          </md-table-header>

          <md-table-body>
            <md-table-row v-for="row in sortedData" :key="row.name" :md-item="row" md-selection>
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
  import axios from 'axios'
  import { map, sortBy, reverse } from 'lodash'

  export default {
    name: 'Subjects',
    data() {
      return {
        newSubject: '',
        adding: false,
        selectedData: [],
        currentSubjectName: "",
        currentSubjectID: 0,
        changing: false,
        sort: {
          name: "id",
          type: "desc"
        }
      }
    },
    computed: {
      ...mapGetters([
        'subjects'
      ]),
      trimedNewSubjectName() {
        return this.newSubject.trim()
      },
      subjectExist() {
        return this.subjects.some(x => x.name === this.trimedNewSubjectName)
      },
      canSubmit() {
        return this.trimedNewSubjectName && !(this.subjectExist || this.adding)
      },
      trimedChangeSubjectName() {
        return this.currentSubjectName.trim()
      },
      changeSubjectExist() {
        return this.subjects.some(x => x.name === this.trimedChangeSubjectName && x.id !== this.currentSubjectID)
      },
      canChange() {
        return this.trimedChangeSubjectName && !(this.changeSubjectExist || this.changing)
      },
      sortedData() {
        const result = sortBy(this.subjects, [this.sort.name])
        return this.sort.type == "desc" ? result : reverse(result)
      },
    },
    methods: {
      ...mapMutations([
        'updateSubjects'
      ]),
      onSelect(data) {
        this.selectedData = data;
      },
      onSort(sort) {
        this.sort = sort;
      },
      openAddDialog() {
        this.newSubject = ''
        this.$refs['addDialog'].open()
      },
      openChangeDialog(subject) {
        this.currentSubjectName = subject.name
        this.currentSubjectID = subject.id
        this.$refs['changeDialog'].open()
      },
      closeAddDialog() {
        this.$refs['addDialog'].close()
      },
      closeChangeDialog() {
        this.$refs['changeDialog'].close()
      },
      addSubject() {
        if (!this.canSubmit) {
          return
        }
        this.adding = true
        API.post('/subject', {
          name: this.trimedNewSubjectName
        })
        .then((r) => {
          this.adding = false
          location.reload()
        })
        .catch((error) => {
          console.error(error)
          this.adding = false
        });
      },
      changeSubject() {
        if (!this.canChange) {
          return
        }
        this.changing = true
        API.put(`/subject/${this.currentSubjectID}`, {
          name: this.trimedChangeSubjectName
        })
        .then((r) => {
          this.changing = false
          this.closeChangeDialog()
          this.updateSubjects(this.subjects.map(s => {
            if (s.id === this.currentSubjectID) {
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
      removeSubjects() {
        axios.all(map(this.selectedData, s => API.delete(`/subject/${s.id}`)))
        .then(axios.spread(function (acct, perms) {
          location.reload()
        }))
      }
    }
  }
</script>
