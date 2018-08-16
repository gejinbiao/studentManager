package com.stylefeng.guns.template;

import com.stylefeng.guns.base.BaseJunit;
import org.beetl.sql.core.ConnectionSource;
import org.beetl.sql.core.ConnectionSourceHelper;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * @author gjb
 * @Title
 * @Description:
 * @Created 2018-06-25
 */
public class CreateSql extends BaseJunit {

    @Autowired
    private DataSource dataSource;

    @Test
    public void createSlq() throws Exception {
        DBStyle dbStyle = new MySqlStyle();
        ConnectionSource ds = ConnectionSourceHelper.getSingle(dataSource);
        SQLManager sql =new SQLManager(dbStyle,ds);
        sql.genSQLTemplateToConsole("test");
    }
}