<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item>
                    <i class="el-icon-lx-cascades"></i>型号列表
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
				<el-button type="primary" icon="el-icon-plus" @click="handleSave()">新增设备型号</el-button>
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
                <el-table-column prop="id" label="型号ID" width="70" align="center"></el-table-column>
                <el-table-column prop="brandName" label="型号名称"></el-table-column>
            
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
				<el-form-item label="型号ID">
				    <el-input :disabled="true" v-model="form.id"></el-input>
				</el-form-item>
		        <el-form-item label="型号名称">
		            <el-input  v-model="form.brandName"></el-input>
		        </el-form-item>

		    </el-form>
		    <span slot="footer" class="dialog-footer">
		        <el-button @click="detailVisible = false">取 消</el-button>
		        <el-button type="primary" @click="saveDetail(form)">确 定</el-button>
		    </span>
		</el-dialog>
		
		<!-- 新增弹出框 -->
		<el-dialog title="增加" :visible.sync="editSave" width="30%">
			<el-form :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
			  <el-form-item label="型号名称" prop="brandName">
			    <el-input v-model="ruleForm2.brandName"></el-input>
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
                id: '',
                brandName: '',
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
			ruleForm2: {
			          brandName: ''
			        },
			rules2: {
			  brandName: [
			     { required: true, message: '请输入设备型号名称', trigger: 'blur' },
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
			  url: 'api/admin/selectBrandList',
			  params:{
				  name:name,
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
            this.getData(this.query.pageIndex-1,this.query.name);
        },
		// 增加操作
		handleSave() {
			this.editSave = true;					
		},
		
		//新增角色
		submitForm2(formName) {
		        this.$refs[formName].validate((valid) => {
				  var res = [];
				  var that=this;
		          if (valid) {			  
					axios({
					  method: 'post',
					  url: 'api/admin/saveBrand',
					  data: {
						brandName: this.ruleForm2.brandName
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
					  url: 'api/admin/deleteBrand/'+row.id,

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
			console.log(row);
            this.form = row;
            this.editVisible = true;
        },
		// 详情操作
		handleDetail(index, row) {
			this.form = row;
			this.detailVisible = true;					
		
		},

		
        // 保存编辑
        saveEdit() {
            this.editVisible = false;
            this.$message.success(`修改第 ${this.idx + 1} 行成功`);
            this.$set(this.tableData, this.idx, this.form);
        },
		
		//保存详情
		saveDetail(form){
			var res = [];
			var that=this;
			axios({
			  method: 'put',
			  url: 'api/admin/updateBrand',
			  data: {
			     id: form.id,
				 brandName:form.brandName
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
