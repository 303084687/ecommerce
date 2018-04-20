package com.weichuang.ecommerce;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weichuang.commons.Constant;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: jersey统一异常处理器 </p>
 * <p>Author:jmzhang/张际明, 16/09/20</p>
 */
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {
	private static final Logger log = LoggerFactory.getLogger(ExceptionMapperSupport.class);
	
	//    @Override
	//    public Response toResponse(Exception exception) {
	//        log.error(exception.getMessage(), exception);
	//
	//        Response response;
	//
	//        com.weichuang.commons.Response result = new com.weichuang.commons.Response(com.weichuang.commons.Response.ERROR, exception.getMessage());
	//
	//        response = Response.ok(result).encoding(Constant.ENCODING).type(Constant.APPLICATION_JSON_UTF8).build();
	//
	//        return response;
	//    }
	@Override
	public Response toResponse(Exception exception) {
		com.weichuang.commons.Response response = null;
		if (exception instanceof ServiceException) {
			ServiceException resourceException = (ServiceException) exception;
			int code = resourceException.getCode();
			String message = resourceException.getMessage();
			log.error(exception.getMessage(), exception);
			response = new com.weichuang.commons.Response(code, message);
		} else {
			StringWriter sw = new StringWriter();
			exception.printStackTrace(new PrintWriter(sw, true));
			String ex = sw.toString();
			log.error("服务器错误" + ex);
			response = new com.weichuang.commons.Response(Result.FAIL.getCode(), Result.FAIL.getMessage());
		}
		return Response.ok(response).encoding(Constant.ENCODING).type(Constant.APPLICATION_JSON_UTF8).build();
	}
}
