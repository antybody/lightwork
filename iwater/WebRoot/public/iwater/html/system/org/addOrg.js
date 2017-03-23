/**
 *  新增菜单的跳转
 */

define(['avalon','util','chooseSelect'],function(avalon,util){
	// 获取页面的类型
	var page_type = util.getHashStr("type");
	// 获取传入的参数
	var params = {};
	
	var ajax ={
		//add_query:'./system/menu/parentMenu',	/*相当于查询*/
		add_submit:'./system/organization/create', /*相当于add的提交事件*/
		query:'./system/organization/orgItem',    /*一般的查询*/
		update_query:'./system/organization/get', /*相当于查询*/
		update_submit:'./system/organization/update',/*相当于update的提交事件*/
		check_only:'./system/organization/checkOnly'  /*唯一性校验*/
		//del:''
	};
	
	 avalon.validators.sysOrg_addOrg_code = {   //自定义的验证规则
			 message: '请填写唯一的菜单代码',
	            get: function (value, field, next) {
	                if(value==''){
	                	next(false);
	                }
	                else{    //验证唯一性
	                	var stk={};
	                	if(page_type=='upd'){
	                		stk={'ui_id':vm.ui_id,"org_code":vm.org_code};
	                	}
	                	if(page_type=='add'){
	                		stk={'ui_id':'',"org_code":vm.org_code};
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
		$id:'org_form',
		reset_flag:false,  /*重置操作标志位   完成判断后及时置为false*/
		ui_id:'',      /*主键*/
		org_type:'',     /*新增组织机构 or 修改组织机构*/
		org_zhname:'',  /*组织机构中文名称*/
		org_enname:'',  /*组织机构英文名称*/
		org_url:'', /*菜单地址*/
		org_parent:'', /*父节点id*/
		org_project:'',   /*父节点*/
		org_code:'',  /*机构代码*/
		org_shortname:'', /*机构缩写*/
		org_sort:'',    /*机构排序*/
		sysOrg_addOrg_code:'',   /*自定义的校验方法*/
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
		 vm.org_zhname=''; /*组织机构中文名称*/
		 vm.org_enname=''; /*组织机构英文名称*/
		 vm.org_url='';   /*组织机构地址*/
		 vm.org_parent='';   /*父节点id*/
		 vm.org_project='';    /*父节点*/
		 vm.org_code='';  /*机构代码*/
		 vm.org_shortname=''; /*机构缩写*/
		 vm.org_sort='';/*机构排序*/
	   $('input[name=org_code]').removeAttr("disabled");//去除input元素的readonly属性
  	   if(page_type=="add"){  
  		  vm.org_type='新增组织机构';
  		  sel_project(0);          //'所属项目'下拉生成
  		  //sel_menu_parent(0,0);    //'父菜单'下拉生成
  		  vm.reset_flag=false;      					//将标志及时还原
  	   }
  	   if(page_type=="upd"){  
  		  vm.org_type='编辑组织机构';
  		$('input[name=org_code]').attr("disabled","disabled");//将input元素设置为不可编辑
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
							 && key !="org_parent"){
						 vm[key]=json_data[key];
					 }
				 }
				 if(json_data.org_parent==''){
					 sel_project(0); 
				 }else{
					 sel_project(json_data.org_parent); 
				 }
				                                                //'所属项目'下拉生成
				 //sel_menu_parent(json_data.item_prefix,json_data.parent_menu);  //'父菜单' 下拉生成并锁定值
			 }
			 else{
				 util.layerMsg(result.message);
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
		var stk=JSON.parse(JSON.stringify(vm.$model));
		stk.org_parent=jQuery("#org_parent").val();
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
	function sel_project(the_org_parent){
		util.getJson(ajax.query,{},function(result){
	          var aJson= result;  
	          if(aJson.code == "0" ){
	        	  var data=aJson.data;
	        	  var dataArray=[];    //组合形成数组
	        	  var i=1;
	        	  var optionStr='<option value="0">--无父机构--</option>';
	        	  //dataArray[0]={'project_name':'','project_value':'0'};
	        	  for(var obj in data){
	        		  optionStr+='<option value="'+data[obj].ui_id+'">'+data[obj].org_zhname+'</option>';
	        		  //i++;
	        	  }
	        	  //vm.org_project=dataArray;
	        	  //vm.org_parent=the_org_parent;
	        	  
	        	  // 更新数据后，刷新视图
	          	  //avalon.scan(document.body);
	        	  jQuery("#org_parent").empty();
	        	  jQuery("#org_parent").append(optionStr);
	          	  jQuery("#org_parent").val(the_org_parent)
	          	  jQuery("#org_parent").chosen(); 
	          	  
		       }
		       else
		    	  util.layerMsg(aJson.message);
		});
	}
	jQuery("#org_parent").change(function(){  
        var checkText=jQuery("#financialIds").find("option:selected").text(); //获取Select选择的Text   
        var checkValue=jQuery("#financialIds").val(); //获取Select选择的Value  
        var checkTile=jQuery("#financialIds").find("option:selected").attr("title"); 
		alert(checkValue+'title'+checkTile)
			//获取Select选择的title   
            //window.location.reload();//刷新当前页面.  
})  
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData       
	}
});