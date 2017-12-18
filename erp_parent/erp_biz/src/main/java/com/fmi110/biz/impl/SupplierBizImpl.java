package com.fmi110.biz.impl;

import com.fmi110.biz.SupplierBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.SupplierDao;
import com.fmi110.entity.Supplier;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class SupplierBizImpl extends BaseBizImpl<Supplier> implements SupplierBiz {

    @Autowired
    private SupplierDao dao;


    @Override
    public BaseDao<Supplier> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }

    /**
     * 导出数据到 execel 文件
     * @param os
     * @param t1
     */
    @Override
    public void export(OutputStream os, Supplier t1) {
        List<Supplier> list = dao.findPage(t1,null,null,null,null);

        HSSFWorkbook wk = new HSSFWorkbook();
        HSSFSheet sheet = null;
        // 根据查询条件设置工作表的名称
        if("1".equals(t1.getType())){
            sheet = wk.createSheet("供应商");
        }else{
            sheet = wk.createSheet("客户");
        }

        HSSFRow row = sheet.createRow(0);

        // 列标题
        String[] headerNames={"名称","地址","联系人","电话","Email"};
        // 指定列宽
        int[] columnWidths = {4000, 8000, 2000, 3000, 8000};
        HSSFCell cell = null;
        for(int i=0;i<headerNames.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(headerNames[i]);
            sheet.setColumnWidth(i,columnWidths[i]);
        }

        // 写入内容
        int i=1;
        for(Supplier s:list){
            row = sheet.createRow(i);
            row.createCell(0).setCellValue(s.getName());
            row.createCell(1).setCellValue(s.getAddress());
            row.createCell(2).setCellValue(s.getContact());
            row.createCell(3).setCellValue(s.getTele());
            row.createCell(4).setCellValue(s.getEmail());
            i++;
        }
        // 写入输出流
        try {
            wk.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                wk.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
