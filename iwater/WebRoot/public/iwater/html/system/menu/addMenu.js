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
		add_submit:'./system/menu/create', /*相当于add的提交事件*/
		query:'./system/menu/menuItem',    /*一般的查询*/
		update_query:'./system/menu/get', /*相当于查询*/
		update_submit:'./system/menu/update',/*相当于update的提交事件*/
		check_only:'./system/menu/checkOnly', /*校验唯一性*/
		del:''
	};
	
	 avalon.validators.sysMenu_addMenu_item = {   //自定义的验证规则
             message: '必选',
             get: function (value, field, next) {
                //想知道它们三个参数是什么,可以console.log(value, field,next)
                 var ok = ((Number(value) != 0));
                 next(ok);
                 return value;
             }
     };
	 
	//自定义菜单代码验证规则
		avalon.validators.sysMenu_addMenu_code = {   
	            message: '请填写唯一的菜单代码',
	            get: function (value, field, next) {
	                if(value==''){
	                	next(false);
	                }
	                else{    //验证唯一性
	                	var stk={};
	                	if(page_type=='upd'){
	                		stk={'ui_id':vm.ui_id,"menu_code":vm.menu_code};
	                	}
	                	if(page_type=='add'){
	                		stk={'ui_id':'',"menu_code":vm.menu_code};
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
		$id:'menu_form',
		reset_flag:false,  /*重置操作标志位   完成判断后及时置为false*/
		ui_id:'',      /*主键*/
		menu_type:'',     /*新增菜单 or 修改菜单*/
		menu_select:'',  /*菜单下拉*/
		menu_project:'',  /*所属项目下拉*/
		menu_zhname:'', /*菜单名称中*/
		menu_enname:'', /*菜单名称英*/
		menu_code:'',   /*菜单编号*/
		item_prefix:'',  /*所属项目*/
		parent_menu:'0', /*父菜单编号*/
		parent_menuName:'',  /*父菜单的名称 用于显示*/
		menu_url:'',    /*菜单路径*/
		menu_sort:'',  /*菜单排序*/
		menu_class:'',  /*菜单样式*/
		pic_class:'',   /*图标样式*/
		isdel:false,   /*是否删除或者使用状态*/
		sysMenu_addMenu_item:'',	/*自定义 校验下拉*/
		sysMenu_addMenu_code:'',  /*自定义 校验菜单代码*/
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
			if(vm.item_prefix==0){
				util.layerMsg("请先选择所属项目");
				return;
			}
			//获得html页面内容 填充至对话框
			var ztree_html = require(['text!./iwater/html/system/menu/ztree.html'],function(t){
				util.layerWin('选择所属菜单','500px','300px',t,function(){
					var zTree=$.fn.zTree.getZTreeObj("treeDemo");
					//获取所有被勾选的节点数据集合
					var checkedNodes=zTree.getCheckedNodes(true);
					if(checkedNodes.length!=0){
						vm.parent_menuName='';
						var all_parent_menu=[];
						for(var i=0;i<checkedNodes.length;i++){
							//alert(checkedNodes[i].id);
							all_parent_menu[i]=checkedNodes[i].cId;
							vm.parent_menuName=vm.parent_menuName+' '+checkedNodes[i].name;
						}
						vm.parent_menu=all_parent_menu;
						return true;
					}
					else{
						//util.layerMsg("请选择");
						vm.parent_menuName='';
						vm.parent_menu=[];
						return true;
					}
				})
				compose_ztree();
			});
			
		},
		change_select:function(e){  //自定义方法  绑定事件
			//'所属项目' 下拉发生改变  清空vm中对应的值
			vm.parent_menu=[];
			vm.parent_menuName='';
		}
	});
	
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
		if(type_flag!='' && type_flag!=undefined){
			page_type=type_flag;
		}
		set_form_data();
	}
	
	//初始化 构成菜单树
	function compose_ztree(){
		util.post(ajax.menu_query,{"item_prefix":vm.item_prefix},function(result) {
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
				 		if(vm.parent_menu!=''){
				 			for(var j=0;j<vm.parent_menu.length;j++){
				 				if(the_data[i].ui_id==vm.parent_menu[j]){
				 					check_flag=true;
				 				}
				 			}
				 		}
				 		data[i]={id:the_data[i].ui_id,pId:the_data[i].parent_menu,cId:the_data[i].ui_id,
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
	
	//初始化  表单数据
	function set_form_data(){
		 //sel_project();   //初始化 '所属项目'下拉
		
		 vm.menu_zhname=''; /*菜单名称中*/
		 vm.menu_enname=''; /*菜单名称英*/
		 vm.menu_code='';   /*菜单编号*/
		 vm.menu_url='';    /*菜单路径*/
		 vm.parent_menu=[]; /*父菜单*/
		 vm.parent_menuName=''; /*父菜单显示*/
		 vm.menu_sort='';   /*菜单排序*/
		 vm.menu_class='';  /*菜单样式*/
		 vm.pic_class='';   /*图标样式*/
		 vm.isdel=false;   /*是否删除或者使用状态*/
	   $('input[name=menu_code]').removeAttr("disabled");//去除input元素的readonly属性
  	   if(page_type=="add"){  
  		  vm.menu_type='新增菜单';
  		$('input[name=menu_sort]').attr("disabled","disabled");  //主要目的是为了屏蔽验证
  		$("#sort_div").hide();
  		  sel_project(0);          //'所属项目'下拉生成
  		  vm.reset_flag=false;      					//将标志及时还原
  	   }
  	   if(page_type=="upd"){  
  		  vm.menu_type='编辑菜单';
  		  $("#sort_div").show();
  		  $('input[name=menu_sort]').removeAttr("disabled");
  		 $('input[name=menu_code]').attr("disabled","disabled");//将input元素设置为readonly
  		  var stk='';
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
					 if(typeof vm[key] != "undefined" 
							 && key !="parent_menu" && key !="item_prefix"){
						 vm[key]=json_data[key];
					 }
					 if(key=='parent_menu'){
						 vm.parent_menu[0]=json_data[key];
					 }
				 }
				 sel_project(json_data.item_prefix);                                                //'所属项目'下拉生成
				 
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
  		  });
  		  
  	   }
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
		if(vm.parent_menu==''){   //所属菜单
			vm.parent_menu='0';
		}
		else{
			vm.parent_menu=vm.parent_menu[0];  
		}
		
		
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
	        	  vm.item_prefix=the_item_prefix;
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