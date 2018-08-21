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
        type: {
            validators: {
                notEmpty: {
                    message: '咨询层次不能为空'
                }
            }
        },
        sex: {
            validators: {
                notEmpty: {
                    message: '性别不能为空'
                }
            }
        },
        level: {
            validators: {
                notEmpty: {
                    message: '级别不能为空'
                }, callback: {
                    message: '必须选择一个级别',
                    callback: function (value) {
                        if (value == '' || value == '0' || value == 0 || value == undefined) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            }
        },
        status: {
            validators: {
                notEmpty: {
                    message: '是否报名不能为空'
                }, callback: {
                    message: '必须选择一个是否报名',
                    callback: function (value) {
                        if (value == '' || value == '0' || value == 0 || value == undefined) {
                            return false;
                        } else {
                            return true;
                        }
                    }
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

    //加载级别
    Feng.getDicByCode("level", "level");

    //加载性别
    Feng.getDicByCode("sex", "sex");

    //加载是否报名
    Feng.getDicByCode("signUp", "status");
});