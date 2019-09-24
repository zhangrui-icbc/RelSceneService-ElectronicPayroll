package com.icbc.rel.hefei.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.icbc.rel.hefei.TO.TwoTupleTO;
import com.icbc.rel.hefei.entity.order.OrdImportPicInfo;


public class FileUploadUtil {
	private static Logger logger = Logger.getLogger(FileUploadUtil.class);
	private String filename;
	
	/*
	 * 文件上传到服务器
	 * 返回存放的完整路径
	 */
	public TwoTupleTO UploadFile(HttpServletRequest request,String savePath) throws Exception {
		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String filename = "";
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			logger.info(savePath + "目录不存在，需要创建");
			// 创建目录
			file.mkdir();
		}
		// 消息提示
		String message = "";
		String option="";
			// 使用Apache文件上传组件处理文件上传步骤：
			// 1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8");
			// 3、判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return null;
			}
			// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				// 如果fileitem中封装的是普通输入项的数据
				if (item.isFormField()) {
					String name = item.getFieldName();
					// 解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					// value = new String(value.getBytes("iso8859-1"),"UTF-8");
					logger.info(name + "=" + value);
					if(name.equals("classify")){
						option=value;
					}
				} else {// 如果fileitem中封装的是上传文件
						// 得到上传的文件名称，
					filename = item.getName();
					if (filename.equals("")) {
						logger.info("请选择需要上传的文件");
						return null;
					} else {
						logger.info("上传文件名称：" + filename);
						option=filename;
						// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
						// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
						filename = filename.substring(filename.lastIndexOf(File.separator) + 1);
						// 获取item中的上传文件的输入流
						InputStream in = item.getInputStream();
						// 创建一个文件输出流
						FileOutputStream out = new FileOutputStream(savePath + File.separator + filename);
						// 创建一个缓冲区
						byte buffer[] = new byte[1024];
						// 判断输入流中的数据是否已经读完的标识
						int len = 0;
						// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
						while ((len = in.read(buffer)) > 0) {
							// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
							out.write(buffer, 0, len);
						}
						// 关闭输入流
						in.close();
						// 关闭输出流
						out.close();
						// 删除处理文件上传时生成的临时文件
						item.delete();
						logger.info("文件上传成功！");
						break;
					}
				}
			}
			TwoTupleTO to=new TwoTupleTO();
			to.setName(option);
			to.setValue(savePath + File.separator + filename);
		
		return to;
	}

	/*
	 * 图片上传到服务器
	 */
	public List<OrdImportPicInfo> UploadPicture(HttpServletRequest request) throws Exception {
		
		String savePath = SystemConfigUtil.picPath;
		String filename = "";
		String filename2 = "";
		String dishname="";
		List<OrdImportPicInfo> list = new ArrayList<OrdImportPicInfo>();
		
		File file = new File(savePath);
		// 判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			logger.info(savePath + "目录不存在，需要创建");
			// 创建目录
			file.mkdir();
		}
		// 使用Apache文件上传组件处理文件上传步骤：
		// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			return null;
		}
		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		List<FileItem> list1 = upload.parseRequest(request);
		
		for (FileItem item : list1) {
			OrdImportPicInfo info = new OrdImportPicInfo();
			// 如果fileitem中封装的是普通输入项的数据
			if (item.isFormField()) {
				String name = item.getFieldName();
				// 解决普通输入项的数据的中文乱码问题
				String value = item.getString("UTF-8");
				// value = new String(value.getBytes("iso8859-1"),"UTF-8");
				logger.info(name + "=" + value);
				if(name.equals("classify")){
					dishname=value;
				}
			} else {// 如果fileitem中封装的是上传文件
					// 得到上传的文件名称，
				filename = item.getName();//中文文件名（有扩展名）
				
				
				if (filename.equals("")) {
					logger.info("请选择需要上传的图片");
					return null;
				} else {
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					//注意斜杠和反斜杠的区别，window一般用\，linux一般用/，windows也识别/。
					dishname = filename.substring(filename.lastIndexOf(File.separator)+1, filename.lastIndexOf("."));//中文文件名（无扩展名）
					String pre=filename.substring(filename.lastIndexOf("."));
					//更改中文文件名为uid
					String uid = UUID.randomUUID().toString().replaceAll("-", "");
					filename2 = uid+pre;//filename2 = uid+扩展名
					// 获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + File.separator + filename2);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					// 删除处理文件上传时生成的临时文件
					item.delete();					
				}
				info.setDishName(dishname);
			}			
			list.add(info);
		}	
		return list;
	}

}
