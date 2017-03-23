/*
 *	字典管理
 *	gaoh 2017.1.22
 */

define(["util","avalon","ztree","css!core/css/import/zTreeStyle"],function (util,avalon) {
	
	//查询条件js
	var menuselect = require(['./core/javascripts/components/lightwork.menuselect']);
	//列表控件
	var tablelist = require(['./core/javascripts/components/lightwork.table']);
	// 设置列表显示的默认显示的行数
	var displayRow = 5;
	//翻页控件
	var pager = require(['./core/javascripts/components/lightwork.pager']);
	//后台访问路径
	var ajax = {
		dic_query:'./public/iwater/config/dic_params.json',
		initLoad:'./system/dic/init/',
		forNextLevel:'./system/dic/nextLevelNode/',
		dic_del:'./system/dic/deleteNode/',
		dic_download:'./system/dic/download/',
		updateForAble:'./system/dic/updateIsable/'
	};
	//中间数组，临时存放查询结果集
	var query_data = [];


	//定义查询区域：参数类型及事件
	var vm = avalon.define({
		$id: "query_param_dic",
		param:[],
		num:2,
		totalCount:0,
		query_item:'',
		ztree_data:[],
		checkedNodeId:null,//已选中的树节点
		param_query:function(){
			vm.query_item = util.get_query_list();
			queryForZtree(vm.query_item);
		},
		download:function(){//自定义方法-导出
			var ztree = $.fn.zTree.getZTreeObj("treeDemo");
			var treeNodes = ztree.getSelectedNodes();
			var stk = {
				'dic_code':treeNodes[0].id,
				'pageSize':displayRow,
				'pageNo':vm_page.currentPage,
				'zhColumns':JSON.stringify(vm_table.gridZhColumns),
				'enColumns':JSON.stringify(vm_table.gridEnColumns)
			};
			/*******form提交实现excel导出********/
			var the_item = encodeURI(JSON.stringify(stk));
			var url = ajax.dic_download;
			//创建form
			var form=$('<form></form>');
			//设置表单属性
			form.attr('action',url);
			form.attr('method','post');
			form.attr('target','_self');
			//创建input
			var my_input=$('<input type="text" name="params"/>');
			my_input.attr('value',the_item);
			//附加到form
			form.append(my_input);
			form.submit();
			//注意return  false取消链接的默认动作
			return false;
			/*******ajax提交实现excel导出********/
//			util.post(ajax.dic_download,stk,function(result){
//				var res = JSON.parse(result);
//				var fileId = JSON.parse(res.data).fileId;
//				window.location.href = "./public/iwater/html/system/dic/dicDownLoad.jsp?fileId="+fileId;
//			});
			/*******ajax提交实现excel导出********/
		},
		clearAll:function(e){// 清空选项
			//针对特定的checkbox
			$("input[data-key='parent_menu']").each(function() { 
				$(this).attr("checked", false); 
			}); 
			// 对input 输入框的处理
			$("input[type='text']").val("");
		}
	});
	
	
	//定义grid
	var vm_table = avalon.define({
		$id:'table_list',		
		gridZhColumns: ['主键','字典项编码','字典项名称','父节点','是否启用'],
		gridEnColumns: ['dic_id','dic_code','dic_name','parent_code','is_usable'],
		isHandle:true,
		keyColumn:'dic_id',
		handleData:[
			{icon:'btn_enable',text:'启用',key:'enable'},
			{icon:'btn_update',text:'修改',key:'update'},
			{icon:'btn_disable',text:'禁用',key:'disable'},
			{icon:'btn_del',text:'删除',key:'del'}
		],
		gridData: [],
		updateEvent:function(stk){
			//修改url地址实现页面的跳转
			window.location.href="#!/dic/addDic?type=upd&dic_id="+stk;
		},
		enableEvent:function(dic_id){
			var isUsable = "1";
			setStatus(dic_id,isUsable);
		},
		disableEvent:function(dic_id){
			var isUsable = "0";
			setStatus(dic_id,isUsable)
		},
		delEvent:function(dic_id){
			util.layerConfirm("确定删除吗？",function(){
				data_del(dic_id);
			});
		},
		cbProxy:function(e,k,index){ 
			avalon.log(k);
			//获得主键
			var stk=$("td[name='dic_id']")[index].innerHTML;
			if($(e.target).text()=='修改'){
				vm_table.updateEvent(stk);
			}
			if($(e.target).text()=='启用'){
				vm_table.enableEvent(stk);
			}
			if($(e.target).text()=='禁用'){
				vm_table.disableEvent(stk);
			}
			if($(e.target).text()=='删除'){
				vm_table.delEvent(stk);
			}
		}
	});
	
	
	


	util.getJson(ajax.dic_query,{},function(result) {
		// 根据具体情况修改错误代码
		var aJson= result[0];
		if(aJson.code == "0" ){
			vm.param = aJson.data;
			// 更新数据后，刷新视图
			//avalon.scan(document.body);
		}else{
			util.layerMsg(aJson.message);
		}
	});


	//设置"是否启用"字段
	function setStatus(dic_id,isUsable){
//		var isUsable = $("td[name='is_usable']")[index].innerHTML;
//		if(isUsable == setStatus){
//			util.layerMsg("无法重复操作！");
//		}else{
			var ztree = $.fn.zTree.getZTreeObj("treeDemo");
			var treeNodes = ztree.getSelectedNodes();
			var stk={'dic_id':dic_id,'is_usable':isUsable,'treeNode':treeNodes[0].id};
			util.post(ajax.updateForAble,stk,function(result) {
				var aJson=JSON.parse(result);
				if(aJson.code=="0"){
					var data = JSON.parse(aJson.data)
					var nodeId = data.treeNode;
					queryTableData(nodeId);//刷新grid
					util.layerMsg("设置成功");
				}else{
					util.layerMsg(aJson.message);
				}
			});
//		}
	}



	//删除函数
	function data_del(dic_id){
		var ztree = $.fn.zTree.getZTreeObj("treeDemo");
		var treeNodes = ztree.getSelectedNodes();
		var stk={'dic_id':dic_id,'treeNode':treeNodes[0].id};
		util.post(ajax.dic_del,stk,function(result) {
			var aJson=JSON.parse(result);
			if(aJson.code=="0"){
				var data = JSON.parse(aJson.data)
				var nodeId = data.treeNode;
				var treeData = data.treeData
				//设置ztree初始化参数
				var setting = {
					view: {
						showLine: false,
						fontCss: getFontCss
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
						onClick: ztreeNodeClick
					}
				};
				//树加载
				$.fn.zTree.init($("#treeDemo"), setting, treeData);
				var ztree = $.fn.zTree.getZTreeObj("treeDemo");
				var curNode = ztree.getNodeByParam("id",nodeId);
				ztree.expandNode(curNode);
				ztree.selectNode(curNode, false);
				//刷新grid
				queryTableData(nodeId);
				util.layerMsg("删除成功");
			}else{
				util.layerMsg(aJson.message);
			}
		});
	}



	//树查询-前端
	function queryForZtree(stk){
		//获取当前树对象
		var ztree = $.fn.zTree.getZTreeObj("treeDemo");
		//根据节点名称（条件1）模糊查询得到的数组
		var nodesForName = [];
		//根据节点编码（条件2）获取的数组
		var nodesForCode = [];
		//根据父节点（条件3）获取的数组
		var nodesForParent = [];
		var resNodes = [];
		//获取所有查询条件
		var paramName = stk.dic_name;
		var paramCode = stk.dic_code;
		var paramParent = stk.parent_code;
		//根据三个查询条件的情况获取结果集
		if(paramName){//条件1存在
			nodesForName = ztree.getNodesByParamFuzzy("name",paramName);
			if(paramCode){//条件2存在
				nodesForCode = ztree.getNodesByParamFuzzy("id",paramCode);
				if(paramParent){//条件3存在
					nodesForParent = ztree.getNodesByParamFuzzy("id",paramCode);
					var miNodes = arrayIntersection(nodesForName,nodesForCode);
					resNodes = arrayIntersection(miNodes,nodesForParent);
				}else{//条件3不存在
					resNodes = arrayIntersection(nodesForName,nodesForCode);
				}
			}else{//条件2不存在
				if(paramParent){//条件3存在
					resNodes = arrayIntersection(miNodes,nodesForParent);
				}else{//条件3不存在
					resNodes = nodesForName;
				}
			}
		}else{//条件1不存在
			if(paramCode){//条件2存在
				nodesForCode = ztree.getNodesByParamFuzzy("id",paramCode);
				if(paramParent){//条件3存在
					nodesForParent = ztree.getNodesByParamFuzzy("id",paramCode);
					resNodes = arrayIntersection(nodesForCode,nodesForParent);
				}else{//条件3不存在
					resNodes = nodesForCode;
				}
			}else{//条件2不存在
				if(paramParent){//条件3存在
					nodesForParent = ztree.getNodesByParamFuzzy("id",paramCode);
					resNodes = nodesForParent;
				}else{//条件3不存在
					resNodes = [];
				}
			}
		}
		updateNodes(false,query_data);
		//设置查询结果条数
		vm.totalCount = resNodes.length;
		//设置查询结果样式
		updateNodes(true,resNodes);
	}
	//取两个数组交集
	function arrayIntersection(a, b){
		var ai=0, bi=0;
		var result = new Array();
		while(ai<a.length && bi<b.length){
			if (a[ai] < b[bi]){
				ai++;
			}else if (a[ai] > b[bi]){
				bi++; 
			}else{
				result.push(a[ai]);
				ai++;
				bi++;
			}
		}
		return result;
	}
	//节点样式
	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}
	//更新查询结果的节点样式
	function updateNodes(highlight,nodeList) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		//把结果集节点样式更新
		for( var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;
			zTree.updateNode(nodeList[i]);
			//展开该节点所在的父节点
			var pNode = nodeList[i].getParentNode();
			var pNodeLevel = parseInt(pNode.level);
			while(pNode.level > 1){
				pNode = pNode.getParentNode();
			}
			if(pNode.id != "root"){
				zTree.expandNode(pNode,highlight, highlight, null);
			}
		}
		query_data = nodeList;
	}



	//初始化加载方法
	var  initPageData =function(){
		initLoad(ajax.initLoad);
	}
	function initLoad(ajaxUrl,stk){
		if(stk==null || stk=="" || stk=="null" || typeof(stk)=="undefined"){
			stk={'dic_id':''};
		}
		util.post(ajaxUrl,stk,function(result) {
			//这里需要注意的是由于 post方式返回的是json字符串“” 所以要进行转换
			var jsonData = JSON.parse(result);
			var resArr = jsonData.data;
			//设置ztree初始化参数
			var setting = {
				view: {
					showLine: false,
					fontCss: getFontCss
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: ztreeNodeClick
				}
			};
			//树加载
			$.fn.zTree.init($("#treeDemo"), setting, resArr);
			var ztree = $.fn.zTree.getZTreeObj("treeDemo");
			var checkedNode = null;
			var nodes = ztree.getNodes();
			if(vm.checkedNodeId == null){
				checkedNode = nodes[0]; 
			}else{
				var	allNodes = ztree.transformToArray(nodes);
				for(var i = 0; i < allNodes.length; i++){
					if(allNodes[i].id == vm.checkedNodeId){
						checkedNode = allNodes[i];
					}
				}
			}
			ztree.selectNode(checkedNode, false);
			ztree.expandNode(checkedNode);
			//根据选中的树节点刷新table
			queryTableData(checkedNode.id);
		});
	}
	
	
	
	// 创建分页所需的的模型库
	var vm_page = avalon.define({		
		$id:'pager_list',
		showPages:1,//显示的页标
		totalPages:1,//初始总页数
		currentPage:1,//当前页数
		onPageClick:function(){
//			alert(util.getHashStr("page"));//通过得到url上键为page的值 来得到新的页码
//			//重新修改请求路径-并进行请求
//			ajax.table_query='./system/dic/list?param={"pageNo":"'+util.getHashStr("page")+'","pageSize":"'+displayRow+'"}';
//			vm_page.currentPage=util.getHashStr("page");
//			var ztree = $.fn.zTree.getZTreeObj("treeDemo");
//			var treeNodes = ztree.getSelectedNodes();//treeNodes[0].id
//			queryTableData(treeNodes[0].id);
		}
	});
	
	
	//加载grid，并重新计算分页
	function queryTableData(dic_code){
		var page = util.getHashStr("page");
		if(typeof(page)=="undefined" || page==null || page==null){
			page = 1;
//			vm_page.currentPage = 1;
		}
		var stk = {
			'dic_code':dic_code,
			'pageSize':displayRow,
			'pageNo':page
		};
		util.post(ajax.forNextLevel,stk,function(result) {
			var aJson = JSON.parse(result);
			if(aJson.code == "0" ){
				var JsonData = JSON.parse(aJson.data);
				var curPageCount = JsonData.pageCount;
				var tableData = JsonData.data;
				//下面的代码是针对具体的分页显示情况作调整
				if(vm_page.showPages>curPageCount){//当实际页数少于当前设置页数
					vm_page.showPages=curPageCount;
				}
				if(vm_page.showPages<curPageCount){//当实际页数多于当前页数时
					if(curPageCount<=5){//实际页数小于等于5
						vm_page.showPages=curPageCount;
					}else{//实际页数大于5
						vm_page.showPages=5;
					}
				}
				vm_page.totalPages = curPageCount;//修改页面显示的总页数
				vm_page.currentPage = JsonData.curPageNo;
				vm_table.gridData = tableData;
				// 更新数据后，刷新视图
				//avalon.scan(document.body);
				var gheight = $("#divForGrid").height();
				$("#divForView").css({"height":gheight});
			}else{
				util.layerMsg(aJson.message);
			}
		});
	}
	
	
	//树节点点击事件，刷新grid
	function ztreeNodeClick(e,treeId, treeNode){
		vm.checkedNodeId = treeNode.id;
		queryTableData(treeNode.id);
	}
	
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:initPageData
	}
});
