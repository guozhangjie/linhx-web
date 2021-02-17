package com.linhx.web.excel;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.linhx.web.entity.ClassRecordEntity;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * StringList 解析监听器
 *
 * @author zhangcanlong
 * @since 2019-10-21
 */
public class ClassRecordExcelListener extends AnalysisEventListener {
    public static Map<String, BigDecimal> oneToOne = new HashMap<>();
    public static Map<String, BigDecimal> oneToAll = new HashMap<>();

    static {
        // 一对一价格
        oneToOne.put("郭双燕", new BigDecimal(0));
        oneToOne.put("何震", new BigDecimal(180));
        oneToOne.put("林老师", new BigDecimal(0));
        oneToOne.put("刘老师", new BigDecimal(0));
        oneToOne.put("刘囡", new BigDecimal(180));
        oneToOne.put("马柏玲", new BigDecimal(150));
        oneToOne.put("齐老师", new BigDecimal(0));
        oneToOne.put("孙璐", new BigDecimal(150));
        oneToOne.put("田利红", new BigDecimal(150));
        oneToOne.put("王静", new BigDecimal(150));
        oneToOne.put("杨蕊碧", new BigDecimal(180));
        oneToOne.put("周程", new BigDecimal(0));

        // 一对多价格
        oneToAll.put("郭双燕", new BigDecimal(0));
        oneToAll.put("何震", new BigDecimal(300));
        oneToAll.put("林老师", new BigDecimal(0));
        oneToAll.put("刘老师", new BigDecimal(0));
        oneToAll.put("刘囡", new BigDecimal(300));
        oneToAll.put("马柏玲", new BigDecimal(0));
        oneToAll.put("齐老师", new BigDecimal(0));
        oneToAll.put("孙璐", new BigDecimal(200));
        oneToAll.put("田利红", new BigDecimal(0));
        oneToAll.put("王静", new BigDecimal(200));
        oneToAll.put("杨蕊碧", new BigDecimal(0));
        oneToAll.put("周程", new BigDecimal(250));
    }

    /**
     * 自定义用于暂时存储data
     * 可以通过实例获取该值
     */
    private List<ClassRecordEntity> datas = new ArrayList<>();

    /**
     * 每解析一行都会回调invoke()方法
     *
     * @param object  读取后的数据对象
     * @param context 内容
     */
    @SneakyThrows
    @Override
    public void invoke(Object object, AnalysisContext context) {
        ClassRecordEntity excelMode = (ClassRecordEntity) object;
        excelMode.setUpdateTime(LocalDateTime.now());
        String teachDate = excelMode.getClassTime();
        String time = teachDate.split(" ")[1];
        LocalDate localDate = LocalDate.parse(teachDate.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        excelMode.setClassDate(localDate);
        excelMode.setClassTime(time);
        String[] split = time.split("~");

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date startDate = dateFormat.parse(split[0]);
        Date endDate = dateFormat.parse(split[1]);
        Integer datePoor = getDatePoor(startDate, endDate);
        BigDecimal b = new BigDecimal((double) datePoor / 60);
        b = b.setScale(2, BigDecimal.ROUND_HALF_UP);
        excelMode.setRealHours(b);

        // 处理金额
        Integer studentCount = excelMode.getActualNumber();
        String teachName = excelMode.getTeacher();
        BigDecimal hours = excelMode.getRealHours();
        if (studentCount == 0) {
            excelMode.setRealMoney(new BigDecimal(0));
        } else if (studentCount == 1 || studentCount == 2) {
            BigDecimal oneMoney = oneToOne.get(teachName);
            BigDecimal allMoney = oneMoney.multiply(hours);
            excelMode.setRealMoney(allMoney);
        } else {
            BigDecimal oneMoney = oneToAll.get(teachName);
            BigDecimal allMoney = oneMoney.multiply(hours);
            excelMode.setRealMoney(allMoney);
        }

        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(excelMode);
        //根据自己业务做处理
    }

    public static Integer getDatePoor(Date startDate, Date endDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        Long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        Long mymint = diff / 1000 / 60;
        int intValue = mymint.intValue();
        return intValue;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //解析结束销毁不用的资源
        //注意不要调用datas.clear(),否则getDatas为null
    }

    /**
     * 返回数据
     *
     * @return 返回读取的数据集合
     **/
    public List<ClassRecordEntity> getDatas() {
        return datas;
    }

    /**
     * 设置读取的数据集合
     *
     * @param datas 设置读取的数据集合
     **/
    public void setDatas(List<ClassRecordEntity> datas) {
        this.datas = datas;
    }
}

