<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>角色列表
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
				<el-button type="primary" icon="el-icon-plus" @click="handleSave()">新增角色</el-button>
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
                <el-table-column prop="name" label="用户名"></el-table-column>
                <el-table-column prop="remark" label="备注"></el-table-column>
                <el-table-column label="等级" prop="grade" align="center">

                </el-table-column>

               <!-- <el-table-column prop="date" label="注册时间"></el-table-column> -->
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
		        <el-form-item label="角色名">
		            <el-input  v-model="form.name"></el-input>
		        </el-form-item>
				<el-form-item label="备注">
				    <el-input v-model="form.remark"></el-input>
				</el-form-item>
				<el-form-item label="等级">
				    <el-input v-model="form.grade"></el-input>
				</el-form-item>
				<el-form-item label="资源列表">
					 <el-checkbox-group 
					    v-model="permsId"
						>
					    <el-checkbox v-for="perms in permsList"  :label="perms.id" :key="perms.id">{{perms.name}}</el-checkbox>
					  </el-checkbox-group>
				</el-form-item>

		    </el-form>
		    <span slot="footer" class="dialog-footer">
		        <el-button @click="detailVisible = false">取 消</el-button>
		        <el-button type="primary" @click="saveDetail(form,permsId)">确 定</el-button>
		    </span>
		</el-dialog>
		
		
		<!-- 新增弹出框 -->
		<el-dialog title="详情" :visible.sync="editSave" width="30%">
			<el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">

			  <el-form-item label="角色名" prop="name">
			    <el-input v-model="ruleForm2.name"></el-input>
			  </el-form-item>
			  <el-form-item label="备注" prop="remark">
			    <el-input v-model="ruleForm2.remark" auto-complete="off"></el-input>
			  </el-form-item>
			  <el-form-item label="等级" prop="grade">
			      <el-input v-model.number="ruleForm2.grade"></el-input>
			  </el-form-item>
			  <el-form-item label="资源">	
			  	 <el-checkbox-group 
			  	    v-model="ruleForm2.resources"
			  		>
			  	    <el-checkbox v-for="perms in permsList"  :label="perms.id" :key="perms.id">{{perms.name}}</el-checkbox>
			  	  </el-checkbox-group>
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
		const validateGrade = (rule, value, callback) => {
		  // 输入 8--，value 为 8
		  // 估计这里内部使用了 parseInt() / parseFloat()
		  if (value < 0 || value > 10) {
		    callback(new Error('等级必须在 1 至 10 之间'))
		  } else {
		    callback()
		  }
		};
        return {
            query: {
                grade: '',
                name: '',
				remark:'',
                pageIndex: 1,
                pageSize: 10
            },
			permsList:[],
			permsId:[],
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
			          grade: '',
			          name: '',
			          remark: '',
					  resources:[]
			        },
			rules2: {
			  name: [
			     { required: true, message: '请输入角色名', trigger: 'blur' },
			     { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
			  ],
			  account: [
			    { required: false, message: '请输入备注', trigger: 'blur' },
			    { min: 2, max: 32, message: '长度在 2 到 32 个字符', trigger: 'blur' }
			  ],
			  grade: [
			    { required: true, validator: validateGrade, trigger: 'blur' },
			    { type: 'number', message: '等级必须为数字值', trigger: 'blur' },
			  ],
			}
        };
    },
    created() {
        this.getData(this.query.pageIndex-1,'');
    },
    methods: {
		// 增加操作
		handleSave() {
			var that=this;
			axios({
			  method: 'get',
			  url: 'api/admin/selectResourceList',
			  params: {
			     size: 50
			    },	
			})	
			  .then(function (response) {
				if(response.data.code==0){
					var res2 = [];						
					response.data.data.content.forEach(v=>{
						res2.push(v)  
					});
					that.permsList=res2;
					that.editSave = true;					
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
		
		//新增角色
		submitForm2(formName) {
		        this.$refs[formName].validate((valid) => {
					var res = [];
					this.ruleForm2.resources.forEach(v=>{
						var perm=new Object();
						perm.id=v;
						res.push(perm);
					});
				  var that=this;
		          if (valid) {			  
					axios({
					  method: 'post',
					  url: 'api/admin/saveRole',
					  data: {
						  grade: this.ruleForm2.grade,
						  name: this.ruleForm2.name,
						  remark: this.ruleForm2.remark,
						  resources:res
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
			  url: 'api/admin/selectRoleList',
			  params:{
				  page:pageIndex,
				  name:name
			  }
			})
			  .then(function (response) {
				console.log(response);
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
					  url: 'api/admin/deleteRole/'+row.id,

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
        // 编辑操作
        handleEdit(index, row) {
            this.idx = index;
            this.form = row;
            this.editVisible = true;
        },
		// 详情操作
		handleDetail(index, row) {
			var that=this;
			axios.all([
				axios({
				  method: 'get',
				  url: 'api/admin/selectRoleDetail',
				  params: {
				     id: row.id
				    },
				}),
				axios({
				  method: 'get',
				  url: 'api/admin/selectResourceList',
				  params: {
				     size: 50
				    },				  
				})
				
			])
			  .then(function (response) {		

				if(response[0].data.code==0&&response[1].data.code==0){
					that.idx = index;
					that.form = response[0].data.data;
					var res1 = [];
					var res2 = [];						
					response[0].data.data.resources.forEach(v=>{
						res1.push(v.id); 
					});
					
					response[1].data.data.content.forEach(v=>{
						res2.push(v)  
					});
					that.permsList=res2;
					that.permsId=res1;
					
					that.detailVisible = true;					
				}else{
				    that.$alert(response[1].data.msg, 'info', {
				        confirmButtonText: 'ok'
				    })
				}
			  })
			  .catch(function (error) {
			    console.log(error);
			  });

		},

		
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            this.$set(this.tableData, this.idx, this.form);
        },
		
		//保存详情
		saveDetail(form,permsId){
			var res = [];
			permsId.forEach(v=>{
				var perm=new Object();
				perm.id=v;
				res.push(perm);
			});
			
			var that=this;
			axios({
			  method: 'put',
			  url: 'api/admin/updateRole',
			  data: {
			     id: form.id,
				 name:form.name,
				 remark:form.remark,
				 grade:form.grade,
			     resources:res,
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
