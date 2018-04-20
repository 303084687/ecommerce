package com.weichuang.ecommerce.product.entity.request;

import java.math.BigDecimal;
import java.util.List;

import com.weichuang.ecommerce.product.entity.ProductStockEntity;

/**
 * <p>ClassName: ProductEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品请求表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月14日 上午11:06:15</p>
 */
public class ProductRequest {
    private int id;

    private String code;// 商品编号

    private String name;// 商品名称

    private String smallPicture;// 商品的小图

    private String images;// 商品图片,多个用|分割

    private int type;// 商品类型1单品2套餐

    private int isPresell;// 是否预售,1否2是默认为1

    private int islimitCoupon;// 是否使用优惠券1 是2否 默认为1

    private String brand;// 商品品牌

    private String norms;// 规格

    private String month;// 套餐选择的月份

    private String deploy;// 包装说明

    private BigDecimal costPrice;// 成本价

    private BigDecimal sellingPrice;// 销售价

    private int discountEnabled;// 优惠价启用标识,1未启用2已启用

    private BigDecimal discountPrice;// 优惠价

    private String discountStart;// 优惠开始时间

    private String discountEnd;// 优惠结束时间

    private String weight;// 重量

    private String shelfLife;// 保质期

    private int actions;// 运营标识 1：新品 2：热卖 3：特价 4：爆款

    private int status;// 1上架2下架

    private String introduction;// 运费说明

    private String storage;// 贮存方法

    private String description;// 商品详情

    private String shareContent;// 商品分享的内容

    private String buyExplain;// 购买说明

    private String stockJson;// 接收js传递过来的json

    List<ProductStockEntity> list;// 商品对应sku信息

    public int getIslimitCoupon() {
        return islimitCoupon;
    }

    public void setIslimitCoupon(int islimitCoupon) {
        this.islimitCoupon = islimitCoupon;
    }

    public String getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
    }

    public String getStockJson() {
        return stockJson;
    }

    public void setStockJson(String stockJson) {
        this.stockJson = stockJson;
    }

    public List<ProductStockEntity> getList() {
        return list;
    }

    public void setList(List<ProductStockEntity> list) {
        this.list = list;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getIsPresell() {
        return isPresell;
    }

    public void setIsPresell(int isPresell) {
        this.isPresell = isPresell;
    }

    public String getBuyExplain() {
        return buyExplain;
    }

    public void setBuyExplain(String buyExplain) {
        this.buyExplain = buyExplain;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getDiscountEnabled() {
        return discountEnabled;
    }

    public void setDiscountEnabled(int discountEnabled) {
        this.discountEnabled = discountEnabled;
    }

    public String getDiscountStart() {
        return discountStart;
    }

    public void setDiscountStart(String discountStart) {
        this.discountStart = discountStart;
    }

    public String getDiscountEnd() {
        return discountEnd;
    }

    public void setDiscountEnd(String discountEnd) {
        this.discountEnd = discountEnd;
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNorms() {
        return norms;
    }

    public void setNorms(String norms) {
        this.norms = norms;
    }

    public String getDeploy() {
        return deploy;
    }

    public void setDeploy(String deploy) {
        this.deploy = deploy;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
