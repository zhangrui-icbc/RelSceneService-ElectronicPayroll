package com.icbc.rel.hefei.TO;

public class PicmessageTO {
	
	private String newsTitle;//����ͼ�ĵ�С����
	private String description;//����ͼ����Ϣ������
	private String contentUrl;//����ͼ����Ϣ�ı����ݵ�����
	private String Picurl;//����ͼ����ϢͼƬ����
	
	public PicmessageTO(String newsTitle,String description,String contentUrl,String picUrl) {
		this.setNewsTitle(newsTitle);
		this.setDescription(description);
		this.setPicurl(contentUrl);
		this.setPicurl(picUrl);
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getPicurl() {
		return Picurl;
	}

	public void setPicurl(String picurl) {
		Picurl = picurl;
	}

	
}
