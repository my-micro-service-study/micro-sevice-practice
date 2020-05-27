package com.tmh.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("verify-login-service")
public interface VerifyLoginServiceFeign {

  @GetMapping("/verify")
  String verify(@RequestParam("phone") String phone, @RequestParam("password") String password);
}
