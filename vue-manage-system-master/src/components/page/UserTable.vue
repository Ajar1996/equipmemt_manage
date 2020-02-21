<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>用户列表
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
				<el-button type="primary" icon="el-icon-plus" @click="handleSave()">新增用户</el-button>
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
                <el-table-column prop="account" label="账号"></el-table-column>
                <el-table-column label="状态" prop="forbidden" align="center">
                    <template slot-scope="scope">
						
						<el-tag v-if="scope.row.forbidden==='0'" type="success"
						 disable-transitions>启用
						</el-tag>
						<el-tag v-else-if="scope.row.forbidden==='1'" type="danger"
						 disable-transitions>禁用
						</el-tag>
						
                    </template>
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
		        <el-form-item label="用户名">
		            <el-input  v-model="form.name"></el-input>
		        </el-form-item>
				<el-form-item label="账号">
				    <el-input :disabled="true" v-model="form.account"></el-input>
				</el-form-item>
				<el-form-item label="密码">
				    <el-input :disabled="true"  v-model="form.password"></el-input>
				</el-form-item>
				<el-form-item label="状态">
				    <el-radio v-model="form.forbidden" label="0">启用</el-radio>
				    <el-radio v-model="form.forbidden" label="1">禁用</el-radio>
				</el-form-item>
				<el-form-item label="角色">
					 <el-checkbox-group 
					    v-model="rolesId"
					    :min="1"
						>
					    <el-checkbox v-for="role in roleList"  :label="role.id" :key="role.id">{{role.name}}</el-checkbox>
					  </el-checkbox-group>
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
			  <el-form-item label="账号" prop="account">
			    <el-input v-model="ruleForm2.account"></el-input>
			  </el-form-item>
			  <el-form-item label="昵称" prop="name">
			    <el-input v-model="ruleForm2.name"></el-input>
			  </el-form-item>
			  <el-form-item label="密码" prop="pass">
			    <el-input type="password" v-model="ruleForm2.pass" auto-complete="off"></el-input>
			  </el-form-item>
			  <el-form-item label="确认密码" prop="checkPass">
			    <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off"></el-input>
			  </el-form-item>
			  <el-form-item label="状态">
			      <el-radio v-model="ruleForm2.forbidden" label="0">启用</el-radio>
			      <el-radio v-model="ruleForm2.forbidden" label="1">禁用</el-radio>
			  </el-form-item>
			  <el-form-item label="角色">
			  	 <el-checkbox-group 
			  	    v-model="ruleForm2.roles"
			  	    :min="1"
			  		>
			  	    <el-checkbox v-for="role in roleList"  :label="role.id" :key="role.id">{{role.name}}</el-checkbox>
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
		
		var checkAge = (rule, value, callback) => {
		  if (!value) {
		    return callback(new Error('年龄不能为空'));
		  }
		  setTimeout(() => {
		    if (!Number.isInteger(value)) {
		      callback(new Error('请输入数字值'));
		    } else {
		      if (value < 18) {
		        callback(new Error('必须年满18岁'));
		      } else {
		        callback();
		      }
		    }
		  }, 1000);
		};
		var validatePass = (rule, value, callback) => {
		  if (value === '') {
		    callback(new Error('请输入密码'));
		  } else {
		    if (this.ruleForm2.checkPass !== '') {
		      this.$refs.ruleForm2.validateField('checkPass');
		    }
		    callback();
		  }
		};
		var validatePass2 = (rule, value, callback) => {
		  if (value === '') {
		    callback(new Error('请再次输入密码'));
		  } else if (value !== this.ruleForm2.pass) {
		    callback(new Error('两次输入密码不一致!'));
		  } else {
		    callback();
		  }
		};
		
        return {
            query: {
                account: '',
                name: '',
				forbidden:'',
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
			          pass: '',
			          checkPass: '',
			          name: '',
					  account:'',
					  roles:[],
					  forbidden:'',
			        },
			rules2: {
			  pass: [
			    { required: true,validator: validatePass, trigger: 'blur' },
				{ min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur' }
			  ],
			  checkPass: [
			    { required: true,validator: validatePass2, trigger: 'blur' },
				{ min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur' }
			  ],
			  name: [
			     { required: true, message: '请输入昵称', trigger: 'blur' },
			     { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
			  ],
			  account: [
			    { required: true, message: '请输入账号', trigger: 'blur' },
			    { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
			  ]
			  
			} 
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
			  url: 'api/admin/selectUserList',
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
		// 增加操作
		handleSave() {
			var that=this;
			axios({
			  method: 'get',
			  url: 'api/admin/selectRoleList',
			})	
			  .then(function (response) {
				if(response.data.code==0){
					var res2 = [];						
					response.data.data.content.forEach(v=>{
						res2.push(v)  
					});
					that.roleList=res2;
					that.editSave = true;					
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
		
		//新增角色
		submitForm2(formName) {
		        this.$refs[formName].validate((valid) => {
					var res = [];
					this.ruleForm2.roles.forEach(v=>{
						var role=new Object();
						role.id=v;
						res.push(role);
					});
				  var that=this;
		          if (valid) {			  
					axios({
					  method: 'post',
					  url: 'api/admin/saveUser',
					  data: {
					    account: this.ruleForm2.account,
					    password: this.ruleForm2.pass,
						name: this.ruleForm2.name,
						forbidden:this.ruleForm2.forbidden,
						roles:res
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
				  url: 'api/admin/selectUserDetail',
				  params: {
				     id: row.id
				    },
				}),
				axios({
				  method: 'get',
				  url: 'api/admin/selectRoleList',
				})
				
			])
			  .then(function (response) {

				if(response[0].data.code==0&&response[1].data.code==0){
					that.idx = index;
					that.form = response[0].data.data;
					var res1 = [];
					var res2 = [];						
					response[0].data.data.roles.forEach(v=>{
						var role=new Object();
						role.id=v.id;
						res1.push(v.id); 
					});
					response[1].data.data.content.forEach(v=>{
						res2.push(v)  
					});
					that.roleList=res2;
					that.rolesId=res1;
					
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
			  url: 'api/admin/updateUser',
			  data: {
			     id: form.id,
				 name:form.name,
				 account:form.account,
				 password:form.password,
				 forbidden:form.forbidden,
			     roles:res,
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
