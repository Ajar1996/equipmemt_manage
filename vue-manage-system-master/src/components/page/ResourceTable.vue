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
				
                <el-input v-model="query.name" placeholder="资源名称" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
				<el-button type="primary" icon="el-icon-plus" @click="handleSave()">新增资源</el-button>
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
                <el-table-column prop="name" label="资源名称"></el-table-column>
                <el-table-column prop="perms" label="标识符"></el-table-column>
				<el-table-column prop="parentId" label="父资源ID"></el-table-column>
                <el-table-column label="类型" prop="type" align="center">
                    <template slot-scope="scope">				
						<el-tag v-if="scope.row.type==='0'" type="info"
						 disable-transitions>目录
						</el-tag>
						<el-tag v-else-if="scope.row.type==='1'" type="success"
						 disable-transitions>菜单
						</el-tag>
						<el-tag v-else-if="scope.row.type==='2'" type="warning"
						 disable-transitions>按钮
						</el-tag>										
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">	
						<el-button
						    type="text"
						    icon="el-icon-edit"
						    @click="handleDetail(scope.$index, scope.row)"
						>编辑</el-button>

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
		
		<!-- 详情弹出框 -->
		<el-dialog title="详情" :visible.sync="detailVisible" width="30%">
		    <el-form ref="form" :model="form" label-width="70px">
		        <el-form-item label="资源名称">
		            <el-input  v-model="form.name"></el-input>
		        </el-form-item>
				<el-form-item label="标识符">
				    <el-input  v-model="form.perms"></el-input>
				</el-form-item>
				<el-form-item label="父资源ID">
				    <el-input v-model="form.parentId"></el-input>
				</el-form-item>
				<el-form-item label="类型">
				    <el-radio v-model="form.type" label="0">目录</el-radio>
				    <el-radio v-model="form.type" label="1">菜单</el-radio>
					<el-radio v-model="form.type" label="2">按钮</el-radio>
				</el-form-item>
		    </el-form>
		    <span slot="footer" class="dialog-footer">
		        <el-button @click="detailVisible = false">取 消</el-button>
		        <el-button type="primary" @click="saveDetail(form,rolesId)">确 定</el-button>
		    </span>
		</el-dialog>
		
		
		<!-- 新增弹出框 -->
		<el-dialog title="详情" :visible.sync="editSave" width="30%">
			<el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
		
			  <el-form-item label="资源名称">
			          <el-input  v-model="ruleForm2.name"></el-input>
			      </el-form-item>
			  	<el-form-item label="标识符">
			  	    <el-input  v-model="ruleForm2.perms"></el-input>
			  	</el-form-item>
			  	<el-form-item label="父资源ID">
			  	    <el-input v-model="ruleForm2.parentId"></el-input>
			  	</el-form-item>
			  	<el-form-item label="类型">
			  	    <el-radio v-model="ruleForm2.type" label="0">目录</el-radio>
			  	    <el-radio v-model="ruleForm2.type" label="1">菜单</el-radio>
			  		<el-radio v-model="ruleForm2.type" label="2">按钮</el-radio>
			  	</el-form-item>
			  <el-form-item>
			    <el-button type="primary" @click="submitForm2('ruleForm2')">提交</el-button>
			    <el-button @click="resetForm('ruleForm2')">重置</el-button>
			  </el-form-item>
			</el-form>
		</el-dialog>
		
		
		
    </div>
</template>

<script>
		import axios from 'axios'

export default {
    name: 'basetable',
    data() {
        return {
            query: {
                parentId: '',
                name: '',
				perms:'',
				type:'',
                pageIndex: 1,
                pageSize: 10
            },
			roleList:[],
			rolesId:[],
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
			ruleForm2: {
			          parentId: '',
			          name: '',
			          perms: '',
					  type:'',
			        },
			rules2: {
			  name: [
			     { required: true, message: '请输入权限名称', trigger: 'blur' },
			     { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
			  ],
			  perms: [
			    { required: false, message: '请输入权限标识符', trigger: 'blur' },
			    { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
			  ],
			  parentId: [
			    { required: true, message: '请输入父资源ID', trigger: 'blur' },
			    { type: 'number', message: '等级必须为数字值', trigger: 'blur' },
			  ]
			}
        };
    },
    created() {
        this.getData(this.query.pageIndex-1,'');
    },
    methods: {
		
		
		// 增加操作
		handleSave() {
			this.editSave = true;	
		},
		
		//新增资源
		submitForm2(formName) {
		        this.$refs[formName].validate((valid) => {
				  var that=this;
		          if (valid) {			  
					axios({
					  method: 'post',
					  url: 'api/admin/saveResource',
					  data: {
						  parentId: this.ruleForm2.parentId,
						  name: this.ruleForm2.name,
						  perms: this.ruleForm2.perms,
						  type:this.ruleForm2.type
					  },
					})
					  .then(function (response) {					  
						if(response.data.code==0){
							that.$message.success('添加成功');
							that.editSave=false;
						}else{
						    that.$alert(response.data.msg, 'info', {
						        confirmButtonText: 'ok'
						    })
						}  					    
					  })
					  .catch(function (error) {
					    console.log(error);
					  });
		          } else {
		            console.log('error submit!!');
		            return false;
		          }
		        });
		      },
		//重置
		resetForm(formName) {
		  this.$refs[formName].resetFields();
		},	      
		
        getData(pageIndex,name) {
			var that=this;
			axios({
			  method: 'get',
			  url: 'api/admin/selectResourceList',
			  params:{
				  page:pageIndex,
				  name:name
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
					  url: 'api/admin/deleteUser/'+row.id,

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
		// 详情操作
		handleDetail(index, row) {
			var that=this;
				axios({
				  method: 'get',
				  url: 'api/admin/selectResourceDetail',
				  params: {
				     id: row.id
				    },
				})					
			  .then(function (response) {
				if(response.data.code==0){
					that.idx = index;
					that.form = response.data.data;			
					that.detailVisible = true;					
				}else{
				    that.$alert(response.data.msg, 'info', {
				        confirmButtonText: 'ok'
				    })
				}
			  })
			  .catch(function (error) {
			    console.log(error);
			  });

		},		
		//保存详情
		saveDetail(form,rolesId){
			var res = [];
			rolesId.forEach(v=>{
				var role=new Object();
				role.id=v;
				res.push(role);
			});
			
			var that=this;
			axios({
			  method: 'put',
			  url: 'api/admin/updateResource',
			  data: {
			     id: form.id,
				 parentId: form.parentId,
				 name: form.name,
				 perms:form.perms,
				 type:form.type,
			    },
			})
			  .then(function (response) {
				if(response.data.code==0){
					that.$message.success(`修改第 ${that.idx + 1} 行成功`);
					that.$set(that.tableData, that.idx, that.form);
					that.detailVisible = false;	
				}else{
				    that.$alert(response.data.msg, 'info', {
				        confirmButtonText: 'ok'
				    })
				}
			  })
			  .catch(function (error) {
			    console.log(error);
			  });
			
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
