package com.car.service;

import com.alibaba.fastjson.JSONObject;
import com.car.baiduMapRoute.HttpRoute;
import com.car.domain.History;
import com.car.domain.Historys;
import com.car.domain.MonitorTips;
import com.car.influxdb.influxdbDao;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * @Author : mmy
 * @Creat Time : 2020/5/10  9:48
 * @Description
 */
@Service
public class ExportService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private MonitorService monitorService;

    public static HashMap<String, JSONObject> map = new HashMap<>();


    /**
     * Excel: {Title: "轨迹数据(2020-05-10 00:00:00~2020-05-10 23:59:59)",…}
     * FileName: "S110009(2020-05-10 00:00:00~2020-05-10 23:59:59)"
     * Params: [{Name: "DeviceNumber", Display: "设备号", Width: 18}, {Name: "LocationTime", Display: "定位时间", Width: 21},…]
     * 0: {Name: "DeviceNumber", Display: "设备号", Width: 18}
     * Display: "设备号"
     * Name: "DeviceNumber"
     * Width: 18
     * 1: {Name: "LocationTime", Display: "定位时间", Width: 21}
     * Display: "定位时间"
     * Name: "LocationTime"
     * Width: 21
     * 2: {Name: "LocationType", Display: "定位方式", Width: 10}
     * Display: "定位方式"
     * Name: "LocationType"
     * Width: 10
     * 3: {Name: "Speed", Display: "速度(km/h)", Width: 11}
     * Display: "速度(km/h)"
     * Name: "Speed"
     * Width: 11
     * 4: {Name: "Lng", Display: "经度", Width: 12}
     * Display: "经度"
     * Name: "Lng"
     * Width: 12
     * 5: {Name: "Lat", Display: "纬度", Width: 12}
     * Display: "纬度"
     * Name: "Lat"
     * Width: 12
     * 6: {Name: "Address", Display: "地址", Width: 46}
     * Display: "地址"
     * Name: "Address"
     * Width: 46
     * Title: "轨迹数据(2020-05-10 00:00:00~2020-05-10 23:59:59)"
     */

    //方法一：
//    public void exportExcel(HttpServletRequest request, HttpServletResponse response, JSONObject jsonObject) throws IOException, WriteException {
//
//        // 文件名
//        String filename = "轨迹.xls";
//
//        // 写到服务器上
////        String path = request.getSession().getServletContext().getRealPath("") + "/" + filename;
//        String path = "E:" + "/" + filename;
//
//
//        File name = new File(path);
//        // 创建写工作簿对象
//        WritableWorkbook workbook = Workbook.createWorkbook(name);
//        // 工作表
//        WritableSheet sheet = workbook.createSheet(path, 0);
//
//        // 设置字体;
//        WritableFont font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
//
//        WritableCellFormat cellFormat = new WritableCellFormat(font);
//        // 设置背景颜色;
//        cellFormat.setBackground(Colour.WHITE);
//        // 设置边框;
//        cellFormat.setBorder(Border.ALL, BorderLineStyle.DASH_DOT);
//        // 设置文字居中对齐方式;
//        cellFormat.setAlignment(Alignment.CENTRE);
//        // 设置垂直居中;
//        cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
//
//
//        // 分别给1,5,6列设置不同的宽度;
//        sheet.setColumnView(0, 35);
//        sheet.setColumnView(1, 35);
//        sheet.setColumnView(2, 30);
//        sheet.setColumnView(3, 20);
//        sheet.setColumnView(4, 20);
//        sheet.setColumnView(5, 20);
//        sheet.setColumnView(6, 46);
//        // 给sheet电子版中所有的列设置默认的列的宽度;
////        sheet.getSettings().setDefaultColumnWidth(20);
//        // 给sheet电子版中所有的行设置默认的高度，高度的单位是1/20个像素点,但设置这个貌似就不能自动换行了
//        // sheet.getSettings().setDefaultRowHeight(30 * 20);
//        // 设置自动换行;
//        cellFormat.setWrap(true);
//
//        // 单元格
//        Label label0 = new Label(0, 0, "设备号", cellFormat);
//        Label label1 = new Label(1, 0, "定位时间", cellFormat);
//        Label label2 = new Label(2, 0, "定位方式", cellFormat);
//        Label label3 = new Label(3, 0, "速度(km/h)", cellFormat);
//        Label label4 = new Label(4, 0, "经度", cellFormat);
//        Label label5 = new Label(5, 0, "纬度", cellFormat);
//        Label label6 = new Label(6, 0, "地址", cellFormat);
//
//        sheet.addCell(label0);
//        sheet.addCell(label1);
//        sheet.addCell(label2);
//        sheet.addCell(label3);
//        sheet.addCell(label4);
//        sheet.addCell(label5);
//        sheet.addCell(label6);
//
//        // 给第二行设置背景、字体颜色、对齐方式等等;
//        WritableFont font2 = new WritableFont(WritableFont.ARIAL, 14, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
//        WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
//        // 设置文字居中对齐方式;
//        cellFormat2.setAlignment(Alignment.CENTRE);
//        // 设置垂直居中;
//        cellFormat2.setVerticalAlignment(VerticalAlignment.CENTRE);
//        cellFormat2.setBackground(Colour.WHITE);
//        cellFormat2.setBorder(Border.ALL, BorderLineStyle.THIN);
//        cellFormat2.setWrap(true);
//
//        // 记录行数
//        int n = 1;
//
//        List<AndroidMonitorTip> monitorTipsList = commonService.getAndroidMonitorTipsByCatalogId("1583500780205246843");
//        System.out.println(monitorTipsList.size());
//
//        for (AndroidMonitorTip a : monitorTipsList) {
//            Label lt0 = new Label(0, n, a.getDev_number(), cellFormat2);
//            Label lt1 = new Label(1, n, a.getLocation_time(), cellFormat2);
//            Label lt2 = new Label(2, n, a.getLocation_type(), cellFormat2);
//            Label lt3 = new Label(3, n, a.getSpeed(), cellFormat2);
//            Label lt4 = new Label(4, n, a.getLng(), cellFormat2);
//            Label lt5 = new Label(5, n, a.getLat(), cellFormat2);
//            Label lt6 = new Label(6, n, a.getAddress(), cellFormat2);
//            sheet.addCell(lt0);
//            sheet.addCell(lt1);
//            sheet.addCell(lt2);
//            sheet.addCell(lt3);
//            sheet.addCell(lt4);
//            sheet.addCell(lt5);
//            sheet.addCell(lt6);
//            n++;
//        }
//        //开始执行写入操作
//        workbook.write();
//        //关闭流
//        workbook.close();
//        OutputStream out = null;
//        response.addHeader("content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(filename, "utf-8"));
//        //通知客服文件的MIME类型
//        response.setContentType("application/octet-stream;charset=UTF-8");
//        response.setContentType("application/vnd.ms-excel");
//
//        // 2.下载
//        out = response.getOutputStream();
//        String path3 = request.getSession().getServletContext().getRealPath("") + "/" + path;
//
//        // inputStream：读文件，前提是这个文件必须存在，要不就会报错
//        InputStream is = new FileInputStream(path);
//
//        byte[] b = new byte[4096];
//        int size = is.read(b);
//        while (size > 0) {
//            out.write(b, 0, size);
//            size = is.read(b);
//        }
//        out.close();
//        is.close();
//
//    }

    //方法二：可以采用POI导出excel，但是比较麻烦
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request, JSONObject jsonObject) {

        try {
            Workbook workbook = new HSSFWorkbook();
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-download");
            String filedisplay = "外部案件导入模板.xls";
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");

            response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);

            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            Sheet sheet = workbook.createSheet("外部案件导入模板");
            // 第三步，在sheet中添加表头第0行
            Row row = sheet.createRow(0);
            // 第四步，创建单元格，并设置值表头 设置表头居中
            CellStyle style = workbook.createCellStyle();
//            style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式

            Cell cell = row.createCell(0);
            cell.setCellValue("商品名称");
            cell.setCellStyle(style);
            sheet.setColumnWidth(0, (25 * 256));  //设置列宽，50个字符宽

            cell = row.createCell(1);
            cell.setCellValue("商品编码");
            cell.setCellStyle(style);
            sheet.setColumnWidth(1, (20 * 256));  //设置列宽，50个字符宽

            cell = row.createCell(2);
            cell.setCellValue("商品价格");
            cell.setCellStyle(style);
            sheet.setColumnWidth(2, (15 * 256));  //设置列宽，50个字符宽

            cell = row.createCell(3);
            cell.setCellValue("商品规格");
            cell.setCellStyle(style);
            sheet.setColumnWidth(3, (15 * 256));  //设置列宽，50个字符宽

            // 第五步，写入实体数据 实际应用中这些数据从数据库得到
            row = sheet.createRow(1);
            row.createCell(0, Cell.CELL_TYPE_STRING).setCellValue(1);
            row.createCell(1, Cell.CELL_TYPE_STRING).setCellValue(2);
            row.createCell(2, Cell.CELL_TYPE_STRING).setCellValue(3);   //商品价格
            row.createCell(3, Cell.CELL_TYPE_STRING).setCellValue(4);  //规格

            // 第六步，将文件存到指定位置
            try {
                OutputStream out = response.getOutputStream();
                workbook.write(out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //方法三：
    public void downloadExcelThree(HttpServletResponse response, HttpServletRequest request, JSONObject jsonObject) throws IOException, ParseException {
        String DeviceNumber = jsonObject.getString("DeviceNumber");
        String EndTime = jsonObject.getString("EndTime");
        String StartTime = jsonObject.getString("StartTime");
        JSONObject Excel = jsonObject.getJSONObject("Excel");
        String FileName = Excel.getString("FileName");
        HashMap<String, Object> hashMap = new HashMap();
        hashMap.put("StartTime", StartTime);
        hashMap.put("EndTime", EndTime);
        hashMap.put("DeviceNumber", DeviceNumber);

//        List<AndroidMonitorTip> monitorTipsList = influxdbDao.queryAndroidMonitorTipsByTime(hashMap);//不在查influxdb 库 本地库

//        List<MonitorTips> monitorTipsList = influxdbDao.queryMonitorTips(hashMap);// 服务器已改  查询是服务器格式

        Historys historys = monitorService.getHistorysByNumAndTime(hashMap);

        List<History> historyList = historys.getData();
        System.out.println(historyList.size());
        if (historyList.size() == 0) {
            return;
        }

        HSSFWorkbook wb = export(historyList);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;fileName=" + FileName + ".xls");
        response.setContentType("application/octet-stream;charset=utf-8");
        OutputStream outputStream = response.getOutputStream();
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
        map.remove(jsonObject.getString("key"));
    }


    public HSSFWorkbook export(List<History> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        String[] excelHeader = {"设备号", "定位时间", "定位方式", "速度(km/h)", "经度", "纬度", "地址"};
        HSSFSheet sheet = wb.createSheet("轨迹");
        HSSFRow row = sheet.createRow(0);
        CellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        Font fontStyle = wb.createFont(); // 字体样式
        fontStyle.setBold(true); // 加粗
        fontStyle.setFontName("黑体"); // 字体
        fontStyle.setFontHeightInPoints((short) 12); // 大小
        // 将字体样式添加到单元格样式中
        style.setFont(fontStyle);
        // 设置样式
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        // 设置样式
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setTopBorderColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        style2.setWrapText(true);

        for (int i = 0; i < excelHeader.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(excelHeader[i]);
            cell.setCellStyle(style);
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, 30 * 200);
        }

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            HSSFCell[] cells = new HSSFCell[7];
            for (int j = 0; j < cells.length; j++) {
                cells[j] = row.createCell(j);
                cells[j].setCellStyle(style2);
            }
            History a = list.get(i);
            cells[0].setCellValue(a.getDev_number());
            cells[1].setCellValue(a.getLocation_time());
            cells[2].setCellValue("北斗");
            cells[3].setCellValue(a.getSpeed());
            cells[4].setCellValue(a.getLng());
            cells[5].setCellValue(a.getLat());
            String address = HttpRoute.getAddressByLocation(a.getLat(), a.getLng()).get(0);
            cells[6].setCellValue(address);
        }
        return wb;
    }


}
