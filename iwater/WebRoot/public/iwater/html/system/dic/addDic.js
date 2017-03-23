/**
 *  新增菜单的跳转
 */

define(['avalon','util'],function(avalon,util){
	// 获取页面的类型
	var page_type = util.getHashStr("type");
	var page_dic_id = util.getHashStr("dic_id");
	// 获取传入的参数
	var params = {};
	
	var ajax ={
		check_repeat:'./system/dic/queryForExist/',//*查询是否存在*/
		add_submit:'./system/dic/insertForNew/',//*add*/
		update_load:'./system/dic/loadBeforeUpdate/',//*编辑前读取原信息*/
		parent_check:'./system/dic/checkNodeForParent/',//*查询父节点是否存在*/
		update_submit:'./system/dic/update/'//*update*/
	};
	
	
	avalon.validators.checkDicCode = {//自定义的验证规则-节点是否存在
		message: '已存在',
		get: function (value, field, next) {
			var stk = {"dic_code":value,"checkType":page_type,"dic_id":vm.dic_id};
			util.post(ajax.check_repeat,stk,function(result){
				var ok = false;
				var res = JSON.parse(result);
				var resData = res.data;
				if(resData == 0){
					ok = true;
				}else{
					ok = false;
				}
				next(ok);
				return value;
			});
		}
	};
	
	
	avalon.validators.checkParent = {//自定义的验证规则-验证父节点是否与当前节点相同
		message: '父节点编码和当前节点编码相同',
		get: function (value, field, next){
			var ok = false;
			var dicCode = $("input[name='dic_code']").val();
			if(dicCode == value){
				ok = false;
			}else{
				ok = true;
			}
			next(ok);
			return value;
		}
	};
	
	
	avalon.validators.checkParentExist = {//自定义的验证规则-验证父节点是否存在
		message: '节点不存在',
		get: function (value, field, next){
			util.post(ajax.parent_check,{"dic_code":value},function(result){
				var ok = false;
				var res = JSON.parse(result);
				var resData = res.data;
				if(resData == 0){
					ok = false;
				}else{
					ok = true;
				}
				next(ok);
				return value;
			});
		}
	};
	
	
	/* 创建添加或者删除的模型库*/
	var vm = avalon.define({
		$id:'dic_form',
		reset_flag:false,/*重置操作标志位-完成判断后及时置为false*/
		dic_id:'',/*主键*/
		opt_type:'',/*新增or修改*/
		dic_code:'',//节点编码
		dic_name:'',//节点中文
		parent_code:'',//父节点编码
		dic_desc:'',//节点描述
		is_usable:"1",//是否启用
		seq:'',//排序
		isdel:false,/*是否删除或者使用状态*/
		checkSel:'',
		checkDicCode:'',
		validate:{//校验方法
//			validateInKeyup: false,//禁用按键弹起事件
			validateInBlur: false,//禁用失焦事件
			onError: function (reasons) {//单个验证失败时触发
				reasons.forEach(function (reason) {
					//进行提示
					var a=document.getElementsByName(reason.element.name)[0].parentNode.lastChild;
					a.innerHTML=reason.getMessage();
					console.log(reason.getMessage());
				});
			},
			onSuccess:function(reasons, event){// 针对表单内单个的元素
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
	
	//avalon.scan(document.body);
	/* 创建添加或者删除的模型库*/
	
	/* 获取数据对模型操作，表单初始化 */
	var  initPageData =function(){
		//初始化'page_type'  全局依赖此变量判断
		var type_flag=util.getHashStr("type");
		if(type_flag == "upd"){
			$("input[name='dic_code']").attr("disable",true);
		}
		var dic_id = util.getHashStr("dic_id");
		var curhref = "#!/dic/addDic?type="+type_flag;
		if(type_flag!='' &type_flag!=undefined){
			page_type=type_flag;
			if(dic_id!='' && dic_id!=undefined){
				page_dic_id = util.getHashStr("dic_id");
				vm.dic_id = util.getHashStr("dic_id");
				curhref = curhref + "&dic_id="+page_dic_id;
			}
		}
		$(".btn_fun.btn_user").attr({"href":curhref});
		$(".btn_fun.bg_btngray").attr({"href":curhref});
		set_form_data();
	}
	
	//初始化-表单数据
	function set_form_data(){
		//vm.isusable=false;/*是否删除或者使用状态*/
		vm.dic_code='';//节点编码
		vm.dic_name='';//节点中文
		vm.parent_code='';//父节点编码
		vm.dic_desc='';//节点描述
		vm.is_usable='1';//是否启用
		vm.seq='';//排序
		if(page_type=="add"){
			vm.opt_type='新增字典项';
			vm.reset_flag=false;//将标志及时还原
		}
		if(page_type=="upd"){
			vm.opt_type='编辑字典项';
			var stk='';
			if(vm.reset_flag){//修改页面的重置
				stk={'dic_id':vm.dic_id};
				vm.reset_flag=false;//将标志及时还原
			}else{//修改页面的初始化	
				stk={'dic_id':util.getHashStr("dic_id")};
			}
			//修改前赋值
			util.post(ajax.update_load,stk,function(result) {
				var aJson=eval("("+result+")");  
				if(aJson.code=="0"){
					var json_data=aJson.data;
					for(key in json_data){
						if(typeof vm[key] != "undefined" 
								&& key !="parent_menu" && key !="item_prefix"){
							vm[key]=json_data[key];
						}
					}
				}else{
					util.layerMsg(aJson.message);
				}
			});
		}
	}



	/* 传出模型的数据操作 */
	function submitData(){
		// 获得表单内全部元素
		var stk=JSON.parse(JSON.stringify(vm.$model));
		/**********硬写值进stk，不合规范**********/
		avalon.log(JSON.parse(JSON.stringify(vm.$model)));
		var tip='';
		var url='';
		if(page_type=='add'){
			tip='添加成功';
			url=ajax.add_submit;
		}
		if(page_type=='upd'){
			tip='修改成功';
//			stk.set("dic_id",vm.dic_id);
			url=ajax.update_submit;
		}
		util.post(url,stk,function(result) {
			//这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			var aJson=eval("("+result+")");
			if(aJson.code=="0"){
				util.layerMsg(tip);
				if(page_type=='add'){
					initPageData();//将表单初始化
				}
			}else{
				util.layerMsg(aJson.message);
			}
		});
		
	}
	
	
	
	
	
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData
	}
});