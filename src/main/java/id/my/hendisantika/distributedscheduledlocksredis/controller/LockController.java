package id.my.hendisantika.distributedscheduledlocksredis.controller;

import id.my.hendisantika.distributedscheduledlocksredis.service.LockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
@Service
@RestController
@RequiredArgsConstructor
public class LockController {
    private final LockService lockService;


}
