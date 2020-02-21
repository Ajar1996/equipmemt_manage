<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>资源列表
                </el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="handle-box">
                <el-button
                    type="primary"
                    icon="el-icon-delete"
                    class="handle-del mr10"
                    @click="delAllSelection"
                >批量删除</el-button>
            </div>
            <el-table
                :data="tableData"
                border
                class="table"
                ref="multipleTable"
                header-cell-class-name="table-header"
                @selection-change="handleSelectionChange"
            >
                <el-table-column type="selection" width="55" align="center"></el-table-column>
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="昵称"></el-table-column>
                <el-table-column prop="account" label="账号"></el-table-column>
				<el-table-column prop="date" label="登录时间"></el-table-column>
				<el-table-column prop="host" label="IP"></el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">	
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >踢下线</el-button>
                    </template>
                </el-table-column>
            </el-table>

        </div>
		
		
		
		
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'basetable',
    data() {
        return {
            query: {

            },
			
			roleList:[],
			rolesId:[],
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
			detailVisible:false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1
        };
    },
    created() {
        this.getData(this.query.pageIndex-1,'');
    },
    methods: {
		renderTime(date) {
		  var dateee = new Date(date).toJSON();
		  return new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '') 
		},
        getData(pageIndex,name) {
			var that=this;
			axios({
			  method: 'get',
			  url: 'api/admin/selectSessions'
			})
			  .then(function (response) {
				if(response.data.code==0){
					var res=[];
 					response.data.data.forEach(v=>{
						var o=new Object();
						o.id=v.attributes['org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY'].primaryPrincipal.id;
						o.name=v.attributes['org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY'].primaryPrincipal.name;
						o.host=v.host;
						o.date=that.renderTime(v.startTimestamp);
						o.account=v.attributes['org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY'].primaryPrincipal.account;
						o.sessionid=v.id;
						res.push(o);
					}); 
					that.tableData=res;
				}else if(response.data.code==8){
				    that.$alert(response.data.msg, 'info', {
				        confirmButtonText: 'ok'
				    })
				}
			  })
			  .catch(function (error) {
			    console.log(error);
			  });
        },
        // 下线操作
        handleDelete(index, row) {
            // 二次确认删除
            this.$confirm('确定踢出该用户吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
					var that=this;
					console.log(row);
					axios({
					  method: 'delete',
					  url: 'api/admin/logOutUser/'+row.sessionid,

					})
					  .then(function (response) {
						if(response.data.code==0){
							that.$message.success('删除成功');
							that.tableData.splice(index, 1);
						}else{
						    that.$alert(response.data.msg, 'info', {
						        confirmButtonText: 'ok'
						    })
						}
					  })
					  .catch(function (error) {
					    console.log(error);
					  });
					
					

                })
                .catch(() => {});
        },
        // 多选操作
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        delAllSelection() {
            const length = this.multipleSelection.length;
            let str = '';
            this.delList = this.delList.concat(this.multipleSelection);
            for (let i = 0; i < length; i++) {
                str += this.multipleSelection[i].name + ' ';
            }
            this.$message.error(`删除了${str}`);
            this.multipleSelection = [];
        },
		
    }
};
</script>

<style scoped>
	
	
	
	
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 120px;
}

.handle-input {
    width: 300px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.mr10 {
    margin-right: 10px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>
