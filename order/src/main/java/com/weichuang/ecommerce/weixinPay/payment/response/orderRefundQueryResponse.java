package com.weichuang.ecommerce.weixinPay.payment.response;

/**
 * <p>ClassName: orderRefundQueryResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:查询退款详情的返回实体类,只封装微信一定返回的字段 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月27日 下午5:50:19</p>
 */
public class orderRefundQueryResponse {
    private String appid; // 公众号id

    private String mch_id; // 商户号

    private String nonce_str; // 随机字符串

    private String sign; // 签名

    private String result_code; // 业务结果

    private String err_code; // 错误码

    private String err_code_des;// 错误描述

    private String transaction_id; // 微信订单号

    private String out_trade_no; // 商户订单号

    private int total_fee; // 订单总金额

    private int cash_fee; // 现金支付金额

    private int refund_count; // 退款笔数

    private String out_refund_no_$n;// 商户退款单号

    private String refund_id_$n; // 微信退款单号

    private int refund_fee_$n; // 申请退款金额

    private String refund_recv_accout_$n; // 退款入账账户

    private int refund_status_$n; // 退款状态

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

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
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

    public int getRefund_count() {
        return refund_count;
    }

    public void setRefund_count(int refund_count) {
        this.refund_count = refund_count;
    }

    public String getOut_refund_no_$n() {
        return out_refund_no_$n;
    }

    public void setOut_refund_no_$n(String out_refund_no_$n) {
        this.out_refund_no_$n = out_refund_no_$n;
    }

    public String getRefund_id_$n() {
        return refund_id_$n;
    }

    public void setRefund_id_$n(String refund_id_$n) {
        this.refund_id_$n = refund_id_$n;
    }

    public int getRefund_fee_$n() {
        return refund_fee_$n;
    }

    public void setRefund_fee_$n(int refund_fee_$n) {
        this.refund_fee_$n = refund_fee_$n;
    }

    public String getRefund_recv_accout_$n() {
        return refund_recv_accout_$n;
    }

    public void setRefund_recv_accout_$n(String refund_recv_accout_$n) {
        this.refund_recv_accout_$n = refund_recv_accout_$n;
    }

    public int getRefund_status_$n() {
        return refund_status_$n;
    }

    public void setRefund_status_$n(int refund_status_$n) {
        this.refund_status_$n = refund_status_$n;
    }

}
