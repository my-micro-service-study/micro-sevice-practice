package com.tmh.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户登陆参数")
public class UserLoginInputDTO {

  @ApiModelProperty(value = "手机号码")
  private String mobile;

  @ApiModelProperty(value = "密码")
  private String password;
}
