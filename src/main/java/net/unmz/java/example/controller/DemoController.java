package net.unmz.java.example.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Project Name:
 * 功能描述：
 *
 * @author Faritor
 * @version 1.0
 * @date 2021-3-9 19:16
 * @since JDK 1.8
 */
@Api(tags = "测试")
@RestController
@RequestMapping
public class DemoController {

    @ApiOperation(value = "测试接口", notes = "测试接口")
    @GetMapping("/demo")
    public Date demo() {
        return new Date();
    }
}
