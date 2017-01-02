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
  import { mapActions } from 'vuex'
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
      ...mapActions([
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
          if (r.status === 200) {
            this.login({
              username: this.username.trim(),
              token: r.data
            }).then(() => this.$router.push('/'))
          }
        })
        .catch((error) => {
          console.error(error)
          this.submiting = false
        });
      }
    }
  }
</script>
