/**
 * 列表组件
 */

define(['avalon'],function(avalon){	
	function heredoc(fn) {
        return fn.toString().replace(/^[^\/]+\/\*!?\s?/, '').
                replace(/\*\/[^\/]+$/, '').trim().replace(/>\s*</g, '><');
    }
	// 列表组件
	avalon.component('ms-tablelist', {
		// 组件模板
		template: heredoc(function () {  
			/*
			  <table>
            <thead>
            <tr>
            <th :for="(index,key) in @zcolumns" 
                :click="@sortBy(key)" 
                :class="{active: @sortKey == key}" :visible="index==0 ? false:true">
            {{key}}
            <span class="arrow" :class="@sortOrders[key] > 0 ? 'asc' : 'dsc'"></span>
            </th>
            <th :if="@isHandle">
                                   操作
            </th>
            </tr>
            </thead>
            <tbody>
            <tr :for="(i,entry) in @data | orderBy(@sortKey,@sortOrders[@sortKey])">
            <td :for="(index,key) in @columns " :visible="index==0 ? false:true" ms-attr="{name:key}">
            {{entry[key]}}
            </td>
            <td :if="@isHandle" class="btn_op">
               <div :for="el in @handleData">
               <a :class="el.icon" href="javascript:void(0)" :click="@cbProxy($event, el,i)" >{{el.text}}</a>
               <span :visible="index%2==1 ? false : true">|</span>
               </div>
            </td>
            </tr>
            </tbody>
            </table>
			 */
		}),
		defaults:{  // 编写跟模板的相关的文件
			columns: [],
			zcolumns:[],
            data: [],
            isHandle:'',
            keyColumn:'',
            handleData:[],
            sortKey: '',
            sortOrders: {},
            sortBy: function(key) {
                this.sortKey = key;
                this.sortOrders[key] = this.sortOrders[key] * -1;
            },
            cbProxy:avalon.noop, /*交给用户自己定义*/
            filter: function(el,index,arg,t) {
		          var reg = new RegExp(avalon.escapeRegExp(arg), 'i');
		          return  reg.test(el[t]);		   
			},
			onInit: function() {
//                var sortOrders = {};
//                this.columns.forEach(function(key) {
//                    sortOrders[key] = 1;
//                });
//                this.sortOrders = sortOrders;
				// 如果列表加载过于缓慢，重新分配
				if($(".right_comment").height()+50 < $(window).height()-50){
					$(".content").height($(window).height()-50);
					$(".org").height($(window).height()-40);
				}else{
				  if($(".org").length == 0)
                     $(".content").height($(".right_comment").height()+50);
				  else 
					  $(".content").height($(".listmember").height()+80);
				  
                  $(".org").height($(".listmember").height()+20);
                }
            },
            onViewChange:function(){
            	// 第一次，如果列表加载过于缓慢，重新分配
            	if($(".right_comment").height()+50 < $(window).height()-50){
					$(".content").height($(window).height()-50);
					$(".org").height($(window).height()-40);
            	}else{
            		if($(".org").length == 0)
                        $(".content").height($(".right_comment").height()+50);
   				   else{
   					  if($(".listmember").height() < $(window).height()){
   						$(".content").height($(window).height()+30);
   						$(".org").height($(window).height()-40);
   					  }else{
   					     $(".content").height($(".listmember").height()+80);
                         $(".org").height($(".listmember").height()+20);
   					  }
   			    	} 
            	}
            }
		}
	});
});