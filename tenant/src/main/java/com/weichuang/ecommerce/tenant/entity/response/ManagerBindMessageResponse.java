package com.weichuang.ecommerce.tenant.entity.response;
/**
* <p>ClassName:ManagerBindMessageResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/28 13:38</p>
**/
public class ManagerBindMessageResponse {
    private int userId;//绑定人id
    private String nickName;//绑定人昵称
    private String iconUrl;//绑定人头像

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
