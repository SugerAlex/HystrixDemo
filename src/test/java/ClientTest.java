import demo.OrderCommand;
import demo.ProductCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootHystrixDemoApplication.class)
public class ClientTest {

    //降级
    public void a() {
        Object execute = new OrderCommand().execute();
        System.out.println(execute.toString());
    }

    //熔断+降级
    public void b() throws InterruptedException {
        for (int i = 0; i < 15; i++) {
            Object execute = new OrderCommand().execute();
            System.out.println(execute.toString());
        }

        System.out.println();
        Thread.sleep(6000);
        System.out.println("sleep了6s");
        System.out.println();

        for (int i = 0; i < 4; i++) {
            Object execute = new OrderCommand().execute();
            System.out.println(execute.toString());
        }
    }

    //限流+降级
    public void c() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Object pc = new ProductCommand().execute();
                System.out.println(pc.toString());
            }).start();
        }
        TimeUnit.MILLISECONDS.sleep(50000);
    }

    @Test
    //隔离
    public void d() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Object execute1 = new OrderCommand().execute();
                System.out.println(execute1.toString());
                Object execute2 = new ProductCommand().execute();
                System.out.println(execute2.toString());
            }).start();
        }
        TimeUnit.MILLISECONDS.sleep(50000);
    }
}