/*
 *  登录验证封装
 *  code by diana 2017.1.10
 */

define(['util'], function (util){
	// 校验的接口地址
    var ajax = {
        //login: './system/login/judge/'
        login:'./login'
    };
    // 校验类
    function LoginFormValid(options){
        this.opt = options;
        this.form = this.opt.form;
        this.type = this.opt.type;
        this.fields = $('input', this.form);
        this.submit = false;

        this.init();
    }
    
    // 具体方法实现
    LoginFormValid.prototype = {
        init: function (){
            // 给每个字段绑定验证属性
            this.fields.each(function (){
                $(this).data('__check__', {
                    check: false,   // 是否执行过验证
                    valid: false,   // 是否验证通过
                    ajax: false     // 是否正在发送ajax验证
                });
            });
            switch (this.type){
                // 登录
                case 'login':
                    this.loginValid(this.form, this.type);
                    break;
                // 重置密码
                case 'resetPassword':
                    this.resetPasswordValid(this.form, this.type);
                    break;               
            }
        },

        loginValid: function (form, form_type){
            var _this = this;
            // 失去焦点验证
            this.fields.bind('blur', function (){
                // console.log(form_type + '_' + this.name)
                if ($(this).data('__check__').check === false)
                    _this[form_type + '_' + this.name]($(this), $(this).siblings('.message-box'));
            })
            // 内容改变重新验证
            .bind('input propertychange', function (){
                $(this).data('__check__').check = false;
            })
            .on('keydown', function (e){
                if (e.keyCode == 13){
                    _this.fields.filter(':visible').blur();
                    form.find('.btn_submit').click();
                }
            });

            // 提交
            form.find('.btnsubmit').bind('click', function (){
                var _submit = $(this),
                    isValid = true;
                
                _this.fields.filter(':visible').each(function (){
                    //由于浏览器记住密码，特殊判断下 输入框是否有内容
                	var check = false;
                	if(_this.check_empty($(this).val())){
	                	 check = $(this).data('__check__').valid;
	                    if (!check){
	                        $(this).trigger('blur');
	                        isValid = false;
	                    }
                	}
                });
                
                if(isValid){
                    _this.ajaxSubmit(_submit);
                }
            });
            
            // 回车提交
            $("body").keydown(function() {
                if (event.keyCode == "13") {//keyCode=13是回车键
                	form.find('.btnsubmit').click();
                };
            });  
        },
       
        ajaxSubmit: function (btnSubmit){
            this[this.type + 'Submit'] && this[this.type + 'Submit'](btnSubmit);
        },
        
        loginSubmit: function (btnSubmit){
            var _this = this;
            var input = _this.fields.filter('[name=account]'),
            // 这里要根据接口修改参数------
            params = {
               "type": 'account',
               "j_username": $.trim(input.val()),
               "j_password": $.trim(_this.fields.filter('[name=password]').val())
            };
            
           util.post(ajax.login, params, function(result) {  
        	   //console.log(result);
//        	   var $field_p = _this.fields.filter('[name=password]');
//        	   $field_p.data('__check__').check = false;
//               var $field_a = _this.fields.filter('[name=account]');
//               $field_a.data('__check__').check = false;
//               // 根据具体情况修改错误代码
//               var aJson=eval("("+result+")");
//
               if(result.code === 0 ){
		    	   util.layerMsg("登录成功");
		    	   window.location =  result.page;
		       }
		       else
		    	  util.layerMsg("登录失败，请检查账号密码！");
		    });   
        },
        // 登录
        // 邮箱/手机/账号信息
        login_account: function ($field, $msg){
            $field.data('__check__').check = true;
            $msg.html('');
            if (this.check_empty($field.val())){
                $field.addClass('input_error');
                $msg.html('<span class="checkerror">请输入' + $field[0].title + '</span>');
                $field.data('__check__').valid = false;
            }
        },
        // 密码
        login_password: function ($field, $msg){
            $field.removeClass('input_error');
            $field.data('__check__').check = true;
            $msg.html('');
            if (this.check_empty($field.val())){
                $field.addClass('input_error');
                $msg.html('<span class="checkerror">请输入' + $field[0].title + '</span>');
                $field.data('__check__').valid = false;
            } else {
                $msg.html('');
                $field.data('__check__').valid = true;
            }
        },

        // 验证是否为空
        check_empty: function (val){
            return $.trim(val) == '' ? true : false;
        },
        check_mail: function (val){
            return /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/.test($.trim(val));
        },
        check_num: function (val){
            return ! isNaN($.trim(val));
        },
        check_username: function (val){
            return /^[\u4e00-\u9fa5\w-－]+$/.test($.trim(val));
        },
        check_username_len: function (val){
            var len = this.strlen($.trim(val));
            return len >= 3 && len <= 15;
        },
        check_valid_code: function (val){
            return /^[0-9]{6}$/.test($.trim(val));
        },
        check_password: function (val){
            return /^(?=.*[a-zA-Z]+)(?=.*[0-9]+)[\w\W]{8,16}$/.test($.trim(val));
        },
        check_equal: function (val, val2){
            return val == val2;
        },

        check_ajax: function (opt){
            var _result = null;
            var ajaxOpt = $.extend({
                dataType: 'json',
                type: 'get'
            }, opt);
            $.ajax(ajaxOpt);
        },

        pwdStrong: function (pwd){
            var strong = 0,
                pwd = $.trim(pwd);
            if (this.check_password(pwd)){
                if (pwd.length > 10){
                    strong++;
                }
                if (/^(?=.*[a-z]+)(?=.*[0-9]+)(?=.*[A-Z]+)[\w\W]{8,16}$/.test(pwd)){
                    strong++;
                }
                if (/\W/.test(pwd)){
                    strong++;
                }
                return strong;
            } else {
                return -1;
            }
        },

        parseURL: function (url){
            var a =  document.createElement('a');
            a.href = url;
            return {
                source: url,
                protocol: a.protocol.replace(':',''),
                host: a.hostname,
                port: a.port,
                query: a.search,
                params: (function(){
                    var ret = {},
                        seg = a.search.replace(/^\?/,'').split('&'),
                        len = seg.length, i = 0, s;
                    for (;i<len;i++) {
                        if (!seg[i]) { continue; }
                        s = seg[i].split('=');
                        ret[s[0]] = s[1];
                    }
                    return ret;
                })(),
                file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1],
                hash: a.hash.replace('#',''),
                path: a.pathname.replace(/^([^\/])/,'/$1'),
                relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1],
                segments: a.pathname.replace(/^\//,'').split('/')
            };
        },

        /**
         * 获取url参数值
         * @param  {string} name [param]
         * @return {string}      [paramValue]
         */
        getQueryParam: function (name){
            name = name.replace(/[\[]/, '\\\[').replace(/[\]]/, '\\\]');
            var regexS = '[\\?&]' + name + '=([^&#]*)';
            var regex = new RegExp(regexS);
            var resluts = regex.exec(window.location.href);
            if (resluts == null) {
                return null;
            } else {
                return resluts[1];
            }
        },

        btnDisabled: function ($btn, iSecond){},

        strlen: function(str) {
            var len;
            var i;
            len = 0;
            for (i = 0; i < str.length; i++) {
                if (str.charCodeAt(i) > 255) len += 2;
                else len++;
            }
            return len;
        },

        successtrack: function (action, display, type){
            var track = 'web-' + action + '-' + display + 'success-' + type;
            if (this.opt.databnipg) {
                track += '-' + this.opt.databnipg.split('-').join('');
            }
            return track;
        }
    };

    $.fn.extend({
        // [string] arg
        loginFormValid: function (arg, callback){
            var opt = {
                form: this,
                type: 'login'
            };
            if (typeof arg === 'string') {
                opt.type = arg;
            } else if (typeof arg === 'object'){
                $.extend(opt, arg);
            }
            callback && (opt.callback = callback);
            new LoginFormValid(opt);
        }
    });
    // return loginFormValid;
});
