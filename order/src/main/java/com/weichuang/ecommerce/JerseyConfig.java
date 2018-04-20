package com.weichuang.ecommerce;

import com.weichuang.ecommerce.order.resource.RevenueResource;
import com.weichuang.ecommerce.withdraw.resource.AdminWithdrawResources;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Context;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.barcode.resource.BarcodeResource;
import com.weichuang.ecommerce.barcode.resource.SalePullNewSetResource;
import com.weichuang.ecommerce.barcode.resource.SalePullNewcResource;
import com.weichuang.ecommerce.barcode.resource.SalesShareEnvelopResource;
import com.weichuang.ecommerce.coupon.resource.CouponResources;
import com.weichuang.ecommerce.coupon.resource.CouponTypeResources;
import com.weichuang.ecommerce.coupon.resource.ReferRecommeResources;
import com.weichuang.ecommerce.order.resource.AdminOrderResource;
import com.weichuang.ecommerce.order.resource.OrderResource;
import com.weichuang.ecommerce.pay.resource.PayResources;
import com.weichuang.ecommerce.product.resource.ProductClassifyResources;
import com.weichuang.ecommerce.product.resource.ProductResources;
import com.weichuang.ecommerce.product.resource.ProductStockResources;
import com.weichuang.ecommerce.weixin.resource.MessageResource;
import com.weichuang.ecommerce.withdraw.resource.AgentIncomeResource;
import com.weichuang.ecommerce.withdraw.resource.IncomeSetResource;
import com.weichuang.ecommerce.withdraw.resource.WithdrawResources;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        // Register endpoints, providers, ...
        this.registerEndpoints();
    }

    @PostConstruct
    public void init() {
        // Register components where DI is needed
        this.configureSwagger();
    }

    /**
     *spring-boot升级到1.4+ 以后，spring-boot专门用来打包的maven plugin对目录结构进行了改变，如果使用了jersey来代替springmvc作为restful的方案，
     *然后并且自己在生成的JerseyConfig类里面，通过 package()函数来指定要扫描的包的路径的话，会导致找不到对应的路径，
     *从而导致了出现java.io.FileNotFoundException 的异常,所以采用注册api类的方式把接口注入,否则会出现404找不到的异常
     * 
     */
    private void registerEndpoints() {
        // springBoot1.4以下版本使用注册方式
        // packages("com.weichuang.ecommerce");
        // springBoot1.4以上版本使用注册方式--开始
        this.register(OrderResource.class);
        this.register(AdminOrderResource.class);
        // 商品
        this.register(ProductResources.class);
        this.register(ProductStockResources.class);
        this.register(ProductClassifyResources.class);

        this.register(AdminWithdrawResources.class);

        this.register(WithdrawResources.class);
        this.register(AgentIncomeResource.class);
        // 微信支付测试,正式环境要删掉
        // this.register(TestWXPayResources.class);
        this.register(PayResources.class);
        this.register(BarcodeResource.class);
        this.register(SalePullNewcResource.class);
        this.register(SalePullNewSetResource.class);
        this.register(IncomeSetResource.class);
        this.register(SalesShareEnvelopResource.class);

        // 优惠券
        this.register(CouponResources.class);
        this.register(CouponTypeResources.class);
        this.register(ReferRecommeResources.class);
        // api注册结束
        this.register(Context.class);
        this.register(GsonProvider.class);
        this.register(RequestContextFilter.class);
        this.register(ExceptionMapperSupport.class);
        // Access through /<Jersey's servlet path>/application.wadl
        this.register(WadlResource.class);
        this.register(MessageResource.class);
        //收入
        this.register(RevenueResource.class);
    }

    private void configureSwagger() {
        // Available at localhost:port/swagger.json
        this.register(ApiListingResource.class);
        this.register(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setConfigId("springboot-jersey-swagger");
        config.setTitle("Spring Boot + Jersey + Swagger");
        config.setVersion("v1");
        // config.setContact("Orlando L Otero");
        config.setSchemes(new String[] { "http" });
        config.setBasePath("/api");
        config.setResourcePackage("com.weichuang.ecommerce");
        config.setPrettyPrint(true);
        config.setScan(true);
    }
}
