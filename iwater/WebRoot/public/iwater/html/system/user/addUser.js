/**
 *  新增菜单的跳转
 */

define(['avalon','util',"ztree","css!core/css/import/zTreeStyle"],function(avalon,util){
	// 获取页面的类型
	var page_type = util.getHashStr("type");
	// 获取传入的参数
	var params = {};
	
	var ajax ={
		add_query:'./system/user/parentMenu',	/*相当于查询*/
		add_submit:'./system/user/create', /*相当于add的提交事件*/
		update_query:'./system/user/get', /*相当于查询*/
		update_submit:'./system/user/update',/*相当于update的提交事件*/
		check_only:'./system/user/checkOnly',   /*验证唯一性*/
		org_query:'./system/organization/allList', /*组织机构列表*/
		role_query:'./system/role/allList',  /*角色列表*/
		dic_query:'./system/dic/nextLevelNodeForAll', /*查询数据字典*/
		del:''
	};
	
	avalon.validators.sysUser_addUser_item = {   //自定义的验证规则
            message: '必选',
            get: function (value, field, next) {
               //想知道它们三个参数是什么,可以console.log(value, field,next)
                var ok = ((Number(value) != 0));
                next(ok);
                return value;
            }
    };
	
	//自定义账号验证规则
	avalon.validators.sysUser_addUser_code = {   
            message: '请填写唯一的账号',
            get: function (value, field, next) {
                if(value==''){
                	next(false);
                }
                else{    //验证唯一性
                	var stk={};
                	if(page_type=='upd'){
                		stk={'ui_id':vm.ui_id,"user_code":vm.user_code};
                	}
                	if(page_type=='add'){
                		stk={'ui_id':'',"user_code":vm.user_code};
                	}
                	util.post(ajax.check_only,stk,function(result) {
           			 //这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
           			 var aJson=eval("("+result+")");
           			 if(aJson.code=="0"){
           				 if(aJson.data=="0"){
           					 next(true);
           				 }
           				 else{
           					 next(false);
           				 }
           			 }
           			 else{
           				 next(false);
           				 util.layerMsg(aJson.message);
           			 }
           		   });
                }
                return value;
            }
    };
	
	
	//自定义密码验证规则
	avalon.validators.sysUser_addUser_pwd = {   
            message: '必须由字母和数字组成，6-12位',
            get: function (value, field, next) {
               //想知道它们三个参数是什么,可以console.log(value, field,next)
                var parttern=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$/;
            	if(!parttern.test(value)){
                	next(false);
                }
                else{    //验证唯一性
                	next(true);
                }
                return value;
            }
    };
	
	/* 创建添加或者删除的模型库*/
	var vm = avalon.define({
		$id:'user_form',
		reset_flag:false,  /*重置操作标志位   完成判断后及时置为false*/
		ui_id:'',      /*主键*/
		menu_type:'',     /*新增菜单 or 修改菜单*/
		user_name:'',  /*用户姓名*/
		user_code:'',  /*账号*/
		user_pwd:'', /*密码*/
		confirm_password:'',  /*验证密码*/
		user_tel:'', /*手机*/
		user_mail:'', /*邮箱*/
		user_phone:'',/*座机*/
		org_zhname:'', /*组织机构*/
		org_id:'',  /*组织机构代号 并不是主键*/
		role_zhname:'',  /*角色*/
		role_id:'',     /*角色代号 并不是主键*/
		role_num:[],    /*角色类型综合数据 */
		user_post:'', /*职务*/
		post_sel:[],  /*职务下拉*/
		sysUser_addUser_code:'',   /*自定义校验  账号*/
		sysUser_addUser_pwd:'',    /*自定义校验 密码*/
		sysUser_addUser_item:'',   /*自定义校验 下拉*/
		validate:{  // 校验方法
			onError: function (reasons) {                          //单个验证失败时触发
               reasons.forEach(function (reason) {
               	//进行提示
               	var a=document.getElementsByName(reason.element.name)[0].parentNode.lastChild;
               	a.innerHTML=reason.getMessage();
               	//alert(reason.getMessage());
               	//avalon.log(this);
                   console.log(reason.getMessage());
               });
           },
           onSuccess:function(reasons, event){       // 针对表单内单个的元素
           	reasons.forEach(function (reason) {
               	var a=document.getElementsByName(reason.element.name)[0].parentNode.lastChild;
               	a.innerHTML='';    
               });
           },            
           onValidateAll: function (reasons) {
               if (reasons.length) {
               	reasons.forEach(function (reason) {
                   	//进行提示
                   	var a=document.getElementsByName(reason.element.name)[0].parentNode.lastChild;
                   	a.innerHTML=reason.getMessage();
                       console.log(reason.getMessage());
                   });
                   avalon.log('有表单没有通过');
               } else {
               	avalon.log('通过验证');
               	submitData();
               }
           }
		},
		org_chose:function(){   
			//获得html页面内容 填充至对话框
			var ztree_html = require(['text!./iwater/html/system/user/ztree.html'],function(t){
				util.layerWin('选择组织机构','500px','300px',t,function(){
					var zTree=$.fn.zTree.getZTreeObj("treeDemo");
					//获取所有被勾选的节点数据集合
					var checkedNodes=zTree.getCheckedNodes(true);
					if(checkedNodes.length!=0){
						vm.org_zhname='';
						var all_org_id=[];
						for(var i=0;i<checkedNodes.length;i++){
							//alert(checkedNodes[i].id);
							all_org_id[i]=checkedNodes[i].cId;
							vm.org_zhname=vm.org_zhname+' '+checkedNodes[i].name;
						}
						vm.org_id=all_org_id;
						return true;
					}
					else{
						util.layerMsg("请选择");
						return false;
					}
				})
				compose_ztree();
			});
			
		},
		role_chose:function(){
			//获得html页面内容 填充至对话框
			var ztree_html = require(['text!./iwater/html/system/user/roleChose.html'],function(t){
				util.layerWin('选择角色','500px','300px',t,function(){
					
					vm.role_zhname='';
					var all_role_id=[];
					//循环每个类的每棵树
					for(var i=0;i<vm.role_num.length;i++){
						var ky=vm.role_num;
						var zTree=$.fn.zTree.getZTreeObj("t"+ky[i].role_no);
						//获取所有被勾选的节点数据集合
						var checkedNodes=zTree.getCheckedNodes(true);
						if(checkedNodes.length!=0){
							for(var j=0;j<checkedNodes.length;j++){
								//alert(checkedNodes[i].id);
								all_role_id[all_role_id.length]=checkedNodes[j].id;
								vm.role_zhname=vm.role_zhname+' '+checkedNodes[j].name;
							}
							
						}
					}
					vm.role_id=all_role_id;
					return true;
					
				})
				compose_chose();
			});
		},
		save:function(){
			this.validate.onManual();
		},
		reset:function(){
			vm.reset_flag=true; 
			initPageData();
		}
	});
	
	//初始化  构成角色选择
	function compose_chose(){
		var setting = {
	            data: {
	            	view: {
						showLine: false
					},
	                simpleData: {
	                    enable: true,
	                    idKey: "id",
	                    pIdKey: "pId",
	                    rootPId: "dept_root"
	                },
	                key:{
	                    name:"name"
	                }
	            },
	            check: {
	                enable: true,
	                chkStyle: "checkbox",
	                radioType: "all"
	            }
	    };
	
		/*
		 * var role_num=[        //这里应该来源于数据字典      role_da 代表这一类的所有角色   后续代码会进行合成
		               {role_no:'001',role_type:'浏览类',role_da:[]},
		               {role_no:'002',role_type:'填报类',role_da:[]},
		               {role_no:'003',role_type:'操作类',role_da:[]},
		               {role_no:'004',role_type:'管理类',role_da:[]}
		];
		
		vm.role_num=role_num;*/
		
		/*var data =[         //示例数据
		 			{ id:1, pId:0, name:"浏览类1", open:true},
		 			{ id:2, pId:0, name:"浏览类2", open:true},
		 			{ id:3, pId:0, name:"浏览类3", open:true}
		 ];*/
		var role_num=vm.role_num;
		
		var stk1='';
		var stk2='';
		for(var i=0;i<role_num.length;i++){
			role_num[i].role_da=[]; //将之前的角色数据清空
			
			var zstr='z'+role_num[i].role_no;
			if(i==0){
				stk1+='<div style="float:left;">'+
				'<a href="javascript:void(0)" class="level1" name="role_type" onclick="changeShow(this,\''+zstr+'\')">'+role_num[i].role_type+'</a>'+
				'</div>';
				stk2+='<div class="zTreeDemoBackground left" id="z'+role_num[i].role_no+'" style="display:block;" name="role_tree">'+
				 '<ul id="t'+role_num[i].role_no+'" class="ztree"></ul>'+
				 '</div>';
			}
			else{
				stk1+='<div style="float:left;">'+
				'<a href="javascript:void(0)" class="level0" name="role_type" onclick="changeShow(this,\''+zstr+'\')">'+role_num[i].role_type+'</a>'+
				'</div>';
				stk2+='<div class="zTreeDemoBackground left" id="z'+role_num[i].role_no+'" style="display:none;" name="role_tree">'+
				 '<ul id="t'+role_num[i].role_no+'" class="ztree"></ul>'+
				 '</div>';
			}
			
		}
		$("#line_type").html(stk1);
		$("#line_role").html(stk2);
		
		util.post(ajax.role_query+'?param='+encodeURI('{pageSize:1}'),{excel_flag:'flag'},function(result) {
			var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 var the_data=aJson.data;
				 //将每一角色类的数据进行归纳
				 for(var i=0;i<the_data.length;i++){
					for(var j=0;j<role_num.length;j++){
						if(the_data[i].role_type==role_num[j].role_no){     //属于该角色类
							var now_num=role_num[j].role_da.length;
							
							var check_flag=false;
							if(vm.role_id!=''){   //使已经被选中的数据保持选中
					 			for(var c=0;c<vm.role_id.length;c++){
					 				if(the_data[i].role_code==vm.role_id[c]){
					 					check_flag=true;
					 				}
					 			}
					 		}
							
							role_num[j].role_da[now_num]={id:the_data[i].role_code,pId:0,name:the_data[i].role_zhname,open:true,checked:check_flag};
						}
					}
				 	//data[i]={id:the_data[i].role_code,pId:0,name:the_data[i].role_zhname,open:true};	
				 }	
				 
				 //生成每一类角色的树
				 for(var i=0;i<role_num.length;i++){
					 var zTreeObj = $.fn.zTree.init($("#t"+role_num[i].role_no),setting,role_num[i].role_da);
				     zTreeObj.expandAll(true);
				 }
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		})
		
		
	}
	
	//初始化 构成组织机构树
	function compose_ztree(){
		util.post(ajax.org_query,{},function(result) {
			 var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 //树  基本设置
				 var setting = {
						 	view: {
								showLine: false
							},
				            data: {
				                simpleData: {
				                    enable: true,
				                    idKey: "id",
				                    pIdKey: "pId",
				                    rootPId: "dept_root"
				                },
				                key:{
				                    name:"name"
				                }
				            },
				            check: {
				                enable: true,
				                chkboxType:{"Y":"","N":""},   /*勾选不发生关联*/
				                chkStyle: "checkbox",
				                radioType: "all"
				            }
				    };
				   
				   //树   数据结构
				 	var data=[];
				 	var the_data=aJson.data;
				 	for(var i=0;i<the_data.length;i++){
				 		var check_flag=false;
				 		//使选中的数据被选中
				 		if(vm.org_id!=''){
				 			for(var j=0;j<vm.org_id.length;j++){
				 				if(the_data[i].org_code==vm.org_id[j]){
				 					check_flag=true;
				 				}
				 			}
				 		}
				 		data[i]={id:the_data[i].ui_id,pId:the_data[i].org_parent,
				 				cId:the_data[i].org_code,name:the_data[i].org_zhname,checked:check_flag};
				 	}
					var zTreeObj = $.fn.zTree.init($("#treeDemo"),setting,data);
			        zTreeObj.expandAll(true);
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
		
	}
	
	
	//avalon.scan(document.body);
	/* 创建添加或者删除的模型库*/
	
	/* 获取数据对模型操作 ，表单初始化
	 * 1.添加初始化= 添加重置
	 * 2.修改初始化
	 * 3.修改重置(与2不同：获得主键的方式)
	 */
	var  initPageData =function(){
		//初始化'page_type'  全局依赖此变量判断
		var type_flag=util.getHashStr("type");
		if(type_flag!='' && type_flag!=undefined){
			page_type=type_flag;
		}
		init_role_num();
		set_form_data();
	}
	
	//初始化 从数据字典中得到角色类型
	function init_role_num(){
		util.post(ajax.dic_query,{parent_code:'role_type'},function(result) {
			var aJson=eval("("+result+")");  
			if(aJson.code=="0"){
				vm.role_num=[];
				for(var i=0;i<aJson.data.length;i++){
					//{role_no:'001',role_type:'浏览类',role_da:[]},
					vm.role_num[vm.role_num.length]={'role_no':aJson.data[i].dic_code,'role_type':aJson.data[i].dic_name,role_da:[]};
				}
			}
			else{
				util.layerMsg(aJson.message);
			}
		});
	}
	
	//生成'职务下拉'  锁定其值
	function sel_post(user_post){
		var post_array=[];    //定义数组装填值
		post_array[0]={'post_name':'请选择','post_value':0};
		util.post(ajax.dic_query,{parent_code:'user_post'},function(result) {
			var aJson=eval("("+result+")");  
			if(aJson.code=="0"){
				for(var i=0;i<aJson.data.length;i++){
					post_array[post_array.length]={'post_name':aJson.data[i].dic_name,'post_value':aJson.data[i].dic_code};
				}
				vm.post_sel=post_array;
				vm.user_post=user_post;
			}
			else{
				util.layerMsg(aJson.message);
			}
		});
		
		/*vm.post_sel=[      //这里应该来源于数据字典
		             {'post_name':'请选择','post_value':0},
		             {'post_name':'总经理','post_value':'001'},
		             {'post_name':'部门经理','post_value':'002'},
		             {'post_name':'主任','post_value':'003'},
		             {'post_name':'其他','post_value':'004'}
		            ];*/
		
	}
	
	//初始化  表单数据
	function set_form_data(){
		 vm.user_name='';
		 vm.user_code='';
		 vm.user_pwd='';
		 vm.confirm_password='';
		 vm.user_mail='';
		 vm.user_tel='';
		 vm.user_phone='';
		 vm.org_id='';
		 vm.org_zhname='';
		 vm.role_id='';
		 vm.role_zhname='';
	   $('input[name=user_code]').removeAttr("disabled");//去除input元素的readonly属性
 	   if(page_type=="add"){  
 		  vm.menu_type='新增用户';
 		  sel_post(0);
 		  vm.reset_flag=false;      					//将标志及时还原
 		  
 	   }
 	   if(page_type=="upd"){  
 		  vm.menu_type='编辑用户';
 		  var stk='';
 		  $('input[name=user_code]').attr("disabled","disabled");//将input元素设置为readonly
 		  if(vm.reset_flag){  		                   //修改页面的重置
 			stk={'ui_id':vm.ui_id};
 			vm.reset_flag=false;      					//将标志及时还原
 		  }
 		  else{				  		                   //修改页面的初始化	
 			stk={'ui_id':util.getHashStr("ui_id")};
 		  }
 		  
 		  //修改前赋值
 		  util.post(ajax.update_query,stk,function(result) {
			 var aJson=eval("("+result+")");  
			 if(aJson.code=="0"){
				 var json_data=aJson.data;
				 for(key in json_data){
					 if(typeof json_data[key] != undefined && typeof vm[key] != "undefined"
							 && key !="user_pwd" && key!="user_post"){
						 vm[key]=json_data[key];
					 }
					 
					 //显示用户之前的组织机构关系
					 if(key=="org_id"){
						 var org_array=[];
						 var sk=eval("("+json_data.org_id+")");
						 //alert(JSON.stringify(sk));
						 for(var i=0;i<sk.length;i++){
							 org_array[i]=sk[i].org_code;
						 }
						 vm.org_id=org_array;
					 }
					 
					 //显示用户之前的角色关系
					 if(key=="role_id"){
						 var role_array=[];
						 var sk=eval("("+json_data.role_id+")");
						 for(var i=0;i<sk.length;i++){
							 role_array[i]=sk[i].role_code;
						 }
						 vm.role_id=role_array;
					 }
					 
				 }
				 sel_post(json_data.user_post);
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
 		  });
 		  
 	   }
	}
	
	
	/* 传出模型的数据操作  */
	function submitData(){
		// 获得表单内全部元素
		var stk=JSON.parse(JSON.stringify(vm.$model));
		avalon.log(JSON.parse(JSON.stringify(vm.$model)));
		var tip='';
		var url='';
		if(page_type=='add'){
			tip='添加成功';
			url=ajax.add_submit;
		}
		if(page_type=='upd'){
			tip='修改成功';
			url=ajax.update_submit;
		}
		 util.post(url,stk,function(result) {
			 //这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			 var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 util.layerMsg(tip);
				 if(page_type=='add'){
					 initPageData();    //将表单初始化
				 }
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
		
	}
	

	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData       
	}
});