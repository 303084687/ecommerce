package com.weichuang.ecommerce.tenant.entity;
/**
* <p>ClassName:ManagerDetailEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/27 16:55</p>
**/
public class ManagerDetailEntity  {

    private int userId;//id编号
    private String realName;//真实姓名
    private String nickName;//昵称
    private String iconUrl;//头像
    private int agentId;//组织id
    private String agentName;//组织名称
    private int receiverAccountid;//收款人id编号
    private String receiverNickname;//收款人昵称
    private String receiverOpenid;//收款人openId

    public int getUserId() {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }



    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public int getReceiverAccountid() {
        return receiverAccountid;
    }

    public void setReceiverAccountid(int receiverAccountid) {
        this.receiverAccountid = receiverAccountid;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

    public String getReceiverOpenid() {
        return receiverOpenid;
    }

    public void setReceiverOpenid(String receiverOpenid) {
        this.receiverOpenid = receiverOpenid;
    }
}
