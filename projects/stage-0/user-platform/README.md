##第十三周作业
作业：
基于文件系统为 Spring Cloud 提供 PropertySourceLocator 实现
配置文件命名规则 (META-INF/config/default.properties 或者 META-INF/config/default.yaml)

实现：my-spring-cloud
本次作业参考小马哥《小马哥讲Spring核心编程思想》中（DynamicResourceMessageSource）关于监控文件变化的代码
及 org.springframework.cloud.config.client.ConfigServicePropertySourceLocator


##第十二周作业
作业：
将上次 MyBatis@Enable 模块驱动，封装成 SpringBoot Starter 方式
参考:MyBatis Spring Project 里面会有 Spring Boot 

实现 user-
在第十周作业基础上增加配置并通过 spring.factories（位置：resources/META-INF） 文件指明 Spring Boot自动装配类实现


##第十一周作业
通过 Java 实现两种 (以及) 更多的一致性 Hash 算法 (可选) 实现服务节点动态更新
org.apache.dubbo.rpc.cluster.loadbalance.ConsistentHas hLoadBalance

实现 my-hash

##第十周作业
完善
@org.geektimes.projects.user.mybatis.annotation.Enable MyBatis 实现，尽可能多地注入 org.mybatis.spring.SqlSessionFactoryBean 中依赖的组件


- 添加typeAliasesPackage、configurationProperties解析
- 实现：org.geektimes.projects.user.mybatis.annotation.MyBatisBeanDefinitionRegistrar
- 测试：org.geektimes.mybatis.EnableMyBatisTest


##第九周作业
Spring Cache 与 Redis 整合
- 如何清除某个 Spring Cache 所有的 Keys 关联的对象
    - 如果 Redis 中心化方案，Redis + Sentinel
    - 如果 Redis 去中心化方案，Redis Cluster
- 如何将 RedisCacheManager 与 @Cacheable 注解打通




##第八周作业
- 如何解决多个 WebSecurityConfigurerAdapter Bean 配置相互冲突的问题？  
    - 提示：假设有两个 WebSecurityConfigurerAdapter Bean 定义，并且标注了不同的 @Order，其中一个关闭 CSRF，一个开启 CSRF，那么最终结果如何确定？
    - 背景：Spring Boot 场景下，自动装配以及自定义 Starter 方式非常流行，部分开发人员掌握了 Spring Security 配置方法，并且自定义了自己的实现，解决了 Order 的问题，然而会出现不确定配置因素。
  
详见 user-web 模块：
org.geektimes.projects.user.web.spring.security

---
##第七周作业
-  使用 Spring Boot 或者 Servlet 来实现一个 Gitee 的 OAuth2 认证

详见 user-web 模块:
org.geektimes.oauth

---
##第六周作业
###my-cache 模块
-  提供一套抽象 API 实现对象的序列化和反序列化 通过 Lettuce 实现一套 Redis CacheManager 以及 Cache

详见 my-cache 模块:
org.geektimes.cache.redis.LettuceCache
org.geektimes.cache.redis.LettuceCacheManager


---
##第五周作业
- 修复本程序 org.geektimes.reactive.streams 包下
- 继续完善 my-rest-client POST 方法
- (可选) 读一下 Servlet 3.0 关于 Servlet 异步 
	- AsyncContext

---

##第四周作业
###完善 my dependency-injection 模块
- 脱离 web.xml 配置实现 ComponentContext 自动初始化
- 使用独立模块并且能够在 user-web 中运行成功

###完善 my-configuration 模块
- Config 对象如何能被 my-web-mvc 使用
- 可能在 ServletContext 获取如何通过ThreadLocal 获取

---

##第三周作业
###需求一（必须）
- 整合 https://jolokia.org/
	- 实现一个自定义 JMX MBean，通过 Jolokia 做 Servlet 代理

###需求二（选做）
- 继续完成 Microprofile config API 中的实现
	- 扩展 org.eclipse.microprofile.config.spi.ConfigSource 实现，包括 OS 环境变量，以及本地配置文件
	- 扩展 org.eclipse.microprofile.config.spi.Converter 实现，提供 String 类型到简单类型
- 	通过 org.eclipse.microprofile.config.Config 读取当前应用名称
	- 应用名称 property name = “application.name”

---

##第二周作业
###需求
- 通过课堂上的简易版依赖注入和依赖查找，实现用户注册功能
- 通过 UserService 实现用户注册注册用户需要校验
- Id：必须大于 0 的整数
- 密码：6-32 位 电话号码: 采用中国大陆方式（11 位校验）

---

##第一周作业
###需求
- 通过自研 Web MVC 框架实现（可以自己实现）一个用户注册，forward 到一个成功的页面（JSP 用法）/register
- 通过 Controller -> Service -> Repository 实现（数据库实现）
- （非必须）JNDI 的方式获取数据库源（DataSource），在获取 Connection

需通过==JAX-RS==注入


