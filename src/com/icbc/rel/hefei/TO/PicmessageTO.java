package com.icbc.rel.hefei.TO;

public class PicmessageTO {
	
	private String newsTitle;//单条图文的小标题
	private String description;//单条图文消息的描述
	private String contentUrl;//单条图文消息文本内容的链接
	private String Picurl;//单条图文消息图片链接
	
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
