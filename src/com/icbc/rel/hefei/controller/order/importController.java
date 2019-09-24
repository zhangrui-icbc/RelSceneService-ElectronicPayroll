package com.icbc.rel.hefei.controller.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.TO.TwoTupleTO;
import com.icbc.rel.hefei.entity.order.OrdImportInfo;
import com.icbc.rel.hefei.entity.order.OrdImportPicInfo;
import com.icbc.rel.hefei.entity.order.OrdMenuInfo;
import com.icbc.rel.hefei.service.order.ImportPicService;
import com.icbc.rel.hefei.service.order.ImportService;
import com.icbc.rel.hefei.service.order.MenuInfoService;
import com.icbc.rel.hefei.service.rel.HadoopService;
import com.icbc.rel.hefei.util.CommonUtil;
import com.icbc.rel.hefei.util.FileUploadUtil;
import com.icbc.rel.hefei.util.SystemConfigUtil;

@Controller
@RequestMapping(value = "/mp")
public class importController {
	private static Logger logger = Logger.getLogger(importController.class);
	@Autowired
	private ImportService importService;
	@Autowired
	private ImportPicService importPicService;
	@Autowired
	private MenuInfoService menuInfoService;

	/*
	 * �ϴ��ļ�����������
	 */
	@RequestMapping("/importmenu")
	@ResponseBody
	public Msg importData(HttpServletRequest request, String activityUid) throws FileUploadException {
		Msg msg = new Msg();
		try {

			FileUploadUtil util = new FileUploadUtil();
			TwoTupleTO to = util.UploadFile(request, SystemConfigUtil.tempPath);
			if (to == null) {
				msg.setCode(-1);
				msg.setMessage("��ѡ���ļ����ϴ���");
				return msg;
			}
			HttpSession session = request.getSession(false);
			if (session == null) {
				msg.setCode(-1);
				msg.setMessage("���ȵ�¼��");
				return msg;
			}
			
			//����˴����ļ���ʽ���õ�����
			String str=to.getName();
			String format=str.substring(str.indexOf(".")+1);
			logger.info("format"+format);
			if(!(format.equals("xls")||format.equals("xlsx"))) {				
				msg.setCode(-1);
				msg.setMessage("�ϴ�ʧ�ܣ����ϴ�xls��xlsx��ʽ�ļ�");
				return msg;
			}
			

			if (activityUid == null || activityUid.equals("null")) {
				msg.setCode(-1);
				msg.setMessage("���ȱ������������Ϣ��");
				return msg;
			}

			String uid = UUID.randomUUID().toString().replaceAll("-", "");
			String result=importService.Readwmsupper(to.getValue(), uid, activityUid);
            if(result!=null) {
            	msg.setCode(-1);
				msg.setMessage(result);
	             return msg;
            }
			OrdImportInfo info = new OrdImportInfo();
			info.setFileName(to.getName());
			info.setStatus(1);
			info.setFileUid(uid);
			info.setActivityUid(activityUid);
			info.setImportTime(new Date());

			importService.AddImportInfo(info);

			msg.setCode(1);
			msg.setMessage(activityUid);
		} catch (Exception ex) {
			logger.error("���뱨��", ex);
			msg.setCode(-1);
			msg.setMessage("�ļ�����ʧ�ܣ�" + ex.getMessage());
		}
		return msg;

	}

	/*
	 * ��ѯ����˵�
	 */
	@RequestMapping(value = "/getFileMenu", method = RequestMethod.POST)
	@ResponseBody
	public Msg getFileMenu(String activityUid, int page, int limit) {
		Msg msg = new Msg();
		List<OrdImportInfo> result = importService.getallFileInfo(activityUid, page, limit);
		int count = importService.getallFileInfoNum(activityUid);
		msg.setData(result);
		msg.setCount(count);
		return msg;
	}

	/*
	 * ���ϲ˵�
	 */
	@RequestMapping(value = "/updateFileMenuStatus")
	@ResponseBody
	public Msg UpdateFileMenuStatus(HttpServletRequest request, String fileUid) {
		Msg msg = new Msg();
		try {
			importService.updateStatus(fileUid);
			msg.setCode(1);
			msg.setMessage("״̬���³ɹ�");
		} catch (Exception ex) {
			msg.setCode(-1);
			logger.error("�˵�����ʧ�ܣ�", ex);
			msg.setMessage("�˵�����ʧ�ܣ�" + ex.getMessage());
		}
		return msg;

	}

	/*
	 * �ϴ��˵�ͼƬ
	 */
	@RequestMapping(value = "/importPic")
	@ResponseBody
	public Msg importPic(HttpServletRequest request, String activityUid) {
		Msg msg = new Msg();
		try {
			if (activityUid == null || activityUid.equals("null")) {
				msg.setCode(-1);
				msg.setMessage("���ȱ������������Ϣ��");
				return msg;
			}

			msg = HadoopService.getUploadFile(request,1024);
			if(msg.getCode()!=1) {
				return msg;
			}
			@SuppressWarnings("unchecked")
			List<TwoTupleTO> result=(List<TwoTupleTO>) msg.getData();
			for (TwoTupleTO item : result) {
				OrdImportPicInfo info = new OrdImportPicInfo();
				info.setActivityUid(activityUid);
				info.setDishName(item.getName());
				info.setPicUrl(item.getValue());
				info.setImportTime(new Date());
				importPicService.add(info);
			}
			msg.setCode(1);
			msg.setMessage(activityUid);
		} catch (Exception ex) {
			msg.setCode(-1);
			logger.error("ͼƬ�ϴ�����", ex);
			msg.setMessage("ͼƬ�ϴ�ʧ�ܣ�" + ex.getMessage());
		}
		return msg;
	}

	/*
	 * ��ѯ����ͼƬ
	 */
	@RequestMapping(value = "/getPicInfo")
	@ResponseBody
	public Msg getPicInfo(String activityUid, int page, int limit) {
		Msg msg = new Msg();
		List<OrdImportPicInfo> result = importPicService.selectAll(activityUid, page, limit);
		int count = importPicService.selectAllNum(activityUid);
		msg.setData(result);
		msg.setCount(count);
		return msg;

	}

	/*
	 * ɾ������ͼƬ
	 */
	@RequestMapping(value = "/deletePic")
	@ResponseBody
	public Msg deletePic(HttpServletRequest request, @RequestParam(value = "iid") String[] iid) {
		Msg msg = new Msg();
		for (int i = 0; i < iid.length; i++) {
			importPicService.deletePic(iid[i]);
		}
		msg.setMessage("ɾ���ɹ�");
		return msg;

	}

	/*
	 * ��ѯ��������
	 */
	@RequestMapping("/GetImportData")
	@ResponseBody
	public Msg GetImportData(HttpServletRequest request, String uid) {
		Msg msg = new Msg();
		List<OrdMenuInfo> result = menuInfoService.getMenuDetail(uid);
		msg.setData(GetWmData(result));
		return msg;
	}

	/*
	 * ��ȡ�������飨��֯�ɱ�����ݣ�
	 */
	public @ResponseBody JSONObject GetWmData(List<OrdMenuInfo> result) {
		JSONObject obj = new JSONObject();
		JSONObject obj1 = new JSONObject();
		JSONObject item;
		JSONArray jsondetailArray = new JSONArray();
		List<Date> dates = new ArrayList<Date>();
		List<String> columns = new ArrayList<String>();
		int k = 0;
		int max = 0;
		for (int i = 0; i < result.size(); i++) {
			if (!dates.contains(result.get(i).getMenuDate())) {
				dates.add(result.get(i).getMenuDate());
				columns.add(CommonUtil.DateConvertStr(result.get(i).getMenuDate(), "yyyy-MM-dd"));
				max = max < k ? k : max;
				k = 1;
			} else {
				k++;
			}
		}
		columns.sort((a,b)->a.compareTo(b.toString()));

		for (int i = 0; i < max; i++) {

			OrdMenuInfo dish = result.get(i);
			item = new JSONObject();
			item.put("classifyName", dish.getClassifyName());
			item.put("setmealName", dish.getSetmealName());
			for (int j = 0; j < columns.size(); j++) {
				String date = columns.get(j);
				item.put(date, result.get(j * max + i).getDishName());
				item.put(date + "_price", result.get(j * max + i).getDishName().equals("����") ? "����"
						: result.get(j * max + i).getDishPrice());
				item.put(date + "_amount",
						result.get(j * max + i).getAmount() == 1000000 ? "����" : result.get(j * max + i).getAmount());
				item.put(date + "_limit",result.get(j * max + i).getMenuLimit()== 0 ? "" : result.get(j * max + i).getMenuLimit());

			}
			jsondetailArray.add(item);

		}

		obj.put("data", JSONArray.toJSON(jsondetailArray));
		obj.put("count", jsondetailArray.size());
		obj1.put("data", obj);
		obj1.put("columns", columns);
		logger.info(JSONArray.toJSON(jsondetailArray));
		return obj1;
	}

}
