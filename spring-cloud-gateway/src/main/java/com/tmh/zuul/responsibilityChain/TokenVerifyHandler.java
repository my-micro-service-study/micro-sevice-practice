package com.tmh.zuul.responsibilityChain;

import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Component
@Slf4j
public class TokenVerifyHandler extends GatewayHandler {

  @Override
  public void handle(RequestContext ctx, String ipAddress, HttpServletRequest request, HttpServletResponse response) {
    String accessToken = request.getParameter("accessToken");
    if (isEmpty(accessToken) || !accessToken.equals("123456")) {
      resultError(ctx, "AccessToken cannot be empty");
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
