<template>
  <a-layout-header class="header">
    <div class="logo" />
    <a-menu
        v-model:selectedKeys="selectedKeys1"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/">
        <router-link to="/">首页</router-link>
      </a-menu-item>

      <a-menu-item key="/admin/ebook">
        <router-link to="/admin/ebook">电子书管理</router-link>
      </a-menu-item>

      <a-menu-item key="/admin/category">
        <router-link to="/admin/category">分类管理</router-link>
      </a-menu-item>

      <a-menu-item key="/admin/user">
        <router-link to="/admin/user">用户管理</router-link>
      </a-menu-item>


      <a-menu-item key="/about">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>

      <a-menu-item >
      <a v-show="user.id"  class="login-menu"  >
        <span>您好:{{ user.name }}</span>
      </a>
      </a-menu-item>
      <a-menu-item   >
        <a  @click = "showLoginModel"  v-show="!user.id" class="login-menu">
          <span>登录</span>
        </a>
      </a-menu-item>

      </a-menu>

    <a-modal v-model:open="open"
            title="登录"
             @ok="login"
             :loading = loading
    >
      <a-form :model="loginUser" :label-col="{span : 6}" >
        <a-form-item label="登录名">
          <a-input v-model:value="loginUser.loginName" />
        </a-form-item>
        <a-form-item label="密码">
          <a-input v-model:value="loginUser.password" type="password" />
        </a-form-item>
      </a-form>

    </a-modal>
  </a-layout-header>


</template>


<script lang="ts">
import {defineComponent, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
declare let hexMd5: any;
declare let KEY: any;
export default defineComponent({
  name: 'the-header',

setup(){

  const loading = ref(false);

  const user = ref();
  user.value = {}
    const open = ref(false)
    const loginUser = ref({
      loginName: "test",
      password: "test"
    })

  const showLoginModel =() =>{
    open.value = true;

  }

  const login=()=>{
    loading.value = true;
      console.log("开始登录")
      loginUser.value.password = hexMd5(loginUser.value.password + KEY);
      axios.post('/user/login', loginUser.value).then((response)=>{
        const data = response.data;
        if (data.success){
          open.value = false;

          message.success("登录成功");
          user.value = data.content
        }else {

          message.error(data.message);
        }
      })
  }

  return{
    open,
    user,
    loginUser,
    loading,
    showLoginModel,
    login
  }
}


});
</script>

<style>

.login-menu {
  float: right;
  color: white;
}

.logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}


</style>
