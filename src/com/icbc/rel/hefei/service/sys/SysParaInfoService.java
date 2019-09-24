package com.icbc.rel.hefei.service.sys;





import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.icbc.rel.hefei.TO.Msg;
import com.icbc.rel.hefei.dao.SysParaDescDao;
import com.icbc.rel.hefei.dao.SysParaInfoDao;
import com.icbc.rel.hefei.entity.SysParaDesc;
import com.icbc.rel.hefei.entity.SysParaInfo;

import net.sf.json.JSONArray;
/*
 * 系统参数设置服务
 */
@Service
public class SysParaInfoService {
	private static final Logger logger = Logger.getLogger(SysParaInfoService.class);
	@Autowired
	private SysParaDescDao paraDescDao;
	@Autowired
	private SysParaInfoDao paraInfoDao;
	
	/*
	 * 查询系统参数配置
	 */
	public List<SysParaDesc> GetAllParameters()
	{
		List<SysParaDesc> results=paraDescDao.GetAllSysParaDescs();
		List<SysParaInfo> paras=paraInfoDao.GetAllSysParaInfos();
		for(SysParaDesc desc:results) {
			List<SysParaInfo> items=paras.stream().filter(x->x.getKey()==desc.getKey()).collect(Collectors.toList());
			for(SysParaInfo para : items) {
				String str="{"+para.getDataName()+"}";
				desc.setParameterDesc(desc.getParameterDesc().replace(str, para.getValue()));
			}
			desc.setParameterDesc(desc.getParameterDesc().replace("#", ""));
		}
		return results;
	}
	
	/*
	 * 根据参数格式动态生成html编辑语句
	 */
	public Msg GetParaHtml( int key) {
		Msg msg=new Msg();
		JSONObject obj=new JSONObject();
		String html="";
		SysParaDesc desc=paraDescDao.GetSysParaDescByKey(key);
		List<SysParaInfo> paras=paraInfoDao.GetSysParaInfoByKey(key);
		String result=desc.getParameterDesc();
		String[] texts=result.split("#");
		boolean flag=true;
		for(String item :texts) {
			//是参数
			if(item.contains("{")) {
				SysParaInfo para=paras.stream().filter(x->("{"+x.getDataName()+"}").equals(item)).findFirst().get();
				html+="<div class=\"layui-input-inline\" id="+para.getDataName()+"><input type=\"text\" class=\"layui-input\" name="+para.getDataName()+"  ></input></div>";
				
				obj.put(para.getDataName(), para.getValue());
			}
			else {
				if(flag) {
					html+="<label class=\"layui-form-label\">"+item+"</label>";
					flag=false;
				}else {
					html+=" <div class=\"layui-form-mid\">"+item+"</div>";
				}
			}
		}
		msg.setMessage(html);
		msg.setData(obj);
		msg.setCode(1);
		return msg;
	}

	
	
	/*
	 * 更新参数配置
	 */
	public void UpdateSysParaInfo(int key,String name,String value)
	{
		paraInfoDao.UpdateSystemPara(key, name,value);
	}
	
	/*
	 * 更新参数配置
	 */
	public void UpdateSysParaDesc(int key)
	{
		paraDescDao.UpdateSysParaDesc(key);
	}
	
	/*
	 * 查询系统参数
	 */
	public List<SysParaInfo> getSyspara(){
		return paraInfoDao.GetAllSysParaInfos();
	}
	 
	 
}
