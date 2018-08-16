/**
 * 数据处理管理初始化
 */
var Data = {
    id: "DataTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Data.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Data.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Data.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加数据处理
 */
Data.openAddData = function () {
    var index = layer.open({
        type: 2,
        title: '添加数据处理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/data/data_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看数据处理详情
 */
Data.openDataDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '数据处理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/data/data_update/' + Data.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除数据处理
 */
Data.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/data/delete", function (data) {
            Feng.success("删除成功!");
            Data.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("dataId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询数据处理列表
 */
Data.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Data.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Data.initColumn();
    var table = new BSTable(Data.id, "/data/list", defaultColunms);
    table.setPaginationType("client");
    Data.table = table.init();
});
