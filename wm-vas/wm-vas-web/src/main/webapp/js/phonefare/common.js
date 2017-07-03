//发布状态
var paublicStatus = {
	1:'已发布',
	0:'未发布'
};

//排序方法
function getSortFun(order, sortBy) {
	  var ordAlpah = (order == 'asc') ? '>' : '<';
	  var sortFun = new Function('a', 'b', 'return a.' + sortBy + ordAlpah + 'b.' + sortBy + '?1:-1');
	  return sortFun;
}

//排序因子
function NumAscSort(a,b){
	return a-b;
}
//删除左右两端的空格
String.prototype.trim = function() {
	  return this.replace(/^\s\s*/, '').replace(/\s\s*$/, '');
};
//数组中是否包含某个元素
function inArray(arr, element) {
	for (var i=0; i< arr.length;i++) {
		if (arr[i] == element)
			return true;
	}
	return false;
}

//全国省份表
var provinceTable = {
		A:'安徽', B:'北京',C:'重庆',F:'福建', G:'甘肃,广东,广西,贵州',
		H:'海南,河北,河南,黑龙江,湖北,湖南',J:'吉林,江苏,江西',L:'辽宁',
		N:'内蒙古,宁夏',Q:'青海',S:'山东,山西,陕西,上海,四川',T:'天津',
		X:'西藏,新疆',Y:'云南',Z:'浙江'
};

//省份编码表
var provinceCode = {
		'安徽':'226424', '北京':'01', '重庆':'554872', '福建':'246651',
		'甘肃':'699446', '广东':'506129' ,'广西':'533239', '贵州':'625375',
		'海南':'550911', '河北':'12961', '河南':'370262', '黑龙江':'146177', 
		'湖北':'424782', '湖南':'456886','吉林':'133475', '江苏':'168234', 
		'江西':'264859', '辽宁':'115709', '内蒙古':'99852', '宁夏':'723429',
		'青海':'718323', '山东':'287701','山西':'67974','陕西':'669009',
		'上海':'162337','四川':'566973','天津':'7150','西藏':'662773','新疆':'726550',
		'云南':'646977','浙江':'191957'
};