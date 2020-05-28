package com.tmh.zuul.responsibilityChain;


import com.tmh.zuul.utils.SpringContextUtil;

/**
 * 使用工厂获取Handler
 */
public class FactoryHandler {

  /**
   * 责任链启动方式：
   *
   * 1.将每一个Handler存放到集合中实现遍历
   */
  public static GatewayHandler getHandler() {
    // 1.黑名单判断
    GatewayHandler ipBlacklistHandler = (GatewayHandler) SpringContextUtil.getBean("ipBlacklistHandler");
    // 2.验证token
    GatewayHandler tokenVerifyHandler = (GatewayHandler) SpringContextUtil.getBean("tokenVerifyHandler");
    ipBlacklistHandler.setNextHandler(tokenVerifyHandler);

    return ipBlacklistHandler;

  }

}
