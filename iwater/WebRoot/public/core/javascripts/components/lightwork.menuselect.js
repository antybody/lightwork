/**
 *  多条件的筛选框
 */
define(['avalon','WdatePicker'], function (avalon){

	function heredoc(fn) {
		return fn.toString().replace(/^[^\/]+\/\*!?\s?/, '').
			replace(/\*\/[^\/]+$/, '').trim().replace(/>\s*</g, '><')
	}
	// 组件模板，也可以定义在html里面
	avalon.component('ms-menuselect', {
		// 组件模板
		template: heredoc(function () {
		/*
			<div class="list_state">
				<div ms-for="(index,el) in @menu | filterBy(@filter,'self','type')">
					<span class="f_name">{{el.text}}：</span>
					<strong  ms-visible="el.ismore" ms-click="@openMenu(el)">更多</strong>
					<ul ms-css="{height:el.isopen === 'true' ? 'auto':'25px'}">
						<li ms-for="(in,elm) in el.subQuery | filterBy(@filter,'input','subtype')">
							<span>{{elm.text}}：<input type="text" :attr="{'data-key':elm.columnkey}" ms-duplex="@elm.columnval"></span>
						</li>
						<li ms-for="elm in el.subQuery | filterBy(@filter,'time','subtype')">
							<span>{{elm.text}}：<input ms-duplex="@elm.columnval" class="Wdate" ms-focus="@setTimeFmt(elm)" type="text" :attr="{'data-key':elm.columnkey}"></span>
						</li>
					</ul>
				</div>
				<div ms-for="(index,el) in @menu | filterBy(@filter,'db','type')">
					<span class="f_name">{{el.text}}：</span>
					<strong ms-class="el.isopen" ms-visible="el.ismore" ms-click="@openMenu(el)">更多</strong>
					<ul class="list_common">
						<li ms-for="elm in el.subQuery">
							<input ms-duplex-checked="elm.ischecked" ms-attr="{type:el.ismoreselect,checked:elm.ischecked,'data-key':elm.columnkey,'data-val':elm.columnval}">
							<a href="javascript:void(0);" ms-click="@aClickEvent(elm,$event)">{{elm.text}}</a>
						</li>
					</ul>
				</div>
				<div class="last">
					<div style="text-align:right;color:#118ecc">
						<span ms-click="@showMore($event)" ms-text="@queryListExpand ? '【收起条件】' : '【更多条件】'"></span>
						<span ms-click="@queryList($event)">【搜索】</span>
						<span ms-click="@clearAll($event)">【清空全部】</span>
					</div>
				</div>
			</div>
		*/
		}),
		// 组件暴露在外面的方法或属性
		defaults: {
			menu: [],
			num:3,
			queryListExpand:false,
			checkAttr:false,
			openMenu: function (el) {
				if(el.isopen === "true")
					el.isopen = 'false';
				else
					el.isopen = 'true';
//				el.isopen = !el.isopen;
			},
			showMore:function() {
				this.queryListExpand =! this.queryListExpand;
				// 如果当前显示的行数小于总行数，那么则把总行数传给默认显示行数
				if(this.num < this.menu.length +1){
					this.num = this.menu.length -1;
				}
				else{
					this.num = 3; // 如果当前显示的行数= 总行数，那么则重置当前行数
				}
			},
			setTimeFmt: function(elm){
				//subformat:yyyyMMdd HH:mm:ss
				WdatePicker({lang:'zh-cn',dateFmt:elm.subformat});
			},
			filter: function(el,index,arg,t) {
				var reg = new RegExp(avalon.escapeRegExp(arg), 'i');
				return  reg.test(el[t]);		   
			},
			aClickEvent:function(elm,e){
				elm.ischecked = !elm.ischecked;
			},
			onInit:function(){
				avalon.log("我正在被初始化");
			},
			onReady:function(){
			},
			queryList:avalon.noop,
			clearAll:avalon.noop
		}
	});
});
