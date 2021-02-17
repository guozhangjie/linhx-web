package com.linhx.web.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.linhx.web.entity.ClassRecordEntity;
import com.linhx.web.excel.ClassRecordExcelListener;
import com.linhx.web.mapper.ClassRecordDao;
import com.linhx.web.service.ClassRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.alibaba.fastjson.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 浪子傑
 * @version 1.0
 * @date 2021/2/14
 */
@RestController
@RequestMapping("/classrecord")
public class ClassRecordController {

    @Autowired
    ClassRecordService classRecordService;

    @GetMapping("/selectClassRecord")
    public JSONObject selectClassRecord(HttpServletRequest request) {
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String searchParams = request.getParameter("searchParams");
        JSONObject search = JSONObject.parseObject(searchParams);


        IPage iPage = new Page(Long.valueOf(page), Long.valueOf(limit));
        QueryWrapper wrapper = new QueryWrapper<String>();

        if (search != null && search.containsKey("teacher")) {
            String teacher = search.getString("teacher");
            if (StringUtils.isNotBlank(teacher)) {
                wrapper.like("teacher", teacher);
            }
        }
        if (search != null && search.containsKey("date")) {
            String date = search.getString("date");
            if (StringUtils.isNotBlank(date)) {
                String[] dates = date.split("~");
                wrapper.ge("class_date", dates[0].trim());
                wrapper.le("class_date", dates[1].trim());
            }
        }
        IPage page1 = classRecordService.page(iPage, wrapper);

        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "查询成功");
        result.put("count", page1.getTotal());
        result.put("data", page1.getRecords());
        return result;
    }

    @PostMapping("/importRecord")
    public JSONObject importRecord(@RequestParam MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ClassRecordExcelListener listener = new ClassRecordExcelListener();
        ExcelReader excelReader = EasyExcel.read(inputStream, ClassRecordEntity.class, listener).build();
        ReadSheet readSheet = new ReadSheet(0);
        excelReader.read(readSheet);
        List<ClassRecordEntity> datas = listener.getDatas();
        boolean isSave = classRecordService.saveBatch(datas);
        JSONObject result = new JSONObject();
        if (isSave) {
            result.put("status", "0");
            result.put("message", "共导入" + datas.size() + "条记录");
        } else {
            result.put("status", "1");
            result.put("message", "导入失败");
        }

        return result;
    }

    @PostMapping("/updateClassRecord")
    public JSONObject updateClassRecord(@RequestBody ClassRecordEntity classRecordEntity){
        boolean isSuccess = classRecordService.updateById(classRecordEntity);
        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "修改：" + isSuccess);

        return result;
    }

    @PostMapping("/deleteClassRecord")
    public JSONObject deleteClassRecord(@RequestBody ClassRecordEntity classRecordEntity){
        boolean isSuccess = classRecordService.removeById(classRecordEntity);
        JSONObject result = new JSONObject();
        result.put("code", "0");
        result.put("msg", "删除：" + isSuccess);

        return result;
    }
}