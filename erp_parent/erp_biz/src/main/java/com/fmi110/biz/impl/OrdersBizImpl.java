package com.fmi110.biz.impl;

import com.fmi110.biz.OrdersBiz;
import com.fmi110.dao.BaseDao;
import com.fmi110.dao.OrderdetailDao;
import com.fmi110.dao.OrdersDao;
import com.fmi110.entity.Emp;
import com.fmi110.entity.Orderdetail;
import com.fmi110.entity.Orders;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by huangyunning on 2017/12/2.
 */
@Service
@Transactional
public class OrdersBizImpl extends BaseBizImpl<Orders> implements OrdersBiz {

    @Autowired
    private OrdersDao      dao;
    @Autowired
    private OrderdetailDao orderdetailDao;

    @Override
    public BaseDao<Orders> getBaseDao() {
        System.out.println("======给父类返回dao======");
        return this.dao;
    }

    @Override
    public void save(Orders order) {
        // 设置订单创建时间
        // 设置订单类型
        // 设置订单状态
        order.setCreatetime(new Date());
        order.setType(Orders.TYPE_IN);
        order.setState(Orders.STATE_CREATE);

        /**
         * todo : 1 商品总价的计算不应该依据前端的数据计算,容易被攥改!!
         * todo : 2 金额的计算问题!!!误差解决
         */

        Double totalMoney = 0d;
        for (Orderdetail o : order.getOrderdetails()) {
            totalMoney += o.getMoney();
            // 设置明细的状态
            // 设置明细的关联关系
            o.setState(Orderdetail.STATE_NOT_IN);
            o.setOrder(order);
        }
        order.setTotalmoney(totalMoney);
        dao.save(order);
    }

    /**
     * 审核
     *
     * @param uuid      审核人的id
     * @param orderUuid 订单的 id
     */
    @Override
    public void doCheck(Long uuid, Long orderUuid) {
        // 获取订单对象
        // 设置审核人
        // 设置审核日期
        // 设置订单状态
        Orders order = dao.findById(orderUuid);

        if (Orders.STATE_CHECK.equals(order.getState())) {
            throw new RuntimeException("订单已审核...");
        }
        Emp checker = new Emp(); // 审核人
        checker.setUuid(uuid);
        order.setChecker(checker);
        order.setChecktime(new Date());
        order.setState(Orders.STATE_CHECK);
    }

    @Override
    public void doStart(Long uuid, Long orderUuid) {
        // 获取订单对象
        // 设置确认人
        // 设置确认日期
        // 设置订单状态
        Orders order   = dao.findById(orderUuid);
        Emp    starter = new Emp(); // 审核人
        starter.setUuid(uuid);
        if (Orders.STATE_START.equals(order.getState())) {
            throw new RuntimeException("订单已确认...");
        }
        order.setStarter(starter);
        order.setStarttime(new Date());
        order.setState(Orders.STATE_START);
    }

    @Override
    public void delete(Orders orders) {
//        /**
//         * 手动维护外键关系,前端页面只传递了订单的主键,这里订单详情需要自己查数据库处理
//         */
//        List<Orderdetail> orderdetails = orderdetailDao.findByOrderUuid(orders.getUuid());
//        if (null != orderdetails) {
//            orders = orderdetails.get(0)
//                                 .getOrder(); // 重新赋值orders对象,解决session只能绑定一个持久态对象的问题
//
//            for (Orderdetail o : orderdetails) {
//                orderdetailDao.delete(o);
//            }
//        }
        // 对象变为持久态
        orders = dao.findById(orders.getUuid());
        super.delete(orders);
    }

    /**
     * 导出订单为 excel 文件
     * @param os
     * @param uuid
     */
    @Override
    public void exportById(OutputStream os, Long uuid) throws IOException {
        // 查询对应的订单
        Orders order = dao.findById(uuid);


        // 创建excel 模版
        HSSFWorkbook wk = new HSSFWorkbook();
        HSSFSheet    sheet = wk.createSheet("采购订单");

        // 创建excel模版
        createExcelTemplate(wk, sheet,order.getOrderdetails().size());


        // 设置日期
        if(order.getCreatetime()!=null){
            sheet.getRow(3).getCell(1).setCellValue(order.getCreatetime());
            sheet.getRow(3).getCell(3).setCellValue(order.getCreater().getName());

        }
        if(order.getChecktime()!=null){
            sheet.getRow(4).getCell(1).setCellValue(order.getChecktime());
            sheet.getRow(4).getCell(3).setCellValue(order.getChecker().getName());

        }
        if(order.getStarttime()!=null){
            sheet.getRow(5).getCell(1).setCellValue(order.getStarttime());
            sheet.getRow(5).getCell(3).setCellValue(order.getStarter().getName());

        }
        if(order.getEndtime()!=null){
            sheet.getRow(6).getCell(1).setCellValue(order.getEndtime());
            sheet.getRow(6).getCell(3).setCellValue(order.getEnder().getName());

        }

        // 填充订单明细
        int rowIndex = 9;
        HSSFRow row = null;
        for(Orderdetail d:order.getOrderdetails()){
            row = sheet.getRow(rowIndex);
            row.getCell(0).setCellValue(d.getGoodsname());
            row.getCell(1).setCellValue(d.getNum());
            row.getCell(2).setCellValue(d.getPrice());
            row.getCell(3).setCellValue(d.getMoney());
            rowIndex++;
        }

        // 合计
        sheet.getRow(rowIndex).getCell(0).setCellValue("合计");
        sheet.getRow(rowIndex)
             .getCell(3)
             .setCellValue(order.getTotalmoney());

        wk.write(os);
        wk.close();
    }

    /**
     * 创建模版
     * @param wk
     * @param sheet
     */
    private void createExcelTemplate(HSSFWorkbook wk, HSSFSheet sheet,int dymanicRows) {

        // 创建10行4列
        int rowTotal = 10+dymanicRows , columTotal = 4;

        // 内容样式
        HSSFCellStyle style_content = wk.createCellStyle();
        //设置边框
        style_content.setBorderTop(BorderStyle.THIN);
        style_content.setBorderBottom(BorderStyle.THIN);
        style_content.setBorderLeft(BorderStyle.THIN);
        style_content.setBorderRight(BorderStyle.THIN);


        for(int i=2;i<2+rowTotal;i++){
            // 插入第 i 行
            HSSFRow row = sheet.createRow(i);

            for(int j=0;j<columTotal;j++){
                HSSFCell cell = row.createCell(j);
                cell.setCellStyle(style_content); // 设置单元格样式
            }
        }

        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));//标题
        sheet.addMergedRegion(new CellRangeAddress(2,2,1,3)); // 供应商
        sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 3));// 订单明细

        // 设置固定文本内容

        // 第一列数据
        sheet.createRow(0).createCell(0).setCellValue("采购单"); // 第一行之前没创建
        sheet.getRow(2).getCell(0).setCellValue("供应商");
        sheet.getRow(3).getCell(0).setCellValue("下单日期");
        sheet.getRow(4).getCell(0).setCellValue("审核日期");
        sheet.getRow(5).getCell(0).setCellValue("采购日期");
        sheet.getRow(6).getCell(0).setCellValue("入库日期");
        sheet.getRow(7).getCell(0).setCellValue("订单明细");
        sheet.getRow(8).getCell(0).setCellValue("商品名称");

        // 第二列数据
        sheet.getRow(8).getCell(1).setCellValue("数量");

        // 第三列数据
        sheet.getRow(3).getCell(2).setCellValue("经办人");
        sheet.getRow(4).getCell(2).setCellValue("经办人");
        sheet.getRow(5).getCell(2).setCellValue("经办人");
        sheet.getRow(6).getCell(2).setCellValue("经办人");
        sheet.getRow(8).getCell(2).setCellValue("价格");

        // 第四列数据
        sheet.getRow(8).getCell(3).setCellValue("金额");


        // 设置行高和列宽
        sheet.getRow(0).setHeight((short) 1000);
        for(int i=2;i<2+rowTotal;i++){
            HSSFRow row = sheet.getRow(i);
            row.setHeight((short) 500);
        }
        for(int j=0;j<columTotal;j++){
            sheet.setColumnWidth(j,5000);
        }

        // 设置对齐方式和字体
        style_content.setAlignment(HorizontalAlignment.CENTER);
        style_content.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置内容部分的字体
        HSSFFont font_content = wk.createFont();
        font_content.setFontName("宋体");
        font_content.setFontHeightInPoints((short) 11);
        style_content.setFont(font_content);

        // 设置标题样式
        HSSFCellStyle style_title = wk.createCellStyle();
        style_title.setAlignment(HorizontalAlignment.CENTER);
        style_title.setVerticalAlignment(VerticalAlignment.CENTER);

        HSSFFont font_title = wk.createFont();
        font_title.setFontName("黑体");
        font_title.setBold(true);
        font_title.setFontHeightInPoints((short) 18);

        style_title.setFont(font_title);

        sheet.getRow(0).getCell(0).setCellStyle(style_title);

        // 设置日期格式
        HSSFCellStyle style_date = wk.createCellStyle();
        style_date.cloneStyleFrom(style_content); // 复制已有的样式
        HSSFDataFormat dataFormat = wk.createDataFormat();
        style_date.setDataFormat(dataFormat.getFormat("yyyy-MM-dd hh:mm"));

        for(int i=3;i<7;i++){
            sheet.getRow(i).getCell(1).setCellStyle(style_date);
        }
    }

}
