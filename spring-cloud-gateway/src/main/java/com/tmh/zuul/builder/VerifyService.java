package com.tmh.zuul.builder;

import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VerifyService implements GatewayBuild {

  @Override
  public Boolean ipBlacklistBlock(RequestContext ctx, String ipAddress, HttpServletResponse response) {
    if (!ipAddress.equals("0:0:0:0:0:0:0:1")) {
      resultError(ctx, "ip:" + ipAddress + ",Insufficient access rights");
      return false;
    }
    return true;
  }

  @Override
  public Boolean apiAuthority(RequestContext ctx, HttpServletRequest request) {
    String accessToken = request.getParameter("accessToken");
    if (StringUtils.isEmpty(accessToken)) {
      resultError(ctx, "AccessToken cannot be empty");
      return false;
    }
    // 调用接口验证accessToken是否失效
    if (!accessToken.equals("123456")) {
      resultError(ctx, "failed");
      return false;
    }
    return true;
  }


  private void resultError(RequestContext ctx, String errorMsg) {
    ctx.setResponseStatusCode(401);
    // 网关响应为false 不会转发服务
    ctx.setSendZuulResponse(false);
    ctx.setResponseBody(errorMsg);
  }

}
