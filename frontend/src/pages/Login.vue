<template>
  <page-content page-title="请登录">
    <div class="main-content">
      <h1 class="md-title">欢迎来到学生成绩管理系统！</h1>
      <span>请登录</span>
      <md-input-container :class="{'md-input-invalid': error}">
        <label>用户名</label>
        <md-input required v-model="username"></md-input>
        <span class="md-error">错误的用户名或密码</span>
      </md-input-container>
      <md-input-container :class="{'md-input-invalid': error}">
        <label>密码</label>
        <md-input required v-model="password" type="password"></md-input>
        <span class="md-error">错误的用户名或密码</span>
      </md-input-container>
      <md-button class="md-primary" @click="doLogin" :disabled="!canSubmit">
        <md-spinner md-indeterminate :md-size="10" v-if="submiting"></md-spinner>
        登录
      </md-button>
    </div>
  </page-content>
</template>

<script>
  import { mapActions } from 'vuex'
  import { API } from 'src/utils'

  export default {
    name: 'Login',
    data() {
      return {
        username: '',
        password: '',
        submiting: false,
        error: false
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
      doLogin() {
        if (!this.canSubmit) {
          return
        }
        this.submiting = true
        this.error = false
        API.post('/authentication', {
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
          } else {
            this.error = true
          }
        })
        .catch((error) => {
          console.error(error)
          this.error = true
          this.submiting = false
        });
      }
    }
  }
</script>
