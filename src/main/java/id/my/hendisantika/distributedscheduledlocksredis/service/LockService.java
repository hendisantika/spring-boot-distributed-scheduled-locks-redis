package id.my.hendisantika.distributedscheduledlocksredis.service;

import id.my.hendisantika.distributedscheduledlocksredis.config.RedisDistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-distributed-scheduled-locks-redis
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 07/12/24
 * Time: 06.28
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LockService {
    private final RedisDistributedLock lock;

    public void performWithLock(String lockKey) throws InterruptedException {
        if (lock.acquireLock(lockKey, 15000, TimeUnit.MILLISECONDS)) {
            log.info("Lock acquired. Background Operation started.");

            // Simulate an operation that takes some time
            Thread.sleep(200);

            log.info("Background Operation completed.");
            // Optionally, release the lock
            // lock.releaseLock(lockKey);
        } else {
            log.error("Failed to acquire lock. Resource is busy.");
        }
    }
}
