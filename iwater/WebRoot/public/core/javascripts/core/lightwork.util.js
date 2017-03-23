/**
* 封装公共前端组件
*/

define(["layer","json2"],function (lay) {
	
	lay.config({
		  path: './public/core/javascripts/import/' //layer.js所在的目录，可以是绝对目录，也可以是相对目录
		});
	
	/**
	 * @description 基于Layer.js的消息提示框
	 */
	var layerMsg = function(message){	
		if(typeof message == undefined)
			message = "操作失败，请刷新重试！";
		lay.msg(message, {icon: 1 });
	};
	
	/**
	 * @description  特定方法  获得页面的特定查询条件
	 *  checkbox,text  (欢迎补充)
	 */
	var get_query_list = function(){
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
					//这里将复选框的值拼接起来  (like "1,2,3")
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
	};
	
	/**
	 * @description 基于Layer.js的确认提示框
	 */
	var layerConfirm = function(message,callback){
		lay.confirm(message, {
			  time:0,btn: ['确定','取消'] //按钮
			}, function(){
				callback();
			}, function(){});
	};
	
	/**
	 * @description 基于Layer.js的弹出框
	 * @param t 标题
	 * @param w 宽度 要加px
	 * @param h 高度
	 * 
	 */
	var layerWin = function(t,w,h,content,callback){		
		lay.open({
			type:1,
			title:t,
			content:content,
			area:[w,h],
			btn:['确定','取消'],
			yes:function(index,layero){
				if(callback()){
					layer.closeAll();
				}
			}
		});
		
	};
	
	/**  
	* @description 重写alert，采用的bootsctrp样式
	* @param message 弹出消息
	* @param width 宽度
	*/ 
	var alert = function(message,width){
//			var dialog = bootbox.dialog({
//	        message: message,
//	        title: "<i class='fa fa-exclamation-triangle orange'></i> 提示信息",
//	        buttons:
//	        {
//	            "success" :
//	            {
//	                "label" : "<i class='fa fa-check'></i> 确定",
//	                "className" : "btn-warning"
//	            }
//	        }
//	    }).css("z-index",9999999999999);
//	    if(width){
//	        $(".modal-dialog").width(width);
//	    }
	};
	/**  
	* @description 重写confirm，采用的bootsctrp样式
	* @param message 消息
	* @param callback 回调函数
	* @param width 宽度
	*/ 
	var confirm = function (message,callback,width){
//	    bootbox.dialog({
//	        message: message,
//	        title: "<i class='fa fa-question-circle orange'></i> 确认信息",
//	        buttons:
//	        {
//	            "success" :
//	            {
//	                "label" : "<i class='fa fa-check'></i> 确定",
//	                "className" : "btn-danger",
//	                "callback":function(){
//	                    callback();
//	                }
//	            },
//	            "reject" :
//	            {
//	                "label" : "<i class='fa fa-times'></i> 取消",
//	                "className" : "btn-info"
//	            }
//	        }
//	    }).css("z-index",9999999999999);
//
//	    if(width){
//	        $(".modal-dialog").width(width);
//	    }
	};
	/**  
	* @description 重写prompt
	* @param title 标题
	* @param callback 回调函数
	*/ 
	var prompt = function(title,callback){
	    var promptOptions = {
	        title: title,
	        buttons: {
	            confirm: {
	                label: "<i class='fa fa-check'></i> 确认",
	                "className" : "btn-danger"
	            },
	            cancel:{
	                label: "<i class='fa fa-times'></i>取消",
	                "className" : "btn-info"
	            }
	        },
	        inputType:"textarea",
	        callback: function(result){
	            callback(result);
	        }
	    };
//	    bootbox.prompt(promptOptions);
	};
	/**  
	* @description 消息提示 一闪而过
	* @param msg 消息内容
	* @param type 提示消息的类型
	*/ 
	var message = function(msg,type){
		 var messageAlert = $('<div class="alert alert-'+type+' text-center"><button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button>'+msg+'<br /></div>');
	    $("body").prepend(messageAlert);
	    setTimeout(function(){
	        messageAlert.hide(1000,"swing");
	    },1500);
	};
	    
    /**  
	* @description $obj对象封装成json形式
	* @param $obj $obj对象
	* @return json结果
	*/
    var toJsonObject = function($obj){
        var serializeObj={};
        var array=$obj.serializeArray();
        $(array).each(function(){
            if(this.value){
                if(serializeObj[this.name]){
                    if($.isArray(serializeObj[this.name])){
                        serializeObj[this.name].push(this.value);
                    }else{
                        serializeObj[this.name]=[serializeObj[this.name],this.value];
                    }
                }else{
                    serializeObj[this.name]=this.value;
                }
            }
        });
        return serializeObj;
    };
    
    /**  
	* @description tab切换 阻止链接跳转
	* @param pos jquery定位符
	*/
    var bootstrapTab = function(pos){
    	$(pos+' a:first').tab('show');//初始化显示tab 
        $(pos+' a').click(function (e) { 
        	e.preventDefault();//阻止a链接的跳转行为 
          	$(this).tab('show');//显示当前选中的链接及关联的content 
        }) ;
    };
    /**  
	* @description 获取页面所有组件
	* @param params 获取条件
	* @param callback 回调函数
	*/
    var getComponent = function(params,callback){
    	$.getJSON(APP_CONTEXT.appComponentPath,function(component){
    		var paths = component.path;
    		var ret = [];
    		//组装组件信息
    		for(var i=0,len=paths.length;i<len;i++){
    			(function(path,i){
    				$.getJSON(path,function(config){
	    				var obj = {
	    					"name":config.name,
	    					"code":config.code,
	    					"pCode":config.pCode
	    				}
	    				ret.push(obj);
	    				var modules = config.modules;
	    				for(var j=0,moduleLen=modules.length;j<moduleLen;j++){
	    					var moduleObj = {
	    						"name":modules[j].caption,
	    						"code":obj.code+modules[j].module,
	    						"pCode":obj.code
	    					} 
	    					ret.push(moduleObj);
	    					var pages = modules[j].pages;
	    					for(var k=0,pageLen=pages.length;k<pageLen;k++){
	    						var pageObj = {
	    							"name":pages[k].caption,
	    							"code":moduleObj.code+pages[k].id,
	    							"pCode":moduleObj.code
	    						}
	    						ret.push(pageObj);
	    						var components = pages[k].components;
	    						for(var l=0,componentLen=components.length;l<componentLen;l++){
	    							var componentObj = {
	    								"name":components[l].caption,
	    								"code":pageObj.code+components[l].component,
	    								"pCode":pageObj.code
	    							}
	    							ret.push(componentObj);
	    						}
	    					}
	    				} 
	    				if(i == len-1){//表示全部执行完成 执行回调
    						return callback(ret);
    					}
	    			})
    			})(paths[i],i)
    		}
    	})
    }
    /**  
	* @description 判断数组中是否有obj元素
	* @param arr 数组
	* @param obj 元素
	* @return boolean
	*/
	var contains = function (arr, obj) {
		if(arr){
			var i = arr.length;
		    while (i--) {
		        if (arr[i] === obj) {
		            return true;
		        }
		    }
		}
	    
	    return false;
	};
	 /**  
    * @description 返回结果统一处理
    * @param data 返回结果
    * @param callback 回调函数
    */ 
	var resProcess = function(data,callback){
		var _this = this;
		return callback(data);
	    if(data.status==-1){
	        alert("运行异常，异常信息："+data.message+",请与管理员联系！");
	        return ;
	    }else{
	        return callback(data.doc);
	    }
	};
    /**  
    * @description 封装get请求
    * @param path 请求路径
    * @param params 请求条件
    * @param callback 回调函数
    */ 
    var get = function(path,params,callback){
    	params.random = Math.random();
    	$.get(path, params,function(data){
            return resProcess(data,callback);
        });
    };
    
    var getJson =function(path,params,callback){
    	var params = JSON.stringify(params);
    	$.getJSON(path,function(data){
    		return resProcess(data,callback);
    	});
    };
    /**  
    * @description 封装post请求
    * @param path 请求路径
    * @param params 请求条件
    * @param callback 回调函数
    */ 
    var post = function(path,params,callback){
    	// 编码后传递，防止服务器上请求不到
    	// 为了兼容IE8
    	if(typeof params.$model != "undefined")
    		params = JSON.stringify(params.$model);
    	else
    		params = JSON.stringify(params);
    	var params = encodeURI(params);    	
	    $.post(path,{"params":params},function(data){
	        return resProcess(data,callback);
	    });
    };
    /**  
     * @description 封装post请求
     * @param path 请求路径
     * @param params 请求条件 不带请求
     * @param callback 回调函数
     */ 
     var post2 = function(path,params,callback){
     	var params = JSON.stringify(params);
 	    $.post(path,params,function(data){
 	        return resProcess(data,callback);
 	    });
     };
    /**  
    * @description 验证表单
    * @param $form jquery表单
    * @param option validform配置
    * @param callback 回调函数
    */ 
    var validform = function($form,option,callback){
    	var options = option ||{
    		tiptype:3,
        	label:".label",
        	showAllError:true,
        	callback:callback
    	}
    	$form.Validform(options);
    };
    var _fieldname;  
    var _SetTime=function(e) {  
    	var tt=e.toElement;
    	var str = "";   
    	str +="<div id=\"_contents\" style=\"padding:5px;background-color:#ECF7FE;font-size:12px;border:1px solid #CFD1D5;position:absolute;left:?px; top:?px; width:?px; height:?px;z-index:1;\">";   
    	str += "时<select id=\"_hour\">";   
    	for (h = 0; h <= 23; h++) {   
    	    if(h >=0 && h<=9)  
    	       str += "<option value=\"0" + h + "\">0" + h + "</option>";  
    	    else  
    	       str += "<option value=\"" + h + "\">" + h + "</option>";   
    	}  
    	str += "</select> 分<select id=\"_minute\">";   
    	for (m = 0; m <= 59; m++) {   
    	    if(m >=0 && m<=9)  
    	       str += "<option value=\"0" + m + "\">0" + m + "</option>";  
    	    else  
    	       str += "<option value=\"" + m + "\">" + m + "</option>";   
    	}  
    	str += "</select> 秒<select id=\"_second\">";   
    	for (s = 0; s <= 59; s++) {   
    	    if(s >=0 && s<=9)  
    	       str += "<option value=\"0" + s + "\">0" + s + "</option>";   
    	    else  
    	       str += "<option value=\"" + s + "\">" + s + "</option>";  
    	}  
    	str += "</select> <input type=\"button\" id=\"queding\" value=\"\确定\" /> <input type=\"button\" id=\"qingchu\"  value=\"\清除\" /></div>";   
    	$("#_contents").remove();
    	$(tt).after(str);
    	$("#queding").click(function(){
    		_select();
    	});
    	$("#qingchu").click(function(){
    		_clear();
    	});
        _fieldname = $(tt);   
        var ttop = tt.offsetTop;    //TT控件的定位点高   
        var thei = tt.clientHeight;    //TT控件本身的高   
        var tleft = tt.offsetLeft;    //TT控件的定位点宽   
        if($(tt).val()!=""){
        	 $("#_hour").val($(tt).val().split("时")[0]);
        	    $("#_minute").val($(tt).val().split("分")[0].split("时")[1]);
        	    $("#_second").val($(tt).val().split("分")[1].split("秒")[0]);
        }
        $("#_contents").css("top",ttop);
        $("#_contents").css("width",$(tt).width());
        $("#_contents").css("margin-left",tleft);
        
    }   
    var _select=function() {   
    	 _fieldname.val($("#_hour").val() +"时"+ $("#_minute").val() +"分"+ $("#_second").val()+"秒") ;   
        $("#_contents").hide();
    }  
    var _clear=function() {   
        $("#_hour").val("00");
        $("#_minute").val("00");
        $("#_second").val("00");  
        _fieldname.val("") ;  
        $("#_contents").hide();
    } 
   
    var _setQurtz=function(e) {  
    	var tt=e.srcElement;
    	var str = "";   
    	str +="<div id=\"_contentsCron\">";   
    	str +="  <table width=\"600\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"solid-bottom\">"
        	+"    <tr>"
        	+"      <td width=\"600\" height=\"20\"><table  border=\"0\"  cellpadding=\"0\"  cellspacing=\"0\"  id=\"secTable\">"
        	+"        <tr>"
        	+"          <td height=\"25\" id=\"cromSec0\" name=\"td1\" width=\"60\" align=\"center\"  class=\"sec2\"  >分钟</td>"
        	+"          <td  width=\"60\" id=\"cromSec1\" name=\"td1\" align=\"center\"  class=\"sec1\"  >小时</td>"
        	+"          <td  width=\"60\" id=\"cromSec2\" name=\"td1\" align=\"center\"  class=\"sec1\"  >天</td>"
        	+"  		<td  width=\"60\" id=\"cromSec3\" name=\"td1\" align=\"center\"  class=\"sec1\"  >月</td>"
        	+"  		<td  width=\"60\" id=\"cromSec4\" name=\"td1\" align=\"center\"  class=\"sec1\" >周</td>"
        	+"  		<td  width=\"60\" id=\"cromSec5\" name=\"td1\" align=\"center\"  class=\"sec1\"  >验证</td>"
        	+"        </tr>"
        	+"      </table>"
        	+"  	</td>"
        	+"    </tr>"
        	+"  </table>"
        	+"  <table  border=\"0\"  cellspacing=\"5\"  cellpadding=\"0\"  width=\"600\"  height=\"200\"  id=\"mainTable\" bgcolor=\"f2f2f2\">"
        	+"    <tbody  style=\"display:block;\">"
        	+"      <tr>"
        	+"        <td height=\"120\">"
        	+"  	  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
        	+"          <tr>"
        	+"            <td width=\"3%\" height=\"27\" align=\"left\" valign=\"middle\">"
        	+"                <div align=\"left\">"
        	+"                  <input name=\"fz\" id=\"fz\" type=\"radio\" value=\"fz1\" onclick=\"fz_radio_click(this)\">"
        	+"                </div></td>"
        	+"            <td width=\"22%\" align=\"left\" valign=\"middle\"> <div align=\"left\">秒周期循环：从</div></td>"
        	+"            <td width=\"15%\" align=\"left\" valign=\"middle\"><div align=\"left\">"
        	+"              <input name=\"mks\" id=\"mks\" type=\"text\" class=\"DG-spinner\" id=\"testElement\" value=\"0\" maxlength=\"3\" properties=\"maxValue:59,minValue:0,shiftIncrement:20\">"
        	+"            </div></td>"
        	+"            <td width=\"25%\" align=\"left\" valign=\"middle\"> <div align=\"left\">秒钟开始时间，间隔</div></td>"
        	+"            <td width=\"15%\" align=\"left\" valign=\"middle\"><div align=\"left\">"
        	+"              <input name=\"mzx\" id=\"mzx\" type=\"text\" class=\"DG-spinner\" id=\"text\" value=\"0\" maxlength=\"3\" properties=\"maxValue:59,minValue:0,shiftIncrement:20\" >"
        	+"            </div></td> "
        	+"            <td width=\"20%\" align=\"left\" valign=\"middle\"><div align=\"left\">秒钟执行一次</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"25\" valign=\"middle\">"
        	+"  		    <div align=\"left\">"
        	+"  		      <input name=\"fz\" id=\"fz\" type=\"radio\" onclick=\"fz_radio_click(this)\" value=\"fz2\" checked>"
        	+"  	        </div></td>"
        	+"            <td height=\"25\"><div align=\"left\">分周期循环：从 </div></td>"
        	+"            <td height=\"25\"><div align=\"left\">"
        	+"              <input name=\"fks\" id=\"fks\" type=\"text\" class=\"DG-spinner\"  value=\"0\" maxlength=\"3\" properties=\"maxValue:59,minValue:0,shiftIncrement:20\">"
        	+"            </div></td>"
        	+"            <td height=\"25\"><div align=\"left\">分钟开始时间，间隔</div></td>"
        	+"            <td height=\"25\"><div align=\"left\">"
        	+"              <input name=\"fzx\" id=\"fzx\" type=\"text\" class=\"DG-spinner\"  value=\"1\" maxlength=\"3\" properties=\"maxValue:59,minValue:0,shiftIncrement:20\">  "
        	+"            </div></td>"
        	+"            <td height=\"25\"><div align=\"left\">分钟执行一次</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"25\" valign=\"middle\">"
        	+"                <div align=\"left\">"
        	+"                  <input name=\"fz\" id=\"fz\" type=\"radio\" value=\"fz3\" onclick=\"fz_radio_click(this)\">"
        	+"                </div></td>"
        	+"            <td height=\"25\"><div align=\"left\">手动指定</div></td>"
        	+"            <td height=\"25\">&nbsp;</td>"
        	+"            <td height=\"25\">&nbsp;</td>"
        	+"            <td height=\"25\">&nbsp;</td>"
        	+"            <td height=\"25\">&nbsp;</td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"100\" colspan=\"6\" align=\"center\"><table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" b>"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"chk1\" id=\"chk1\" value=\"1\" disabled=\"disabled\"></td>"
        	+"                <td>1</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk2\" id=\"chk2\" value=\"2\" disabled=\"disabled\"></td>"
        	+"                <td>2</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk3\" id=\"chk3\" value=\"3\" disabled=\"disabled\"></td>"
        	+"                <td>3</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk4\" id=\"chk4\" value=\"4\" disabled=\"disabled\"></td>"
        	+"                <td>4</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk5\" id=\"chk5\" value=\"5\" disabled=\"disabled\"></td>"
        	+"                <td>5</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk6\" id=\"chk6\" value=\"6\" disabled=\"disabled\"></td>"
        	+"                <td>6</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk7\" id=\"chk7\" value=\"7\" disabled=\"disabled\"></td>"
        	+"                <td>7</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk8\" id=\"chk8\" value=\"8\" disabled=\"disabled\"></td>"
        	+"                <td>8</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk9\" id=\"chk9\" value=\"9\" disabled=\"disabled\"></td>"
        	+"                <td>9</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk10\"  id=\"chk10\" value=\"10\" disabled=\"disabled\"></td>"
        	+"                <td>10</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk11\" id=\"chk11\" value=\"11\" disabled=\"disabled\"></td>"
        	+"                <td>11</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk12\" id=\"chk12\" value=\"12\" disabled=\"disabled\"></td>"
        	+"                <td>12</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk13\" id=\"chk13\" value=\"13\" disabled=\"disabled\"></td>"
        	+"                <td>13</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk14\" id=\"chk14\" value=\"14\" disabled=\"disabled\"></td>"
        	+"                <td>14</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk15\" id=\"chk15\" value=\"15\" disabled=\"disabled\"></td>"
        	+"                <td>15</td>"
        	+"              </tr>"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"chk16\" id=\"chk16\" value=\"16\" disabled=\"disabled\"></td>"
        	+"                <td>16</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk17\" id=\"chk17\" value=\"17\" disabled=\"disabled\"></td>"
        	+"                <td>17</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk18\" id=\"chk18\" value=\"18\" disabled=\"disabled\"></td>"
        	+"                <td>18</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk19\" id=\"chk19\" value=\"19\" disabled=\"disabled\"></td>"
        	+"                <td>19</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk20\" id=\"chk20\" value=\"20\" disabled=\"disabled\"></td>"
        	+"                <td>20</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk21\" id=\"chk21\" value=\"21\" disabled=\"disabled\"></td>"
        	+"                <td>21</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk22\" id=\"chk22\" value=\"22\" disabled=\"disabled\"></td>"
        	+"                <td>22</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk23\" id=\"chk23\" value=\"23\" disabled=\"disabled\"></td>"
        	+"                <td>23</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk24\" id=\"chk24\" value=\"24\" disabled=\"disabled\"></td>"
        	+"                <td>24</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk25\"  id=\"chk25\" id=\"chk1\" value=\"25\" disabled=\"disabled\"></td>"
        	+"                <td>25</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk26\"  id=\"chk26\" value=\"26\" disabled=\"disabled\"></td>"
        	+"                <td>26</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk27\"  id=\"chk27\" value=\"27\" disabled=\"disabled\"></td>"
        	+"                <td>27</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk28\" id=\"chk28\" value=\"28\" disabled=\"disabled\"></td>"
        	+"                <td>28</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk29\" id=\"chk29\" value=\"29\" disabled=\"disabled\"></td>"
        	+"                <td>29</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk30\" id=\"chk30\" value=\"30\" disabled=\"disabled\"></td>"
        	+"                <td>30</td>"
        	+"              </tr>"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"chk31\" id=\"chk31\" value=\"31\" disabled=\"disabled\"></td>"
        	+"                <td>31</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk32\" id=\"chk32\" value=\"32\" disabled=\"disabled\"></td>"
        	+"                <td>32</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk33\" id=\"chk33\" value=\"33\" disabled=\"disabled\"></td>"
        	+"                <td>33</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk34\" id=\"chk34\" value=\"34\" disabled=\"disabled\"></td>"
        	+"                <td>34</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk35\" id=\"chk35\" value=\"35\" disabled=\"disabled\"></td>"
        	+"                <td>35</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk36\" id=\"chk36\" value=\"36\" disabled=\"disabled\"></td>"
        	+"                <td>36</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk37\" id=\"chk37\" value=\"37\" disabled=\"disabled\"></td>"
        	+"                <td>37</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk38\" id=\"chk38\" value=\"38\" disabled=\"disabled\"></td>"
        	+"                <td>38</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk39\" id=\"chk39\" value=\"39\" disabled=\"disabled\"></td>"
        	+"                <td>39</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk40\" id=\"chk40\" value=\"40\" disabled=\"disabled\"></td>"
        	+"                <td>40</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk41\" id=\"chk41\" value=\"41\" disabled=\"disabled\"></td>"
        	+"                <td>41</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk42\" id=\"chk42\" value=\"42\" disabled=\"disabled\"></td>"
        	+"                <td>42</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk43\" id=\"chk43\" value=\"43\" disabled=\"disabled\"></td>"
        	+"                <td>43</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk44\" id=\"chk44\" value=\"44\" disabled=\"disabled\"></td>"
        	+"                <td>44</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk45\"  id=\"chk45\" value=\"45\" disabled=\"disabled\"></td>"
        	+"                <td>45</td>"
        	+"              </tr>"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"chk46\" id=\"chk46\" value=\"46\" disabled=\"disabled\"></td>"
        	+"                <td>46</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk47\"  id=\"chk47\" value=\"47\" disabled=\"disabled\"></td>"
        	+"                <td>47</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk48\" id=\"chk48\"  value=\"48\" disabled=\"disabled\"></td>"
        	+"                <td>48</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk49\" id=\"chk49\"  value=\"49\" disabled=\"disabled\"></td>"
        	+"                <td>49</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk50\" id=\"chk50\"  value=\"50\" disabled=\"disabled\"></td>"
        	+"                <td>50</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk51\" id=\"chk51\"  value=\"51\" disabled=\"disabled\"></td>"
        	+"                <td>51</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk52\" id=\"chk52\"  value=\"52\" disabled=\"disabled\"></td>"
        	+"                <td>52</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk53\" id=\"chk53\"  value=\"53\" disabled=\"disabled\"></td>"
        	+"                <td>53</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk54\" id=\"chk54\"  value=\"54\" disabled=\"disabled\"></td>"
        	+"                <td>54</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk55\" id=\"chk55\"  value=\"55\" disabled=\"disabled\"></td>"
        	+"                <td>55</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk56\"  id=\"chk56\" value=\"56\" disabled=\"disabled\"></td>"
        	+"                <td>56</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk57\" id=\"chk57\"  value=\"57\" disabled=\"disabled\"></td>"
        	+"                <td>57</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk58\" id=\"chk58\"  value=\"58\" disabled=\"disabled\"></td>"
        	+"                <td>58</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk59\" id=\"chk59\"  value=\"59\" disabled=\"disabled\"></td>"
        	+"                <td>59</td>"
        	+"                <td><input type=\"checkbox\" name=\"chk0\" id=\"chk0\"  value=\"0\" disabled=\"disabled\"></td>"
        	+"                <td>0</td>"
        	+"              </tr>"
        	+"            </table></td>"
        	+"          </tr>"
        	+"        </table>	  </td>"
        	+"      </tr>"
        	+"    </tbody>"
        	+"   <tbody  style=\"display:none;\">"
        	+"      <tr>"
        	+"        <td height=\"120\">"
        	+"  	  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
        	+"          <tr>"
        	+"            <td width=\"10\" height=\"26\"><div align=\"left\">"
        	+"              <input name=\"xs\" id=\"xs\" type=\"radio\" value=\"xs1\" checked  onClick=\"xs_radio_click(this)\">"
        	+"            </div></td>"
        	+"            <td width=\"97%\" align=\"left\"><div align=\"left\">每一小时</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"20\" width=\"10\"><div align=\"left\">"
        	+"              <input name=\"xs\" id=\"xs\" type=\"radio\" value=\"xs2\" onClick=\"xs_radio_click(this)\">"
        	+"            </div></td>"
        	+"            <td height=\"20\" align=\"left\"><div align=\"left\">手动指定</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"100\" colspan=\"2\" align=\"center\"><table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
        	+"              <tr>"
        	+"                <td>AM：</td>" 
        	+"                <td><input type=\"checkbox\" name=\"sj0\" id=\"sj0\" value=\"0\"  disabled=\"disabled\"></td>"
        	+"                <td>0</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj1\"  id=\"sj1\" value=\"1\"  disabled=\"disabled\"></td>"
        	+"                <td>1</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj2\"  id=\"sj2\"  value=\"2\" disabled=\"disabled\"></td>"
        	+"                <td>2</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj3\"   id=\"sj3\" value=\"3\" disabled=\"disabled\"></td>"
        	+"                <td>3</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj4\"   id=\"sj4\" value=\"4\" disabled=\"disabled\"></td>"
        	+"                <td>4</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj5\"   id=\"sj5\" value=\"5\" disabled=\"disabled\"></td>"
        	+"                <td>5</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj6\"   id=\"sj6\" value=\"6\" disabled=\"disabled\"></td>"
        	+"                <td>6</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj7\"   id=\"sj7\" value=\"7\" disabled=\"disabled\"></td>"
        	+"                <td>7</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj8\"  id=\"sj8\"  value=\"8\" disabled=\"disabled\"></td>"
        	+"                <td>8</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj9\"   id=\"sj9\" value=\"9\" disabled=\"disabled\"></td>"
        	+"                <td>9</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj10\"  id=\"sj10\"  value=\"10\" disabled=\"disabled\"></td>"
        	+"                <td>10</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj11\"   id=\"sj11\" value=\"11\" disabled=\"disabled\"></td>"
        	+"                <td>11</td>"
        	+"              </tr>"
        	+"              <tr>"
        	+"                <td>PM：</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj12\"   id=\"sj12\"  value=\"12\" disabled=\"disabled\"></td>"
        	+"                <td>12</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj13\"  id=\"sj13\"  value=\"13\" disabled=\"disabled\"></td>"
        	+"                <td>13</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj14\"  id=\"sj14\"  value=\"14\" disabled=\"disabled\"></td>"
        	+"                <td>14</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj15\"  id=\"sj15\"  value=\"15\" disabled=\"disabled\"></td>"
        	+"                <td>15</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj16\"  id=\"sj16\"  value=\"16\" disabled=\"disabled\"></td>"
        	+"                <td>16</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj17\"  id=\"sj17\"  value=\"17\" disabled=\"disabled\"></td>"
        	+"                <td>17</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj18\"  id=\"sj18\"  value=\"18\" disabled=\"disabled\"></td>"
        	+"                <td>18</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj19\"  id=\"sj19\"  value=\"19\" disabled=\"disabled\"></td>"
        	+"                <td>19</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj20\"  id=\"sj20\"  value=\"20\" disabled=\"disabled\"></td>"
        	+"                <td>20</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj21\"  id=\"sj21\"  value=\"21\" disabled=\"disabled\"></td>"
        	+"                <td>21</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj22\"  id=\"sj22\"  value=\"22\" disabled=\"disabled\"></td>"
        	+"                <td>22</td>"
        	+"                <td><input type=\"checkbox\" name=\"sj23\"  id=\"sj23\"  value=\"23\" disabled=\"disabled\"></td>"
        	+"                <td>23</td>"
        	+"              </tr>"
        	+"            </table></td>"
        	+"          </tr>"
        	+"        </table>	  </td>"
        	+"      </tr>"
        	+"    </tbody>"
        	+"    <tbody  style=\"display:none;\">"
        	+"      <tr>"
        	+"        <td height=\"120\">"
        	+"  	  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
        	+"          <tr>"
        	+"            <td width=\"10\" height=\"24\"><div align=\"left\">"
        	+"              <input name=\"mt\" id=\"mt\" type=\"radio\" value=\"mt1\" checked onClick=\"mt_radio_click(this)\" >"
        	+"            </div></td>"
        	+"            <td width=\"97%\" align=\"left\"><div align=\"left\">每一天（<span class=\"STYLE1\">温馨提示：如果使用了周循环天循环不起效！</span>）</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"20\" width=\"10\"><div align=\"left\">"
        	+"              <input name=\"mt\" id=\"mt\" type=\"radio\" value=\"mt2\"  onClick=\"mt_radio_click(this)\">"
        	+"            </div></td>"
        	+"            <td height=\"20\" align=\"left\"><div align=\"left\">手动指定</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"100\" colspan=\"2\" align=\"center\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"t1\" id=\"t1\" value=\"1\"  disabled=\"disabled\"></td>"
        	+"                <td>1</td>"
        	+"                <td><input type=\"checkbox\" name=\"t2\" id=\"t2\"  value=\"2\" disabled=\"disabled\"></td>"
        	+"                <td>2</td> "
        	+"                <td><input type=\"checkbox\" name=\"t3\"  id=\"t3\" value=\"3\" disabled=\"disabled\"></td>"
        	+"                <td>3</td>"
        	+"                <td><input type=\"checkbox\" name=\"t4\"  id=\"t4\" value=\"4\" disabled=\"disabled\"></td>"
        	+"                <td>4</td>"
        	+"                <td><input type=\"checkbox\" name=\"t5\"  id=\"t5\" value=\"5\" disabled=\"disabled\"></td>"
        	+"                <td>5</td>"
        	+"                <td><input type=\"checkbox\" name=\"t6\" id=\"t6\"  value=\"6\" disabled=\"disabled\"></td>"
        	+"                <td>6</td>"
        	+"                <td><input type=\"checkbox\" name=\"t7\"  id=\"t7\" value=\"7\" disabled=\"disabled\"></td>"
        	+"                <td>7</td>"
        	+"                <td><input type=\"checkbox\" name=\"t8\" id=\"t8\"  value=\"8\" disabled=\"disabled\"></td>"
        	+"                <td>8</td>"
        	+"                <td><input type=\"checkbox\" name=\"t9\"  id=\"t9\" value=\"9\" disabled=\"disabled\"></td>"
        	+"                <td>9</td>"
        	+"                <td><input type=\"checkbox\" name=\"t10\" id=\"t10\" value=\"10\" disabled=\"disabled\"></td>"
        	+"                <td>10</td>"
        	+"                <td><input type=\"checkbox\" name=\"t11\" id=\"t11\"  value=\"11\" disabled=\"disabled\"></td>"
        	+"                <td>11</td>"
        	+"                <td><input type=\"checkbox\" name=\"t12\"  id=\"t12\" value=\"12\" disabled=\"disabled\"></td>"
        	+"                <td>12</td>"
        	+"                <td><input type=\"checkbox\" name=\"t13\" id=\"t13\"  value=\"13\" disabled=\"disabled\"></td>"
        	+"                <td>13</td>"
        	+"                <td><input type=\"checkbox\" name=\"t14\" id=\"t14\"  value=\"14\" disabled=\"disabled\"></td>"
        	+"                <td>14</td>"
        	+"                <td><input type=\"checkbox\" name=\"t15\" id=\"t15\"  value=\"15\" disabled=\"disabled\"></td>"
        	+"                <td>15</td>"
        	+"              </tr>"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"t16\" id=\"t16\"  value=\"16\" disabled=\"disabled\"></td>"
        	+"                <td>16</td>"
        	+"                <td><input type=\"checkbox\" name=\"t17\" id=\"t17\"  value=\"17\" disabled=\"disabled\"></td>"
        	+"                <td>17</td>"
        	+"                <td><input type=\"checkbox\" name=\"t18\" id=\"t18\"  value=\"18\" disabled=\"disabled\"></td>"
        	+"                <td>18</td>"
        	+"                <td><input type=\"checkbox\" name=\"t19\" id=\"t19\"  value=\"19\" disabled=\"disabled\"></td>"
        	+"                <td>19</td>"
        	+"                <td><input type=\"checkbox\" name=\"t20\" id=\"t20\"  value=\"20\" disabled=\"disabled\"></td>"
        	+"                <td>20</td>"
        	+"                <td><input type=\"checkbox\" name=\"t21\" id=\"t21\"  value=\"21\" disabled=\"disabled\"></td>"
        	+"                <td>21</td>"
        	+"                <td><input type=\"checkbox\" name=\"t22\" id=\"t22\"  value=\"22\" disabled=\"disabled\"></td>"
        	+"                <td>22</td>"
        	+"                <td><input type=\"checkbox\" name=\"t23\" id=\"t23\"  value=\"23\" disabled=\"disabled\"></td>"
        	+"                <td>23</td>"
        	+"                <td><input type=\"checkbox\" name=\"t24\" id=\"t24\"  value=\"24\" disabled=\"disabled\"></td>"
        	+"                <td>24</td>"
        	+"                <td><input type=\"checkbox\" name=\"t25\" id=\"t25\"  value=\"25\" disabled=\"disabled\"></td>"
        	+"                <td>25</td>"
        	+"                <td><input type=\"checkbox\" name=\"t26\" id=\"t26\"  value=\"26\" disabled=\"disabled\"></td>"
        	+"                <td>26</td>"
        	+"                <td><input type=\"checkbox\" name=\"t27\"  id=\"t27\" value=\"27\" disabled=\"disabled\"></td>"
        	+"                <td>27</td>"
        	+"                <td><input type=\"checkbox\" name=\"t28\" id=\"t28\" value=\"28\" disabled=\"disabled\"></td>"
        	+"                <td>28</td>"
        	+"                <td><input type=\"checkbox\" name=\"t29\" id=\"t29\"  value=\"29\" disabled=\"disabled\"></td>"
        	+"                <td>29</td>"
        	+"                <td><input type=\"checkbox\" name=\"t30\" id=\"t30\"  value=\"30\" disabled=\"disabled\"></td>"
        	+"                <td>30</td>"
        	+"              </tr>"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"t31\" id=\"t31\"  value=\"31\"  disabled=\"disabled\"></td>"
        	+"                <td>31</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"                <td>&nbsp;</td>"
        	+"              </tr>"
        	+"            </table></td>"
        	+"          </tr>"
        	+"        </table>	  </td>"
        	+"      </tr>"
        	+"    </tbody>"
        	+"    <tbody  style=\"display:none;\">"
        	+"      <tr>"
        	+"        <td height=\"120\">"
        	+"  	  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
        	+"          <tr>"
        	+"            <td height=\"20\" width=\"20\"><div align=\"left\">"
        	+"              <input name=\"my\"   id=\"my\" type=\"radio\" value=\"my1\" checked onClick=\"my_radio_click(this)\">"
        	+"            </div></td>"
        	+"            <td width=\"570\"><div align=\"left\">每一月</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"20\" width=\"20\"><div align=\"left\">"
        	+"              <input name=\"my\"  id=\"my\" type=\"radio\" value=\"my2\"  onClick=\"my_radio_click(this)\">"
        	+"            </div></td>"
        	+"            <td height=\"20\"><div align=\"left\">手动指定</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"100\" colspan=\"2\" align=\"center\"><table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"
        	+"              <tr> "
        	+"                <td width=\"4%\"><input type=\"checkbox\" name=\"y1\" id=\"y1\" value=\"1\"  disabled=\"disabled\"></td>"
        	+"                <td width=\"11%\">1</td>"
        	+"                <td width=\"5%\"><input type=\"checkbox\" name=\"y2\" id=\"y2\"  value=\"2\" disabled=\"disabled\"></td>"
        	+"                <td width=\"12%\">2</td>"
        	+"                <td width=\"5%\"><input type=\"checkbox\" name=\"y3\"  id=\"y3\" value=\"3\" disabled=\"disabled\"></td>"
        	+"                <td width=\"12%\">3</td>"
        	+"                <td width=\"4%\"><input type=\"checkbox\" name=\"y4\" id=\"y4\"  value=\"4\" disabled=\"disabled\"></td>"
        	+"                <td width=\"13%\">4</td>"
        	+"                <td width=\"4%\"><input type=\"checkbox\" name=\"y5\" id=\"y5\"  value=\"5\" disabled=\"disabled\"></td>"
        	+"                <td width=\"13%\">5</td>"
        	+"                <td width=\"4%\"><input type=\"checkbox\" name=\"y6\" id=\"y6\"  value=\"6\" disabled=\"disabled\"></td>"
        	+"                <td width=\"13%\">6</td>"
        	+"              </tr>"
        	+"              <tr>"
        	+"                <td><input type=\"checkbox\" name=\"y7\" id=\"y7\"  value=\"7\" disabled=\"disabled\"></td>"
        	+"                <td>7</td>"
        	+"                <td><input type=\"checkbox\" name=\"y8\" id=\"y8\"  value=\"8\" disabled=\"disabled\"></td>"
        	+"                <td>8</td>"
        	+"                <td><input type=\"checkbox\" name=\"y9\" id=\"y9\"  value=\"9\" disabled=\"disabled\"></td>"
        	+"                <td>9</td>"
        	+"                <td><input type=\"checkbox\" name=\"y10\" id=\"y10\"  value=\"10\" disabled=\"disabled\"></td>"
        	+"                <td>10</td>"
        	+"                <td><input type=\"checkbox\" name=\"y11\" id=\"y11\"  value=\"11\" disabled=\"disabled\"></td>"
        	+"                <td>11</td>"
        	+"                <td><input type=\"checkbox\" name=\"y12\" id=\"y12\"  value=\"12\" disabled=\"disabled\"></td>"
        	+"                <td>12</td>"
        	+"              </tr>"
        	+"            </table></td>"
        	+"          </tr>"
        	+"        </table>	  </td>"
        	+"      </tr>"
        	+"    </tbody>"
        	+"    <tbody  style=\"display:none;\">"
        	+"      <tr>"
        	+"        <td height=\"120\">"
        	+"  	  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
        	+"          <tr>"
        	+"            <td height=\"20\" width=\"10\"><input name=\"mzhou\" id=\"mzhou\" type=\"checkbox\" value=\"checkbox\" onclick=\"zhou_checkbox_click()\"></td>"
        	+"            <td width=\"100%\"><div align=\"left\">每一周(<span class=\"STYLE1\">温馨提示：选择周循环则天循环失效！</span>)</div></td>"
        	+"          </tr>"
        	+"          <tr>"
        	+"            <td height=\"50\" colspan=\"2\" align=\"center\"><table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0 \">"
        	+"              <tr>"
        	+"                <td width=\"51\"><div align=\"left\">"
        	+"                  <input name=\"mzh\"  id=\"mzh\" type=\"radio\"   disabled=\"disabled\" value=\"mzh1\" checked onclick=\"zhou_radio_click(this)\">"
        	+"                </div></td>"
        	+"                <td colspan=\"14\"><div align=\"left\">每一周</div></td>"
        	+"                </tr>"
        	+"              <tr>"
        	+"                <td><div align=\"left\">"
        	+"                  <input name=\"mzh\" id=\"mzh\"  type=\"radio\" value=\"mzh2\"  disabled=\"disabled\" onclick=\"zhou_radio_click(this)\">"
        	+"                </div></td>"
        	+"                <td colspan=\"14\"><div align=\"left\">手动指定</div></td>"
        	+"                </tr>"
        	+"              <tr>"
        	+"                <td>&nbsp;</td>"
        	+"                <td width=\"10\"><input type=\"checkbox\" name=\"zhou1\" id=\"zhou1\"  value=\"1\" disabled=\"disabled\"></td>"
        	+"                <td width=\"35\">周一</td>"
        	+"                <td width=\"10\"><input type=\"checkbox\" name=\"zhou2\" id=\"zhou2\"  value=\"2\" disabled=\"disabled\"></td>"
        	+"                <td width=\"35\">周二</td>"
        	+"                <td width=\"10\"><input type=\"checkbox\" name=\"zhou3\" id=\"zhou3\"  value=\"3\" disabled=\"disabled\"></td>"
        	+"                <td width=\"35\">周三</td>"
        	+"                <td width=\"10\"><input type=\"checkbox\" name=\"zhou4\" id=\"zhou4\"  value=\"4\" disabled=\"disabled\"></td>"
        	+"                <td width=\"35\">周四</td>"
        	+"                <td width=\"10\"><input type=\"checkbox\" name=\"zhou5\" id=\"zhou5\"  value=\"5\" disabled=\"disabled\"></td>"
        	+"                <td width=\"35\">周五</td>"
        	+"                <td width=\"10\"><input type=\"checkbox\" name=\"zhou6\" id=\"zhou6\"  value=\"6\" disabled=\"disabled\"></td>"
        	+"                <td width=\"35\">周六</td>"
        	+"  			  <td width=\"10\"><input type=\"checkbox\" name=\"zhou7\"  id=\"zhou7\" value=\"7\" disabled=\"disabled\"></td>"
        	+"                <td width=\"35\">周日</td>"
        	+"              </tr>"
        	+"            </table></td>"
        	+"          </tr>"
        	+"        </table>	  </td>"
        	+"      </tr>"
        	+"    </tbody>"
        	+"    "
        
        	+"    "
        	+"    <tbody  style=\"display:none;\">"
        	+"      <tr>"
        	+"        <td height=\"25\"><span id=\"mspan1\"></span></td>"
        	+"      </tr>"
        	+"  	<tr>"
        	+"        <td height=\"25\"><span id=\"mspan2\"></span></td>"
        	+"      </tr>"
        	+"      <tr>"
        	+"        <td height=\"58\"><div align=\"center\">"
        	+"          <input type=\"button\" id =\"submitCron\" name=\"Submit\" value=\"生成表达式\" >"
        	+"        </div></td>"
        	+"      </tr>"
        	+"    </tbody>"
        	+"  </table>"	;
    	str += "</div>";   
    	if($("#_contentsCron").length==0){
    		$(tt).after(str);
            _fieldname = $(tt);   
            var ttop = tt.offsetTop;    //TT控件的定位点高   
            var thei = tt.clientHeight;    //TT控件本身的高   
            var tleft = tt.offsetLeft;    //TT控件的定位点宽   
            $("#submitCron").click(function(){
            	getvalues(tt);
        	});
            $("#cromSec0").click(function(){
            	secBoard(0,this);
        	});
            $("#cromSec1").click(function(){
            	secBoard(1,this);
        	});
            $("#cromSec2").click(function(){
            	secBoard(2,this);
        	});
            $("#cromSec3").click(function(){
            	secBoard(3,this);
        	});
            $("#cromSec4").click(function(){
            	secBoard(4,this);
        	});
            $("#cromSec5").click(function(){
            	secBoard(5,this);
        	});
            $("#cromSec6").click(function(){
            	secBoard(6,this);
        	});
            if($(tt).val()!=""){
            	
            }
            $("#_contentsCron").css("margin-top",ttop+thei);
    		
    	}
    	
    	
        
    } 
    var  secBoard = function(n,t)
    {
    	for(var i=0;i<6;i++)
    	$("td[name='td1']")[i].className="sec1";
    	t.className="sec2";
    	for(var i=0;i<6;i++)
    	 document.getElementById('mainTable').children[i].style.display="none";
    	document.getElementById('mainTable').children[n].style.display="block";
    }
    /**
     *  获取url 上的参数
     */
    var getHashStr = function(name) {
	    var url = location.hash; //获取url中"?"符后的字串
	    var theRequest = new Object();
	    if (url.indexOf("?")) {
	        var str = url.substr(url.indexOf("?") + 1);
	        var strs = str.split("&");
	        for (var i = 0; i < strs.length; i++) {
	            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
	        }
	    }
	    return theRequest[name];
	};
    
	/**
	 *  校验是否为空
	 */
	var checkEmpty = function (val){
        return $.trim(val) == '' ? true : false;
    };

    // 封装的方法
    return {
        alert:alert,
        confirm:confirm,
        prompt:prompt,
        message:message,
        toJsonObject:toJsonObject,
        getComponent:getComponent,
        contains:contains,
        get:get,
        _SetTime:_SetTime,
        _setQurtz:_setQurtz,
        _select:_select,
        _clear:_clear,
    	post:post,
    	post2:post2,
    	validform:validform,
    	layerMsg:layerMsg,
    	getJson:getJson,
    	getHashStr:getHashStr,
    	layerConfirm:layerConfirm,
    	layerWin:layerWin,
    	checkEmpty:checkEmpty,
    	get_query_list:get_query_list
    };
});