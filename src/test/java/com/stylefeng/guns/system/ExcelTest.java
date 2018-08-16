package com.stylefeng.guns.system;

import com.github.crab2died.ExcelUtils;
import com.github.crab2died.exceptions.Excel4JException;
import com.stylefeng.guns.base.BaseJunit;
import com.stylefeng.guns.modular.system.entity.TestUser;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * @author gjb
 * @Title
 * @Description:
 * @Created 2018-07-04
 */
public class ExcelTest extends BaseJunit {


    @Test
    public void testObject2Excel() throws Excel4JException, IOException {

        String tempPath = "/normal_template.xlsx";
        List<TestUser> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(new TestUser("张三" + (i + 1), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 2), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 3), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 4), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 5), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 6), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 7), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 8), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 9), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
            list.add(new TestUser("张三" + (i + 10), "13812341234", "其他1", "其他1", new Date(), new Date(), "admin"));
        }
        Map<String, String> data = new HashMap<>();
        data.put("title", "战争学院花名册");
        data.put("info", "学校统一花名册");
        // 基于模板导出Excel
        ExcelUtils.getInstance().exportObjects2Excel(tempPath, 0, list, data, TestUser.class, false, "G:/A.xlsx");
        // 不基于模板导出Excel
        ExcelUtils.getInstance().exportObjects2Excel(list, TestUser.class, true, null, true, "G:/B.xlsx");
    }

}