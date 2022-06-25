import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  // {
  //   // hidden: true,
  //   path: '/',
  //   component: Layout,
  //   redirect: '/dashboard',
  //   children: [{
  //     path: 'dashboard',
  //     name: 'Dashboard',
  //     component: () => import('@/views/dashboard/index'),
  //     meta: { title: 'Dashboard', icon: 'dashboard' }
  //   }]
  // },
  {
    path: '/',
    component: Layout,
    redirect: '/profile',
    children: [
      {
        path: 'profile',
        name: 'Profile',
        component: () => import('@/views/profile/index'),
        meta: { title: '个人中心', icon: 'form' }
      }
    ]
  },
  // {
  //   path: '/theme',
  //   component: Layout,
  //   children: [
  //     {
  //       path: 'index',
  //       component: () => import('@/views/theme/index'),
  //       name: 'Theme',
  //       meta: { title: 'Theme', icon: 'theme' }
  //     }
  //   ]
  // },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

export const asyncRoutes = [
  {
    path: '/user',
    component: Layout,
    meta: { title: '用户论文', icon: 'dashboard', roles: ['admin', 'user'] },
    children: [
      {
        path: 'submit',
        name: 'UserPaperSubmit',
        component: () => import('@/views/user/paper/submit'),
        meta: { title: '提交论文', icon: 'form' }
      },
      {
        path: 'list',
        name: 'UserPaperList',
        component: () => import('@/views/user/paper/index'),
        meta: { title: '我的论文', icon: 'table' }
      }
    ]
  },
  {
    path: '/reviewer',
    component: Layout,
    meta: {
      title: '审稿人论文', icon: 'dashboard', roles: ['admin', 'reviewer']
    },
    children: [
      {
        path: 'invites',
        name: 'ReviewerInvites',
        component: () => import('@/views/reviewer/paper/invites'),
        meta: { title: '查看邀约', icon: 'form' }
      },
      {
        path: 'review',
        name: 'ReviewerReview',
        component: () => import('@/views/reviewer/paper/review'),
        meta: { title: '审阅论文', icon: 'form' }
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    meta: { title: '超管论文', icon: 'dashboard', roles: ['admin'] },
    children: [{
      path: 'allocate',
      name: 'AdminPaperAllocate',
      component: () => import('@/views/admin/paper/allocate'),
      meta: { title: '待确定分配', icon: 'dashboard' }
    }, {
      path: 'reviewing',
      name: 'AdminPaperReviewing',
      component: () => import('@/views/admin/paper/reviewing'),
      meta: { title: '审稿中', icon: 'dashboard' }
    }, {
      path: 'modifing',
      name: 'AdminPaperModifing',
      component: () => import('@/views/admin/paper/modify'),
      meta: { title: '退回修改中', icon: 'dashboard' }
    },
    {
      path: 'finished',
      name: 'AdminPaperFinished',
      component: () => import('@/views/admin/paper/finished'),
      meta: { title: '审稿结束', icon: 'dashboard' }
    }]
  }
]

//
const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
