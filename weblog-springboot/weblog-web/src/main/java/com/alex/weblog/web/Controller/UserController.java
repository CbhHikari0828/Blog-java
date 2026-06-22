package com.alex.weblog.web.Controller;

import com.alex.weblog.common.aspect.ApiOperationLog;
import com.alex.weblog.common.enums.ResponseCodeEnum;
import com.alex.weblog.common.exception.BizException;
import com.alex.weblog.common.utils.JsonUtil;
import com.alex.weblog.common.utils.Response;
import com.alex.weblog.web.model.User;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@Slf4j
public class UserController {

    @PostMapping("/user")
    @ApiOperationLog(description = "测试接口")
    public User user(@RequestBody @Validated User user){

        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
    }

    @PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    @ApiOperation(value = "测试接口")
    public Response test(@RequestBody @Validated User user) {
        // 打印入参
        log.info(JsonUtil.toJsonString(user));

        // 设置三种日期字段值
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());

        return Response.success(user);
    }


}
