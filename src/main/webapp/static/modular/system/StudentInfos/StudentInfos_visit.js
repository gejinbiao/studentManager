/**
 * 初始化学生信息详情对话框
 */
var StudentInfosInfoVisit = {
    limit: 10,
    offset: 0,
    total: 0,
    currentRow: 0,
    StudentInfosIfoData: {},
    visitorValidateFields: {
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
        }, status: {
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
StudentInfosInfoVisit.clearData = function () {
    this.StudentInfosInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentInfosInfoVisit.set = function (key, val) {
    this.StudentInfosInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StudentInfosInfoVisit.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StudentInfosInfoVisit.close = function () {
    parent.layer.close(window.parent.StudentInfos.layerIndex);
}

/**
 * 收集数据
 */
StudentInfosInfoVisit.collectData = function () {
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
StudentInfosInfoVisit.validate = function () {
    $('#studentInfosForm').data("bootstrapValidator").resetForm();
    $('#studentInfosForm').bootstrapValidator('validate');
    return $("#studentInfosForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
StudentInfosInfoVisit.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/StudentInfos/add", function (data) {
        Feng.success("添加成功!");
        window.parent.StudentInfos.table.refresh();
        StudentInfosInfoVisit.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.StudentInfosInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StudentInfosInfoVisit.editSubmit = function () {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    var status = $("#status").val();
    //0未选中,3咨询中
    if (status == '' && status == 0 || status == 3) {
        //下次回访时间
        var nextVisitDate = $("#nextVisitDate").val();
        //本次回访结果
        var description = $("#description").val();
        if (nextVisitDate == undefined || nextVisitDate == '') {
            Feng.alertFail("处于咨询中,下次回访时间不能为空");
            return false;
        }

        if (description == undefined || description == '') {
            Feng.alertFail("处于咨询中,本次回访结果不能为空");
            return false;
        }
    }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/StudentInfos/updateStudentAndVisit", function (data) {
        if (data.code == 200) {
            Feng.success(data.message);
        }else {
            Feng.error(data.message);
        }


        StudentInfosInfoVisit.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set($('#studentInfosForm').serializeJSON());
    ajax.start();
}

$(function () {

    $(".col-sm-7").css("height", $(".col-sm-5").height());
    //绑定效验规则
    Feng.initValidator("studentInfosForm", StudentInfosInfoVisit.visitorValidateFields);


    //加载级别
    StudentInfosInfoVisit.getDicByCode("level", "level");

    //加载级别
    StudentInfosInfoVisit.getDicByCode("sex", "sex");

    //加载是否报名
    StudentInfosInfoVisit.getDicByCode("signUp", "status");

    //加载评论列表
    StudentInfosInfoVisit.getVisitRecord();


    //初始化下拉控件
    $("#level").val($("#hideLevel").val());
    //初始化下拉控件
    $("#status").val($("#hideStatus").val());
});

StudentInfosInfoVisit.getVisitRecord = function () {
    var index = Feng.load2();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/VisitorRecord/list", function (data) {
        if (data.code == 0) {
            Feng.alertFail("加载评论列表失败:" + data.message);
            return;
        }
        $(".timeline-label").remove();
        this.total = data.total;
        if (data.rows != '' && data.rows != null && data.rows.length > 0) {
            $.each(data.rows, function (i, e) {
                StudentInfosInfoVisit.currentRow++;
                var html = '<div class="timeline-item">' +
                    '<div class="timeline-point timeline-point-' + check(i) + '">' +
                    /*'<i class="fa fa-start"></i>' + 星星*/
                    '<i class="fa">' + StudentInfosInfoVisit.currentRow + '</i>' +
                    '</div>' +
                    '<div class="timeline-event timeline-event-' + check(i) + '">' +
                    /*'<div class="timeline-heading">' +
                    '<h4>' + e.operatorName + '</h4>' +
                    '</div>' +*/
                    '<div class="timeline-body">' +
                    '<p>' + e.description + '</p>' +
                    '</div>' +
                    '<div class="timeline-footer">' +
                    '<p class="text-left" style="display: inline">' + e.operatorName + '</p>' +
                    '<p class="text-right" style="display: inline; float: right">' + e.createTime + '</p>' +
                    '</div>' +
                    '</div>' +
                    '</div>'

                $('.timeline').append(html);
            });
            if (StudentInfosInfoVisit.offset * StudentInfosInfoVisit.limit <= this.total && this.total > 10) {
                var load = '<span class="timeline-label">' +
                    '<a href="#" class="btn btn-primary" title="加载更多" onclick="StudentInfosInfoVisit.getVisitRecord()">' +
                    '<i class="fa fa-fw fa-history"></i>' +
                    '</a>' +
                    '</span>';
                $('.timeline').append(load);
            } else {
                var load = '<span class="timeline-label">' +
                    '<a href="#" class="btn btn-default" title="没有更多了">' +
                    '<i class="fa fa-fw fa-history"></i>' +
                    '</a>' +
                    '</span>';
                $('.timeline').append(load);
            }

        } else {

            $('.timeline').append("暂时没有任何评论").removeClass();
        }


        setTimeout(function () {
            layer.close(index);
        }, 2000);
    }, function (data) {
        Feng.error("加载回访记录失败!" + data.responseJSON.message + "!");
        setTimeout(function () {
            layer.close(index);
        }, 2000);
    });
    ajax.set("studentId", $("#id").val());
    ajax.set("limit", this.limit);
    ajax.set("offset", this.offset);
    ajax.start();
    StudentInfosInfoVisit.offset += StudentInfosInfoVisit.limit;
}

function check(i) {
    if (i % 2 == 0) {
        return "warning";
    } else {
        return "success";
    }


}

StudentInfosInfoVisit.getDicByCode = function (value, id) {
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/dict/selectByDicCode", function (data) {
        var option = '<option value="0">请选择</option>';
        $.each(data, function (i, e) {
            option += '<option value="' + e.num + '">' + e.name + '</option>';
        });
        $('#' + id).append(option);

    }, function (data) {
        Feng.error("加载级别失败!" + data.responseJSON.message + "!");
    });
    ajax.set("dicCode", value);
    ajax.start();
}

