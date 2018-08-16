/**
 * 初始化数据处理详情对话框
 */
var DataInfoDlg = {
    dataInfoData : {}
};

/**
 * 清除数据
 */
DataInfoDlg.clearData = function() {
    this.dataInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataInfoDlg.set = function(key, val) {
    this.dataInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DataInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DataInfoDlg.close = function() {
    parent.layer.close(window.parent.Data.layerIndex);
}

/**
 * 收集数据
 */
DataInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
DataInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/data/add", function(data){
        Feng.success("添加成功!");
        window.parent.Data.table.refresh();
        DataInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DataInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/data/update", function(data){
        Feng.success("修改成功!");
        window.parent.Data.table.refresh();
        DataInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.dataInfoData);
    ajax.start();
}

$(function() {

});
