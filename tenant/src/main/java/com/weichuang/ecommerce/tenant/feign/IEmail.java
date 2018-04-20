package com.weichuang.ecommerce.tenant.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;

@FeignClient(name = "push-email-logistics", fallback = EmailImpl.class)
@SuppressWarnings("all")
public interface IEmail {
	@RequestMapping(value = "/api/email/template_email", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	Response sendTemplateMail(TemplateMailRequest templateMailRequest) throws ServiceException;
}
