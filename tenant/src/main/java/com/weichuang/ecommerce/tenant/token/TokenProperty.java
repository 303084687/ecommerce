package com.weichuang.ecommerce.tenant.token;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: SessionProperty</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 这里用一句话描述这个方法的作用</p>
 * <p>author zhouhe</p>
 * <p>date 2017/4/12 16:20</p>
 */
@Component
@ConfigurationProperties(prefix = "tokenVr")
public class TokenProperty implements Serializable {
	private static final long serialVersionUID = -6261373783967457206L;
	public Long duration;
	
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}
}
