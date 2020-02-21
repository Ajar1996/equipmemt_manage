import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/',
            component: () => import(/* webpackChunkName: "home" */ '../components/common/Home.vue'),
            meta: { title: '自述文件' },
            children: [
                {
                    path: '/onlineuser',
                    component: () => import(/* webpackChunkName: "icon" */ '../components/page/OnlineUser.vue'),
                    meta: { title: '在线用户' }
                },
                {
                    path: '/usertable',
                    component: () => import(/* webpackChunkName: "table" */ '../components/page/UserTable.vue'),
                    meta: { title: '用户列表' }
                },
                {
                    path: '/roletable',
                    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/RoleTable.vue'),
                    meta: { title: '角色列表' }
                },
				{
				    path: '/resourcetable',
				    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/ResourceTable.vue'),
				    meta: { title: '资源列表' }
				},
				{
				    path: '/dashboard',
				    component: () => import(/* webpackChunkName: "tabs" */ '../components/page/Dashboard.vue'),
				    meta: { title: '系统首页' }
				},
				{
				    path: '/equimenttable',
				    component: resolve => require(['../components/page/EquimentTable.vue'], resolve),
				    meta: {title: '设备管理'}
				},
				{
				    // 富文本编辑器组件
				    path: '/brandtable',
				    component: resolve => require(['../components/page/BrandTable.vue'], resolve),
				    meta: {title: '型号管理'}
				},
				{
				    path: '/circulatetable',
				    component: resolve => require(['../components/page/CirculateTable.vue'], resolve),
				    meta: {title: '出入库管理'}
				},
				{
				    path: '/repairtable',
				    component: resolve => require(['../components/page/RepairTable.vue'], resolve),
				    meta: {title: '设备报修报废'}
				},
				{

				    path: '/repairapply',
				    component: resolve => require(['../components/page/RepairForm.vue'], resolve),
				    meta: {title: '报修申请'}
				},
				{

				    path: '/scrapapply',
				    component: resolve => require(['../components/page/ScrapForm.vue'], resolve),
				    meta: {title: '报废申请'}
				},

            ]
        },
        {
            path: '/login',
            component: () => import(/* webpackChunkName: "login" */ '../components/page/Login.vue')
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
});
