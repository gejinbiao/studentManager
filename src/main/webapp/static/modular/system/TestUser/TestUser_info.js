/**
 * 初始化测试用户详情对话框
 */
var TestUserInfoDlg = {
    TestUserInfoData : {},
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
                    message: '手机号不能为空'
                }
            }
        },
        other1: {
            validators: {
                notEmpty: {
                    message: '其他1不能为空'
                }
            }
        },
        other2: {
            validators: {
                notEmpty: {
                    message: '其他2不能为空'
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
    opreator: {
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
TestUserInfoDlg.clearData = function() {
    this.TestUserInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestUserInfoDlg.set = function(key, val) {
    this.TestUserInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TestUserInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TestUserInfoDlg.close = function() {
    parent.layer.close(window.parent.TestUser.layerIndex);
}

/**
 * 收集数据
 */
TestUserInfoDlg.collectData = function() {
     var json = $('#testUserForm').serializeJSON();
        for (var key in json) {
            if (json[key] != '') {
                this.set(key);
            }
        }
}

/**
 * 验证数据是否为空
 */
TestUserInfoDlg.validate = function () {
    $('#testUserForm').data("bootstrapValidator").resetForm();
    $('#testUserForm').bootstrapValidator('validate');
    return $("#testUserForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TestUserInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
            return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TestUser/add", function(data){
        Feng.success("添加成功!");
        window.parent.TestUser.table.refresh();
        TestUserInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TestUserInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TestUserInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
            return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/TestUser/update", function(data){
        Feng.success("修改成功!");
        window.parent.TestUser.table.refresh();
        TestUserInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.TestUserInfoData);
    ajax.start();
}

$(function() {
//绑定效验规则
Feng.initValidator("testUserForm", TestUserInfoDlg.validateFields);
});
