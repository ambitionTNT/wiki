import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/home.vue'
import About from '../views/about.vue'
import Doc from '@/views/doc.vue'
import AdminEbook from '../views/admin/admin-ebook.vue'
import AdminUser from "@/views/admin/admin-user.vue";
import AdminCategory from "@/views/admin/admin-category.vue";
import AdminDoc from "@/views/admin/admin-doc.vue";



const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: Home
  },

  {
    path: '/doc',
    name: 'Doc',
    component: Doc
  },

  {
    path: '/admin/user',
    name: 'AdminUser',
    component: AdminUser
  },
  {
    path: '/about',
    name: 'About',
    component: About

    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/about.vue')
  },
  {
    path: '/admin/ebook',
    name: 'adminEbook',
    component: AdminEbook
  },
  {
    path: '/admin/category',
    name: 'adminCategory',
    component: AdminCategory
  },
  {
    path: '/admin/doc',
    name: 'adminDoc',
    component: AdminDoc
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
