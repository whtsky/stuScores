<template>
  <page-content page-title="学科管理">
    <md-dialog md-open-from="#add" md-close-to="#add" ref="addDialog">
      <md-dialog-title>添加学科</md-dialog-title>
      <md-dialog-content>
          <md-input-container :class="{'md-input-invalid': subjectExist}">
            <label>学科名称</label>
            <md-input required></md-input>
            <span class="md-error">该学科已存在</span>
          </md-input-container>
        </form>
      </md-dialog-content>

      <md-dialog-actions>
        <md-button class="md-primary" @click="closeAddDialog()">Cancel</md-button>
        <md-button class="md-primary" @click="closeAddDialog()">Ok</md-button>
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
          <md-button class="md-icon-button">
            <md-icon>delete</md-icon>
          </md-button>
        </md-table-alternate-header>

        <md-table md-sort="calories">
          <md-table-header>
            <md-table-row>
              <md-table-head md-sort-by="dessert">学科名称</md-table-head>
              <md-table-head md-sort-by="fat" md-numeric>学生人数</md-table-head>
            </md-table-row>
          </md-table-header>

          <md-table-body>
            <md-table-row v-for="(row, rowIndex) in nutrition" :key="rowIndex" :md-item="row" md-selection>
              <md-table-cell v-for="(column, columnIndex) in row" :key="columnIndex" :md-numeric="columnIndex !== 'dessert' && columnIndex !== 'comment' && columnIndex !== 'type'">
                <span v-if="columnIndex === 'comment'">{{ column }}</span>

                <md-button class="md-icon-button" v-if="columnIndex === 'comment'">
                  <md-icon>edit</md-icon>
                </md-button>

                <md-select
                  placeholder="Type"
                  :name="'type' + columnIndex"
                  :id="'type' + columnIndex"
                  v-model="nutrition[rowIndex].type"
                  v-if="columnIndex === 'type'">
                  <md-option value="ice_cream">Ice Cream</md-option>
                  <md-option value="pastry">Pastry</md-option>
                  <md-option value="other">Other</md-option>
                </md-select>

                <span v-if="columnIndex !== 'type' && columnIndex !== 'comment'">{{ column }}</span>
              </md-table-cell>
            </md-table-row>
          </md-table-body>
        </md-table>
      </md-table-card>
    </div>
  </page-content>
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Subjects',
    data() {
      return {
        newSubject: ''
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
      }
    },
    methods: {
      openAddDialog() {
        this.newSubject = ''
        this.$refs['addDialog'].open()
      },
      closeAddDialog() {
        this.$refs['addDialog'].close()
      }
    }
  }
</script>
