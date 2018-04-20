package com.weichuang.ecommerce.tenant.feign;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DELL on 2016/10/22.
 */
public class TemplateMailRequest implements Serializable {
	private static final long serialVersionUID = 5572642956380243788L;
	private String to;
	private String[] cc;
	private String[] bcc;
	private Date sentDate;
	private String subject;
	private String text;
	private String imagePath;
	private Integer mailTemplateType;
	
	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String[] getCc() {
		return cc;
	}
	
	public void setCc(String[] cc) {
		this.cc = cc;
	}
	
	public String[] getBcc() {
		return bcc;
	}
	
	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}
	
	public Date getSentDate() {
		return sentDate;
	}
	
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Integer getMailTemplateType() {
		return mailTemplateType;
	}
	
	public void setMailTemplateType(Integer mailTemplateType) {
		this.mailTemplateType = mailTemplateType;
	}
}
