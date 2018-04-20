package com.weichuang.ecommerce.tenant.entity.response;
/**
 * <p>ClassName:InviteNewEntity</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:邀新</p>
 * <p>author:zhanghongsheng</p>
 * <p>2017/11/19 14:10</p>
 **/
public class InviteNewResponse {
        //是否新会员
        private boolean newIs;
        //会员手机号
        private String mobile;
        //绑定时间
        private String bindTime;
        //昵称
        private String nickName;
        //销售名称
        private String saleName;
        //门店名称
        private String saleAgentName;

        public boolean isNewIs() {
            return newIs;
        }

        public void setNewIs(boolean newIs) {
            this.newIs = newIs;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getBindTime() {
            return bindTime;
        }

        public void setBindTime(String bindTime) {
            this.bindTime = bindTime;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getSaleName() {
            return saleName;
        }

        public void setSaleName(String saleName) {
            this.saleName = saleName;
        }

        public String getSaleAgentName() {
            return saleAgentName;
        }

        public void setSaleAgentName(String saleAgentName) {
            this.saleAgentName = saleAgentName;
        }


}