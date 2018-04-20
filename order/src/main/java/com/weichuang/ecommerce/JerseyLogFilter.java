//package com.zhidian.ecommerce;
//
//import com.alibaba.fastjson.JSON;
//import com.zhidian.commons.ApiLogRequestEntity;
//import com.zhidian.commons.ApiLogResponseEntity;
//import com.zhidian.commons.Utils;
//import org.apache.commons.lang3.StringUtils;
//import org.glassfish.jersey.message.internal.HeaderUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.container.*;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.ext.Provider;
//import java.io.IOException;
//import java.net.URI;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicLong;
//
//@Provider
//@PreMatching
//public class JerseyLogFilter implements ContainerRequestFilter, ContainerResponseFilter {
//    private static final Logger log = LoggerFactory.getLogger(JerseyLogFilter.class);
//
//    private static final String NOTIFICATION_PREFIX = "* ";
//    private static final String SERVER_REQUEST = "> ";
//    private static final String SERVER_RESPONSE = "< ";
//    private static final String CLIENT_REQUEST = "/ ";
//    private static final String CLIENT_RESPONSE = "\\ ";
//    private static final AtomicLong logSequence = new AtomicLong(0);
//
//    @Context
//    private HttpServletRequest httpServletRequest;
//
//    @Autowired
//    private RabbitMQHelper rabbitMQHelper;
//
//    @Autowired
//    private DiscoveryClient client;
//
//    /**
//     * 请求拦截
//     * @param requestContext
//     * @throws IOException
//     */
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        if("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
//            return;
//        }
//
//        try {
//            String id = Utils.generateUUID();
//            requestContext.setProperty("log-id", id);
//
//            long sequenceId = logSequence.incrementAndGet();
//            StringBuilder b = new StringBuilder();
//            StringBuilder requestContent = new StringBuilder();
//            printRequestLine(SERVER_REQUEST, b, sequenceId, requestContext.getMethod(), requestContext.getUriInfo().getRequestUri());
//            printPrefixedHeaders(SERVER_REQUEST, b, sequenceId, requestContext.getHeaders());
//            printPrefixedRequestBody(SERVER_REQUEST, requestContent, sequenceId, requestContext);
//            log.info(b.toString());
//            log.info(requestContent.toString());
//
//            ServiceInstance instance = client.getLocalServiceInstance();
//
//            ApiLogRequestEntity apiLogRequestEntity = new ApiLogRequestEntity();
//            apiLogRequestEntity.setRequestId(id);
//            apiLogRequestEntity.setMethod(requestContext.getMethod());
//            apiLogRequestEntity.setHost(instance.getHost());
//            apiLogRequestEntity.setPort(instance.getPort());
//            apiLogRequestEntity.setServiceId(instance.getServiceId());
//            apiLogRequestEntity.setRequestDate(new Date());
//            apiLogRequestEntity.setRequestLog(b.toString());
//            apiLogRequestEntity.setRequestContent(requestContent.toString());
//
//            rabbitMQHelper.convertAndSend("log-exchange", "log-request-routingKey", apiLogRequestEntity);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 返回拦截
//     * @param requestContext
//     * @param responseContext
//     * @throws IOException
//     */
//    @Override
//    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
//        if("OPTIONS".equalsIgnoreCase(requestContext.getMethod())) {
//            return;
//        }
//
//        try {
//            long sequenceId = logSequence.incrementAndGet();
//            StringBuilder b = new StringBuilder();
//            StringBuilder responseContent = new StringBuilder();
//            //获取容器响应状态
//            printResponseLine(SERVER_RESPONSE, b, sequenceId, responseContext.getStatus());
//            printPrefixedHeaders(SERVER_RESPONSE, b, sequenceId, HeaderUtils.asStringHeaders(responseContext.getHeaders()));
//            printPrefixedResponseBody(SERVER_RESPONSE, responseContent, sequenceId, responseContext);
//            log.info(b.toString());
//            log.info(responseContent.toString());
//
//            ApiLogResponseEntity apiLogResponseEntity = new ApiLogResponseEntity();
//            apiLogResponseEntity.setRequestId(requestContext.getProperty("log-id").toString());
//            apiLogResponseEntity.setResponseDate(new Date());
//            apiLogResponseEntity.setResponseStatus(responseContext.getStatus());
//            apiLogResponseEntity.setResponseLog(b.toString());
//            apiLogResponseEntity.setResponseContent(responseContent.toString());
//
//            rabbitMQHelper.convertAndSend("log-exchange", "log-response-routingKey", apiLogResponseEntity);
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    private StringBuilder prefixId(StringBuilder b, long id) {
//        b.append(Long.toString(id)).append(" ");
//        return b;
//    }
//
//    /**
//     * 请求地址
//     * @param prefix
//     * @param b
//     * @param id
//     * @param method
//     * @param uri
//     */
//    private void printRequestLine(final String prefix, StringBuilder b, long id, String method, URI uri) {
//        prefixId(b, id).append(NOTIFICATION_PREFIX)
//                .append("AirLog - Request received on thread ")
//                .append(Thread.currentThread().getName()).append("\n");
//        prefixId(b, id).append(prefix).append(method).append(" ").append(uri.toASCIIString()).append("\n");
//    }
//
//    /**
//     * 返回路径
//     * @param prefix
//     * @param b
//     * @param id
//     * @param status
//     */
//    private void printResponseLine(final String prefix, StringBuilder b, long id, int status) {
//        prefixId(b, id).append(NOTIFICATION_PREFIX)
//                .append("AirLog - Response received on thread ")
//                .append(Thread.currentThread().getName()).append("\n");
//        prefixId(b, id).append(prefix).append(Integer.toString(status)).append("\n");
//    }
//
//    /**
//     * 请求头信息
//     * @param prefix
//     * @param b
//     * @param id
//     * @param headers
//     */
//    private void printPrefixedHeaders(final String prefix, StringBuilder b, long id, MultivaluedMap<String, String> headers) {
//        for (Map.Entry<String, List<String>> e : headers.entrySet()) {
//            List<?> val = e.getValue();
//            String header = e.getKey();
//
//            if (val.size() == 1) {
//                prefixId(b, id).append(prefix).append(header).append(": ").append(val.get(0)).append("\n");
//            } else {
//                StringBuilder sb = new StringBuilder();
//                boolean add = false;
//                for (Object s : val) {
//                    if (add) {
//                        sb.append(',');
//                    }
//                    add = true;
//                    sb.append(s);
//                }
//                prefixId(b, id).append(prefix).append(header).append(": ").append(sb.toString()).append("\n");
//            }
//        }
//    }
//
//    /**d
//     * 获取请求参数
//     * @param prefix
//     * @param b
//     * @param id
//     * @param requestContext
//     */
//    private void printPrefixedRequestBody(final String prefix, StringBuilder b, long id, ContainerRequestContext requestContext) {
//        try {
//            HttpServletRequest requestWrapper = new ApiHttpServletRequestHelper(httpServletRequest);
//            String data = IOReaderHelper.ioReader(requestWrapper);
//            requestContext.setEntityStream(requestWrapper.getInputStream());
//            if(StringUtils.isNotBlank(data)) {
////                prefixId(b, id).append(prefix).append("body").append(": ").append("\n");
////                prefixId(b, id).append(prefix).append(data).append("\n");
//                b.append(data);
//            }
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//    }
//
//    /**
//     * 获取返回参数
//     * @param prefix
//     * @param b
//     * @param id
//     * @param responseContext
//     */
//    private void printPrefixedResponseBody(final String prefix, StringBuilder b, long id, ContainerResponseContext responseContext) {
//        Object obj = responseContext.getEntity();
//        if(null != obj) {
////            prefixId(b, id).append(prefix).append("body").append(": ").append("\n");
////            prefixId(b, id).append(prefix).append(JSON.toJSON(obj)).append("\n");
//            b.append(JSON.toJSON(obj));
//        }
//    }
//
//}
