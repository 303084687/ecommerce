package com.weichuang.commons.sign.partner;

import com.weichuang.commons.sign.XMLUtils;

import javax.xml.bind.JAXBException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: AppStoreResource</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.zhidianwuxian.cn</p>
 * <p>Description: 签名 权限 认证</p>
 * <p>author zhouhe</p>
 * <p>date 2016/11/13 18:48</p>
 */
public class PartnerConfig {
    private static Map<String, Partner> partners = new HashMap<String, Partner>();
    private static PartnerConfig instance;

    public static PartnerConfig getInstance() {
        if (instance == null) {
            instance = new PartnerConfig();
            try {
                Partners partnersBean = XMLUtils.xml2Java(Partners.class, PartnerConfig.class.getResourceAsStream("/PartnerConfig.xml"));
                List<Partner> partnerList = partnersBean.getPartners();
                for (Partner p : partnerList) {
                    partners.put(p.getAppKey(), p);
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            } catch (IOException e) {
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
