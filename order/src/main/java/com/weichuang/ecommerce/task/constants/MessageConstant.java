package com.weichuang.ecommerce.task.constants;

public class MessageConstant {

    //a.下单未支付的订单，5分钟后进行提醒；订单关闭前1天的早上9:00再进行提醒
    public static final String PAY_REMIND = "亲爱的会员，您购买的“${productName}”订单已生成，还没有付款，为了保证您能准时收到，请尽快付款哦~。若您有任何疑问都可拨打我们的客服电话：400-900-4050，再次感谢您的购买，心意浓散养蛋每天吃一枚噢！";

    //c 付款成功，多次发货场景
    public static final String MULTIPLE_SHIPPING = "亲爱的会员，您已成功购买“${productName}”，我们会按照您选择的日期为您准时配送，请及时关注物流信息。若您有任何疑问都可拨打我们的客服电话：400-900-4050，再次感谢您的购买，心意浓散养蛋每天吃一枚噢！";

    //d.付款成功，单次发货场景：
    public static final String SINGLE_SHIPPING = "亲爱的会员，您已成功购买“${productName}”，我们会火速为您安排配送，请及时关注物流信息。若您有任何疑问都可拨打我们的客服电话：400-900-4050，再次感谢您的购买，心意浓散养蛋每天吃一枚噢！";

    //e.后台发货，多次发货场景：
    public static final String ADMIN_MULTIPLE = "亲爱的会员，您购买的“${productName}”第${times}次配送已经为您发货，请及时关注物流信息。若您有任何疑问都可拨打我们的客服电话：400-900-4050，感谢您对我们的认可，心意浓散养蛋每天吃一枚噢！";

    //f.后台发货，单次发货场景：
    public static final String ADMIN_SINGLE = "亲爱的会员，您购买的“${productName}”已经为您发货，请及时关注物流信息。若您有任何疑问都可拨打我们的客服电话：400-900-4050，感谢您对我们的认可，心意浓散养蛋每天吃一枚噢！";


}
