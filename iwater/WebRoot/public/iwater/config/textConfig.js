/*
 * 主文件-主配置文件
 * 加载引用的文件
 */
requirejs
		.config({
			baseUrl : '', // 注意不要随便修改
			paths : {
				app : 'iwater/javascripts',
				"jquery" : 'core/javascripts/import/jquery-1.8.3',
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
				"mmHistory":'core/javascripts/import/mmHistory',
				"storage":'core/javascripts/import/storage'
			},
			priority: ['text'],
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


// 开启页面初始化的相关事件
requirejs(['jquery','app/system/indexController'], function($) {	
	

	
});
