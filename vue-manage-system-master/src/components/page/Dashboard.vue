<template>
    <div>
		
		<el-row>
		    <el-col :span="24">
		        <el-row :gutter="20" class="mgb20">
		            <el-col :span="8">
		                <el-card shadow="hover" :body-style="{padding: '0px'}">
		                    <div class="grid-content grid-con-1">
		                        <i class="el-icon-lx-people grid-con-icon"></i>
		                        <div class="grid-cont-right">
		                            <div class="grid-num">1234</div>
		                            <div>用户访问量</div>
		                        </div>
		                    </div>
		                </el-card>
		            </el-col>
		            <el-col :span="8">
		                <el-card shadow="hover" :body-style="{padding: '0px'}">
		                    <div class="grid-content grid-con-2">
		                        <i class="el-icon-lx-notice grid-con-icon"></i>
		                        <div class="grid-cont-right">
		                            <div class="grid-num">321</div>
		                            <div>系统消息</div>
		                        </div>
		                    </div>
		                </el-card>
		            </el-col>
		            <el-col :span="8">
		                <el-card shadow="hover" :body-style="{padding: '0px'}">
		                    <div class="grid-content grid-con-3">
		                        <i class="el-icon-lx-goods grid-con-icon"></i>
		                        <div class="grid-cont-right">
		                            <div class="grid-num">5000</div>
		                            <div>数量</div>
		                        </div>
		                    </div>
		                </el-card>
		            </el-col>
		        </el-row>          
		    </el-col>
		</el-row>
		
		
        <el-row :gutter="20">
            <el-col :span="12">
                <el-card shadow="hover" class="mgb20" style="height:252px;">
                    <div class="user-info">
                        <img src="../../assets/img/img.jpg" class="user-avator" alt="">
                        <div class="user-info-cont">
                            <div class="user-info-name">{{query.name}}</div>
                            <div>
								<el-tag v-for="role in query.roleList"  :label="role" :key="role">{{role}}</el-tag>
							</div>
                        </div>
                    </div>
                    <div class="user-info-list">上次登录时间：<span>{{query.startTimestamp}}</span></div>
                    <div class="user-info-list">上次登录地点：<span>{{query.host}}</span></div>
					
					
					
                </el-card>
            </el-col>
			<el-col :span="12">
			    <el-card shadow="hover">
			        <schart ref="line" class="schart" canvasId="line" :data="data" type="line" :options="options2"></schart>
			    </el-card>
			</el-col>
		</el-row>
			
		

    </div>
</template>

<script>
	import axios from 'axios';
    import Schart from 'vue-schart';
    import bus from '../common/bus';
    export default {
        name: 'dashboard',
        data() {
            return {
				query: {
				    host: '',
				    startTimestamp: '',
					type:'',
					name:'',
					roleList:[],
				},

              
                data: [{
                        name: '2018/09/04',
                        value: 1083
                    },
                    {
                        name: '2018/09/05',
                        value: 941
                    },
                    {
                        name: '2018/09/06',
                        value: 1139
                    },
                    {
                        name: '2018/09/07',
                        value: 816
                    },
                    {
                        name: '2018/09/08',
                        value: 327
                    },
                    {
                        name: '2018/09/09',
                        value: 228
                    },
                    {
                        name: '2018/09/10',
                        value: 1065
                    }
                ],
                options: {
                    title: '最近七天每天的用户访问量',
                    showValue: false,
                    fillColor: 'rgb(45, 140, 240)',
                    bottomPadding: 30,
                    topPadding: 30
                },
                options2: {
                    title: '最近七天用户访问趋势',
                    fillColor: '#FC6FA1',
                    axisColor: '#008ACD',
                    contentColor: '#EEEEEE',
                    bgColor: '#F5F8FD',
                    bottomPadding: 30,
                    topPadding: 30
                }
            }
        },
        components: {
            Schart
        },
        computed: {
        },
        created(){
            this.handleListener();
            this.changeDate();
			this.getData();
        },
        activated(){
            this.handleListener();
        },
        deactivated(){
            window.removeEventListener('resize', this.renderChart);
            bus.$off('collapse', this.handleBus);
        },
        methods: {
            changeDate(){
                const now = new Date().getTime();
                this.data.forEach((item, index) => {
                    const date = new Date(now - (6 - index) * 86400000);
                    item.name = `${date.getFullYear()}/${date.getMonth()+1}/${date.getDate()}`
                })
            },
            handleListener(){
                bus.$on('collapse', this.handleBus);
                // 调用renderChart方法对图表进行重新渲染
                window.addEventListener('resize', this.renderChart)
            },
            handleBus(msg){
                setTimeout(() => {
                    this.renderChart()
                }, 300);
            },
            renderChart(){
                this.$refs.bar.renderChart();
                this.$refs.line.renderChart();
            },
			renderTime(date) {
			  var dateee = new Date(date).toJSON();
			  return new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '') 
			},
			getData(){
				var that=this;
				axios({
				  method: 'get',
				  url: 'api/admin/selectUserEntity',
				})
				  .then(function (response) {
					if(response.data.code==0){
						
						console.log(response);
						//IP
						that.query.host=response.data.data.session.host;
						
						var date=response.data.data.session.startTimestamp;
						//登录时间
						that.query.startTimestamp=that.renderTime(date);
						//昵称
						that.query.name=response.data.data.userVo.name;
						//账户
						that.query.account=response.data.data.userVo.account;
						//状态
						that.query.forbidden=response.data.data.userVo.forbidden;
						//角色
						var res1 = [];
						
						response.data.data.userVo.roles.forEach(v=>{
							res1.push(v.name); 
						});
						
						that.query.roleList=res1;
						
					}else if(response.data.code==8){
					    that.$alert(response.data.msg, 'info', {
					        confirmButtonText: 'ok'
					    })
					}
				  })
				  .catch(function (error) {
				    console.log(error);
				  });
			}
			
			
			
			
        }
    }

</script>


<style scoped>
    .el-row {
        margin-bottom: 20px;
    }

    .grid-content {
        display: flex;
        align-items: center;
        height: 100px;
    }

    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }

    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }

    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }

    .grid-con-1 .grid-con-icon {
        background: rgb(45, 140, 240);
    }

    .grid-con-1 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-2 .grid-con-icon {
        background: rgb(100, 213, 114);
    }

    .grid-con-2 .grid-num {
        color: rgb(45, 140, 240);
    }

    .grid-con-3 .grid-con-icon {
        background: rgb(242, 94, 67);
    }

    .grid-con-3 .grid-num {
        color: rgb(242, 94, 67);
    }

    .user-info {
        display: flex;
        align-items: center;
        padding-bottom: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }

    .user-avator {
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }

    .user-info-cont {
        padding-left: 50px;
        flex: 1;
        font-size: 14px;
        color: #999;
    }

    .user-info-cont div:first-child {
        font-size: 30px;
        color: #222;
    }

    .user-info-list {
        font-size: 14px;
        color: #999;
        line-height: 25px;
    }

    .user-info-list span {
        margin-left: 70px;
    }

    .mgb20 {
        margin-bottom: 20px;
    }

    .todo-item {
        font-size: 14px;
    }

    .todo-item-del {
        text-decoration: line-through;
        color: #999;
    }

    .schart {
        width: 100%;
        height: 300px;
    }

</style>
