package com.weichuang.ecommerce.weixinPay.payment.response;

/**
 * <p>ClassName: orderQueryResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据订单号查询微信订单信息返回封装类,只封装必返回的字段 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月27日 下午4:50:58</p>
 */
public class orderQueryResponse {
    private String appid; // 公众号id

    private String mch_id; // 商户号

    private String nonce_str; // 随机字符串

    private String sign; // 签名

    private String result_code; // 业务结果

    private String openid; // 用户标识

    private String trade_type; // 交易类型

    private String trade_state; // 交易状态

    private String bank_type; // 付款银行

    private int total_fee; // 总金额(单位为分)

    private int cash_fee; // 现金支付金额

    private String transaction_id; // 微信支付订单号

    private String out_trade_no; // 商户订单号

    private String time_end; // 支付完成时间

    private String trade_state_desc; // 交易状态描述

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(int cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTrade_state_desc() {
        return trade_state_desc;
    }

    public void setTrade_state_desc(String trade_state_desc) {
        this.trade_state_desc = trade_state_desc;
    }

}
