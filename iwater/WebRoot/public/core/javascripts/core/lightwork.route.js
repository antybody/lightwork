/**
 * 前端路由管理
 * cody by diana 2017.1.12
 * 1\劫持浏览器端的信息
 * 2\加载动态的html
 * 3\更新对应的视图窗口
 * 4\路径不要太深
 */
define(['mmRouter','util'], function (mmRoute,util){
	
	// 从外界传入的参数规范，必须是这些，可以新增
	var opt = {indexPage:'',rootPage:'',vmId:'',prefPath:'',prefJSPath:''};
	var fun_callback = "";
	
	/**
	 * indexPage:默认加载的文件，只填入文件即可，如aaa.html,则仅 输入aaa即可
	 * vmid:需要渲染的的视图Model $id
	 */
	var route_init = function(arg,callback){
	   // 获取相关参数
		if (typeof arg === 'string') {
            throw '输入的参数不符合规范！';
        } else if (typeof arg === 'object'){
            $.extend(opt, arg);
        }
		fun_callback = callback;
	   // 定义路由劫持的规则
	   avalon.router.get("/*path", route_callback); //劫持url hash并触发回调
	   // 开启路由hashchange方法
	   avalon.history.start();	   
	   // 全页面扫描
	   avalon.scan(document.body);
	};	

	/**
	 * 劫持成功后的回调函数
	 */		
	var route_callback = function(){
		
		avalon.log("进入劫持的回调方法~~~~~");

	   // 根据分发来的地址，判断文件的动态加载	
       var path_tail = this.path.replace(/^\/+|\/+$/g, ""); //去掉this.path值斜杠
       
       load_html(path_tail);
	};	

	/**
	 * 根据截取或者默认传来的文件名加载对应的html片段
	 */
	var load_html = function(root){
		// 如果文件名没有录入
		 if( root === '') return; 
		 if(root.match(/([^\/]+)(?=\.)/ig) == null) // 如果文件不是以扩展名结尾的
			 avalon.log("文件不是以扩展名结尾的");
	     else  
	    	 root = root.match(/([^\/]+)(?=\.)/ig)[0];
		 
		 //var req = require([opt.prefJSPath+root+".js"]);
		 // 查找路径下的文件，并加载 "text!./main.html"
		 // js 文件不能已这种路径加载
		 try{
			 require(["text!"+opt.prefPath+root+".html",opt.prefJSPath+root+".js"], function (html,page) { 
			        avalon.log("加载其他完毕");
			        // 渲染视图
			        if(typeof avalon.vmodels[opt.vmId].content_page == undefined)
			        	throw '视图缺少content_page属性';
			        else
			        	avalon.vmodels[opt.vmId].content_page = html;
			        
			        // 如果有回调函数，则调用
			        if(typeof fun_callback == "function")
			        	fun_callback();
			        
			        // 回调页面里的方法
			        if(page != undefined){
			        if(typeof page["initPageData"] != undefined)
			        	page.initPageData();
			        }
			 });
		 }catch(e){ util.layerMsg("页面不存在，请核查！");};
		 
	};
	
	return {
		route_init:route_init
	};
	
});