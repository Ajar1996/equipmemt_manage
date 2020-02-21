<template>
    <div class="login-wrap">
        <div class="ms-login">
            <div class="ms-title">后台管理系统</div>
			
            <el-form :model="param" :rules="rules" ref="login" label-width="0px" class="ms-content">
				<p class="register" @click="toRegister()">注册</p>
                <el-form-item prop="username">
                    <el-input v-model="param.username" placeholder="username">
                        <el-button slot="prepend" icon="el-icon-lx-people"></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input
                        type="password"
                        placeholder="password"
                        v-model="param.password"
                        @keyup.enter.native="submitForm()"
                    >
                        <el-button slot="prepend" icon="el-icon-lx-lock"></el-button>
                    </el-input>
                </el-form-item>
                <div class="login-btn">
                    <el-button type="primary" @click="submitForm()">登录</el-button>
					
                </div>
                <p class="login-tips">Tips : 管理员账号admin 密码123456</p>
            </el-form>
        </div>
		
		
		<!-- 注册弹出框 -->
		<el-dialog title="详情" :visible.sync="editVisible" width="30%">
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
    data: function() {
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
            param: {
                username: '',
                password: '',
            },
			editVisible: false,
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
            },
			
			ruleForm2: {
			          pass: '',
			          checkPass: '',
			          name: '',
					  account:'',
			        },
			        rules2: {
			          pass: [
			            { validator: validatePass, trigger: 'blur' },
						{ min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur' }
			          ],
			          checkPass: [
			            { validator: validatePass2, trigger: 'blur' },
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
    methods: {
		toRegister(){
			this.editVisible = true;
		},
		
		submitForm2(formName) {
		        this.$refs[formName].validate((valid) => {
				  var that=this;
		          if (valid) {
					axios({
					  method: 'post',
					  url: 'api/anno/register',
					  data: {
					    account: this.ruleForm2.account,
					    password: this.ruleForm2.pass,
						name: this.ruleForm2.name,
						forbidden:0,
						roles:[{id:9}]
					  },
					})
					  .then(function (response) {
						  
						if(response.data.code==0){
							console.log(response);
							that.$message.success('注册成功');
							that.editVisible=false;
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
		resetForm(formName) {
		  this.$refs[formName].resetFields();
		},	      
		
        submitForm() {
            this.$refs.login.validate(valid => {
				var that=this;
                if (valid) {
					
					axios({
					  method: 'post',
					  url: 'api/anno/login',
					  data: {
					    account: this.param.username,
					    password: this.param.password
					  }
					})
					  .then(function (response) {
						  
						if(response.data.code==0){
						       that.$message.success('登录成功');
						       localStorage.setItem('ms_username', that.param.username);
						       that.$router.push('/');
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
                    this.$message.error('请输入账号和密码');
                    console.log('error submit!!');
                    return false;
                }
            });
        },
    },
};
</script>

<style scoped>
.login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
    background-image: url(../../assets/img/login-bg.jpg);
    background-size: 100%;
}
.ms-title {
    width: 100%;
    line-height: 50px;
    text-align: center;
    font-size: 20px;
    color: #fff;
    border-bottom: 1px solid #ddd;
}
.register{
	font-size: 12px;
	line-height: 30px;
	color: #fff;
	text-align: right;
}
.ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 350px;
    margin: -190px 0 0 -175px;
    border-radius: 5px;
    background: rgba(255, 255, 255, 0.3);
    overflow: hidden;
}
.ms-content {
    padding: 30px 30px;
}
.login-btn {
    text-align: center;
}
.login-btn button {
    width: 100%;
    height: 36px;
    margin-bottom: 10px;
}
.login-tips {
    font-size: 12px;
    line-height: 30px;
    color: #fff;
}


</style>