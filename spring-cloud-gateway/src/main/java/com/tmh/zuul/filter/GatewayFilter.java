package com.tmh.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tmh.zuul.builder.GatewayDirector;
import com.tmh.zuul.responsibilityChain.FactoryHandler;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GatewayFilter extends ZuulFilter {

  @Autowired
  private GatewayDirector gatewayDirector;

  /**
   * 请求之前拦截处理业务逻辑 建议将限制黑名单存放到redis或者携程的阿波罗
   */
  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    // 1.获取请求对象
    HttpServletRequest request = ctx.getRequest();
    // 2.获取客户端真实ip地址
    String ipAddress = getIpAddr(request);
    HttpServletResponse response = ctx.getResponse();
//    gatewayDirector.direct(ctx, ipAddress, response, request);
    FactoryHandler.getHandler().handle(ctx, ipAddress, request, response);
    // 3. 过滤请求参数、防止XSS攻击
    Map<String, List<String>> filterParameters = filterParameters(request, ctx);
    ctx.setRequestQueryParams(filterParameters);
    return null;
  }

  /**
   * 过滤参数
   */
  private Map<String, List<String>> filterParameters(HttpServletRequest request, RequestContext ctx) {
    Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
    if (requestQueryParams == null) {
      requestQueryParams = new HashMap<>();
    }
    Enumeration em = request.getParameterNames();
    while (em.hasMoreElements()) {
      String name = (String) em.nextElement();
      String value = request.getParameter(name);
      ArrayList<String> arrayList = new ArrayList<>();
      // 将参数转化为html参数 防止xss攻击 < 转为&lt;
      arrayList.add(StringEscapeUtils.escapeHtml(value));
      requestQueryParams.put(name, arrayList);
    }
    return requestQueryParams;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  /**
   * 在请求之前实现拦截
   */
  public String filterType() {
    return "pre";
  }

  /**
   * 获取Ip地址
   */
  public String getIpAddr(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
