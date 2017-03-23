/**
 *  新增菜单的跳转
 */

define(['avalon','util','hashmap','cron1'],function(avalon,util){
	
	// 获取页面的类型
	var page_type = util.getHashStr("type");
	// 获取传入的参数
	var params = {};
	
	var ajax ={
		add_query:'',/*相当于查询*/
		add_submit:'./system/cacheManager/create', /*相当于add的提交事件*/
		query:'',
		update_query:'./system/cacheManager/get', /*相当于查询*/
		update_submit:'./system/cacheManager/update',/*相当于update的提交事件*/
		submitUrl:''/*form提交的地址*/
	};
	 avalon.validators.checkSel = {   //自定义的验证规则
             message: '必选',
             get: function (value, field, next) {
                //想知道它们三个参数是什么,可以console.log(value, field,next)
                 var ok = ((Number(value) != 0));
                 next(ok);
                 return value;
             }
     };
	/* 创建添加或者删除的模型库*/
	var vm = avalon.define({
		$id:'cache_form',
		reset_flag:false,  /*重置操作标志位   完成判断后及时置为false*/
		cache_id:'',     /*缓存id*/
		menu_type:'',     /*新增缓存 or 修改缓存*/
		cache_menu:'', /*菜单*/
		cache_package:'', /*包名称*/
		cache_role:'rolename',   /*角色名*/
		cache_idname:'', /*方法名*/
		cache_type:'',    /*缓存类型*/
		cache_rate:'',   /*刷新频率*/
		rate_name:'',   /*刷新频率*/
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
                    avalon.log('有表单没有通过');
                } else {
                	avalon.log('通过验证');
                	//$("form").serialize()
                	submitData();
                }
            }
		},
		save:function(){
			this.validate.onValidateAll();
		},
		_SetTime:function(e){
			util._setQurtz(e);
			//$("input[name='rate_name']").cronGen();			
		},
		reset:function(){
			vm.reset_flag=true; 
			initPageData();
		}		
	});
	
	//avalon.scan(document.body);
	/* 创建添加或者删除的模型库*/
	
	
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
  	   // 更新数据后，刷新视图
   	   //avalon.scan(document.body); 
   	  
	}
	//初始化  表单数据
	function set_form_data(){
		vm.cache_menu=''; /*菜单*/
		vm.cache_package=''; /*包名称*/
		vm.cache_role='rolename';   /*角色名*/
		vm.cache_idname=''; /*方法名*/
		vm.cache_type='';    /*缓存类型*/
		vm.cache_rate='';   /*刷新频率*/
		vm.rate_name='';   /*刷新频率*/
	   // 给特定的单元格添加事件
		//$("input[name='rate_name']").cronGen();
  	   if(page_type=="add"){  
  		  vm.menu_type='新增缓存';
  		  vm.reset_flag=false;      					//将标志及时还原
  	   }
  	   if(page_type=="upd"){  
  		  vm.menu_type='编辑缓存';
  		  var stk='';
  		  if(vm.reset_flag){  		                   //修改页面的重置
  			stk={'cache_id':vm.cache_id};
  			vm.reset_flag=false;      					//将标志及时还原
  		  }else{				  		                   //修改页面的初始化	
  			stk={'cache_id':util.getHashStr("cache_id")};
  		  }   
  		  
  		  //修改前赋值
  		  util.post(ajax.update_query,stk,function(result) {
			 var aJson=eval("("+result+")");  
			 if(aJson.code=="0"){
				 var json_data=aJson.data[0];
				 for(key in json_data){
					 if(typeof json_data[key] != undefined && typeof vm[key] != "undefined"){
						 vm[key]=json_data[key];
					 }
				 }
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
  		  });
  		  
  	   }
	}
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData       
	}
});

