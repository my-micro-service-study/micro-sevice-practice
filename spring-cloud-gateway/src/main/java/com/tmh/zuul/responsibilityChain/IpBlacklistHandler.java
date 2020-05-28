package com.tmh.zuul.responsibilityChain;

import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@Slf4j
public class IpBlacklistHandler extends GatewayHandler {

  @Override
  public void handle(RequestContext ctx, String ipAddress, HttpServletRequest request, HttpServletResponse response) {
    if (!ipAddress.equals("0:0:0:0:0:0:0:1")) {
      resultError(ctx, "ip:" + ipAddress + ",Insufficient access rights");
      return;
    }
    if (!isNull(getNextHandler())) {
      getNextHandler().handle(ctx, ipAddress, request, response);
    }
  }

  public void resultError(RequestContext ctx, String errorMsg) {
    ctx.setResponseStatusCode(401);
    // 网关响应为false 不会转发服务
    ctx.setSendZuulResponse(false);
    ctx.setResponseBody(errorMsg);
  }
}
