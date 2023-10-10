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

      <a-menu-item key="/admin/ebook" >
        <router-link to="/admin/ebook"  :style="user.id? {} : {display:'none'}">电子书管理</router-link>
      </a-menu-item>

      <a-menu-item key="/admin/category"  >
        <router-link to="/admin/category"  :style="user.id? {} : {display:'none'}">分类管理</router-link>
      </a-menu-item>

      <a-menu-item key="/admin/user"  >
        <router-link to="/admin/user" :style="user.id? {} : {display:'none'}">用户管理</router-link>
      </a-menu-item>
      <a-menu-item  class="login-menu"   >
        <a  @click = "showLoginModel"  v-show="!user.id" class="login-menu">
          <span>登录</span>
        </a>
      </a-menu-item>

      <a-menu-item key="/about" :style="user.id? {} : {display:'none'}">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>



      <a-menu-item  class="login-menu" >
        <a v-show="user.id"    >
          <span>您好:{{ user.name }}</span>
        </a>
      </a-menu-item>
      <a-menu-item   class="login-menu" >

        <a-popconfirm
            title="删除后不可回复，您确认要删除?"
            ok-text="Yes"
            cancel-text="No"
            @confirm="logout()"

        >
        <a  v-show="user.id">

          <span>退出登录</span>
        </a>
        </a-popconfirm>
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
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";

declare let hexMd5: any;
declare let KEY: any;
export default defineComponent({
  name: 'the-header',

setup(){

  const loading = ref(false);

  const user = computed(()=>
    store.state.user
   )
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

          store.commit("setUser", data.content);
        }else {

          message.error(data.message);
        }
      })
  }
  const logout =()=>{
    loading.value = true;
    console.log("开始退出登录"+ user.value.token);
    axios.get('/user/logout/' + user.value.token).then((response)=>{
      const data = response.data;
      if (data.success){

        message.success("登录成功");

        store.commit("setUser", {});
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
    login,
    logout
  }
}


});
</script>

<style>

.login-menu {
  float: right;
  color: white;
  font-size: 18px;
}

.logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: rgba(255, 255, 255, 0.3);
}


</style>
