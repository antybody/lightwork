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
		$id:'query_form',
		query_type:'',//条件类型
		zh_name:'',//条件名称
		parent_code:'',//字典代号
		isShowMore:'',//是否显示更多
		en_name:'',//对应数据库字段
		isChecked:'',//默认是否选中
		
		read_query:function(){//读取已有配置
			
		},
		add_query:function(){//添加
			
		},
		view_query:function(){
			
		},
		to_json:function(){
			
		},
		query_query_query:function(e){//点击下面的条件查询该条件 的具体配置信息
			var tt=e.toElement;
			$(".query_li_query").removeClass("query_li_query2");
			$(tt).parent().addClass("query_li_query2");
		},
		del_query:function(e){//删除条件
			var tt=e.toElement;
			$(tt).parent().remove();
		}
	});
	
	avalon.scan(document.body);
	//初始化  表单数据
	function set_form_data(){
		vm.query_type='',//条件类型
		vm.zh_name='',//条件名称
		vm.parent_code='',//字典代号
		vm.isShowMore='',//是否显示更多
		vm.en_name='',//对应数据库字段
		vm.isChecked='',//默认是否选中
  		//修改前赋值
  		util.post(ajax.query,'',function(result) {
			 var aJson=eval("("+result+")");  
			 if(aJson.code=="0"){
				 var json_data=aJson.data;
				 for(key in json_data){
					 if(typeof json_data[key] != undefined ){
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
//	return {
//		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
//		//initPageData:set_form_data       
//	}
});