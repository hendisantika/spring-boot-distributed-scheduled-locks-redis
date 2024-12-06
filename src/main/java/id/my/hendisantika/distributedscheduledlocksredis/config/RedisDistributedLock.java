package id.my.hendisantika.distributedscheduledlocksredis.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-distributed-scheduled-locks-redis
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 07/12/24
 * Time: 06.29
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class RedisDistributedLock {
    private final RedisTemplate<String, Object> redisTemplate;

    public boolean acquireLock(String lockKey, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(lockKey, "locked", timeout, unit);
    }

    public void releaseLock(String lockKey) {
        redisTemplate.delete(lockKey);
    }
}
