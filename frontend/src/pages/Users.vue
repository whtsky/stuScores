<template>
  <page-content page-title="用户管理">
    <md-dialog md-open-from="#add" md-close-to="#add" ref="addDialog">
      <md-dialog-title>添加用户</md-dialog-title>
      <md-dialog-content>
        <md-input-container :class="{'md-input-invalid': userExist}">
          <label>用户名</label>
          <md-input required v-model="current.username"></md-input>
          <span class="md-error">用户名已被占用</span>
        </md-input-container>
        <md-input-container>
          <label>密码</label>
          <md-input required v-model="current.password" type="password"></md-input>
        </md-input-container>
      </md-dialog-content>

      <md-dialog-actions>
        <md-button class="md-primary" @click="closeAddDialog()">取消</md-button>
        <md-button class="md-primary" @click="addUser()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="adding"></md-spinner>
          添加用户
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <md-dialog ref="changeDialog">
      <md-dialog-title>修改密码</md-dialog-title>
      <md-dialog-content>
        <md-input-container>
          <label>用户名</label>
          <md-input v-model="current.username" :disabled="true"></md-input>
        </md-input-container>
        <md-input-container>
          <label>密码</label>
          <md-input required v-model="current.password" type="password"></md-input>
        </md-input-container>
      </md-dialog-content>

      <md-dialog-actions>
        <md-button class="md-primary" @click="closeChangeDialog()">取消</md-button>
        <md-button class="md-primary" @click="changePassword()" :disabled="!canSubmit">
          <md-spinner md-indeterminate :md-size="10" v-if="changing"></md-spinner>
          修改密码
        </md-button>
      </md-dialog-actions>
    </md-dialog>

    <div class="main-content">
      <md-table-card>
        <md-toolbar>
          <h1 class="md-title">用户管理</h1>
          <md-button class="md-icon-button" id="add" @click="openAddDialog()">
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
              <md-table-head md-numeric md-sort-by="id">ID</md-table-head>
              <md-table-head md-sort-by="name">用户名</md-table-head>
            </md-table-row>
          </md-table-header>

          <md-table-body>
            <md-table-row v-for="(row, rowIndex) in sortedData" :key="row.username" :md-item="row" md-selection>
              <md-table-cell :md-numeric="true">
                <span>{{ row.id }}</span>
              </md-table-cell>
              <md-table-cell :md-numeric="false">
                <span>{{ row.username }}</span>
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
    name: 'Users',
    data() {
      return {
        current: {
          id: 0,
          username: '',
          password: ''
        },
        adding: false,
        changing: false,
        selectedData: [],
        sort: {
          name: "id",
          type: "desc"
        }
      }
    },
    computed: {
      ...mapGetters([
        'users'
      ]),
      userExist() {
        if (this.current.username === '') {
          return false
        }
        const username = this.current.username.trim()
        return this.users.some(x => x.username === username && x.id !== this.current.id)
      },
      canSubmit() {
        return !(this.userExist || this.adding || this.changing || this.current.password === '')
      },
      sortedData() {
        const result = sortBy(this.users, [this.sort.name])
        return this.sort.type == "desc" ? result : reverse(result)
      },
    },
    methods: {
      ...mapMutations([
        'updateUsers'
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
          username: '',
          password: ''
        }
        this.$refs['addDialog'].open()
      },
      addUser() {
        if (!this.canSubmit) {
          return
        }
        this.adding = true
        API.post('/user', {
          username: this.current.username.trim(),
          password: this.current.password
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
      openChangeDialog(user) {
        this.current = {
          ...user,
          password: ''
        }
        this.$refs['changeDialog'].open()
      },
      closeAddDialog() {
        this.$refs['addDialog'].close()
      },
      closeChangeDialog() {
        this.$refs['changeDialog'].close()
      },
      changePassword() {
        if (!this.canSubmit) {
          return
        }
        this.changing = true
        API.put(`/user/${this.current.id}`, {
          password: this.current.password
        })
        .then((r) => {
          this.changing = false
          this.closeChangeDialog()
          this.updateUsers(r.data)
        })
        .catch((error) => {
          console.error(error)
          this.changing = false
        });
      },
      remove() {
        axios.all(map(this.selectedData, s => API.delete(`/user/${s.id}`)))
        .then(axios.spread(function (acct, perms) {
          location.reload()
        }))
      }
    }
  }
</script>
