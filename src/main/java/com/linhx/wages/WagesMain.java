package com.linhx.wages;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WagesMain {



    public static void main(String[] args) {
        List<ExcelMode> excelModes = readExcel();
        Map<String, List<ExcelMode>> stringListMap = excelModes.stream().collect(Collectors.groupingBy(ExcelMode::getTeachName));

        for (Map.Entry<String, List<ExcelMode>> stringListEntry : stringListMap.entrySet()) {
            String key = stringListEntry.getKey();
            List<ExcelMode> value = stringListEntry.getValue();
            System.out.println("================" + key + "=====================");
            BigDecimal sums = new BigDecimal(0);
            for (ExcelMode excelMode : value) {
                System.out.println(excelMode);
                sums = sums.add(excelMode.getMoney());
            }
            System.out.println("总金额：" + sums.toString());
            System.out.println("================" + key + "=====================");
        }

    }

    public static List<ExcelMode> readExcel(){
        // 读取 excel 表格的路径
        String readPath = "C:\\Users\\lenovo\\Downloads\\班级上课记录 02-08(3).xls";

        ExcelListener listener = new ExcelListener();

        ExcelReader excelReader = EasyExcel.read(readPath, ExcelMode.class, listener).build();
        ReadSheet readSheet = new ReadSheet(0);
        excelReader.read(readSheet);
        List<ExcelMode> datas = listener.getDatas();
        System.out.println(datas);
        excelReader.finish();
        return datas;

    }
}
