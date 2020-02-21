<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>报修报废管理
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
                <el-input v-model="query.name" placeholder="用户名" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
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
                <el-table-column prop="id" label="ID" width="70" align="center"></el-table-column>
                <el-table-column prop="equipmentId" width="150" label="设备ID"></el-table-column>
                <el-table-column prop="name" width="150" label="申请人姓名"></el-table-column>
                <el-table-column prop="phone" width="150" label="手机"></el-table-column>
                <el-table-column prop="address" width="300" label="返修地址"></el-table-column>
				<el-table-column prop="reason" width="150" label="原因"></el-table-column>
				<el-table-column label="紧急程度" prop="level" align="center">
				    <template slot-scope="scope">
						
						<el-tag v-if="scope.row.level==='high'" type="danger"
						 disable-transitions>紧急
						</el-tag>
						<el-tag v-else-if="scope.row.level==='normal'" type="info"
						 disable-transitions>普通
						</el-tag>
					</template>
				</el-table-column>

				<el-table-column prop="applicationTime" width="300" label="申请时间"></el-table-column>
				<el-table-column prop="repairTime" width="150" label="修理时间"></el-table-column>
				<el-table-column prop="sentTime" width="300" label="发出时间"></el-table-column>
				
                <el-table-column label="状态" prop="status" align="center">
                    <template slot-scope="scope">
                		
                		<el-tag v-if="scope.row.status===0" type="success"
                		 disable-transitions>申请报修
                		</el-tag>
                		<el-tag v-else-if="scope.row.status===1" type="danger"
                		 disable-transitions>申请报废
                		</el-tag>
						<el-tag v-if="scope.row.status===2" type="info"
						 disable-transitions>修理状态
						</el-tag>
						<el-tag v-else-if="scope.row.status===3" type="warning"
						 disable-transitions>寄回状态
						</el-tag>
                		<el-tag v-else-if="scope.row.status===4"
                		 disable-transitions>完结状态
                		</el-tag>
                    </template>
                </el-table-column>

               <!-- <el-table-column prop="date" label="注册时间"></el-table-column> -->
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">	
						<el-button
						    type="text"
						    icon="el-icon-edit"
						    @click="handleDetail(scope.$index, scope.row,2)"
						>修理</el-button>
						<el-button
						    type="text"
						    icon="el-icon-edit"
						    @click="handleDetail(scope.$index, scope.row,3)"
						>发回</el-button>
						<el-button
						    type="text"
						    icon="el-icon-edit"
						    @click="handleDetail(scope.$index, scope.row,4)"
						>结束</el-button>
                        <el-button
                            type="text"
                            icon="el-icon-delete"
                            class="red"
                            @click="handleDelete(scope.$index, scope.row)"
                        >删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="pagination">
                <el-pagination
                    background
                    layout="total, prev, pager, next"
                    :current-page="query.pageIndex"
                    :page-size="query.pageSize"
                    :total="pageTotal"
                    @current-change="handlePageChange"
                ></el-pagination>
            </div>
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
                name: '',
				phone:'',
				address: '',
				reason:'',
				equipmentId: '',
				level:'',
				remark: '',
				applicationTime:'',
				repairTime: '',
				sentTime:'',
				status: '',
                pageIndex: 1,
                pageSize: 10
            },
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
			detailVisible:false,
			editSave:false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
        };
    },
    created() {
        this.getData(this.query.pageIndex-1,'');
    },
    methods: {
        getData(pageIndex,name) {
			var that=this;
			axios({
			  method: 'get',
			  url: 'api/admin/selectRepairList',
			  params:{
				  name:name,
				  page:pageIndex
			  }
			})
			  .then(function (response) {
				if(response.data.code==0){
					that.tableData=response.data.data.content;
					that.pageTotal = response.data.data.totalElements;
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
        // 触发搜索按钮
        handleSearch() {
			this.$set(this.query, 'pageIndex', 1);
            this.getData(this.query.pageIndex-1,this.query.name);
        },
		
        // 删除操作
        handleDelete(index, row) {
			
            // 二次确认删除
            this.$confirm('确定要删除吗？', '提示', {
                type: 'warning'
            })
                .then(() => {
					var that=this;
					axios({
					  method: 'delete',
					  url: 'api/admin/deleteRepair/'+row.id,

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
		handleDetail(index, row, type) {
		    // 二次确认
		    this.$confirm('确定要更改吗？', '提示', {
		        type: 'warning'
		    })
		        .then(() => {
					var that=this;
					axios({
					  method: 'Put',
					  url: 'api/admin/updateRepair',
					  data: {
						 id:row.id,
						 name: row.name,
						 phone:row.phone,
						 address: row.address,
						 reason:row.reason,
						 equipmentId: row.equipmentId,
						 level:row.level,
						 remark: row.remark,
						 status: type				
					  },
					  })
					  .then(function (response) {
						if(response.data.code==0){
							that.$message.success('更改成功');
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

        // 分页导航
        handlePageChange(val) {
            this.$set(this.query, 'pageIndex', val);
            this.getData(val-1);
        }
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
