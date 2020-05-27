package com.tmh.verify.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录验证服务接口")
public class VerifyLoginService {

  @ApiOperation(value = "根据手机号码验证是否登录成功")
  @GetMapping("/verify")
  @ApiImplicitParams({
      @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "用户手机号码"),
      @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码")})
  public String verify(@RequestParam("phone") String phone, @RequestParam("password") String password) {
    if (phone.equals("123456") && password.equals("123456")) {
      return "succeed";
    }
    throw new RuntimeException("登录失败");
  }
}
