/**
 * 初始化学生信息详情对话框
 */
var StudentInfosInfoDlg = {
    limit: 0,
    offset: 10,
    StudentInfosIfoData: {},
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        phone: {
            validators: {
                notEmpty: {
                    message: '电话号码不能为空'
                }
            }
        },
        remark: {
            validators: {
                notEmpty: {
                    message: '不能为空'
                }
            }
        },
        createTime: {
            validators: {
                notEmpty: {
                    message: '创建时间不能为空'
                }
            }
        },
        updateTime: {
            validators: {
                notEmpty: {
                    message: '修改时间不能为空'
                }
            }
        },
        operator: {
            validators: {
                notEmpty: {
                    message: '操作人不能为空'
                }
            }
        }
    }

};

/**
 * 清除数据
 */
StudentInfosInfoDlg.clearData = function () {
    this.StudentInfosInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentInfosInfoDlg.set = function (key, val) {
    this.StudentInfosInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentInfosInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StudentInfosInfoDlg.close = function () {
    parent.layer.close(window.parent.StudentInfos.layerIndex);
}

/**
 * 收集数据
 */
StudentInfosInfoDlg.collectData = function () {
    var json = $('#studentInfosForm').serializeJSON();
    for (var key in json) {
        if (json[key] != '') {
            this.set(key);
        }
    }
}

/**
 * 验证数据是否为空
 */
StudentInfosInfoDlg.validate = function () {
    $('#studentInfosForm').data("bootstrapValidator").resetForm();
    $('#studentInfosForm').bootstrapValidator('validate');
    return $("#studentInfosForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
StudentInfosInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/StudentInfos/add", function (data) {
        Feng.success("添加成功!");
        window.parent.StudentInfos.table.refresh();
        StudentInfosInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.StudentInfosInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StudentInfosInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/StudentInfos/update", function (data) {
        Feng.success("修改成功!");
        window.parent.StudentInfos.table.refresh();
        StudentInfosInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.StudentInfosInfoData);
    ajax.start();
}

$(function () {
    //绑定效验规则
    Feng.initValidator("studentInfosForm", StudentInfosInfoDlg.validateFields);
});