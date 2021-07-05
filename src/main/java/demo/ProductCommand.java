package demo;

import com.netflix.hystrix.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static demo.ProductService.queryByProductId;

@RequestMapping("/demo")
@RestController
public class ProductCommand extends HystrixCommand<String>{

    public ProductCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("demo"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("productCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("productThreadPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                    .withCoreSize(10)
                    .withMaxQueueSize(2))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()//
                    .withExecutionTimeoutInMilliseconds(1500))
        );
    }


    @GetMapping("/product")
    @Override
    protected String run() throws Exception {
        Thread.sleep(1000);
        String result = queryByProductId();
        return result;
    }

    @Override
    protected String getFallback() {
        if (this.circuitBreaker.isOpen()) {
            System.out.println("熔断器处于开启状态");
        } else {
            System.out.println("熔断器处于关闭状态");
        }
        return Thread.currentThread().getName()+ "====商品库存查询失败，我是降级方法====";
    }
}
