package com.alex.weblog.web.Controller;

import com.alex.weblog.common.aspect.ApiOperationLog;
import com.alex.weblog.web.model.User;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    @PostMapping("/user")
    @ApiOperationLog(description = "测试接口")
    public User user(@RequestBody User user){

        log.info("你好");
        // 反参
        return user;
    }
}
