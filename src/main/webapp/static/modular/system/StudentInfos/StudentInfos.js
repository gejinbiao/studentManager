/**
 * 学生信息管理初始化
 */
var StudentInfos = {
    id: "StudentInfosTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    userTableId: "userTable",
    userTable: null
};

/**
 * 初始化表格的列
 */
StudentInfos.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
        {title: '主键id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '电话号码', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '性别', field: 'sexName', visible: true, align: 'center', valign: 'middle'},
        {title: '级别', field: 'level', visible: false, align: 'center', valign: 'middle'},
        {title: '级别', field: 'levelName', visible: true, align: 'center', valign: 'middle'},
        {title: '层次', field: 'type', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: false, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
        {title: '备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {
            title: '回访时间',
            field: 'nextVisitDate',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: "visitDateForamtter"
        },
        {title: '操作人', field: 'operatorName', visible: true, align: 'center', valign: 'middle'},
        {title: '当前咨询师', field: 'currentOperatorName', visible: true, align: 'center', valign: 'middle'}
    ];
};

function visitDateForamtter(val, row, index) {
    if (val != undefined && val != null && val != '') {
        var date = new Date(val);
        return date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
    } else {
        return "";
    }
}

/**
 * 检查是否选中
 */
StudentInfos.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        StudentInfos.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加学生信息
 */
StudentInfos.openAddStudentInfos = function () {
    var index = layer.open({
        type: 2,
        title: '添加学生信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/StudentInfos/StudentInfos_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看学生信息详情
 */
StudentInfos.openStudentInfosDetail = function () {
    if (this.check()) {
        //获取是否是多条
        var selected = $('#' + this.id).bootstrapTable('getSelections');
        if (selected.length > 1) {
            Feng.info("修改只能选择一条记录！");
            return;
        }
        var index = layer.open({
            type: 2,
            title: '学生信息详情',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/StudentInfos/StudentInfos_update/' + StudentInfos.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除学生信息
 */
StudentInfos.delete = function () {
    if (this.check()) {
        Feng.confirm("确认删除吗?", function () {
            var selected = $('#' + StudentInfos.id).bootstrapTable('getSelections');
            //拼接id
            var ids = '';
            $.each(selected, function (index, value) {

                if (index == 0) {
                    ids = value.id;
                } else {
                    ids = ids + "," + value.id;
                }
            });
            var ajax = new $ax(Feng.ctxPath + "/StudentInfos/delete", function (data) {
                Feng.success("删除成功!");
                StudentInfos.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("studentInfosIds", ids);
            ajax.start();
        });
    }
};

/**
 * 清空form
 */
StudentInfos.resetSearch = function () {

    $("#" + StudentInfos.id + "Form input").val("");
    /*
     StudentInfos.search();*/
}

/**
 * 查询学生信息列表
 */
StudentInfos.search = function () {
    //公共方法里获取参数
    var queryData = {};
    StudentInfos.table.refresh({query: queryData});
};

StudentInfos.openUploadView = function () {
    $('#importModalLabel').modal("show");


};

/**
 * 页面加载完后立即执行初始化创建日期及回访日期
 */
$(document).ready(function () {
    var myDate = new Date();
//获取当前年
    var year = myDate.getFullYear();
//获取当前月
    var month = myDate.getMonth() + 1;
//获取当前日
    var date = myDate.getDate();

    var time = year + "-" + month + "-" + date;
    $("#createTime").val(time);
    $("#nextVisitDate").val(time);
});


$(function () {
    var defaultColunms = StudentInfos.initColumn();
    var table = new BSTable(StudentInfos.id, "/StudentInfos/list", defaultColunms);
    table.setPaginationType("server");
    StudentInfos.table = table.init();
    $("#file").fileinput({
        language: 'zh',  //设置语言
        enctype: 'multipart/form-data',//文件类型
        showPreview: false,
        showUpload: true,
        showCancel: true,
        elErrorContainer: '#kartik-file-errors',
        allowedFileExtensions: ['xlsx'],
        uploadUrl: Feng.ctxPath + '/StudentInfos/importExcel'
    });
});

//导入文件上传完成之后的事件
$("#file").on("fileuploaded", function (event, data, previewId, index) {

    var code = data.response.code;
    if (code == 200) {
        $("#importModalLabel").modal("hide");
        Feng.alertSuccess(data.response.message);
        //初始化表格
        StudentInfos.search();
    } else {
        Feng.alertFail(data.response.message);
    }
});

/**
 * 展示查询用户
 */
StudentInfos.share = function () {
    if (this.check()) {
        $('#userModal').modal("show");
        $('#userTable').bootstrapTable('destroy');
        var defaultColunms = [
            {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', align: 'center', valign: 'middle'},
            {title: '性别', field: 'sexName', align: 'center', valign: 'middle'},
            {title: '部门', field: 'deptName', align: 'center', valign: 'middle'}
        ];
        var userTable = new BSTable(StudentInfos.userTableId, "/mgr/list2", defaultColunms);
        userTable.height = 400;
        userTable.setPaginationType("server");
        userTable.setShowRefresh(false);
        userTable.setShowColumnsFlag(false);
        StudentInfos.userTable = userTable.init();
    }


}

/**
 * 查询用户-查询
 */
StudentInfos.searchUser = function () {
    //公共方法里获取参数
    var queryData = {};
    StudentInfos.userTable.refresh({query: queryData});

}

/**
 * 查询用户-重置
 */
StudentInfos.resetUserSearch = function () {
    $("#" + StudentInfos.userTableId + "Form input").val("");
}

/**
 * 进行分量
 */
StudentInfos.submit = function () {

    var userSelected = $('#' + StudentInfos.userTableId).bootstrapTable('getSelections');
    if (userSelected.length == 0) {
        Feng.info("请选择要分量的用户！");
        return false;
    }
    var selected = $('#' + StudentInfos.id).bootstrapTable('getSelections');
    var ids = '';
    $.each(selected, function (index, value) {

        if (index == 0) {
            ids = value.id;
        } else {
            ids = ids + "," + value.id;
        }
    });

    var ajax = new $ax(Feng.ctxPath + "/StudentInfos/share", function (data) {
        if (data.code == 200) {
            $('#userModal').modal("hide");
            Feng.alertSuccess(data.message);
            StudentInfos.table.refresh();
        } else {
            Feng.alertFail(data.message);
        }

    }, function (data) {
        Feng.alertFail("分量失败!" + data.responseJSON.message + "!");
    });
    ajax.set("studentInfosIds", ids);
    ajax.set("userId", userSelected[0].id);
    ajax.start();

}
/**
 * 双击事件
 */
$('#' + StudentInfos.id).on('dbl-click-cell.bs.table', function (target, field, value, row) {
    cons(row.id);
});

/**
 * 进行咨询
 */
StudentInfos.consult = function () {
    var selected = $('#' + StudentInfos.id).bootstrapTable('getSelections');
    if (selected.length != 1) {
        Feng.info("请选择一条数据");
        return;
    }
    cons(selected[0].id);
}

//咨询
function cons(id) {
    var width = $(document.body).width() - 100;
    console.log(width);
    var height = $(document.body).height() - 100;
    console.log(height);
    var index = layer.open({
        type: 2,
        title: '学生信息详情',
        area: [width + 'px', height + 'px'], //宽高
        fix: false, //不固定
        maxmin: true,
        scrollbar: false,
        content: Feng.ctxPath + '/StudentInfos/StudentInfos_visit/' + id
    });
    StudentInfos.layerIndex = index;
}