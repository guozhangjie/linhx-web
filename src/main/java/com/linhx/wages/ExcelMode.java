package com.linhx.wages;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExcelMode {

    @ExcelProperty(value = "授课老师",index = 5)
    private String teachName;

    @ExcelProperty(value = "上课时间",index = 4)
    private String teachDate;

    @ExcelProperty(value = "实到人数",index = 10)
    private Integer studentCount;

    @ExcelIgnore
    private BigDecimal hours;

    @ExcelIgnore
    private BigDecimal money;
}
