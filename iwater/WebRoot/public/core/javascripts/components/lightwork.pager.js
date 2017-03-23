/**
 *  分页组件
 */

define(['avalon','util'],function(avalon,util){
	
	function heredoc(fn) {
        return fn.toString().replace(/^[^\/]+\/\*!?\s?/, '').
                replace(/\*\/[^\/]+$/, '').trim().replace(/>\s*</g, '><');
    }
	
	// 分页组件
	avalon.component('ms-pager',{
		// 组件html模板
		template:heredoc(function(){
			/*
			 <ul class="pagination">
			    <li class="first" 
			        ms-class='{disabled: @currentPage === 1}'>
			        <a ms-attr='{href:@getHref("first"),title:@getTitle("first")}'
			           ms-click='cbProxy($event, "first")'
			           >
			            {{@firstText}}
			        </a>
			    </li>
			    <li class="prev" 
			        ms-class='{disabled: @currentPage === 1}'>
			        <a ms-attr='{href:@getHref("prev"),title:@getTitle("prev")}'
			           ms-click='cbProxy($event, "prev")'
			           >
			            {{@prevText}}
			        </a>
			    </li>
			    <li ms-for='page in @pages' 
			        ms-class='{active: page === @currentPage}' >
			        <a ms-attr='{href:@getHref(page),title:@getTitle(page)}'
			           ms-click='cbProxy($event, page)'
			           >
			            {{page}}
			        </a>
			    </li>
			    <li class="next" 
			        ms-class='{disabled: @currentPage === @totalPages}'>
			        <a ms-attr='{href:@getHref("next"),title: @getTitle("next")}'
			           ms-click='cbProxy($event, "next")'
			           >
			            {{@nextText}}
			        </a>
			    </li>
			    <li class="last" 
			        ms-class='{disabled: @currentPage === @totalPages}'>
			        <a ms-attr='{href:@getHref("last"),title: @getTitle("last")}'
			           ms-click='cbProxy($event, "last")'
			           >
			            {{@lastText}}
			        </a>
			    </li>
			</ul>			 
		    */
		}),
		// 组件对外暴露的属性和方法
		defaults:{
			    is_ie: true,
			    is_more: true,
			    hash:'',
		        getHref: function (a) {
		        	if (this.is_more) {
		                if (location.hash) {
		                    var search = location.hash,
		                        page   = util.getHashStr('page');
		                    if (page) { // 存在就替换
		                        search = search.replace('page=' + page, 'page=' + this.toPage(a));
		                        return search;
		                    }
		                    else { // 不存在就叠加
		                        return location.hash + '?page=' + this.toPage(a);
		                    }
		                }
		                else {
		                    return '#?page=' + this.toPage(a);
		                }
		            }
		            else {
		                return '#page-' + this.toPage(a);
		            }
		        },
		        getTitle: function (title) {
		            return title;
		        },
		        isDisabled : function (name, page) {
		            return this.$buttons[name] = (this.currentPage === page);
		        },
		        $buttons: {},
		        showPages: 5,    /* 显示页数  */
		        pages: [],
		        totalPages: 15,  /* 总页数  */
		        currentPage: 1,  /* 当前页码  */
		        firstText: '第一页',  /* 1 对应名称  */
		        prevText: '上一页',   /* 2 对应名称  */
		        nextText: '下一页',   /* 3 对应名称  */
		        lastText: '最后一页',  /* 4 对应名称  */
		        toPage: function (p) {  
		            var cur = this.currentPage;
		            var max = this.totalPages;
		            switch (p) {
		                case 'first':
		                    return 1;
		                case 'prev':
		                    return Math.max(cur - 1, 1)/*从第一页开始*/;
		                case 'next':
		                    return Math.min(cur + 1, max);
		                case 'last':
		                    return max;
		                default:
		                    return p;
		            }
		        },
		        onPageClick: avalon.noop ,
		        cbProxy:  function (e, p) {
		            var cur = this.toPage(p);
		            if (this.$buttons[p] || p === this.currentPage) {
		                if (cur === 1) {
		                    return this.onPageClick(e, cur);
		                }
		                e.preventDefault();
		                return; //disabled, active不会触发
		            }
		            /*替换链接改变hash的形式*/
		            window.location.hash = this.getHref(p);
		            this.render(cur);
		            return this.onPageClick(e, cur);
		        },
		        render     : function (cur) {/*更新页码*/
		            var obj = getPages.call(this, cur);
		            this.currentPage = obj.currentPage;
		            this.pages = obj.pages;
		        },
		        /*此处供正常单页应用*/
		        rpage: function () {
		            return this.is_more ? /(?:#|\?)page\=(\d+)/ : /(?:#|\?)page\-(\d+)/;
		        },
		        cur: function () { /*正确获取匹配页码*/
		            var cur = this.currentPage;
		            var match = this.rpage && location.href.match(this.rpage());
		            if (match && match[1]) {
		                var cur = ~~match[1];
		                if (cur < 0 || cur > this.totalPages) {
		                    cur = 1;
		                }
		            }
		            return cur;
		        },
		        onInit: function (e) {
		        	var that = this;
		            /**复杂单页应用，切换选项卡，重置页码
		             * 但切换选项卡或者数据页数变化时，重置页码
		             * */
		            this.$watch('totalPages', function () {
		                that.render(that.cur());
		            });
		            this.$watch('currentPage', function () {
		                that.render(that.cur());
		            });
		            if (!that.is_ie && !that.is_more) {
		                /**
		                 * 完美支持单页一分页组件（仅支持现代浏览器）
		                 * 浏览器回退键功能启动
		                 * */
		                window.addEventListener("hashchange", function () {
		                    that.cbProxy(window.event, that.cur());
		                }, false);
		            }
		            else if (!that.is_ie && that.is_more) {
		                /**
		                 * 支持单页多分页组件（仅支持现代浏览器）
		                 * 此功能适用于单页多分页情景，开启此功能，可配合路由。
		                 * 监听location.hash触发特定的onPageClick
		                 * */
		                window.addEventListener("hashchange", function () {
		                    that.render(that.cur());
		                }, false);
		            }
		            /*进入页面预载入页码*/
		            that.render(that.cur());
		        }
		}
		
	});
	
	function getPages(currentPage) {
	    var pages = [];
	    var s = this.showPages;
	    var total = this.totalPages;
	    var half = Math.floor(s / 2);
	    var start = currentPage - half + 1 - s % 2;
	    var end = currentPage + half;

	    // handle boundary case
	    if (start <= 0) {
	        start = 1;
	        end = s;
	    }
	    if (end > total) {
	        start = total - s + 1;
	        end = total;
	    }

	    var itPage = start;
	    while (itPage <= end) {
	        pages.push(itPage);
	        itPage++;
	    }

	    return {currentPage: currentPage, pages: pages};
	}
	

});