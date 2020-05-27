## 使用apollo的配置
1. 在Edit Configurations的环境变量中加入以下内容
~~~
-Denv=dev -Dapollo.configService=http://127.0.0.1:8080
~~~
2. 在apollo服务中加入以下内容
~~~
server.port=80
spring.application.name=api-gateway-service
eureka.client.service-url.defaultZone=http://localhost:8100/eureka
zuul.routes.api-a.path=/member-service/**
zuul.routes.api-a.serviceId=member-service
zuul.routes.api-b.path=/verify-login-service/**
zuul.routes.api-b.serviceId=verify-login-service
gateway.zuul.swaggerDocument=[
    {
        "name": "member-service",
        "location": "/member-service/v2/api-docs",
        "version": "2.0"
    },
    {
        "name": "verify-login-service",
        "location": "/verify-login-service/v2/api-docs",
        "version": "2.0"
    }
]
~~~
