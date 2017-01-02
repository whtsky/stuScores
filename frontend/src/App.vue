<template>
  <md-theme md-name="default">
    <div class="container">
      <md-sidenav class="main-sidebar md-left md-fixed" ref="main-sidebar">
        <md-theme md-name="sidebar">
          <md-toolbar class="stu-logo">
            <img src="./assets/logo.png">
            <span>学生成绩管理系统</span>
          </md-toolbar>
        </md-theme>

        <div class="main-sidebar-links">
          <md-list class="md-dense">
            <md-list-item>
              <router-link exact to="/subject">学科管理</router-link>
            </md-list-item>

            <md-list-item>
              <router-link exact to="/student">学生管理</router-link>
            </md-list-item>

            <md-list-item>
              <router-link exact to="/exam">考试管理</router-link>
            </md-list-item>
            <md-list-item>
              <router-link exact to="/user">用户管理</router-link>
              <md-divider class="md-inset"></md-divider>
            </md-list-item>
          </md-list>
        </div>
        <div class="main-sidebar-admin-actions" v-show="username">
          <md-list class="md-dense">
            <md-list-item @click="doLogout()">
              退出登录
            </md-list-item>
          </md-list>
        </div>
      </md-sidenav>

      <transition name="md-router">
        <router-view></router-view>
      </transition>
    </div>
  </md-theme>
</template>

<style lang="scss">
  @import "../node_modules/vue-material/src/core/stylesheets/variables.scss";

  $sizebar-size: 280px;

  html,
  body {
    height: 100%;
  }
  body {
    display: flex;
  }
  .container {
    min-height: 100%;
    display: flex;
    flex-flow: column nowrap;
    flex: 1;
    transition: $swift-ease-out;
    @media (min-width: 1281px) {
      padding-left: $sizebar-size;
    }
  }
  .main-sidebar.md-sidenav {
    .md-sidenav-content {
      width: $sizebar-size;
      display: flex;
      flex-flow: column;
      @media (min-width: 1281px) {
        top: 0;
        pointer-events: auto;
        transform: translate3d(0, 0, 0);
        box-shadow: $material-shadow-2dp;
      }
    }
    .md-backdrop {
      @media (min-width: 1281px) {
        opacity: 0;
        pointer-events: none;
      }
    }
    .md-toolbar {
      min-height: 172px;
      border-bottom: 1px solid rgba(#000, .12);
    }
    .stu-logo {
      font-size: 24px;
      display: flex;
      flex-flow: column;
      justify-content: center;
      align-items: center;
      text-decoration: none;
      padding-bottom: 16px;
      img {
        width: 120px;
        height: 120px;
        margin-bottom: 16px;
      }
    }
    .main-sidebar-links, .main-sidebar-admin-actions {
      overflow: auto;
      .md-inset .md-list-item-container {
        padding-left: 36px;
      }
      .md-list-item-container {
        font-size: 14px;
        font-weight: 500;
      }
    }
    .main-sidebar-admin-actions {
      margin-top: auto;
    }
  }
  .main-content {
    padding: 16px;
    flex: 1;
    overflow: auto;
    transform: translate3D(0, 0, 0);
  }
  .md-router-enter-active,
  .md-router-leave-active {
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
    transition: $swift-ease-out;
    @media (min-width: 1281px) {
      left: 280px;
    }
  }
  .md-router-enter,
  .md-router-leave-active {
    opacity: 0;
  }
</style>

<script>
  import { mapGetters, mapActions } from 'vuex'

  export default {
    name: 'App',
    computed: {
      ...mapGetters([
        'username'
      ]),
    },
    methods: {
      ...mapActions([
        'logout'
      ]),
      toggleSidenav() {
        this.$refs['main-sidebar'].toggle();
      },
      closeSidenav() {
        this.$refs['main-sidebar'].close();
      },
      doLogout() {
        this.logout().then(r => this.$router.push('/login'))
      }
    }
  }
</script>
