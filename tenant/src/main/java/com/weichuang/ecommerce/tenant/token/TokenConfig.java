package com.weichuang.ecommerce.tenant.token;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import com.weichuang.commons.sign.XMLUtils;
import com.weichuang.commons.sign.partner.Partner;
import com.weichuang.commons.sign.partner.Partners;

/**
 * <p>ClassName: TokenConfig</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户访问接口用TokenConfig</p>
 * <p>author zhouhe</p>
 * <p>date 2017/1/19 11:05</p>
 */
public class TokenConfig {
	private static Map<String, Partner> partners = new HashMap<String, Partner>();
	private static TokenConfig instance;
	
	public static TokenConfig getInstance() {
		if (instance == null) {
			instance = new TokenConfig();
			try {
				Partners partnersBean = XMLUtils.xml2Java(Partners.class,
						TokenConfig.class.getResourceAsStream("/TokenConfig.xml"));
				List<Partner> partnerList = partnersBean.getPartners();
				for (Partner p : partnerList) {
					partners.put(p.getAppKey(), p);
				}
			}
			catch (JAXBException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public static Partner getPartner(String appKey) {
		if (partners.size() == 0) {
			getInstance();
		}
		return partners.get(appKey);
	}
	
	public static void setPartner(String appkey, Partner partner) {
		partners.put(appkey, partner);
	}
}
