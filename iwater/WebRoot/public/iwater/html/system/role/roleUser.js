/**
 *  对于用户管理的逻辑处理
 *  code by diana 2017.2.4
 */
define(["util","avalon"],function (util,avalon) {
	
	var menuselect = require(['./core/javascripts/components/lightwork.menuselect']);
	var tablelist = require(['./core/javascripts/components/lightwork.table']);
	
	 // 查询条件接口
	var ajax={
		role_query:'./system/role/get',				/*查询该用户资料*/
		role_user_query:'./system/role/getRoleUser',	/*查询角色 权限*/
	};
	
	// 创建菜单对应的模型库 
	var vm = avalon.define({
		$id:"role_user",
		user_name:'',
		user_code:'',
		role_name:'',
		role_code:'',
		gridZhColumns: ['主键','姓名','账号','手机','邮箱'],
		gridEnColumns: ['ui_id','user_name','user_code','user_tel',"user_mail"],
		gridData:[]
	});
	
	
    // 从接口处获取具体的列表信息---
    function initPageData(){
	    util.post(ajax.role_query,{ui_id:util.getHashStr("ui_id")},function(result) {
	    	var aJson=eval("("+result+")");
	        if(aJson.code == "0" ){
	          //显示用户的基本资料
	          vm.role_name=aJson.data.role_zhname;
	          vm.role_code=aJson.data.role_code;
	          getRoleUser(vm.role_code);
	      	  avalon.scan(document.body);        	  
		    }
		    else
		      util.layerMsg(aJson.message);
		 });
    }
    
    function getRoleUser(role_code){
    	util.post(ajax.role_user_query,{"role_code":role_code},function(result) {
	    	var aJson=eval("("+result+")");
	        if(aJson.code == "0" ){
	          vm.gridData = aJson.data;
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