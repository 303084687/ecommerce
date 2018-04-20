package com.weichuang.commons;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.zhidianwuxian.cn</p>
 * <p>Description:  </p>
 * <p>Author:xiaochangdong, 2017/4/21</p>
 */
public class LogExceptionStackUtil {

    /** 
    * <p>Description: 在日志文件中打印异常栈</p>
    * <p>author xiaochangdong </p>
    * <p>date 2017/4/21 14:36 </p>
    * <p>return </p>
    */
    public static String LogExceptionStack(Throwable e) {
        StringWriter errorsWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(errorsWriter));
        return errorsWriter.toString();
    }
}
