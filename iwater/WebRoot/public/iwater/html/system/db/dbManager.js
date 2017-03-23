/**
 *  新增菜单的跳转
 */

define(['avalon','util'],function(avalon,util){
	// 获取传入的参数
	var ajax ={
		redis_query:'./system/dbManager/queryRedis',    /*一般的查询*/
		redis_update_submit:'./system/dbManager/updateRedis',/*相当于update的提交事件*/
		db_query:'./system/dbManager/queryDB',    /*一般的查询*/
		db_update_submit:'./system/dbManager/updateDB',/*相当于update的提交事件*/
	};

	/* 创建添加或者删除的模型库*/
	var vm = avalon.define({
		$id:'db_form',
		redis_addr:'',//访问地址
		redis_port:'',//访问端口
		redis_auth:'',//授权密码，有没有这一项取决于要链接的redis服务是否设置了此项
		redis_max_total:'',//连接池的最大数据库连接数。设为0表示无限制
		redis_max_idle:'',//最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连接将被标记为不可用，然后释放。设为0表示无限制。
		redis_max_wait:'', //最大建立连接等待时间。如果超过此时间将连接到异常。设为-1表示无限制。
		redis_test_on_borrow:'',//在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；
		dbredisRead:function(e){//读取缓存配置
			clickDeal(e);
		},dbRead:function(e){//读取数据库配置
			clickDeal(e);
		},dbNewFile:function(e){
			clickDeal(e);
		},reset:function(e){
			clickDeal(e);
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
	//avalon.scan(document.body);
	//处理点击事件
	function clickDeal(e){
		var tt=e.srcElement;
		var index=$(tt).parent().attr('data-index');
		switch(index)
		{
			case '0':
				$('.list_table1').show();
				$('.list_table2').hide();
				$('#save_db').attr('data-index-now',index);
				$('#reset_db').attr('data-index-now',index);	
				$('.btn_db_bs').removeClass("btn_db_click");
				$('.btn_db_bs').addClass("btn_db");
				$(tt).parent().removeClass("btn_db");
				$(tt).parent().addClass("btn_db_click");
				set_redis_form_data();
				break;
			case '1':
				$('.list_table1').hide();
				$('.list_table2').show();
				$('#save_db').attr('data-index-now',index);
				$('#reset_db').attr('data-index-now',index);	
				$('.btn_db_bs').removeClass("btn_db_click");
				$('.btn_db_bs').addClass("btn_db");
				$(tt).parent().removeClass("btn_db");
				$(tt).parent().addClass("btn_db_click");
				set_db_form_data();
				break;
			case '2':
				//util.layerMsg($(tt).parent().attr('data-index-now')); 
				var dataindexnow=$(tt).parent().attr('data-index-now');
				submitData(dataindexnow);
				break;
			case '3':
				 
				var dataindexnow=$(tt).parent().attr('data-index-now');
				if(dataindexnow=='0'){
					set_redis_form_data();
				}else if(dataindexnow=='1'){
					set_db_form_data();
				}
				break;
	  
		}
	}
	//初始化redis表单数据
	function set_redis_form_data(){
			$('.list_table1').show();
			$('.list_table2').hide();
			vm.redis_addr='',//访问地址
			vm.redis_port='',//访问端口
			vm.redis_auth='',//授权密码，有没有这一项取决于要链接的redis服务是否设置了此项
			vm.redis_max_total='',//连接池的最大数据库连接数。设为0表示无限制
			vm.redis_max_idle='',//最大空闲数，数据库连接的最大空闲时间。超过空闲时间，数据库连接将被标记为不可用，然后释放。设为0表示无限制。
			vm.redis_max_wait='', //最大建立连接等待时间。如果超过此时间将连接到异常。设为-1表示无限制。
			vm.redis_test_on_borrow='',
	  		//修改前赋值
	  		util.post(ajax.redis_query,'',function(result) {
				 var aJson=eval("("+result+")");  
				 if(aJson.code=="0"){
					 var json_data=aJson.data;
					 for(key in json_data){
						 if(typeof vm[key] != "undefined" ){
							 vm[key]=json_data[key];
						 }
					 }
				 }
				 else{
					 util.layerMsg(aJson.message);
				 }
	  		});
	}
	//初始化  数据库配置文件表单数据
	function set_db_form_data(){
	  		//修改前赋值
	  		util.post(ajax.db_query,'',function(result) {
				 var aJson=eval("("+result+")");  
				 if(aJson.code=="0"){
					 var str2='';
					 var json_data=aJson.data;
					 for(key in json_data){
						 if(typeof vm[key] != "undefined" ){
							  str2+='<div class="iptgroup2"> '
								 	+'	<label style="width:270px;" >'+key+'=</label> '
								 	+'	<input class="ipt_text serializeInput" disabled="disabled"   name="'+key+'" value="'+json_data[key]+'" type="text" /> '
								 	+'	<input type="checkbox" class="clickCheckBox2" /> '
								 	+'	<input type="hidden" /> '
								 	+'	</div>'
							 vm[key]=json_data[key];
						 }
					 }
					 $('.list_table2').empty();
					 $('.list_table2').append(str2);
					 $('.clickCheckBox2').click(function(){
						 if($(this).is(':checked')){
								$(this).prev().attr("disabled",false);
								$(this).next().val($(this).prev().val()); 
						 }else{
								$(this).prev().attr("disabled",true);
								$(this).prev().val($(this).next().val());
						 }
					 })
				 }
				 else{
					 util.layerMsg(aJson.message);
				 }
	  		});
	}
	function formToJson(data) {  
         
         //data=data.replace("\"",""); 
         data=data.replace(/&/g,'","');  
         data=data.replace(/=/g,'":"'); 
         data='{"'+data+'"}';  
         return data;  
      }
	/* 传出模型的数据操作  */
	function submitData(parms){
		// 获得表单内全部元素
		var stk;
		var tip='修改成功';
		var url;
		if(parms=='0'){
			url=ajax.redis_update_submit;
			stk=JSON.parse(JSON.stringify(vm.$model));
		}else if(parms=='1'){
			url=ajax.db_update_submit;
			
			var _text = jQuery('.serializeInput');
			var postStr='';
			for(var i=0;i<_text.length;i++){
				if(i==_text.length-1){
					postStr+=$(_text[i]).attr('name')+"=="+$(_text[i]).val();
				}else{
					postStr+=$(_text[i]).attr('name')+"=="+$(_text[i]).val()+"&&";
				}
			}
			//jQuery('.serializeInput').attr("disabled",false);
			stk=postStr;
			//stk='';
			//jQuery('.serializeInput').attr("disabled",);
		}
		
		
		if(stk==''){
			 util.layerMsg('无修改内容！');
		}else{
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
	}	
	return {
		//这里写法固定  lightwork.route.js会默认调用完成页面初始化
		initPageData:set_redis_form_data       
	}
});