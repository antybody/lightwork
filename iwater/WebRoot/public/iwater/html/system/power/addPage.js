/**
 *  新增菜单的跳转
 */

define(['avalon','util','ztree',"css!core/css/import/zTreeStyle"],function(avalon,util){
	// 获取页面的类型
	var page_type = util.getHashStr("type");
	// 获取传入的参数
	var params = {};
	
	var ajax ={
		menu_query:'./system/menu/parentMenu',	/*相当于查询*/
		add_submit:'./system/power/create', /*相当于add的提交事件*/
		query:'./system/menu/menuItem',    /*一般的查询*/
		update_query:'./system/power/get', /*相当于查询*/
		check_only:'./system/power/checkOnly',   /*验证唯一性*/
		update_submit:'./system/power/update',/*相当于update的提交事件*/
		dic_query:'./system/dic/nextLevelNodeForAll', /*查询数据字典*/
		del:''
	};
	
	
	avalon.validators.sysPower_addPage_menu = {   //自定义'所属菜单'验证
            message: '必选',
            get: function (value, field, next) {
               //想知道它们三个参数是什么,可以console.log(value, field,next)
            	if(vm.power_owner=='' || vm.power_owner.length==0){
            		next(false);
            	}
            	else{
            		next(true);
            	}
                return value;
            }
    };
	
	
	 avalon.validators.sysPower_addPage_item = {   //自定义'所属项目'验证
             message: '必选',
             get: function (value, field, next) {
                //想知道它们三个参数是什么,可以console.log(value, field,next)
                 var ok = ((Number(value) != 0));
                 next(ok);
                 return value;
             }
     };
	 
	
		avalon.validators.sysPower_addPage_code = {   //自定义账号验证规则
	            message: '请填写唯一的代码',
	            get: function (value, field, next) {
	                if(value==''){
	                	next(false);
	                }
	                else{    //验证唯一性
	                	var stk={};
	                	if(page_type=='upd'){
	                		stk={'ui_id':vm.ui_id,"power_code":vm.power_code};
	                	}
	                	if(page_type=='add'){
	                		stk={'ui_id':'',"power_code":vm.power_code};
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
	
	/* 创建添加或者删除的模型库*/
	var vm = avalon.define({
		$id:'page_form',
		reset_flag:false,  /*重置操作标志位   完成判断后及时置为false*/
		ui_id:'',      /*主键*/
		power_type:'004',     /*默认是页面资源004*/
		power_showType:'',   /*注册页面资源 or  修改页面资源*/
		menu_select:'',  /*菜单下拉*/
		menu_project:'',  /*所属项目下拉*/
		power_prefix:'0',  /*所属项目*/
		buttonArr:[],   /*按钮选择列表*/
		power_owner:[], /*所属菜单 code*/
		power_ownerName:'',  /*所属菜单的名称  提示作用*/
		power_name:'',   /*权限名称*/
		power_code:'',	/*权限代码*/
		power_url:'',	/*权限路径*/
		power_follower:'',  /*权限跟从 按钮*/
		sysPower_addPage_menu:'',	/*自定义 校验  必选*/
		sysPower_addPage_code:'',  /*自定义校验  唯一性*/
		sysPower_addPage_item:'',  /*自定义校验  必须选择*/
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
		save:function(){
			this.validate.onManual();
		},
		reset:function(){
			vm.reset_flag=true; 
			initPageData();
		},
		org_chose:function(){    //点击选择'菜单' 
			//首先判断是否选择'所属项目'
			if(vm.power_prefix==0){
				util.layerMsg("请先选择所属项目");
				return;
			}
			//获得html页面内容 填充至对话框
			var ztree_html = require(['text!./iwater/html/system/power/ztree.html'],function(t){
				util.layerWin('选择所属菜单','500px','300px',t,function(){
					var zTree=$.fn.zTree.getZTreeObj("treeDemo");
					//获取所有被勾选的节点数据集合
					var checkedNodes=zTree.getCheckedNodes(true);
					if(checkedNodes.length!=0){
						vm.power_ownerName='';
						var all_power_owner=[];
						for(var i=0;i<checkedNodes.length;i++){
							//alert(checkedNodes[i].id);
							all_power_owner[i]=checkedNodes[i].cId;
							vm.power_ownerName=vm.power_ownerName+' '+checkedNodes[i].name;
						}
						vm.power_owner=all_power_owner;
						return true;
					}
					else{
						//util.layerMsg("请选择");
						vm.power_ownerName='';
						vm.power_owner=[];
						return true;
					}
				})
				compose_ztree();
			});
			
		},
		change_select:function(e){  //自定义方法  绑定事件
			//'所属项目' 下拉发生改变  清空vm中对应的值
			vm.power_owner=[];
			vm.power_ownerName='';
		},
		changeShow:function(e){     //按钮点击事件
			var target = e.target;  //获得dom对象
			//点击切换颜色  改变选中样式
			if($(target).attr("class")=="level0"){   //未选中 -> 选中  
				$(target).removeAttr("class");
				$(target).attr("class","level1");
				$("#btn"+target.id).attr("checked",true);
			}
			else{		//选中 ->  未选中
				$(target).removeAttr("class");
				$(target).attr("class","level0");
				$("#btn"+target.id).attr("checked",false);
			}
			
			//改变其值   组装: 'add,update,query'
			var type_str='';
			$("input[name=power_follower]:checked").each(function(){
				type_str=type_str+$(this).val()+",";
			});
			if(type_str.length!=0){
				type_str=type_str.substring(0, type_str.length-1);
			}
			vm.power_follower=type_str;
		}
	});
	
	//初始化 构成菜单树
	function compose_ztree(){
		util.post(ajax.menu_query,{"item_prefix":vm.power_prefix},function(result) {
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
				                chkStyle: "radio",
				                radioType: "all"
				            }
				    };
				   
				   //树   数据结构
				 	var data=[];
				 	var the_data=aJson.data;
				 	for(var i=0;i<the_data.length;i++){
				 		var check_flag=false;
				 		//使选中的数据被选中
				 		if(vm.power_owner!=''){
				 			for(var j=0;j<vm.power_owner.length;j++){
				 				if(the_data[i].menu_code==vm.power_owner[j]){
				 					check_flag=true;
				 				}
				 			}
				 		}
				 		data[i]={id:the_data[i].ui_id,pId:the_data[i].parent_menu,cId:the_data[i].menu_code,
				 				name:the_data[i].menu_zhname,checked:check_flag};
				 	}
					var zTreeObj = $.fn.zTree.init($("#treeDemo"),setting,data);
			        zTreeObj.expandAll(true);
			        
			        //项目没有可选的菜单
			        if(data.length==0){
			        	$("#showTip").html("该项目没有可选的菜单");
			        }
			        else{
			        	$("#showTip").html("");
			        }
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
		
	}
	
	avalon.scan(document.body);
	/* 创建添加或者删除的模型库*/
	
	/* 获取数据对模型操作 ，表单初始化
	 * 1.添加初始化= 添加重置
	 * 2.修改初始化
	 * 3.修改重置(与2不同：获得主键的方式)
	 */
	var  initPageData =function(){
		//初始化'page_type'  全局依赖此变量判断
		var type_flag=util.getHashStr("type");
		init_button();
		if(type_flag!='' && type_flag!=undefined){
			page_type=type_flag;
		}
		set_form_data();
	}
	
	//初始化  表单数据
	function set_form_data(){
		 //sel_project();   //初始化 '所属项目'下拉
		
		vm.power_prefix='0';  /*所属项目*/
		vm.power_owner=[]; /*所属菜单 code*/
		vm.power_ownerName='';  /*所属菜单的名称  提示作用*/
		vm.power_name='';   /*权限名称*/
		vm.power_code='';	/*权限代码*/
		vm.power_url='';	/*权限路径*/
		vm.power_follower='';  /*权限跟从 按钮*/
		$('input[name=power_code]').removeAttr("disabled");//去除input元素的readonly属性
  	   if(page_type=="add"){  
  		  vm.power_showType='注册页面资源';
  		  sel_project(0);          //'所属项目'下拉生成
  		  vm.reset_flag=false;      					//将标志及时还原
  	   }
  	   if(page_type=="upd"){  
  		  vm.power_showType='页面资源维护';
  		  var stk='';
  		 $('input[name=power_code]').attr("disabled","disabled");//将input元素设置为readonly
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
					 if(typeof json_data[key] != undefined 
							  && key !="power_owner" && key!="power_prefix"){
						 vm[key]=json_data[key];
					 }
					 if(key=='power_owner'){
						 vm.power_owner[0]=json_data[key];
					 }
					 if(key=='power_follower'){  //之前的按钮
						 var zk=json_data[key].split(",");
						 //使隐藏checkbox被选中   样式随之改变
						 for(var j=0;j<zk.length;j++){
							 $("#btn"+zk[j]).attr("checked",true);
							 $("#"+zk[j]).attr("class","level1");
						 }
					 }
				 }
				 sel_project(json_data.power_prefix);                                                //'所属项目'下拉生成
			}
			 else{
				 util.layerMsg(aJson.message);
			 }
  		  });
  		  
  	   }

  	   //init_button();
	}
	
	//页面的'按钮'  初始化
	function init_button(){
		vm.buttonArr=[];
        
		var button_array=[];
		util.post(ajax.dic_query,{parent_code:'btn_type'},function(result) {
			var aJson=eval("("+result+")");  
			if(aJson.code=="0"){
				for(var i=0;i<aJson.data.length;i++){
					button_array[button_array.length]={'bt_type':aJson.data[i].dic_code,'bt_val':aJson.data[i].dic_name};
				}
				
				//页面按钮     from 数据字典
		        /*var button_array=[
		                          {"bt_type":"add","bt_val":"新增/注册"},
		                          {"bt_type":"update","bt_val":"修改"},
		                          {"bt_type":"query","bt_val":"查询/搜索"},
		                          {"bt_type":"upload","bt_val":"上传"},
		                          {"bt_type":"download","bt_val":"下载"},
		                          {"bt_type":"del","bt_val":"删除"}
		                          ];*/
		        var stz='';
		        for(var i=0;i<button_array.length;i++){
		      	  stz='<a href="javascript:void(0)" class="level0" name="button_type" id="'+button_array[i].bt_type+'">'+button_array[i].bt_val+'</a> '+
		      	  	'<input type="checkbox" name="power_follower" value="'+button_array[i].bt_type+'" id="btn'+button_array[i].bt_type+'" style="display:none;"/>';
		      	  vm.buttonArr.push(stz);
		        }
				
			 }
			 else{
				 util.layerMsg(aJson.message);
			 } 
		});
		
	}
	
	
	
	/*
	 * 这里进行备注:  
	 * 级联页面不使用ms-click  原因: 用户体验差，点击时选项会发生闪变
	 *             ms-change 原因: 获得的是之前选中的值，不符合需要
	 */
	

	/* 传出模型的数据操作  */
	function submitData(){
		// 获得表单内全部元素
		
		//对个别特殊的数据做处理
		vm.power_owner=vm.power_owner[0];   //所属菜单
		
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
	
	/*  //vm中值清空
	   for (key in JSON.parse(JSON.stringify(vm.$model))){ 
			if(typeof vm.$model[key] === "string" )
				vm[key] = '';
			if(typeof vm.$model[key] === "boolean")
				vm[key] = false;
		}
	 */
		
		
	/*
	 * '所属项目'  下拉生成 和 值选定
	 */
	function sel_project(the_item_prefix){
		util.getJson(ajax.query,{},function(result){
	          var aJson= result;  
	          if(aJson.code == "0" ){
	        	  var data=aJson.data;
	        	  var dataArray=[];    //组合形成数组
	        	  var i=1;
	        	  dataArray[0]={'project_name':'--请选择--','project_value':0};
	        	  for(var obj in data){
	        		  dataArray[i]={'project_name':data[obj],'project_value':data[obj]};
	        		  i++;
	        	  }
	        	  vm.menu_project=dataArray;
	        	  vm.power_prefix=the_item_prefix;
	        	  // 更新数据后，刷新视图
	          	  avalon.scan(document.body);
		       }
		       else
		    	  util.layerMsg(aJson.message);
		});
	}
	
	
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData       
	}
});