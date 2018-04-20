package com.weichuang.ecommerce.tenant.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;

@Component
@SuppressWarnings("all")
public class EmailImpl implements IEmail {
	private static final Logger log = LoggerFactory.getLogger(EmailImpl.class);
	
	@Override
	public Response sendTemplateMail(TemplateMailRequest templateMailRequest) throws ServiceException {
		log.error("请求出错email/template_email,执行断路器");
		return null;
	}
}
