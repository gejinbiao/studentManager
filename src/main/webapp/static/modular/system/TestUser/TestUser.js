/**
 * 测试用户管理初始化
 */
var TestUser = {
    id: "TestUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TestUser.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
         {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
         {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
         {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
         {title: '其他1', field: 'other1', visible: true, align: 'center', valign: 'middle'},
         {title: '其他2', field: 'other2', visible: true, align: 'center', valign: 'middle'},
         {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
         {title: '操作人', field: 'opreator', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TestUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TestUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加测试用户
 */
TestUser.openAddTestUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加测试用户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/TestUser/TestUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看测试用户详情
 */
TestUser.openTestUserDetail = function () {
    if (this.check()) {
        //获取是否是多条
        var selected = $('#' + this.id).bootstrapTable('getSelections');
        if (selected.length > 1) {
            Feng.info("修改只能选择一条记录！");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '测试用户详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/TestUser/TestUser_update/' + TestUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除测试用户
 */
TestUser.delete = function () {
    if (this.check()) {
    Feng.confirm("确认删除吗?",function () {
    var selected = $('#' + TestUser.id).bootstrapTable('getSelections');
                //拼接id
                var ids = '';
                $.each(selected, function (index, value) {

                    if (index == 0) {
                        ids = value.id;
                    } else {
                        ids = ids + "," + value.id;
                    }
                });
        var ajax = new $ax(Feng.ctxPath + "/TestUser/delete", function (data) {
            Feng.success("删除成功!");
            TestUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("testUserIds", ids);
        ajax.start();
        });
    }
};

/**
 * 清空form
 */
TestUser.resetSearch = function () {

    $("#" + TestUser.id + "Form input").val("");
    /*
     TestUser.search();*/
}

/**
 * 查询测试用户列表
 */
TestUser.search = function () {
    //公共方法里获取参数
    var queryData = {};
     TestUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TestUser.initColumn();
    var table = new BSTable(TestUser.id, "/TestUser/list", defaultColunms);
    table.setPaginationType("server");
    TestUser.table = table.init();
});
