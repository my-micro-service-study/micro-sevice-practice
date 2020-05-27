package com.tmh.member.service;

import com.tmh.member.dto.UserLoginInputDTO;
import com.tmh.member.feign.VerifyLoginServiceFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "会员服务接口")
public class MemberLoginService {

  @Autowired
  private VerifyLoginServiceFeign verifyLoginServiceFeign;

  @PostMapping("/login")
  @ApiOperation(value = "会员用户登陆信息接口")
  public String login(@RequestBody UserLoginInputDTO userLoginInputDTO) {
    return verifyLoginServiceFeign.verify(userLoginInputDTO.getMobile(), userLoginInputDTO.getPassword());
  }
}
