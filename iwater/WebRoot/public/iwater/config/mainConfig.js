/*
 * 主文件-主配置文件
 * 加载引用的文件
 */
requirejs
		.config({
			baseUrl : 'public', // 注意不要随便修改
			paths : {
				app : 'iwater/javascripts',
				"jquery" : 'core/javascripts/import/jquery-1.8.2.min',
				"jquery.validform" : 'core/javascripts/import/jquery.validform',
				"layer" : 'core/javascripts/import/layer',
				"uiTool" : 'core/javascripts/core/lightwork.ui',
				"page" : 'core/javascripts/core/lightwork.page',
				"loginValid":'core/javascripts/core/lightwork.login',
				"util" : 'core/javascripts/core/lightwork.util',
				"avalon" : 'core/javascripts/import/avalon',
				"mmRouter":'core/javascripts/import/mmRouter',
				 text:'core/javascripts/import/text',
				"pageRouter":'core/javascripts/core/lightwork.route',
				"ztree":'core/javascripts/import/jquery.ztree.all-3.5.min',
				"mmHistory":'core/javascripts/import/mmHistory',
				"storage":'core/javascripts/import/storage',
				"WdatePicker" : 'core/javascripts/import/WdatePicker',
				"hashmap" : 'core/javascripts/import/hashmap',
				"cron1" : 'core/javascripts/import/cron',
				"md5":'core/javascripts/import/md5',
				json2:'core/javascripts/import/json2',
				"chooseSelect":'core/javascripts/import/chosen.jquery'
				},
			map: {
				  '*': {
				    'css': 'core/javascripts/import/css' // or whatever the path to require-css is
				  }
				},
			priority: ['text','json2'],
			shim : {
				'hashchange' : {
					deps : [ 'jquery' ],
					exports : 'hashchange'
				},
				"jquery.validform" : {
					deps : [ 'jquery' ]
				},
				"cron1" :{
					deps : [ 'hashmap' ]
				},
				"layer" : {
					deps : [ 'jquery' ]
				},
				"avalon" :{
					exports: 'avalon'
				},
				"mmRouter":{
					deps : [ 'avalon' ],
					exports: 'mmRouter'
				},
				"mmHistory":{
					exports: 'mmHistory'
				},
				"storage":{
					exports: 'storage'
				}
			}
		});

requirejs.onError = function(err) {
	
	if (err.requireType === 'timeout') {
		console.error('请求超时，请检查服务端配置，modules: ' + err.requireModules);
	}
	if (err.requireType === 'scripterror') {
		console.error('请检查服务端文件路径是否正确，modules:' + err.requireModules);
	}

	throw err;
};

/**
 * 设置应用的基本参数
 */
function ApplicationContext() {
    
	this.indexPage = "main";  // 初始化页面文件名,后面不要加后缀,不要加前/,不要带路径
	this.rootPage = "index"; // 初始化根节点
	this.vmId = "index_controller";
	this.prefPath = "./iwater/html/system/"; // 内容页面放置的前缀 ,必须以/结尾
	this.prefJSPath = "./public/iwater/html/system/"; // 内容页面放置的前缀 ,必须以/结尾
}

// 全局变量.
var APP_CONTEXT = new ApplicationContext();

// 开启页面初始化的相关事件
requirejs(['pageRouter','util'], function(pageRouter,util) {	
	/* 用于页面自适应 */
	var wrap = 3;
	var bool = true;
	resposition(wrap);
	setSize();
	function resposition(t){
		$('.list_menu').off("click",leftmove);
		var drw = document.body.scrollWidth;
		$('.right_comment').css("width",$('.content_wrap').width()*0.8);
		if(t!=1){
			$('.right_comment').animate({left:($('.content_wrap').width()-$('.right_comment').width()+180)/2+"px"},"fast",getmove);
		}else if(t==1){
			$('.right_comment').animate({left:($('.content_wrap').width()-$('.right_comment').width())/2+"px"},"fast",getmove);
		}
	}
	function getmove(){
		$('.list_menu').on("click",leftmove);
	}

	function leftmove(){
		wrap = wrap==1?0:1;
		if(wrap==1){
			$('.left_list').animate({width:0},resposition(wrap));
		}else if(wrap==0){
			$('.left_list').animate({width:"180px"},resposition(wrap));
		}
	}
	function setSize(){
		$(".content").height($(document).height()-50);
	}
	// 为适应页面
	$(window).resize(function (){
		resposition(wrap);
		setSize();
	});
	
    // 定义与页面匹配的Model
	var vm = avalon.define({		
		$id:'index_controller',
		username:"",
		menu_arr:"",
		content_page:"empty",
		showOrHide:function(e){   //点击切换显示状态的方法
			var k=e.target.parentNode.parentNode.lastChild;
			//$(k).toggle(1000);
			$(k).slideToggle(1000);
		},
		clickChange:function(e){  //点击改变显示样式
			//先将其他标签改为 普通样式
			//$("#left_menu a").removeAttr("class");
			//将点击标签 改为  点击样式
			//$(e.target).attr("class","bga");
		}
    });	
	
	// 具体的菜单加载
	var ajax={
			menu:'./system/login/getMenu'   //./public/iwater/config/menu_test.json
	};
	var params ={
    		user:'',
    		role:''
    	 };
	util.getJson(ajax.menu,params,function(result) {
        // 根据具体情况修改错误代码
        var aJson= result;
        if(aJson.code == "0" ){
	    	   for(var i in aJson.data){
	               if(vm.hasOwnProperty(i)){
	                  vm[i] = aJson.data[i];
	               }
	          }
	    	   avalon.scan(document.body);
	    	   // 加载默认的右侧主页
	    	   if((window || this).location.href.indexOf("#!/") < 0)
	    	     window.location.hash = "#!/main";
	       }
	       else
	     util.layerMsg(aJson.msg);
	 }); 
	
	// 开启路由分发机制
	pageRouter.route_init(APP_CONTEXT,function(){
		// 分发成功后的回调
		resposition(wrap);
		setSize();
	});
		
});
