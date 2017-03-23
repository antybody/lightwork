/**
 *  新增菜单的跳转
 */

define(['avalon','util'],function(avalon,util){
	// 获取传入的参数
	var ajax ={
		query:'./system/bsManager/query',    /*一般的查询*/
		update_submit:'./system/bsManager/update',/*相当于update的提交事件*/
	};
	
	/* 创建添加或者删除的模型库*/
	var vm = avalon.define({
		$id:'bs_form',
		pic_local:'',//图片上传路径（本地）
		pic_remote:'',//图片上传路径（远程）
		word_local:'',//文档上传路径（本地）
		word_remote:'',//文档上传路径（远程）
		download_local:'',//资料下载路径（本地）
		download_remote:'',//资料下载路径（远程）
		db_name:'',//数据库配置文件
		redis_name:'',//缓存数据库配置文件
		admin_login:'',//管理员界面
		unadmin_login:'',//非管理员界面
		system_querypath:'',//查询条件路径（system）
		business_querypath:'',//查询条件路径（业务应用）
		save:function(){
			submitData();
		},
		reset:function(){
			set_form_data();
		},
		clickCheckBox:function(e){
			var tt=e.srcElement;
			//var aa='';
			if($(tt).is(':checked')){
				$(tt).prev().attr("disabled",false);
				$(tt).next().val($(tt).prev().val()); 
			}else{
				$(tt).prev().attr("disabled",true);
				$(tt).prev().val($(tt).next().val());
			}
			//util.layerMsg(aa);
		}
	});
	
	avalon.scan(document.body);
	//初始化  表单数据
	function set_form_data(){
		vm.pic_local='',//图片上传路径（本地）
		vm.pic_remote='',//图片上传路径（远程）
		vm.word_local='',//文档上传路径（本地）
		vm.word_remote='',//文档上传路径（远程）
		vm.download_local='',//资料下载路径（本地）
		vm.download_remote='',//资料下载路径（远程）
		vm.db_name='',//数据库配置文件
		vm.redis_name='',//缓存数据库配置文件
		vm.admin_login='',//管理员界面
		vm.unadmin_login='',//非管理员界面
		vm.system_querypath='',//查询条件路径（system）
		vm.business_querypath='',//查询条件路径（业务应用）
  		//修改前赋值
  		util.post(ajax.query,'',function(result) {
			 var aJson=eval("("+result+")");  
			 if(aJson.code=="0"){
				 var json_data=aJson.data;
				 for(key in json_data){
					 if(typeof json_data[key] != undefined && typeof vm[key] != "undefined"){
						 vm[key]=json_data[key];
					 }
				 }
			 }
			 else{
				 util.layerMsg(result.message);
			 }
  		});
  		  
	}
	
	/* 传出模型的数据操作  */
	function submitData(){
		// 获得表单内全部元素
		var stk=JSON.parse(JSON.stringify(vm.$model));
		avalon.log(JSON.parse(JSON.stringify(vm.$model)));
		var tip='修改成功';
		var url=ajax.update_submit;
		 util.post(url,stk,function(result) {
			 //这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			 var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 util.layerMsg(tip);
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
		
	}	
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:set_form_data       
	}
});