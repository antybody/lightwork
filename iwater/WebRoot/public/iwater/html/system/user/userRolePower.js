/**
 *  对于用户管理的逻辑处理
 *  code by diana 2017.2.4
 */
define(["util","avalon"],function (util,avalon) {
	
	var menuselect = require(['./core/javascripts/components/lightwork.menuselect']);
	var tablelist = require(['./core/javascripts/components/lightwork.table']);
	
	 // 查询条件接口
	var ajax={
		user_query:'./system/user/get',				/*查询该用户资料*/
		role_power:'./system/role/getRolePower',	/*查询角色 权限*/
	};
	
	// 创建菜单对应的模型库 
	var vm = avalon.define({
		$id:"user_role_show",
		user_name:'',
		user_code:'',
		arr:[],
		roleArr:[],
		changeShow:function(e){
			//现将全部变成正常色
			$("[name=role_type]").each(function(){
				$(this).removeAttr("class");
				$(this).attr("class","level0");
			});
			
			var target = e.target;  //获得dom对象
			//将选中变成选中色
			$(target).removeAttr("class");
			$(target).attr("class","level1");
			
			//显示相应的数据
			showPower(target.id);
		}
	});
	
	var vm_menu = avalon.define({
		$id:"user_role_menu",
		/*权限菜单 数据*/
		gridZhColumns: ['主键','序号','菜单名称','菜单编号'],
		gridEnColumns: ['ui_id','num','power_name','power_code'],
		gridData: []
	});
	
	var vm_inter = avalon.define({
		$id:"user_role_inter",
		/*权限菜单 数据*/
		inteZhColumns: ['主键','序号','接口名称','接口编号','接口地址'],
		inteEnColumns: ['ui_id','num','power_name','power_code','power_url'],
		inteData: []
	});
	
	//展示角色对应的权限
	function showPower(id){
		util.post(ajax.role_power,{role_code:id},function(result) {
	    	var aJson=eval("("+result+")");
	        if(aJson.code == "0" ){
	        	//alert(JSON.stringify(aJson));
	        	var skz=aJson.data;
	        	var menu_role=[];
	        	var inter_role=[];
	        	var num1=0;
	        	var num2=0;
	        	for(var i=0;i<skz.length;i++){
	        		if(skz[i].power_type=='002'){  //菜单
	        			num1++;
	        			skz[i].num=num1;
	        			menu_role[menu_role.length]=skz[i];
	        		}
	        		if(skz[i].power_type=='001'){  //接口
	        			num2++;
	        			skz[i].num=num2;
	        			inter_role[inter_role.length]=skz[i];
	        		}
	        	}
	        	vm_menu.gridData=menu_role;
	        	vm_inter.inteData=inter_role;
	        }
		    else
		      util.layerMsg(aJson.message);
		 });
		
		
	}
	
	
    // 从接口处获取具体的列表信息---
    function initPageData(){
    	vm_menu.gridData=[];
        vm_inter.inteData=[];
	    util.post(ajax.user_query,{ui_id:util.getHashStr("ui_id")},function(result) {
	    	var aJson=eval("("+result+")");
	        if(aJson.code == "0" ){
	          //显示用户的基本资料
	          vm.user_name=aJson.data.user_name;
	          vm.user_code=aJson.data.user_code;
	          
	          vm.roleArr=[];
	          //用户角色
	          var role_array=eval("("+aJson.data.role_id+")");
	          var stz='';
	          for(var i=0;i<role_array.length;i++){
	        	  if(i==0){
	        		  stz='<a href="javascript:void(0)" class="level1" name="role_type" id="'+role_array[i].role_code+'">'+role_array[i].role_zhname+'</a> ';
	        		  showPower(role_array[i].role_code);  //初始显示角色权限
	        	  }
	        	  else{
	        		  stz='<a href="javascript:void(0)" class="level0" name="role_type" id="'+role_array[i].role_code+'">'+role_array[i].role_zhname+'</a> ';
	        	  }
	        	  
	        	  vm.roleArr.push(stz);
	          }
	          
	          if(vm.roleArr.length==0){
	        	  $("#roleShow").html("暂无角色");
	        	  vm.gridData=[];
		          vm.inteData=[];
	          }
	          
	          //$("#line_type").html(stz);
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