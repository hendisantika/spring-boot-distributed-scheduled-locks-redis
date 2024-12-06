package id.my.hendisantika.distributedscheduledlocksredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootDistributedScheduledLocksRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDistributedScheduledLocksRedisApplication.class, args);
    }

}
