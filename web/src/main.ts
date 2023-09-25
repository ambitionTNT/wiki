import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue';
import axios from "axios";
const app = createApp(App)
app.use(store).use(router).use(Antd).mount('#app')
axios.defaults.baseURL = process.env.VUE_APP_SERVE



const icons: any = Icons;
for (const i in icons) {
    app.component(i, icons[i]);
}
console.log('环境:' , process.env.NODE_ENV)
console.log('服务端:' , process.env.VUE_APP_SERVE)

axios.interceptors.request.use(function (config) {
    console.log('请求参数:',config);
    return config
}, error => {
    return Promise.reject(error)
});

axios.interceptors.response.use(function (response){
    console.log('相应参数:',response);
    return response
},error => {
    return Promise.reject(error)
})

