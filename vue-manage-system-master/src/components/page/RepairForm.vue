<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator="/">
                <el-breadcrumb-item><i class="el-icon-lx-calendar"></i> 表单</el-breadcrumb-item>
                <el-breadcrumb-item>报修表单</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="container">
            <div class="form-box">
                <el-form ref="form" :model="form" label-width="80px">
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="form.name"></el-input>
                    </el-form-item>
					<el-form-item label="联系电话" prop="phone">
					    <el-input v-model="form.phone"></el-input>
					</el-form-item>
					<el-form-item label="地址" prop="address">
					    <el-input v-model="form.address"></el-input>
					</el-form-item>
					<el-form-item label="报修原因" prop="reason">
					    <el-input v-model="form.reason"></el-input>
					</el-form-item>
					<el-form-item label="设备ID" prop="equipmentId">
					    <el-input v-model="form.equipmentId"></el-input>
					</el-form-item>
					<el-form-item label="备注" prop="remark">
					    <el-input v-model="form.remark"></el-input>
					</el-form-item>
                    <el-form-item label="紧急程度" prop="level">
                        <el-select v-model="form.level" placeholder="请选择">
                            <el-option key="high" label="紧急" value="high"></el-option>
                            <el-option key="normal" label="普通" value="normal"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary"  @click="onSubmit('form')">表单提交</el-button>
                        <el-button @click="resetForm('form')">重置</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>

    </div>
</template>

<script>
	import axios from 'axios'
    export default {
        name: 'baseform',
        data: function(){
            return {
       
                form: {
                    name: '',
                    phone: '',
                    address: '',
                    reason: '',
					equipmentId: '',
					remark: '',
					level: ''
                }
            }
        },
        methods: {
            onSubmit(Form) {
				var that=this;
				axios({
				  method: 'post',
				  url: 'api/admin/applyRepair',
				  data: {
					name: this.form.name,
					phone: this.form.phone,
					address: this.form.address,
					reason: this.form.reason,
					equipmentId: this.form.equipmentId,
					remark: this.form.remark,
					level:this.form.level,
					status: '1',
				  },
				})
				  .then(function (response) {					  
					if(response.data.code==0){
						that.$message.success('提交成功！');
						that.$refs[Form].resetFields();
					}else{
						that.$message.success(response.data.msg);
					}  					    
				  })
				  .catch(function (error) {
						console.log('error submit!!');
				  });
            },
			resetForm(Form) {
				this.$refs[Form].resetFields();
        }
    },
}
	
</script>