package com.weichuang.ecommerce.order.entity.request;

/**
 * <p>ClassName: OrderRecevierRequest</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 订单收货人数据请求</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/09/14 09:11 </p>
 */
public class OrderRecevierRequest {

	private String receiverName;//收货人姓名
	private int provinceId;//省的id
	private int cityId;//市的id
	private int conutyId;//县的id
	private String address;//收货详细地址
	private String mobile;//收货人手机号
	private String postcode;//邮编

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getConutyId() {
		return conutyId;
	}

	public void setConutyId(int conutyId) {
		this.conutyId = conutyId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}


