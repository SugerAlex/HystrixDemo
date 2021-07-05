package demo;

import com.netflix.hystrix.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static demo.OrderService.queryByOrderId;

@RestController
@RequestMapping("/demo")
public class OrderCommand extends HystrixCommand<String>{

    public OrderCommand() {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("demo"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("orderCommand"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("orderThreadPool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(5)
                        .withMaxQueueSize(2))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()//
                        .withExecutionTimeoutInMilliseconds(1500))
        );
    }

    @GetMapping("/order")
    @Override
    protected String run() throws Exception {
        String result = queryByOrderId(1);
        return result;
    }

    @Override
    protected String getFallback() {
        if (this.circuitBreaker.isOpen()) {
            System.out.println("熔断器处于开启状态");
        } else {
            System.out.println("熔断器处于关闭状态");
        }
        return "====订单查询失败，我是降级方法====";
    }
}
