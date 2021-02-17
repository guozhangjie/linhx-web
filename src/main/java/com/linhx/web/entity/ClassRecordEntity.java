package com.linhx.web.entity;

import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author guozj
 * @since 2021-02-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("class_record")
public class ClassRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    /**
     * 授课老师
     */
    @ExcelProperty(value = "授课老师",index = 5)
    private String teacher;

    /**
     * 班级名称
     */
    @ExcelProperty(value = "班级名称",index = 3)
    private String className;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称",index = 2)
    private String courseName;

    /**
     * 点名老师
     */
    @ExcelProperty(value = "点名老师",index = 1)
    private String callTeacher;

    /**
     * 点名时间
     */
    @ExcelProperty(value = "点名时间",index = 0)
    @DateTimeFormat("yyyy-MM-dd HH:mm")
    private Date callTime;

    /**
     * 上课日期
     */
    @ExcelIgnore
    private LocalDate classDate;

    /**
     * 上课时间
     */
    @ExcelProperty(value = "上课时间",index = 4)
    private String classTime;

    /**
     * 授课课时
     */
    @ExcelProperty(value = "授课课时",index = 6)
    private String teachingHours;

    /**
     * 核实课时
     */
    @ExcelProperty(value = "核实课时",index = 7)
    private String classHours;

    /**
     * 审核状态
     */
    @ExcelProperty(value = "审核状态",index = 8)
    private String status;

    /**
     * 总人数
     */
    @ExcelProperty(value = "总人数",index = 9)
    private Integer totalNumber;

    /**
     * 实到人数
     */
    @ExcelProperty(value = "实到人数",index = 10)
    private Integer actualNumber;

    /**
     * 课消金额
     */
    @ExcelProperty(value = "课消金额",index = 11)
    private String classExpense;

    /**
     * 更新时间
     */
    @ExcelIgnore
    private LocalDateTime updateTime;

    /**
     * 实际金额
     */
    @ExcelIgnore
    private BigDecimal realMoney;

    /**
     * 实际上课时间
     */
    @ExcelIgnore
    private BigDecimal realHours;


}
