<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>出入库管理
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
                <el-input v-model="query.name" placeholder="设备ID" class="handle-input mr10"></el-input>
                <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
				<el-button type="primary" icon="el-icon-plus" @click="handleOut()">出库</el-button>
				<el-button type="primary" icon="el-icon-plus" @click="handlePut()">入库</el-button>
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
				<el-table-column prop="name" width="150" label="借用人姓名"></el-table-column>
				<el-table-column prop="department" width="150" label="部门"></el-table-column>
				<el-table-column prop="time" width="300" label="时间"></el-table-column>
				<el-table-column label="状态" prop="type" align="center">
				    <template slot-scope="scope">
						
						<el-tag v-if="scope.row.type===0" type="success"
						 disable-transitions>出库
						</el-tag>
						<el-tag v-else-if="scope.row.type===1" type="danger"
						 disable-transitions>入库
						</el-tag>
						
				    </template>
				</el-table-column>
				
				
               <!-- <el-table-column prop="date" label="注册时间"></el-table-column> -->
                <el-table-column label="操作" width="180" align="center">
                    <template slot-scope="scope">	

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
		
		
		<!-- 新增弹出框 -->
		<el-dialog title="出库" :visible.sync="editOut" width="30%">
			<el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
			  <el-form-item label="设备ID" prop="equipmentId">
			    <el-input v-model="ruleForm2.equipmentId"></el-input>
			  </el-form-item>
			  <el-form-item label="借用人姓名" prop="name">
			    <el-input v-model="ruleForm2.name"></el-input>
			  </el-form-item>
			  <el-form-item label="部门" prop="department">
			    <el-input v-model="ruleForm2.department"></el-input>
			  </el-form-item>  
			  
			  <el-form-item>
			    <el-button type="primary" @click="submitForm2('ruleForm2')">提交</el-button>
			    <el-button @click="resetForm('ruleForm2')">重置</el-button>
			  </el-form-item>  
			</el-form>
		</el-dialog>
		
		<el-dialog title="入库" :visible.sync="editPut" width="30%">
			<el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
			  <el-form-item label="设备ID" prop="equipmentId">
			    <el-input v-model="ruleForm2.equipmentId"></el-input>
			  </el-form-item>
			  <el-form-item label="借用人姓名" prop="name">
			    <el-input v-model="ruleForm2.name"></el-input>
			  </el-form-item>
			  <el-form-item label="部门" prop="department">
			    <el-input v-model="ruleForm2.department"></el-input>
			  </el-form-item>  
			  
			  <el-form-item>
			    <el-button type="primary" @click="submitForm3('ruleForm2')">提交</el-button>
			    <el-button @click="resetForm2('ruleForm2')">重置</el-button>
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
                id: '',
                name: '',
				equipmentId:'',
				department: '',
				time: '',
				type:'',
                pageIndex: 1,
                pageSize: 10
            },
			equimentList:[],
			brandList:[],
            tableData: [],
            multipleSelection: [],
            delList: [],
            editVisible: false,
			detailVisible:false,
			editOut:false,
			editPut:false,
            pageTotal: 0,
            form: {},
            idx: -1,
            id: -1,
			ruleForm2: {
			          name: '',
					  equipmentId:'',
					  department:'',
					  
					  
			        },
			rules2: {
			  name: [
			     { required: true, message: '请输入借用人姓名', trigger: 'blur' },
			     { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
			  ],
			  equipmentId: [
			     { required: true, message: '请输入设备ID', trigger: 'blur' },
			     { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
			  ],
			  department: [
			     { required: true, message: '请输入借用人部门', trigger: 'blur' },
			     { min: 3, max: 32, message: '长度在 3 到 32 个字符', trigger: 'blur' }
			  ],
			} 
        };
    },
    created() {
        this.getData(this.query.pageIndex-1,'');
    },
    methods: {
        getData(pageIndex,equipmentId) {
			var that=this;
			axios({
			  method: 'get',
			  url: 'api/admin/selectCirculateList',
			  params:{
				  equipmentId:equipmentId,
				  page:pageIndex
			  }
			})
			  .then(function (response) {
				if(response.data.code==0){
					console.log(response.data);
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
            this.getData(this.query.pageIndex-1,this.query.equipmentId);
        },
		// 增加操作
		handleOut() {
			this.editOut = true;					
		},
		handlePut() {
			this.editPut = true;					
		},
		
		//出库
		submitForm2(formName) {
		        this.$refs[formName].validate((valid) => {
				  var res = [];
				  var that=this;
		          if (valid) {			  
					axios({
					  method: 'post',
					  url: 'api/admin/saveCirculateOut',
					  data: {
						name: this.ruleForm2.name,
						equipmentId:this.ruleForm2.equipmentId,
						department: this.ruleForm2.department,						
					  },
					})
					  .then(function (response) {					  
						if(response.data.code==0){
							that.$message.success('出库成功');
							that.editOut=false;
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
		resetForm1(formName) {
		  this.$refs[formName].resetFields();
		},
		resetForm2(formName) {
		  this.$refs[formName].resetFields();
		},		  
		//入库
		submitForm3(formName) {
		        this.$refs[formName].validate((valid) => {
				  var res = [];
				  var that=this;
		          if (valid) {			  
					axios({
					  method: 'post',
					  url: 'api/admin/saveCirculatePut',
					  data: {
						name: this.ruleForm2.name,
						equipmentId:this.ruleForm2.equipmentId,
						department: this.ruleForm2.department,						
					  },
					})
					  .then(function (response) {					  
						if(response.data.code==0){
							that.$message.success('入库成功');
							that.editPut=false;
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
					  url: 'api/admin/deleteCirculate/'+row.id,

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
