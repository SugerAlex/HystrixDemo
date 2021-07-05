import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;

@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
@SpringBootApplication
@ComponentScan("demo")
public class SpringbootHystrixDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootHystrixDemoApplication.class, args);
	}
}
