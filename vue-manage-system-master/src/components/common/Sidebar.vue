<template>
    <div class="sidebar">
        <el-menu
            class="sidebar-el-menu"
            :default-active="onRoutes"
            :collapse="collapse"
            background-color="#324157"
            text-color="#bfcbd9"
            active-text-color="#20a0ff"
            unique-opened
            router
        >
            <template v-for="item in items">
                <template v-if="item.subs">
                    <el-submenu :index="item.index" :key="item.index">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span slot="title">{{ item.title }}</span>
                        </template>
                        <template v-for="subItem in item.subs">
                            <el-submenu
                                v-if="subItem.subs"
                                :index="subItem.index"
                                :key="subItem.index"
                            >
                                <template slot="title">{{ subItem.title }}</template>
                                <el-menu-item
                                    v-for="(threeItem,i) in subItem.subs"
                                    :key="i"
                                    :index="threeItem.index"
                                >{{ threeItem.title }}</el-menu-item>
                            </el-submenu>
                            <el-menu-item
                                v-else
                                :index="subItem.index"
                                :key="subItem.index"
                            >{{ subItem.title }}</el-menu-item>
                        </template>
                    </el-submenu>
                </template>
                <template v-else>
                    <el-menu-item :index="item.index" :key="item.index">
                        <i :class="item.icon"></i>
                        <span slot="title">{{ item.title }}</span>
                    </el-menu-item>
                </template>
            </template>
        </el-menu>
    </div>
</template>

<script>
import bus from '../common/bus';
export default {
    data() {
        return {
            collapse: false,
            items: [
                {
                    icon: 'el-icon-lx-home',
                    index: 'dashboard',
                    title: '系统首页'
                },
				{
				    icon: 'el-icon-lx-cascades',
				    index: '3',
				    title: '系统管理',
				    subs: [
				        {
				            index: 'usertable',
				            title: '用户列表'
				        },
						{
							index: 'roletable',
							title: '角色列表'
						},
						{
							index: 'resourcetable',
							title: '资源列表'
						},
						{
							index: 'onlineuser',
							title: '在线用户'
						},
				    ]
				},
				{
				    icon: 'el-icon-lx-global',
				    index: '2',
				    title: '设备管理',
				    subs: [
				        {
				            index: 'equimenttable',
				            title: '设备管理'
				        },
				        {
				            index: 'brandtable',
				            title: '设备型号'
				        },
				        {
				            index: 'circulatetable',
				            title: '出入库管理'
				        },
				        {
				            index: 'repairtable',
				            title: '报修管理'
				        },
				    ]
				},
				{
				    icon: 'el-icon-lx-people',
				    index: '1',
				    title: '普通员工',
				    subs: [
				        {
				            index: 'equimenttable',
				            title: '设备查询'
				        },
				        {
				            index: 'brandtable',
				            title: '型号查询'
				        },
						{
							index: 'repairapply',
							title: '报修申请'
						},
						{
							index: 'scrapapply',
							title: '报废申请'
						},
				    ]
				},
/*                {
                    icon: 'el-icon-lx-people',
                    index: 'usertable',
                    title: '用户列表'
                },
                {
                    icon: 'el-icon-lx-cascades',
                    index: 'roletable',
                    title: '角色列表'
                },
				{
				    icon: 'el-icon-lx-calendar',
				    index: 'resourcetable',
				    title: '资源列表'
				},
                {
                    icon: 'el-icon-lx-global',
                    index: 'onlineuser',
                    title: '在线用户'
                }, */
/* 				{
				    icon: 'el-icon-lx-global',
				    index: 'equimenttable',
				    title: '设备管理'
				},
				{
				    icon: 'el-icon-lx-global',
				    index: 'brandtable',
				    title: '设备型号'
				},
				{
				    icon: 'el-icon-lx-global',
				    index: 'circulatetable',
				    title: '出入库管理'
				},
				{
				    icon: 'el-icon-lx-global',
				    index: 'repairtable',
				    title: '报修报废管理'
				}, */


            ]
        };
    },
    computed: {
        onRoutes() {
            return this.$route.path.replace('/', '');
        }
    },
    created() {
        // 通过 Event Bus 进行组件间通信，来折叠侧边栏
        bus.$on('collapse', msg => {
            this.collapse = msg;
            bus.$emit('collapse-content', msg);
        });
    }
};
</script>

<style scoped>
.sidebar {
    display: block;
    position: absolute;
    left: 0;
    top: 70px;
    bottom: 0;
    overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
    width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
    width: 250px;
}
.sidebar > ul {
    height: 100%;
}
</style>
