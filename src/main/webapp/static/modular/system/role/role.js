/**
 * 角色管理的单例
 */
var Role = {
    id: "roleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    dataAuthority: null //数据权限id

};

/**
 * 初始化表格的列
 */
Role.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '上级角色', field: 'pName', align: 'center', valign: 'middle', sortable: true},
        {title: '所在部门', field: 'deptName', align: 'center', valign: 'middle', sortable: true},
        {title: '别名', field: 'tips', align: 'center', valign: 'middle', sortable: true}]
    return columns;
};


/**
 * 检查是否选中
 */
Role.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Role.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
Role.openAddRole = function () {
    var index = layer.open({
        type: 2,
        title: '添加角色',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/role/role_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
Role.openChangeRole = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改角色',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/role/role_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除角色
 */
Role.delRole = function () {
    if (this.check()) {

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/role/remove", function () {
                Feng.success("删除成功!");
                Role.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("roleId", Role.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除角色 " + Role.seItem.name + "?", operation);
    }
};

/**
 * 权限配置
 */
Role.assign = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '权限配置',
            area: ['300px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/role/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 搜索角色
 */
Role.search = function () {
    var queryData = {};
    queryData['roleName'] = $("#roleName").val();
    Role.table.refresh({query: queryData});
}

/**
 * 数据权限
 */
Role.viewDataAuthority = function () {
    $(':checkbox[name=flag]').prop('checked', false);
    if (this.check()) {
        $('#dataModalLabel').modal("show");
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/RoleDataAuthority/detail", function (data) {
            console.log(data);
            if (data.code == 200) {
                if (data.data != '') {
                    if (data.data.dataAuthority == 0) {
                        $("#checkOneSelf").prop('checked', true);
                        Role.dataAuthority = data.data.dataAuthority;
                    } else if (data.data.dataAuthority == 1) {
                        $("#checkAll").prop('checked', true);
                        Role.dataAuthority = data.data.dataAuthority;
                    } else {
                        Role.dataAuthority = null;
                    }
                }
            } else {
                Feng.alertFail(data.message);
            }
        }, function (data) {
            Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set("roleId", this.seItem.id);
        ajax.start();
    }
};


/**
 * 保存数据权限
 */
Role.saveDataAuthority = function () {
    //获取勾选框值
    $(':checkbox[name=flag]').each(function () {
        if ($(this).is(":checked")) {
            Role.dataAuthority = $(this).val();
        }
    });
    if (this.check()) {
        //提交信息
        var ajax = new $ax(Feng.ctxPath + "/RoleDataAuthority/add", function (data) {
            if (data.code == 200) {
                Feng.alertSuccess(data.message);
                $('#dataModalLabel').modal("hide");
            } else {
                Feng.alertFail(data.message);
            }
        }, function (data) {
            Feng.error("添加失败!" + data.responseJSON.message + "!");
        });
        ajax.set("roleId", this.seItem.id);
        if (this.dataAuthority != null && this.dataAuthority != '') {
            ajax.set("dataAuthority", parseInt(this.dataAuthority));
        }
        ajax.start();
    }

}


$(function () {
    var defaultColunms = Role.initColumn();
    var table = new BSTable(Role.id, "/role/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Role.table = table;

    /**
     * 单选框
     */
    $(':checkbox[name=flag]').each(function () {
        $(this).click(function () {
            if ($(this).is(":checked")) {
                // 先把所有的checkbox 都设置为不选种
                $(':checkbox[name=flag]').prop('checked', false);
                Role.dataAuthority = $(this).val();
                // 把自己设置为选中
                $(this).prop('checked', true);
            } else {
                Role.dataAuthority = null;
            }
        });
    });

});


