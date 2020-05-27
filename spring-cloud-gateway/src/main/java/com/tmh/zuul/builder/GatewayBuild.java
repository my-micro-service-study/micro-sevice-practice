package com.tmh.zuul.builder;

import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 网关权限Build
 */
public interface GatewayBuild {

  /**
   * 黑名单拦截
   */
  Boolean ipBlacklistBlock(RequestContext ctx, String ipAddress, HttpServletResponse response);

  /**
   * api权限控制
   */
  Boolean apiAuthority(RequestContext ctx, HttpServletRequest request);
}
