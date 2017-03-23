/*
 * 主文件-紧用于登录的主配置文件
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
			},
			shim : {
				'hashchange' : {
					deps : [ 'jquery' ],
					exports : 'hashchange'
				},
				"jquery.validform" : {
					deps : [ 'jquery' ]
				},
				"layer" : {
					deps : [ 'jquery' ]
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
	this.name = "iwater";
	this.title = "轻量开发框架";
	this.appPath = "public/iwater"; // 设置应用的访问路径
	this.indexPage = "system.users.list";

	this.code = "component_smartcloudServer";
	this.pCode = "component_root";

	this.htmlPath = "/html";
	this.javascriptsPath = "/javascripts";
}

// 全局变量.
var APP_CONTEXT = new ApplicationContext();

// 开启登录的相关事件
requirejs(['jquery','loginValid'], function($) {	
	// 为适应页面
	if($(window).height()>630){
		$('.wrap').css("height",$(window).height() +'px');
	}
	$(window).resize(function () {
		if($(window).height()>630){
			$('.wrap').css("height",$(window).height() +'px');
		}
	});
//	util.post();
	// 对输入框方法初始化
    $("#loginForm").loginFormValid();
	
});
