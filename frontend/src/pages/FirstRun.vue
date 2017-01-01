<template>
  <page-content page-title="首次运行">
    <div class="main-content">
      <h1 class="md-title">欢迎来到学生成绩管理系统！</h1>
      <span>这是你首次运行本系统，请先创建一个管理员账号：</span>
      <md-input-container>
        <label>用户名</label>
        <md-input required v-model="username"></md-input>
      </md-input-container>
      <md-input-container>
        <label>密码</label>
        <md-input required v-model="password" type="password"></md-input>
      </md-input-container>
      <md-button class="md-primary" @click="createUser()" :disabled="!canSubmit">
        <md-spinner md-indeterminate :md-size="10" v-if="submiting"></md-spinner>
        创建用户
      </md-button>
    </div>
  </page-content>
</template>

<script>
  import { mapMutations } from 'vuex'
  import { API } from 'src/utils'

  export default {
    name: 'FirstRun',
    data() {
      return {
        username: '',
        password: '',
        submiting: false
      }
    },
    computed: {
      canSubmit() {
        return this.username.trim() !== '' && this.password.trim() !== '' && !this.submiting
      },
    },
    methods: {
      ...mapMutations([
        'login'
      ]),
      createUser() {
        if (!this.canSubmit) {
          return
        }
        this.submiting = true
        API.post('/firstrun', {
          username: this.username.trim(),
          password: this.password.trim()
        })
        .then((r) => {
          this.submiting = false
          this.login(this.username.trim, r.data)
          this.$router.go('/')
        })
        .catch((error) => {
          console.error(error)
          this.submiting = false
        });
      }
    }
  }
</script>
