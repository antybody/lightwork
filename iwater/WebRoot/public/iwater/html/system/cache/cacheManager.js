/**
 *  对于加载缓存管理的逻辑处理
 *  code by whb 2017.2.9
 */
define(["util","avalon"],function (util,avalon) {
	
	var menuselect = require(['./core/javascripts/components/lightwork.menuselect']);
	var tablelist = require(['./core/javascripts/components/lightwork.table']);
	var displayRow = 10;  // 设置列表显示的默认显示的行数
	var pager = require(['./core/javascripts/components/lightwork.pager']);
	 // 查询条件接口
	var ajax={
		menu_query:'./public/iwater/config/cache_query.json',
		table_query:'./system/cacheManager/list?param='+encodeURI('{"pageNo":"1","pageSize":"'+displayRow+'"}'),
		download_query:'./system/cacheManager/download',
		menu_del:'./system/cacheManager/del',
		cache_begin:'./system/cacheManager/addTimer',
		cache_end:'./system/cacheManager/removeTimer',
		cache_refreshNow:'./system/cacheManager/refreshNow'
	};
	
	// 创建菜单对应的模型库
	var vm = avalon.define({
		$id:"cache_query_cache",
		menu:[],
		num:3,
		totalCount:0,
		query_item:'',    /*查询条件  默认为空  代表全查*/
		queryList:function(e){ // 查询的处理逻辑
			vm.query_item=get_query_list();
			vm_page.currentPage=1;
			ajax.table_query='./system/cacheManager/list?param='+encodeURI('{"pageNo":1,"pageSize":"'+displayRow+'"}');
			getGridData();  //重新初始化页面
			//alert(JSON.stringify(stk));
		},
		downloadCache:function(){  //自定义方法  导出菜单
			var the_item=JSON.stringify(get_query_list());
			var url=ajax.download_query;
			//创建form
			var form=$('<form></form>');
			//设置表单属性
			form.attr('action',url);
			form.attr('method','post');
			form.attr('target','_self');
			
			//创建input
			var my_input=$('<input type="text" name="param"/>');
			my_input.attr('value',the_item);
			//附加到form
			form.append(my_input);
			form.submit();
			//注意return  false取消链接的默认动作
			return false;
	    },
		clearAll:function(e){  // 清空选项
			// 对check框的处理
			vm.menu.forEach(function (el) {
				el.subQuery.forEach(function (elm) {
					if(elm.hasOwnProperty("ischecked"))
						 elm.ischecked = "";
	            });
            });
			// 对input 输入框的处理
			$("input[type='text']").val("");
		}
	});
	
	// 创建列表对应的模型库
	var vm_table = avalon.define({
		$id:'cache_list_cache',		
		gridZhColumns: ['主键','菜单名称','对应包名称','用户角色名称','方法名称','类型','刷新频率','状态','最后刷新时间'],
		gridEnColumns: ['cache_id','cache_menu','cache_package','cache_role','cache_idname','cache_type','rate_name','cache_state','cache_refreshtime'],
		isHandle:true,
		keyColumn:'id',
		handleData:[{icon:'btn_re',text:'修改',key:'update'},{icon:'btn_del',text:'删除',key:'del'}
		,{icon:'btn_refreshNow',text:'刷新',key:'refreshNow'}
		,{icon:'btn_beginRefresh',text:'启动',key:'beginRefresh'}
		,{icon:'btn_endRefresh',text:'停止',key:'endRefresh'}],
		gridData: [],
        updateEvent:function(stk){
        	window.location.href="#!/cache/addCache?type=upd&cache_id="+stk;
        },
        delEvent:function(stk){
        	var flag=confirm("确定删除吗?");
        	if(flag){
        		data_del(stk);
        	}
        },
        refreshNowEvent:function(stk){
        	cache_refreshNow(stk);
        },
        beginRefreshEvent:function(stk){
        	cache_begin(stk);
        },
        endRefreshEvent:function(stk){
        	cache_end(stk);
        },
        cbProxy:function(e,k,index){ 
        	avalon.log(k);
        	var stk=$("td[name='cache_id']")[index].innerHTML;
        	var sta=$("td[name='cache_state']")[index].innerHTML;
        	var stt=$("td[name='cache_type']")[index].innerHTML;
        	var key=k.$model.key;
        	if(key=="update"){
        		if(sta=='正常运行'){
        			util.layerMsg("请先删除定时任务再编辑!");
        		}else{
        			vm_table.updateEvent(stk);
        		}
        	}else if(key=="del"){
        		if(sta=='正常运行'){
        			util.layerMsg("请先删除定时任务再操作!");
        		}else{
        			vm_table.delEvent(stk);
        		}
        	}else if(key=="refreshNow"){
        		if(sta=='不存在'){
        			util.layerMsg("不存在该缓存!");
        		}else if(sta=='未启动'||sta=='正常运行'){
        			vm_table.refreshNowEvent(stk);
        		}
        	}else if(key=="beginRefresh"){
        		if(sta=='不存在'){
        			util.layerMsg("不存在该缓存!");
        		}else if(sta=='未启动'){
        			vm_table.beginRefreshEvent(stk);
        		}else if(sta=='正常运行'){
        			util.layerMsg("该缓存已启动刷新服务!");
        		}
        	}else if(key=="endRefresh"){
        		if(sta=='不存在'){
        			util.layerMsg("不存在该缓存!");
        		}else if(sta=='正常运行'){
        			vm_table.endRefreshEvent(stk);
        		}else if(sta=='未启动'){
        			util.layerMsg("该缓存未启动刷新服务!");
        		}
        	}
        	//this.delEvent();  $("td[name='id']")[index]
        }
	});
	//进行删除
	function data_del(cache_id){
		var stk={'cache_id':cache_id};
		util.post(ajax.menu_del,stk,function(result) {
			 //这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			 var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 util.layerMsg("删除成功");
				 getGridData();    //将表单初始化
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
	}
	//缓存刷新服务启动
	function cache_begin(cache_id){
		var stk={'cache_id':cache_id};
		util.post(ajax.cache_begin,stk,function(result) {
			 //这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			 var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 util.layerMsg("启动成功");
				 getGridData();    //将表单初始化
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
	}
	//缓存定时刷新删除
	function cache_end(cache_id){
		var stk={'cache_id':cache_id};
		util.post(ajax.cache_end,stk,function(result) {
			 //这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			 var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 util.layerMsg("停止成功");
				 getGridData();    //将表单初始化
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
	}
	//立即刷新一次
	function cache_refreshNow(cache_id){
		var stk={'cache_id':cache_id};
		util.post(ajax.cache_refreshNow,stk,function(result) {
			 //这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			 var aJson=eval("("+result+")");
			 if(aJson.code=="0"){
				 util.layerMsg("刷新成功");
				 getGridData();    //将表单初始化
			 }
			 else{
				 util.layerMsg(aJson.message);
			 }
		 });
	}

	//搜集查询条件
	function get_query_list(){
		var stk=new Object();
		var text_val=$("input[type='text']");
		var checkbox_val=$("input[type='checkbox']");
		//$(this).attr("data-key")   jquery对象 data-key 属性值
		$(text_val).each(function(){
			stk[$(this).attr("data-key")]=$(this).val();
		});
		$(checkbox_val).each(function(){
			if($(this).attr("checked")){
				if(stk.hasOwnProperty($(this).attr("data-key"))){
					//这里将复选框的值拼接起来  ("1,2,3")
					var add_str=stk[$(this).attr("data-key")];
					add_str=add_str+'-'+$(this).attr("data-val");
					stk[$(this).attr("data-key")]=add_str;
				}
				else{
					stk[$(this).attr("data-key")]=$(this).attr("data-val");
				}
			}
		});
		return stk;
	}
	 // 从接口处获取具体的查询条件信息
    util.getJson(ajax.menu_query,{},function(result) {
          // 根据具体情况修改错误代码
          var aJson= result[0];          
          if(aJson.code == "0" ){
        	  vm.menu = aJson.data;
        	  // 更新数据后，刷新视图
        	  //avalon.scan(document.body);        	  
	       }
	       else
	    	  util.layerMsg(aJson.msg);
	 }); 
	// 创建分页所需的的模型库
	var vm_page = avalon.define({		
		$id:'cache_pager_cache',
		showPages:0,   //显示的页标  
		totalPages:0,	 //初始总页数
		currentPage:1,  //当前页数
		onPageClick:function(e,cur){
			//alert(util.getHashStr("page"));  //通过得到url上键为page的值 来得到新的页码
			//重新修改请求路径  并进行请求
			ajax.table_query='./system/cacheManager/list?param='+encodeURI('{"pageNo":"'+cur+'","pageSize":"'+displayRow+'"}');
			vm_page.currentPage=cur;
			getGridData();
		}
	});
	 // 从接口处获取具体的列表信息---
    function getGridData(){
    	
	    util.post(ajax.table_query,vm.query_item,function(result) {
	    	var aJson=eval("("+result+")");
	        if(aJson.code == "0" ){
	          //下面的代码是针对具体的分页显示情况作调整
	          if(vm_page.showPages>aJson.pageInfo.totalPage){  //当实际页数少于当前设置页数
	        	vm_page.showPages=aJson.pageInfo.totalPage;
	          }
	          if(vm_page.showPages<aJson.pageInfo.totalPage){  //当实际页数多于当前页数时
	        	  if(aJson.pageInfo.totalPage<=5){             //实际页数小于等于5
	        		  vm_page.showPages=aJson.pageInfo.totalPage;
	        	  }
	        	  else{							               //实际页数大于5
	        		  vm_page.showPages=5;
	        	  }
	          }
	          vm_page.totalPages=aJson.pageInfo.totalPage;   //修改页面显示的总页数
	          vm.totalCount=aJson.pageInfo.totalCount;  	 //修改页面显示的总记录数
	      	  vm_table.gridData = aJson.data;
	      	  // 更新数据后，刷新视图
	      	  //avalon.scan(document.body);        	  
		    }
		    else
		      util.layerMsg(aJson.message);
		 });
    }
    
    //初始化显示页面
    getGridData();
});