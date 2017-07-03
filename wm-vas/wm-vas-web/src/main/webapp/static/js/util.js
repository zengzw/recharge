(function(a){a.fn.serializeJson=function(c){var b={};var d=this.serializeArray();a(d).each(function(){if(b[this.name]){if(a.isArray(b[this.name])){b[this.name].push(this.value)}else{b[this.name]=[b[this.name],this.value]}}else{if((c&&c.indexOf(this.name)>-1)){b[this.name]=[this.value]}else{b[this.name]=this.value}}});return b}})(jQuery);

$.acceptParam=function(c){var b=location.href;var d=b.substring(b.indexOf("?")+1,b.length).split("&");var a={};for(i=0;j=d[i];i++){a[j.substring(0,j.indexOf("=")).toLowerCase()]=j.substring(j.indexOf("=")+1,j.length)}var e=a[c.toLowerCase()];if(typeof(e)=="undefined"){return""}else{return e}};

function show(b,a){$.messager.show({title:"",msg:'<i class="i-l i-l-'+b+'"></i><span>'+a+"</span>",showType:"fade",timeout:1000,height:150,width:400,style:{right:"",bottom:""}})};

function isNotBlank(str){if(undefined===str||""===str||null===str){return false}else{return true}};

//自定义验证
$.extend($.fn.validatebox.defaults.rules,{
	phoneRex:{
	  validator:function(value){
		  var rex=/^1[3-8]+\d{9}$/;
		  //var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		  //区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
		  //电话号码：7-8位数字： \d{7,8
		  //分机号：一般都是3位数字： \d{3,}
		   //这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/		 
		  var rex2=/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
		  if(rex.test(value)||rex2.test(value))
		  {
		    // alert('t'+value);
		    return true;
		  }else
		  {
		   //alert('false '+value);
		     return false;
		  }
	  },
	  message: '请输入正确电话或手机格式'
	},
	inputNumber:{
		validator:function(value){
			  //正则表达式判断数字,0-100,包含0和100,且小数点后最多有2位
			  //var rex = /^(\d{1,2}(\.\d{1,2})?|100)$/;
			  var reg = /^\d\.([0-9]{1,2}|[0-9][0-9])$|^[0-9]\d{0,1}(\.\d{1,2}){0,1}$|^100(\.0{1,2}){0,1}$/
			  if(reg.test(value))
			  {
			    return true;
			  }else{
			     return false;
			  }
		  },
		  message: '只能输入0-100,且小数点为两位'
	}
});



//扩展 datagrid
$.extend($.fn.datagrid.methods, {
    editCell : function(jq, param) {
        return jq.each(function() {
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields', true).concat(
                    $(this).datagrid('getColumnFields'));
            for ( var i = 0; i < fields.length; i++) {
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for ( var i = 0; i < fields.length; i++) {
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

