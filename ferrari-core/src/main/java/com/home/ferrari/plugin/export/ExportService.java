package com.home.ferrari.plugin.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;

@Service
public class ExportService {
	public void exportExcel(HttpServletResponse response, String title, String[] rowName, List<Map<String, Object>> dataList) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet = workbook.createSheet(title); // 创建工作表
			// 定义所需列数
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
			HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
			int columnNum = rowName.length;
			HSSFRow rowRowName = sheet.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)
			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
			}
			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> map = dataList.get(i);// 遍历每个对象
				HSSFRow row = sheet.createRow(i + 1);// 创建所需的行数
				int j = 0;
				for(Map.Entry<String, Object> entry : map.entrySet()) {
					HSSFCell cell = row.createCell(j++, CellType.STRING); // 设置单元格的数据类型
					if(null!=entry.getValue()){
						cell.setCellValue(entry.getValue().toString());
					}	
					cell.setCellStyle(style); // 设置单元格样式
				}
			}
			if (workbook != null) {
				try {
					String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()) + ".xls";
					String headStr = "attachment; filename=\"" + fileName + "\"";
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", headStr);
					OutputStream out = response.getOutputStream();
					workbook.write(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportExcel2Sheet(HttpServletResponse response, String title1,
			String title2, String[] rowName1, String[] rowName2,
			List<Map<String, Object>> dataList1,
			List<Map<String, Object>> dataList2) {
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet1 = workbook.createSheet(title1); // 创建工作表
			// 定义所需列数
			HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
			HSSFCellStyle style = this.getStyle(workbook); // 单元格样式对象
			int columnNum = rowName1.length;
			HSSFRow rowRowName = sheet1.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)
			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum; n++) {
				sheet1.setColumnWidth(n, 25 * 256);
				HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(rowName1[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
			}
			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < dataList1.size(); i++) {
				Map<String, Object> map = dataList1.get(i);// 遍历每个对象
				HSSFRow row = sheet1.createRow(i + 1);// 创建所需的行数
				int j = 0;
				for(Map.Entry<String, Object> entry : map.entrySet()) {
					HSSFCell cell = row.createCell(j++, CellType.STRING); // 设置单元格的数据类型
					if(null!=entry.getValue()){
						cell.setCellValue(entry.getValue().toString());
					}	
					cell.setCellStyle(style); // 设置单元格样式
				}
			}
			
			HSSFSheet sheet2 = workbook.createSheet(title2); // 创建工作表
			// 定义所需列数
			int columnNum2 = rowName2.length;
			HSSFRow rowRowName2 = sheet2.createRow(0); // 在索引2的位置创建行(最顶端的行开始的第二行)
			// 将列头设置到sheet的单元格中
			for (int n = 0; n < columnNum2; n++) {
				sheet2.setColumnWidth(n, 25 * 256);
				HSSFCell cellRowName = rowRowName2.createCell(n); // 创建列头对应个数的单元格
				cellRowName.setCellType(CellType.STRING); // 设置列头单元格的数据类型
				HSSFRichTextString text = new HSSFRichTextString(rowName2[n]);
				cellRowName.setCellValue(text); // 设置列头单元格的值
				cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
			}
			// 将查询出的数据设置到sheet对应的单元格中
			for (int i = 0; i < dataList2.size(); i++) {
				Map<String, Object> map = dataList2.get(i);// 遍历每个对象
				HSSFRow row = sheet2.createRow(i + 1);// 创建所需的行数
				int j = 0;
				for(Map.Entry<String, Object> entry : map.entrySet()) {
					HSSFCell cell = row.createCell(j++, CellType.STRING); // 设置单元格的数据类型
					if(null!=entry.getValue()){
						cell.setCellValue(entry.getValue().toString());
					}	
					cell.setCellStyle(style); // 设置单元格样式
				}
			}
			
			if (workbook != null) {
				try {
					String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()) + ".xls";
					String headStr = "attachment; filename=\"" + fileName + "\"";
					response.setContentType("APPLICATION/OCTET-STREAM");
					response.setHeader("Content-Disposition", headStr);
					OutputStream out = response.getOutputStream();
					workbook.write(out);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 列头单元格样式
	 */
	public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

		// 设置字体FontRecord
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 11);
		// 字体加粗
		font.setBold(true);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(BorderStyle.THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;

	}

	/*
	 * 列数据信息单元格样式
	 */
	public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		// 设置字体大小
		// font.setFontHeightInPoints((short)10);
		// 字体加粗
		// font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 设置字体名字
		font.setFontName("Courier New");
		// 设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置底边框;
		style.setBorderBottom(BorderStyle.THIN);
		// 设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		// 设置左边框;
		style.setBorderLeft(BorderStyle.THIN);
		// 设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		// 设置右边框;
		style.setBorderRight(BorderStyle.THIN);
		// 设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		// 设置顶边框;
		style.setBorderTop(BorderStyle.THIN);
		// 设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		// 在样式用应用设置的字体;
		style.setFont(font);
		// 设置自动换行;
		style.setWrapText(false);
		// 设置水平对齐的样式为居中对齐;
		style.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;

	}
}
