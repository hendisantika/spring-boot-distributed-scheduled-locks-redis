package id.my.hendisantika.distributedscheduledlocksredis.controller;

import id.my.hendisantika.distributedscheduledlocksredis.service.LockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-distributed-scheduled-locks-redis
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 07/12/24
 * Time: 06.31
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class LockController {
    private final LockService lockService;

    @GetMapping("/perform/{lockKey}")
    public String performOperation(@PathVariable String lockKey) throws InterruptedException {
        lockService.performWithLock(lockKey);
        log.info("Background Operation completed");
        return "Background Operation completed";
    }
}
