/**
 *  新增角色的跳转
 */

define(['avalon','util','ztree',"css!core/css/import/zTreeStyle"],function(avalon,util){
	// 获取页面的类型
	var page_type = util.getHashStr("type");
	
	var params = '';    //角色数据集合
	
	var ajax ={
		add_submit:'./system/role/create', /*相当于add的提交事件*/
		update_query:'./system/role/get', /*相当于查询*/
		check_only:'./system/role/checkOnly',   /*验证唯一性*/
		update_submit:'./system/role/update',/*相当于update的提交事件*/
		power_query:'./system/power/allList',  /*权限资源查询*/
		dic_query:'./system/dic/nextLevelNodeForAll', /*查询数据字典*/
		del:''
	};
	
	
	 avalon.validators.sysRole_addRole_item = {   //自定义'所属项目'验证
             message: '必选',
             get: function (value, field, next) {
                //想知道它们三个参数是什么,可以console.log(value, field,next)
                 var ok = ((Number(value) != 0));
                 next(ok);
                 return value;
             }
     };
	
		avalon.validators.sysRole_addRole_code = {   //自定义账号验证规则
	            message: '请填写唯一的账号  规范:ROLE_XXX (全大写)',
	            get: function (value, field, next) {
	            	var flag=true;
	            	if(value.length>5){
	            		if(value.substring(0,5)=='ROLE_'){
	            			var partern=/^[A-Z]+$/;
	            			if(partern.test(value.substring(5,value.length))){
	            				flag=false;   //说明符合规范
	            			}
	            		}	
	            	}
	                if(flag){   //验证规范性
	                	next(false);
	                }
	                else{    //验证唯一性  
	                	var stk={};
	                	if(page_type=='upd'){
	                		stk={'ui_id':vm.ui_id,"role_code":vm.role_code};
	                	}
	                	if(page_type=='add'){
	                		stk={'ui_id':'',"role_code":vm.role_code};
	                	}
	                	util.post(ajax.check_only,stk,function(result) {
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
		$id:'role_form',
		reset_flag:false,  /*重置操作标志位   完成判断后及时置为false*/
		ui_id:'',      /*主键*/
		show_type:'',     /*新增角色  or  角色维护*/
		type_select:'',  /*角色类型下拉*/
		role_type:'0',  /*角色类型*/
		role_code:'',   /*角色代码*/
		rel_role_power:{inter:[],menu:{}},   /*角色对应的权限数据集合  有特定格式*/
		role_zhname:'',	/*角色中文名称*/
		role_enname:'',	/*角色英文名称*/
		sysRole_addRole_item:'',	/*自定义 校验  必选*/
		sysRole_addRole_code:'',  /*自定义校验  唯一性*/
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
	
	//初始化  表单数据
	function set_form_data(){
		pro_button_array();   //生成全局变量 button_array 供调用
		vm.role_code=''; /*角色代码*/
		vm.role_zhname='';  /*角色中文名称*/
		vm.role_enname='';  /*角色英文名称*/
		vm.role_type='0';   /*角色类型*/
		vm.rel_role_power={inter:[],menu:{}};  /*角色对应的权限数据集合  有特定格式*/
		
		$('input[name=role_code]').removeAttr("disabled");//去除input元素的readonly属性
		
		 create_select(0);   //角色类型 下拉
  	   if(page_type=="add"){
  		  vm.show_type='新增角色';
  		  //create_select(0);   //角色类型 下拉
  		  compose_tree();
  		  vm.reset_flag=false;      					//将标志及时还原
  	   }
  	   if(page_type=="upd"){  
  		  vm.show_type='角色维护';
  		  var stk='';
  		  $('input[name=role_code]').attr("disabled","disabled");//将input元素设置为readonly
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
							  && key !="rel_role_power"){
						 vm[key]=json_data[key];
					 }
					 if(key=="rel_role_power"){    //角色权限关系
						 vm[key]=eval("("+json_data[key]+")");
					 }
				 }
				 create_select(json_data.role_type);   //角色类型 下拉
				 compose_tree();   //等待赋值完成之后生成树
			}
			 else{
				 util.layerMsg(aJson.message);
			 }
  		  });
  		  
  	   }

	}
	
	var button_array=[];
	/*var button_array=[				//页面按钮     from 数据字典
	                                {"bt_type":"add","bt_val":"新增/注册"},
	                                {"bt_type":"update","bt_val":"修改"},
	                                {"bt_type":"query","bt_val":"查询/搜索"},
	                                {"bt_type":"upload","bt_val":"上传"},
	                                {"bt_type":"download","bt_val":"下载"},
	                                {"bt_type":"del","bt_val":"删除"}
	                  ];*/
	
	
	//生成button_array  供全局比对调用
	function pro_button_array(){
		button_array=[];
		util.post(ajax.dic_query,{parent_code:'btn_type'},function(result) {
			var aJson=eval("("+result+")");  
			if(aJson.code=="0"){
				for(var i=0;i<aJson.data.length;i++){
					button_array[button_array.length]={'bt_type':aJson.data[i].dic_code,'bt_val':aJson.data[i].dic_name};
				}
			}
			else{
				util.layerMsg(aJson.message);
			}
		});
	}
	
	
	var setting = {        //树的配置
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
            },
            callback:{
            	onClick:function(event,treeId,treeNode){   //点击文字标签
            		if(treeNode.typeId=='002'){  //菜单
            			click_menu(treeNode.zId);
            		}
            		if(treeNode.typeId=='004'){  //页面
            			click_page(treeNode.bId,treeNode.zId);
            		}
    			},
    			onCheck:function(event,treeId,treeNode){   //选中或者取消选中
    				/*
    				 * 实现功能: 1.子选  父必选   2.父取消，子取消
    				 * 			3.相关数据与rel_role_power 进行同步(接口权限最后提交表单时同步)
    				 * 参数说明:  typeId  判断点击的类型
    				 * 			 zId  相关的代码(自身代码 或 上一级代码)
    				 */  
    				if(treeNode.checked){	
    					if(treeNode.typeId=='002'){  //选中菜单
    						rele_date1(treeNode.zId);
                		}
    					if(treeNode.typeId=='004'){  //选中页面
    						relevant_1(treeNode.zId);
    						rele_date2(treeNode.zId);
                		}
                		if(treeNode.typeId=='005'){	 //选中按钮
                			relevant_2(treeNode.zId);
                			rele_date3(treeNode.zId);
                		}
    				}
    				else{					//取消
    					if(treeNode.typeId=='002'){  //取消菜单
    						relevant_3(treeNode.zId);
    						rele_date4(treeNode.zId);
                		}
                		if(treeNode.typeId=='004'){	 //取消页面
                			relevant_4(treeNode.zId);
                			rele_date5(treeNode.zId);
                		}
                		if(treeNode.typeId=='005'){  //取消按钮
                			rele_date6(treeNode.zId);
                		}
    				}
    			}
            }
    };
	
	//选中菜单 同步数据
	function rele_date1(id){
		vm.rel_role_power.menu[id]={};
		//alert(JSON.stringify(vm.rel_role_power));
	}
	
	//选中页面 同步数据
	function rele_date2(id){
		for(var i=0;i<params.length;i++){
			if(params[i].power_code==id && params[i].power_type=='004'){  //选中的页面
				var a=vm.rel_role_power.menu;
				var flag=true;
				
				for(var key in a){        
					if(key==params[i].power_owner){   //1.menu中有该页面的菜单
						//a[key]=b;
						var c=a[key];
						c[id]='';
						flag=false;
					}
				}
				
				if(flag){     						 //2.menu 中没有该页面的菜单
					var b={};
					b[params[i].power_code]='';
					a[params[i].power_owner]=b;
				}
			}
		}
		//alert(JSON.stringify(vm.rel_role_power));
	}

	//选中按钮 同步数据
	function rele_date3(id){
		var str='';
		var zTree=$.fn.zTree.getZTreeObj("treeDemo4");
		//获取所有被勾选的节点数据集合
		var checkedNodes=zTree.getCheckedNodes(true);
		if(checkedNodes.length!=0){                                   //这里可能存在bug  要求保存用户之前勾选的按钮
			for(var j=0;j<checkedNodes.length;j++){
				if(j!=0){
					str+=",";
				}
				str+=checkedNodes[j].id;
			}
		}
		
		for(var i=0;i<params.length;i++){
			if(params[i].power_code==id && params[i].power_type=='004'){  //按钮的父页面
				var a=vm.rel_role_power.menu;
				var flag=true;
				
				for(var key in a){        
					if(key==params[i].power_owner){   //1.menu中有该页面的菜单
						//a[key]=b;
						var c=a[key];
						c[id]=str;
						flag=false;
					}
				}
				
				if(flag){     						 //2.menu 中没有该页面的菜单
					var b={};
					b[params[i].power_code]=str;
					a[params[i].power_owner]=b;
				}
			}
		}
		//alert(JSON.stringify(vm.rel_role_power));
	}

	//取消菜单 同步数据
	function rele_date4(id){
		delete vm.rel_role_power.menu[id];
		//alert(JSON.stringify(vm.rel_role_power));
	}

	//取消页面 同步数据
	function rele_date5(id){
		var a=vm.rel_role_power.menu;
		for(var i=0;i<params.length;i++){
			if(params[i].power_code==id && params[i].power_type=='004'){  //选中的页面
				var c=a[params[i].power_owner];
				delete c[params[i].power_code];
			}
		}
		//alert(JSON.stringify(vm.rel_role_power));
	}

	//取消按钮 同步数据
	function rele_date6(id){
		rele_date3(id);
	}
	
	//页面选中  对应菜单选中
	function relevant_1(id){
		//首先找到数据对象
		var data='';
		for(var i=0;i<params.length;i++){
			if(params[i].power_code==id){
				data=params[i];
			}
		}
		for(var i=0;i<params.length;i++){
			if(data.power_owner==params[i].power_code && params[i].power_type=='002'){
				choseOrCancel("treeDemo2",params[i].ui_id,true);
			}
		}
	}
	
	//按钮选中  对应页面,菜单选中
	function relevant_2(id){
		//首先找到数据对象
		var data='';
		for(var i=0;i<params.length;i++){
			if(params[i].power_code==id){
				data=params[i];
			}
		}
		choseOrCancel("treeDemo3",data.ui_id,true);  //页面选中
		
		for(var i=0;i<params.length;i++){
			if(data.power_owner==params[i].power_code && params[i].power_type=='002'){
				choseOrCancel("treeDemo2",params[i].ui_id,true);  //菜单选中
			}
		}
	}
	
	//菜单取消   取消子页面  子按钮
	function relevant_3(id){
		//1.判断当前显示的页面  是不是 取消菜单的子页面  如果是全部取消
		/*for(var i=0;i<params.length;i++){
			if(id==params[i].power_owner && params[i].power_type=='004'){
				choseOrCancel("treeDemo3",params[i].ui_id,false);  //1.子页面取消选中
			}
		}*/
		var ztree1=$.fn.zTree.getZTreeObj("treeDemo3");
		var checkedNodes1=ztree1.getCheckedNodes(true);
		for(var i=0;i<params.length;i++){
			if(checkedNodes1.length!=0){
				if(checkedNodes1[0].zId==params[i].power_code && params[i].power_type=='004'){
					if(params[i].power_owner==id){
						ztree1.checkAllNodes(false);
					}
				}
			}
		}
		
		//2.判断当前显示的按钮页面的父页面 是不是 取消选中菜单的子页面  如果是全部取消
		var zTree=$.fn.zTree.getZTreeObj("treeDemo4");
		//获取所有被勾选的节点数据集合
		var checkedNodes=zTree.getCheckedNodes(true);
		if(checkedNodes.length!=0){
			for(var i=0;i<params.length;i++){
				if(id==params[i].power_owner && params[i].power_type=='004'){  //其中的子页面
					if(params[i].power_code==checkedNodes[0].zId){
						//符合条件全部取消
						zTree.checkAllNodes(false);
					}
				}
			}
		}	
	}
	
	//页面取消  取消子按钮
	function relevant_4(id){
		//判断当前显示的按钮页面的父页面  是不是取消的页面  如果是全部取消
		var zTree=$.fn.zTree.getZTreeObj("treeDemo4");
		var checkedNodes=zTree.getCheckedNodes(true);
		if(checkedNodes.length!=0){
			for(var i=0;i<params.length;i++){
				if(id==params[i].power_code && params[i].power_type=='004'){  //找到页面
					if(params[i].power_code==checkedNodes[0].zId){
						//符合条件全部取消
						zTree.checkAllNodes(false);
					}
				}
			}
		}	
	}

	/*
	 * 单个勾选或者取消的方法
	 * treeName: 树名称
	 * id: 节点id
	 * flag: true选中   false 取消选中
	 */
	function choseOrCancel(treeName,id,flag){
		var zTree=$.fn.zTree.getZTreeObj(treeName);
		var node = zTree.getNodeByParam("id",id, null);  //获得节点
		zTree.checkNode(node, flag, false,false);
	}
	
	
	/*
	 * 主要实现的功能:
	 * 1.级联显示数据   菜单-页面-按钮       
	 * 2.将选中和取消的数据同步至 rel_role_power
	 * 3.级联选中   父取消 子取消  ,子选中  父选中
	 * 4.保持选中的数据被选中   修改的数据被选中  
	 * 
	 * 树中参数 遵循规范
	 * id:  主键
	 * pId: 父节点
	 * zId: 关联代码(接口,菜单,页面代码,  按钮没有代码存放的是父页面代码)
	 * typeId:区分菜单，页面，按钮
	 * 
	 * rel_role_power  权限资源数据集合 
	 * 	示例: {
	 * 				inter:[],
	 * 				menu:{ key1 : val1...}
	 * 			}
	 * 	
	 * 			菜单  key1 =  power_code  (不重复,方便添加和删除)
	 * 				val1:{ key2 : val2}
	 * 
	 * 			页面 key2 =  power_code  (不重复,方便添加和删除)
	 * 				val2:  按钮字符串( 例:'add,upload')
	 * 
	 */
	
	/*
	 * 树的关联显示    页面和按钮
	 * params: bId: 按钮的字符串  ('add,update,del')
	 * 		   power_code: 所属页面代码
	 */
	function click_page(bId,power_code){
		var menuJson=vm.rel_role_power.menu;
		var btnStr='';   //当前已被选中的按钮
		for(var i=0;i<params.length;i++){
			if(power_code==params[i].power_code
					&& menuJson.hasOwnProperty(params[i].power_owner)){   //按钮的最上一级 菜单存在
				var pageJson=menuJson[params[i].power_owner];
				if(pageJson.hasOwnProperty(params[i].power_code)){     //按钮的上一级  页面也存在
					btnStr=pageJson[params[i].power_code];
				}
			}
		}
		
		
		var data=[];
        if(bId!=''){	//显示第一个页面的按钮
        	var the_button=bId.split(",");
        	for(var i=0;i<the_button.length;i++){
        		for(var j=0;j<button_array.length;j++){
        			if(button_array[j].bt_type==the_button[i]){
        				
        				var checkFlag=false;
        				if(btnStr!=''){  //判断按钮是否已被选中
        					var btnArr=btnStr.split(",");
        					for(var z=0;z<btnArr.length;z++){
        						if(btnArr[z]==the_button[i]){
        							checkFlag=true;
        						}
        					}
        				}
        				
        				data[data.length]={ id:button_array[j].bt_type,zId:power_code,
        						name:button_array[j].bt_val,typeId:'005',open:true,checked:checkFlag};
        			}
        		}
        	}
        }
        var zTreeObj4 = $.fn.zTree.init($("#treeDemo4"),setting,data);
        zTreeObj4.expandAll(true);
	}
	
	/*
	 *树的关联显示   菜单-页面-按钮
	 * params:  菜单代码
 	 */
	function click_menu(zId){
		var menuJson=vm.rel_role_power.menu;
		
		var data=[];
		util.post(ajax.power_query+"?param="+encodeURI("{pageSize:1}"),{excel_flag:'true'},function(result) { 
			var aJson=eval("("+result+")");
			params=aJson.data;
	        if(aJson.code == "0" ){
	        	for(var i=0;i<aJson.data.length;i++){
	        		if(aJson.data[i].power_type=='004' 
	        			&& aJson.data[i].power_owner==zId){	//页面资源  
	        			
	        			var checkFlag=false;
	        			if(menuJson.hasOwnProperty(zId)){  //数据集合中有这个菜单的数据
	        				var pageJson=menuJson[zId];
	        				if(pageJson.hasOwnProperty(aJson.data[i].power_code)){   //数据集合中有这个页面的数据
	        					checkFlag=true;
	        				}
	        			}
	        			
	        			data[data.length]={ id:aJson.data[i].ui_id,
	        								  pId:0,zId:aJson.data[i].power_code,
	        								  sId:aJson.data[i].power_owner,
	        								  bId:aJson.data[i].power_follower,
	        								  typeId:aJson.data[i].power_type,
	        								  name:aJson.data[i].power_name, open:true,
	        								  checked:checkFlag};
	        		}
	        	}
	        	//重新生成(菜单)树
	        	var zTreeObj = $.fn.zTree.init($("#treeDemo3"),setting,data);
	            zTreeObj.expandAll(true);
	            if(data.length!=0){
	            	zTreeObj.selectNode(zTreeObj.getNodeByParam('id',data[0].id));//使第一个节点高亮 
	            }
	            //根据第一个页面  生成(按钮)树
	            if(data.length!=0){
	            	click_page(data[0].bId,data[0].zId);
	            }
	            else{
	            	click_page('','');
	            }
	        }
	        else{
	        	util.layerMsg(aJson.message);
	        }
		});
	}
	
	/*
	 * 树的初始显示(不涉及点击级联)
	 * 说明:  三级级联 :  菜单  -  页面  -  按钮   (接口独立，不与这三者关联)	
	 * 
	 */
	function compose_tree(){
		
		var data1=[];  //接口资源
		var data2=[];  //菜单权限
		var data3=[];  //页面资源
		var data4=[];  //页面按钮
		
		util.post(ajax.power_query+"?param="+encodeURI("{pageSize:1}"),{excel_flag:'true'},function(result) {
			var aJson=eval("("+result+")");
			params=aJson.data;
	        if(aJson.code == "0" ){
	        	for(var i=0;i<aJson.data.length;i++){
	        		if(aJson.data[i].power_type=='001'){   //接口资源
	        			
	        			var checkFlag=false;		//是否被选中
	        			var interArr=vm.rel_role_power.inter;
	        			for(var k=0;k<interArr.length;k++){
	        				if(interArr[k]==aJson.data[i].power_code){
	        					checkFlag=true;
	        				}
	        			}
	        			
	        			data1[data1.length]={ id:aJson.data[i].ui_id, 
	        								  pId:0,zId:aJson.data[i].power_code,
	        								  typeId:aJson.data[i].power_type,
	        								  name:aJson.data[i].power_name, open:true, checked:checkFlag};
	        		}
	        		if(aJson.data[i].power_type=='002'){   //菜单资源
	        			
	        			var checkFlag=false;		//是否被选中
	        			var menuArr=vm.rel_role_power.menu;
	        			if(menuArr.hasOwnProperty(aJson.data[i].power_code)){
	        				checkFlag=true;
	        			}
	        			
	        			data2[data2.length]={ id:aJson.data[i].ui_id, 
	        								  pId:aJson.data[i].power_parent,zId:aJson.data[i].power_code,
	        								  typeId:aJson.data[i].power_type,
	        								  name:aJson.data[i].power_name, open:true,checked:checkFlag};
	        		}
	        		if(aJson.data[i].power_type=='004'){	//页面资源  
	        			
	        			var checkFlag=false;		//是否被选中
	        			var menuArr=vm.rel_role_power.menu;
	        			if(menuArr.hasOwnProperty(aJson.data[i].power_owner)){  //页面的菜单是否被选中
	        				var pageArr=menuArr[aJson.data[i].power_owner];
	        				if(pageArr.hasOwnProperty(aJson.data[i].power_code)){    //页面是否被选中
	        					checkFlag=true;
	        				}
	        			}
	        			
	        			data3[data3.length]={ id:aJson.data[i].ui_id,
	        								  pId:0,zId:aJson.data[i].power_code,
	        								  sId:aJson.data[i].power_owner,
	        								  bId:aJson.data[i].power_follower,
	        								  typeId:aJson.data[i].power_type,
	        								  name:aJson.data[i].power_name, open:true,checked:checkFlag};
	        		}
	        	}
	        	
	        	
	        	//生成树
	        	//1.接口
	    		var zTreeObj1 = $.fn.zTree.init($("#treeDemo1"),setting,data1);
	            zTreeObj1.expandAll(true);
	            
	            //2.菜单
	            var zTreeObj2 = $.fn.zTree.init($("#treeDemo2"),setting,data2);
	            zTreeObj2.expandAll(true);
	            if(data2.length!=0){
	            	zTreeObj2.selectNode(zTreeObj2.getNodeByParam('id',data2[0].id));//使第一个节点高亮 
	            }
	            
	            //3.页面
	            var data_help=data3;  //辅助数组
	            data3=[];
	            for(var i=0;i<data_help.length;i++){   //算法 筛选出第一个菜单的页面
	            	if(data_help[i].sId==data2[0].zId){
	            		data3[data3.length]=data_help[i];
	            	}
	            }
	            var zTreeObj3 = $.fn.zTree.init($("#treeDemo3"),setting,data3);
	            zTreeObj3.expandAll(true);
	            if(data3.length!=0){
	            	zTreeObj3.selectNode(zTreeObj3.getNodeByParam('id',data3[0].id));//使第一个节点高亮 
	            }
	            
	            //4.按钮
	            if(data3.length!=0){	//显示第一个页面的按钮
	            	var the_button=data3[0].bId.split(",");
	            	for(var i=0;i<the_button.length;i++){
	            		for(var j=0;j<button_array.length;j++){
	            			if(button_array[j].bt_type==the_button[i]){
	            				
	            				var checkFlag=false;		//是否被选中
	    	        			var menuArr=vm.rel_role_power.menu;
	    	        			if(menuArr.hasOwnProperty(data2[0].zId)){  //页面的菜单是否被选中
	    	        				var pageArr=menuArr[data2[0].zId];
	    	        				if(pageArr.hasOwnProperty(data3[0].zId)){    //页面是否被选中
	    	        					var btnStr=pageArr[data3[0].zId];
	    	        					if(btnStr!=''){
	    	        						var btnArr=btnStr.split(",");
	    	        						for(var k=0;k<btnArr.length;k++){
	    	        							if(btnArr[k]==button_array[j].bt_type){   //当前按钮初始选中
	    	        								checkFlag=true;
	    	        							}
	    	        						}
	    	        					}
	    	        				}
	    	        			}
	            				
	    	        			
	            				data4[i]={ id:button_array[j].bt_type,zId:data3[0].zId,name:button_array[j].bt_val,typeId:'005',open:true,checked:checkFlag}
	            				
	            			}
	            		}
	            	}
	            }
	            var zTreeObj4 = $.fn.zTree.init($("#treeDemo4"),setting,data4);
	            zTreeObj4.expandAll(true);
	        	
	        }
	        else{
	        	util.layerMsg(aJson.message);
	        }
		});
		
		
	}
	
	//搜集接口数据  
	function collect_inter(){
		var zTree=$.fn.zTree.getZTreeObj("treeDemo1");
		var checkedNodes=zTree.getCheckedNodes(true);
		var inter_arr=vm.rel_role_power.inter;
		inter_arr=[];
		if(checkedNodes.length!=0){
			for(var i=0;i<checkedNodes.length;i++){
				inter_arr[inter_arr.length]=checkedNodes[i].zId;
			}
		}	
		vm.rel_role_power.inter=inter_arr;
	}

	/* 传出模型的数据操作  */
	function submitData(){
		// 获得表单内全部元素
		collect_inter();
		var pre_rel=vm.rel_role_power;
		vm.rel_role_power=JSON.stringify(vm.rel_role_power);
		var stk=JSON.parse(JSON.stringify(vm.$model));
		vm.rel_role_power=pre_rel;  //保存数据模型 
		//alert(JSON.stringify(stk));
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
		
	//角色类型下拉生成 和 值锁定
	function create_select(id){
		util.post(ajax.dic_query,{parent_code:'role_type'},function(result) {
			var aJson=eval("("+result+")");
			var dic_type=[];
			if(aJson.code=="0"){
				dic_type[0]={type_id:'0',type_val:'--请选择--'};
				for(var i=0;i<aJson.data.length;i++){
					//{role_no:'001',role_type:'浏览类',role_da:[]},
					dic_type[dic_type.length]={'type_id':aJson.data[i].dic_code,'type_val':aJson.data[i].dic_name};
				}
				vm.type_select=dic_type;
				vm.role_type=id;
			}
			else{
				util.layerMsg(aJson.message);
			}
		});
		
		/*var dic_type=[
		              {type_id:'0',type_val:'--请选择--'},
		              {type_id:'001',type_val:'浏览类'},
		              {type_id:'002',type_val:'填报类'},
		              {type_id:'003',type_val:'操作类'},
		              {type_id:'004',type_val:'管理类'}
		              ];
		vm.type_select=dic_type;
		vm.role_type=id;*/
	}
	
	
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData       
	}
});