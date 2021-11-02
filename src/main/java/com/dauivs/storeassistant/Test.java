package com.dauivs.storeassistant;

import com.dauivs.storeassistant.utils.ExcelUtil;
import com.dauivs.storeassistant.utils.ListUtil;

import java.io.File;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List list = ListUtil.tableToList(ExcelUtil.read(new File("D:\\resource\\store_assistant\\商品信息.xlsx")));
        list.forEach(System.out::println);
    }
}
