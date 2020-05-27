package com.tmh.zuul.builder;

import com.netflix.zuul.context.RequestContext;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 * 链接Build
 */
@Component
public class GatewayDirector {

  @Resource(name = "verifyService")
  private GatewayBuild gatewayBuild;

  /**
   * 连接建造者
   */
  public void direct(RequestContext ctx, String ipAddress, HttpServletResponse response, HttpServletRequest request) {
    // 1.黑名单
    Boolean blackBlock = gatewayBuild.ipBlacklistBlock(ctx, ipAddress, response);
    if (!blackBlock) {
      return;
    }
    // 2.验证accessToken
    Boolean apiAuthority = gatewayBuild.apiAuthority(ctx, request);
    if (!apiAuthority) {
      return;
    }
  }
}
