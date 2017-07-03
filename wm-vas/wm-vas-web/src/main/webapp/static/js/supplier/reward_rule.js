var editingId;

function del(id) {
    $.messager.confirm({
        width: 400,
        title: '删除提示',
        msg: '<div class="content">你确认要删除这条记录吗?</div>',
        ok: '<i class="i-ok"></i> 确定',
        cancel: '<i class="i-close"></i> 取消',
        fn: function (r) {
            if (r) {
                $.ajax({
                    type: 'post',
                    url: '../../government/deleteGovernment.do',
                    async: false,
                    data: {
                        id: id
                    },
                    success: function (result) {
                        showByMsg('info', '删除成功!');
                        reloadGrid();
                    },
                    error: function (e) {
                        alert(e);
                    }
                });
            } else {

            }
        }
    });
}

function view(supplierCode) {
    location.href = 'view.html?supplierCode=' + supplierCode;
}

function edit(supplierCode) {
    location.href = 'edit.html?supplierCode=' + supplierCode;
}

function areaView(supplierCode, company) {
    location.href = 'area_list.html?supplierCode=' + supplierCode + "&company=" + company;
}

function reloadGrid() {
    $('#dataGrid').datagrid('reload');
}

function showByMsg(showType, msg) {
    $.messager.show({
        title: '',
        msg: '<i class="i-l i-l-' + showType + '"></i><span>' + msg + '</span>',
        showType: 'fade',
        timeout: 1000,
        height: 150,
        width: 300,
        style: {
            right: '',
            bottom: ''
        }
    });
}
function add() {
    location.href = 'add.html';
}


function saveReWard_MPCZ(row) {
	
    if (!$('#dataGrid_MPCZ_FORM').form('validate')) {
        return;
    }
    $.ajax({
        type: 'post',
        url: '../../vas/business/update/profit',
        data: {
            'businessCode': 'MPCZ',
            'supplierCode': row.supplierCode,
            'totalShareRatio': row.totalShareRatio,
            // 'platformShareRatio': row.platformShareRatio,
            // 'areaShareRatio': row.areaShareRatio,
            'shareWay': 3 //缴电费传3，差额分成
        },
        success: function (data) {
        	loadGrid_MPCZ();
        }
    });
}

function saveReWard_DJDF(row) {
	
    if (!$('#dataGrid_DJDF_FORM').form('validate')) {
        return;
    }
    $.ajax({
        type: 'post',
        url: '../../vas/business/update/profit',
        data: {
            'businessCode': 'DJDF',
            'supplierCode': row.supplierCode,
            'totalShareRatio': row.totalShareRatio,
            // 'platformShareRatio': row.platformShareRatio,
            // 'areaShareRatio': row.areaShareRatio,
            'shareWay': 3 //缴电费传3，差额分成
        },
        success: function (data) {
        	loadGrid_DJDF();
        }
    });
}

function saveReWard_HCP(row) {
    if (!$('#dataGrid_HCP_FORM').form('validate')) {
        return;
    }
    $.ajax({
        type: 'post',
        url: '../../vas/business/update/profit',
        data: {
            'businessCode': 'HCP',
            'supplierCode': row.supplierCode,
            'totalShareRatio': row.totalShareRatio,
            'platformShareRatio': row.platformShareRatio,
            'areaShareRatio': row.areaShareRatio,
            'shareWay': 3 //缴电费传3，差额分成
        },
        success: function (data) {
        	loadGrid_HCP();
        }
    });
}

function loadGrid_MPCZ(){
	$('#dataGrid_MPCZ').datagrid({
	    url: '../../vas/business/suppliers',
	    striped: false,
	    fitColumns: true,
	    singleSelect: true,
	    rownumbers: false,
	    //pagination: true,
	    method: "GET",
	    //rownumbers:true,
	    //pageList: [10, 20, 50],
	    //pageSize: 10,
	    onBeforeEdit: function (index, row) {
	    },
	    onEndEdit: function (index, row) {
	        console.error("====end edit");
	        saveReWard_DJDF(row);
	    },
	    onAfterEdit: function (index, row) {
	    },
	    onBeginEdit: function (index, row) {
	        var editors = $('#dataGrid_MPCZ').datagrid('getEditors', index);
	        var n1 = $(editors[0].target);
	        //var n2 = $(editors[1].target);
	        //var n3 = $(editors[2].target);
	        
	        /*n2.add(n3).textbox({
	            onChange: function () {
	                console.info('ssssssssssssssssssss=========================' + n3);
	            }
	        });
	        n1.add(n2).textbox({
	            onChange: function () {
	                console.info('ssssssssssssssssssss=========================' + n2);
	            }
	        });*/
	    },
	    onClickRow: function (index, row) {
	        console.log(row)
	        if (undefined != editingId) {
	            $('#dataGrid_MPCZ').datagrid('endEdit', editingId);
	            editingId = index;
	            $('#dataGrid_MPCZ').datagrid('beginEdit', editingId);
	        }
	        else {
	            $('#dataGrid_MPCZ').datagrid('beginEdit', index);
	            editingId = index;
	        }
	        ;

	        var editors = $('#dataGrid_MPCZ').datagrid('getEditors', index);

	        //console.log(editors[0].target);

	        editors[0].target.bind('change', function () {
	            console.info("0");
	        });

	        /*editors[1].target.bind('change', function () {
	            console.info("1");
	        });

	        editors[2].target.bind('change', function () {
	            console.info("2");
	        });*/

	    },
	    onLoadSuccess: function () {
	        var _this = $(this);
	        $('td[field=platformShareRatio]').on("keyup", 'input.textbox-text', function () {
	            $(this).parents('tr').find('td[field=areaShareRatio] input.textbox-f').textbox('setValue', (100 - $(this).val()).toFixed(2));
	        });

	        $('td[field=areaShareRatio]').on("keyup", 'input.textbox-text', function () {
	            $(this).parents('tr').find('td[field=platformShareRatio] input.textbox-f').textbox('setValue', (100 - $(this).val()).toFixed(2));
	        });
	    },
	    loadFilter: function (data) {
	        return data.data;
	    },
	    queryParams: {
	        'businessCode': 'MPCZ'
	    },
	    columns: [[
	        {field: 'supplierName', title: '增值服务供应商', width: 150},
	        {
	            field: 'totalShareRatio',
	            title: '提点系数(%)',
	            width: 60,
	            editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
	            formatter: function (value, data, index) {
	                if (value > 0) {
	                    return parseFloat(value).toFixed(2);
	                } else {
	                    return "0.00";
	                }
	            }
	        },
	        // {
	        //     field: 'platformShareRatio',
	        //     title: '平台分成比(%)',
	        //     width: 80,
	        //     editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
	        //     formatter: function (value, data, index) {
	        //         if (value > 0) {
	        //             return parseFloat(value).toFixed(2);
	        //         } else {
	        //             return "0.00";
	        //         }
	        //     }
	        // },
	        // {
	        //     field: 'areaShareRatio',
	        //     title: '县域分成比(%)',
	        //     width: 60,
	        //     editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
	        //     formatter: function (value, data, index) {
	        //         if (value > 0) {
	        //             return parseFloat(value).toFixed(2);
	        //         } else {
	        //             return "0.00";
	        //         }
	        //     }
	        // },
	        {
	            field: 'cz',
	            title: '操作',
	            align: 'center',
	            width: 200,
	            fitColumns: true,
	            formatter: function (value, data, index) {
	                var temp = "";
	                temp += '<a class="btn btn-1" href="javascript:;">保存设置</a>';
	                return temp;
	            }
	        }
	    ]],
	});
}

function loadGrid_DJDF(){
	$('#dataGrid_DJDF').datagrid({
	    url: '../../vas/business/suppliers',
	    striped: false,
	    fitColumns: true,
	    singleSelect: true,
	    rownumbers: false,
	    //pagination: true,
	    method: "GET",
	    //rownumbers:true,
	    //pageList: [10, 20, 50],
	    //pageSize: 10,
	    onBeforeEdit: function (index, row) {
	    },
	    onEndEdit: function (index, row) {
	        console.error("====end edit");
	        saveReWard_DJDF(row);
	    },
	    onAfterEdit: function (index, row) {
	    },
	    onBeginEdit: function (index, row) {
	        var editors = $('#dataGrid_DJDF').datagrid('getEditors', index);
	        var n1 = $(editors[0].target);
	        //var n2 = $(editors[1].target);
	        //var n3 = $(editors[2].target);
	        
	        /*n2.add(n3).textbox({
	            onChange: function () {
	                console.info('ssssssssssssssssssss=========================' + n3);
	            }
	        });
	        n1.add(n2).textbox({
	            onChange: function () {
	                console.info('ssssssssssssssssssss=========================' + n2);
	            }
	        });*/
	    },
	    onClickRow: function (index, row) {
	        console.log(row)
	        if (undefined != editingId) {
	            $('#dataGrid_DJDF').datagrid('endEdit', editingId);
	            editingId = index;
	            $('#dataGrid_DJDF').datagrid('beginEdit', editingId);
	        }
	        else {
	            $('#dataGrid_DJDF').datagrid('beginEdit', index);
	            editingId = index;
	        }
	        ;

	        var editors = $('#dataGrid_DJDF').datagrid('getEditors', index);

	        //console.log(editors[0].target);

	        editors[0].target.bind('change', function () {
	            console.info("0");
	        });

	        /*editors[1].target.bind('change', function () {
	            console.info("1");
	        });

	        editors[2].target.bind('change', function () {
	            console.info("2");
	        });*/

	    },
	    onLoadSuccess: function () {
	        var _this = $(this);
	        $('td[field=platformShareRatio]').on("keyup", 'input.textbox-text', function () {
	            $(this).parents('tr').find('td[field=areaShareRatio] input.textbox-f').textbox('setValue', (100 - $(this).val()).toFixed(2));
	        });

	        $('td[field=areaShareRatio]').on("keyup", 'input.textbox-text', function () {
	            $(this).parents('tr').find('td[field=platformShareRatio] input.textbox-f').textbox('setValue', (100 - $(this).val()).toFixed(2));
	        });
	    },
	    loadFilter: function (data) {
	        return data.data;
	    },
	    queryParams: {
	        'businessCode': 'DJDF'
	    },
	    columns: [[
	        {field: 'supplierName', title: '增值服务供应商', width: 150},
	        {
	            field: 'totalShareRatio',
	            title: '提点系数(%)',
	            width: 60,
	            editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
	            formatter: function (value, data, index) {
	                if (value > 0) {
	                    return parseFloat(value).toFixed(2);
	                } else {
	                    return "0.00";
	                }
	            }
	        },
	        // {
	        //     field: 'platformShareRatio',
	        //     title: '平台分成比(%)',
	        //     width: 80,
	        //     editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
	        //     formatter: function (value, data, index) {
	        //         if (value > 0) {
	        //             return parseFloat(value).toFixed(2);
	        //         } else {
	        //             return "0.00";
	        //         }
	        //     }
	        // },
	        // {
	        //     field: 'areaShareRatio',
	        //     title: '县域分成比(%)',
	        //     width: 60,
	        //     editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
	        //     formatter: function (value, data, index) {
	        //         if (value > 0) {
	        //             return parseFloat(value).toFixed(2);
	        //         } else {
	        //             return "0.00";
	        //         }
	        //     }
	        // },
	        {
	            field: 'cz',
	            title: '操作',
	            align: 'center',
	            width: 200,
	            fitColumns: true,
	            formatter: function (value, data, index) {
	                var temp = "";
	                temp += '<a class="btn btn-1" href="javascript:;">保存设置</a>';
	                return temp;
	            }
	        }
	    ]],
	});
}
function loadGrid_HCP(){
    $('#dataGrid_HCP').datagrid({
        url: '../../vas/business/suppliers',
        striped: false,
        fitColumns: true,
        singleSelect: true,
        rownumbers: false,
        //pagination: true,
        method: "GET",
        //rownumbers:true,
        //pageList: [10, 20, 50],
        //pageSize: 10,
        onBeforeEdit: function (index, row) {
        },
        onEndEdit: function (index, row) {
            console.error("====end edit");

            saveReWard_HCP(row);
        },
        onAfterEdit: function (index, row) {
        },
        onBeginEdit: function (index, row) {
            var editors = $('#dataGrid_HCP').datagrid('getEditors', index);
            var n1 = $(editors[0].target);
            var n2 = $(editors[1].target);
            var n3 = $(editors[2].target);


            n2.add(n3).textbox({
                onChange: function () {
                    console.info('ssssssssssssssssssss=========================' + n3);
                }
            });
            n1.add(n2).textbox({
                onChange: function () {
                    console.info('ssssssssssssssssssss=========================' + n2);
                }
            });
        },
        onClickRow: function (index, row) {
            console.log(row)
            if (undefined != editingId) {
                $('#dataGrid_HCP').datagrid('endEdit', editingId);
                editingId = index;
                $('#dataGrid_HCP').datagrid('beginEdit', editingId);
            }
            else {
                $('#dataGrid_HCP').datagrid('beginEdit', index);
                editingId = index;
            }
            ;

            var editors = $('#dataGrid_HCP').datagrid('getEditors', index);

            //console.log(editors[0].target);

            editors[0].target.bind('change', function () {
                console.info("0");
            });

            editors[1].target.bind('change', function () {
                console.info("1");
            });

            editors[2].target.bind('change', function () {
                console.info("2");
            });

        },
        onLoadSuccess: function () {
            var _this = $(this);
            $('td[field=platformShareRatio]').on("keyup", 'input.textbox-text', function () {
                $(this).parents('tr').find('td[field=areaShareRatio] input.textbox-f').textbox('setValue', (100 - $(this).val()).toFixed(2));
            });

            $('td[field=areaShareRatio]').on("keyup", 'input.textbox-text', function () {
                $(this).parents('tr').find('td[field=platformShareRatio] input.textbox-f').textbox('setValue', (100 - $(this).val()).toFixed(2));
            });
        },
        loadFilter: function (data) {
            return data.data;
        },
        queryParams: {
            'businessCode': 'HCP'
        },
        columns: [[
            {field: 'supplierName', title: '增值服务供应商', width: 150},
            {
                field: 'totalShareRatio',
                title: '提点金额',
                width: 60,
                editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
                formatter: function (value, data, index) {
                    if (value > 0) {
                        return parseFloat(value).toFixed(2);
                    } else {
                        return "0.00";
                    }
                }
            },
             {
                 field: 'platformShareRatio',
                 title: '平台分成比(%)',
                 width: 80,
                 editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
                 formatter: function (value, data, index) {
                     if (value > 0) {
                         return parseFloat(value).toFixed(2);
                     } else {
                         return "0.00";
                     }
                 }
             },
             {
                 field: 'areaShareRatio',
                 title: '县域分成比(%)',
                 width: 60,
                 editor: {type: 'textbox', options: {validType: 'inputNumber[0,100]'}},
                 formatter: function (value, data, index) {
                     if (value > 0) {
                         return parseFloat(value).toFixed(2);
                     } else {
                         return "0.00";
                     }
                 }
             },
            {
                field: 'cz',
                title: '操作',
                align: 'center',
                width: 200,
                fitColumns: true,
                formatter: function (value, data, index) {
                    var temp = "";
                    temp += '<a class="btn btn-1" href="javascript:;">保存设置</a>';
                    return temp;
                }
            }
        ]],
    });
}


$(function () {
	loadGrid_DJDF();
	loadGrid_HCP();
	loadGrid_MPCZ();
	
    $("#search_DJDF").on('click', function () {
        $("#dataGrid_DJDF").datagrid('load', {
            company: $('#company').textbox('getValue'),
            businessCode: 'DJDF'
        });
    });
    
    $("#search_HCP").on('click', function () {
        $("#dataGrid_HCP").datagrid('load', {
            company: $('#company_HCP').textbox('getValue'),
            businessCode: 'HCP'
        });
    });
    
    
    $("#clear_DJDF").on('click', function () {
        $("#company").textbox('setValue', '');
        $("#dataGrid_DJDF").datagrid('load', {
            businessCode: 'DJDF'
        });
    });
    
    $("#clear_HCP").on('click', function () {
        $("#company_HCP").textbox('setValue', '');
        $("#dataGrid_HCP").datagrid('load', {
            businessCode: 'HCP'
        });
    });
    
    
    $("#clear_MPCZ").on('click', function () {
        $("#company").textbox('setValue', '');
        $("#dataGrid_MPCZ").datagrid('load', {
            businessCode: 'MPCZ'
        });
    });
    
    $("#clear_MPCZ").on('click', function () {
        $("#company_MPCZ").textbox('setValue', '');
        $("#dataGrid_MPCZ").datagrid('load', {
            businessCode: 'MPCZ'
        });
    });
	$('#tt').tabs({
	    border:false,
	    onSelect:function(title,index){
	       if('缴电费' == title){
	    	   loadGrid_DJDF();
	       }else if('火车票' == title){
	    	   loadGrid_HCP();
	       }else if('话费充值' == title){
	    	   loadGrid_MPCZ();
	       }
	    }
	});
	
});
