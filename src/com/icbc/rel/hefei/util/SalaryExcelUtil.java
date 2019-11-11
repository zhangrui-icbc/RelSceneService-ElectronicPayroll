package com.icbc.rel.hefei.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SalaryExcelUtil {

    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

	/**
     * 导出excel
     * @param title  导出表的标题
     * @param rowsName 导出表的列名
     * @param dataList  需要导出的数据
     * @param fileName  生成excel文件的文件名
     * @param response
     */
    public void exportExcel(String title, String[] rowsName, List<Object[]> dataList, String fileName, HttpServletResponse response) throws Exception{
        OutputStream output = response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition",
                "attachment; filename="+fileName);
        //response.setContentType("application/msexcel");
       response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //response.setContentType("application/octet-stream; charset=UTF-8");// 根据个人需要,这个是下载文件的类型
  
        this.export(title,rowsName,dataList,fileName,output);
        this.close(output);
    }

    /*
     * 导出数据
     */
    private void export(String title,String[] rowName,List<Object[]> dataList,String fileName,OutputStream out) throws Exception {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
            HSSFSheet sheet = workbook.createSheet(title); // 创建工作表
            HSSFRow rowm = sheet.createRow(0);  // 产生表格标题行
            HSSFCell cellTiltle = rowm.createCell(0);   //创建表格标题列
            // sheet样式定义;    getColumnTopStyle();    getStyle()均为自定义方法 --在下面,可扩展
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);// 获取列头样式对象
            HSSFCellStyle style = this.getStyle(workbook); // 获取单元格样式对象
            //合并表格标题行，合并列数为列名的长度,第一个0为起始行号，第二个1为终止行号，第三个0为起始列好，第四个参数为终止列号
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
            cellTiltle.setCellStyle(columnTopStyle);    //设置标题行样式
            cellTiltle.setCellValue(title);     //设置标题行值
            int columnNum = rowName.length;     // 定义所需列数
            HSSFRow rowRowName = sheet.createRow(2); // 在索引2的位置创建行(最顶端的行开始的第二行)
            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n); // 创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING); // 设置列头单元格的数据类型
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text); // 设置列头单元格的值
                cellRowName.setCellStyle(columnTopStyle); // 设置列头单元格样式
            }

            // 将查询出的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);   // 遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);   // 创建所需的行数
                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;   // 设置单元格的数据类型
//                    if (j == 0) {
//                        cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
//                        cell.setCellValue(i + 1);
//                    } else {
                        cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString()); // 设置单元格的值
                        }
                        else{
                        	 cell.setCellValue(""); // 设置单元格的值
                        }
                    //}
                    cell.setCellStyle(style); // 设置单元格样式
                }
            }

            // 让列宽随着导出的列长自动适应
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                    HSSFRow currentRow;
                    // 当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }
                    if (currentRow.getCell(colNum) != null) {
                        HSSFCell currentCell = currentRow.getCell(colNum);
                        currentCell.setCellType(Cell.CELL_TYPE_STRING);
                        if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        	//String cellValue = numCell.getStringCellValue();
                            int length = currentCell.getStringCellValue()
                                    .getBytes().length;
                            if (columnWidth < length) {
                                columnWidth = length;
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 列头单元格样式
     */
    private HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {

        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("宋体");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        // 设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        return style;

    }

    /*
     * 列数据信息单元格样式
     */
    private HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        // font.setFontHeightInPoints((short)10);
        // 字体加粗
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 设置字体名字
        font.setFontName("宋体");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        // 设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        // 设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        // 设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    /**
     * 关闭输出流
     * @param os
     */
    private void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
	 * 读入excel文件，解析后返回
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static List<String[]> ReadExcel(Workbook workbook, int sheetNum) {
		// 检查文件
		// checkFile(file);
		// 获得Workbook工作薄对象
		
		// 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
		List<String[]> list = new ArrayList<String[]>();
		if (workbook != null) {
			
			// 获得当前sheet工作表
			Sheet sheet = workbook.getSheetAt(sheetNum);
			if (sheet == null) {
				return list;
			}
			// 获得当前sheet的开始行
			int firstRowNum = sheet.getFirstRowNum();
			// 获得当前sheet的结束行
			int lastRowNum = sheet.getPhysicalNumberOfRows();
			int lastCellNum=0;
			lastCellNum = sheet.getRow(1).getPhysicalNumberOfCells();
			// 循环除了第一行的所有行
			for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
				// 获得当前行
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					continue;
				}
				// 获得当前行的开始列
				// int firstCellNum = row.getFirstCellNum();
				// 获得当前行的列数
				//if(rowNum==firstRowNum + 1) {
				
				//}
				String[] cells = new String[lastCellNum];
				// 循环当前行
				String total="";
				
				for (int cellNum = 0; cellNum < lastCellNum; cellNum++) {
					Cell cell = row.getCell(cellNum);
					String value=getCellValue(cell);
					cells[cellNum] = value;
					total+=value.trim();
				}
				//空行
				if(total.equals("")) {
					continue;
				}
				list.add(cells);
				// }
			}
			// workbook. close();
		}
		return list;
	}

	public static Workbook getWorkBook(String filePath) throws IOException {
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath));

			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (filePath.endsWith("xls")) {
				// 2003
				workbook = new HSSFWorkbook(in);
			} else if (filePath.endsWith("xlsx")) {
				// 2007
				workbook = new XSSFWorkbook(in);
			}
		} catch (IOException e) {
			throw e;
		}
		return workbook;
	}
	
	public static Workbook getWorkBookByUrl(String filePath) throws IOException {
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		URL url=new URL(filePath);
		
		try {
			BufferedInputStream in = new BufferedInputStream(url.openStream());

			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (filePath.endsWith("xls")) {
				// 2003
				workbook = new HSSFWorkbook(in);
			} else if (filePath.endsWith("xlsx")) {
				// 2007
				workbook = new XSSFWorkbook(in);
			}
		} catch (IOException e) {
			throw e;
		}
		return workbook;
	}
	
	

	public static String getCellValue(Cell cell) {
		String cellValue = "";
		if (cell == null) {
			return cellValue;
		}
		// 把数字当成String来读，避免出现1读成1.0的情况
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date d = cell.getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				cellValue = formater.format(d);
				cell.setCellValue(cellValue);
			}
			cell.setCellType(Cell.CELL_TYPE_STRING);
			
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			cellValue = String.valueOf(cell.getNumericCellValue());

			break;
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA: // 公式
			cellValue =NumberToTextConverter.toText(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "非法字符";
			break;

		default:
			cellValue = "未知类型";
			break;
		}
		return cellValue;
	}
	
	
	//ILNIQ
	@SuppressWarnings("resource")
	public static void writer(String path, String fileName, String fileType, List<String[]> list, String titleRow[])throws IOException {
		Workbook wb = null;
		String excelPath = path + File.separator + fileName + "." + fileType;
		File file = new File(excelPath);
		Sheet sheet = null;
		// 创建工作文档对象
		if (!file.exists()) {
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook();

			} else if (fileType.equals("xlsx")) {

				wb = new XSSFWorkbook();
			} else {
				try {
					throw new Exception("文件格式不正确");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 创建sheet对象
			sheet = (Sheet) wb.createSheet("sheet1");
			OutputStream outputStream = new FileOutputStream(excelPath);
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
			System.out.println("不存在");
		} else {
			System.out.println("存在");
			if (fileType.equals("xls")) {
				wb = new HSSFWorkbook();

			} else if (fileType.equals("xlsx")) {
				wb = new XSSFWorkbook();

			} else {
				try {
					throw new Exception("文件格式不正确");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 创建sheet对象
		if (sheet == null) {
			sheet = (Sheet) wb.createSheet("sheet1");
		}

	/*	// 添加表头
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		row.setHeight((short) 540);
		cell.setCellValue("失败日志"); // 创建第一行
*/
		CellStyle style = wb.createCellStyle(); // 样式对象
		// 设置单元格的背景颜色为淡蓝色
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		style.setWrapText(true);// 指定当单元格内容显示不下时自动换行

		//cell.setCellStyle(style); // 样式，居中

		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 280);
		style.setFont(font);
		
		// 单元格合并
		// 四个参数分别是：起始行，起始列，结束行，结束列
		//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
		//sheet.autoSizeColumn(5200);

		Row row = sheet.createRow(0); // 创建第二行
		Cell cell=row.createCell(0);
		for (int i = 0; i < titleRow.length; i++) {
			System.out.println("i:"+i);
			cell = row.createCell(i);
			cell.setCellValue(titleRow[i]);
			//cell.setCellStyle(style); // 样式，居中
			//sheet.setColumnWidth(i, 20 * 256);
			//sheet.autoSizeColumn(i);
		}
		row.setHeight((short) 540);

		// 循环写入行数据
		for (int i = 0; i < list.size(); i++) {
			row = (Row) sheet.createRow(i + 1);
			row.setHeight((short) 500);
			for (int j = 0; j < list.get(i).length; j++) {
				row.createCell(j).setCellValue(list.get(i)[j]);
			}
		}

		// 创建文件流
		OutputStream stream = new FileOutputStream(excelPath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
	}
	
	//删除指定路径的文件
	
	
	

}
