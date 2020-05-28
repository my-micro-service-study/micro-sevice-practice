package com.tmh.zuul.responsibilityChain;

import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class GatewayHandler {

  protected GatewayHandler nextHandler;

  public GatewayHandler getNextHandler() {
    return nextHandler;
  }

  public void setNextHandler(GatewayHandler nextHandler) {
    this.nextHandler = nextHandler;
  }

  public abstract void handle(RequestContext ctx, String ipAddress, HttpServletRequest request, HttpServletResponse response);

}
