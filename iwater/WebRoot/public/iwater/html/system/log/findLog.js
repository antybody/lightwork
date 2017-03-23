/**
 *  新增菜单的跳转
 */

define(['avalon','util','ztree',"css!core/css/import/zTreeStyle"],function(avalon,util){
	// 获取页面的类型
	var page_type = util.getHashStr("type");
	// 获取传入的参数
	var params = {};
	
	var ajax ={
		find_query:'./system/log/get', /*相当于查询*/
	};

	
	/* 创建添加或者删除的模型库*/
	var vm = avalon.define({
		$id:'log_form',
		reset_flag:false,  /*重置操作标志位   完成判断后及时置为false*/
		ui_id:'',      /*主键*/
		log_desc:'',     /*日志用途*/
		add_date:'',  /*添加时间*/
		log_user:'',  /*操作人名称*/
		log_IP:'', /*操作IP*/
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
		 vm.log_desc=''; /*日志用途*/
		 vm.add_date=''; /*添加时间*/
		 vm.log_user='';   /*操作人*/
		 vm.log_IP='';    /*操作IP*/
		 vm.log_method='';    /*访问方法*/
		 vm.log_type='';    /*访问方法*/
	   	  stk={'ui_id':util.getHashStr("ui_id")};
  		  //修改前赋值
  		  util.post(ajax.find_query,stk,function(result) {
			 var aJson=eval("("+result+")");  
			 if(aJson.code=="0"){
				 var json_data=aJson.data;
				 for(key in json_data){
					 if(key=="log_type"){
						 if(json_data[key]=="1"){
							 $("#"+key).html("service");
						 }else{
							 $("#"+key).html("controller");
						 }
					 }else{
						 $("#"+key).html(json_data[key]);
					 }
					 
					 /*alert(json_data[key]);*/
					/* if(typeof json_data[key] != undefined){
						 vm[key]=json_data[key];
					 }
					 if(key=='parent_menu'){
						 vm.parent_menu[0]=json_data[key];
					 }*/
				 }
			 }
			 else{
				 util.layerMsg(result.message);
			 }
  		  });
  		  
  	  /* }*/
	}
	
	
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData       
	}
});