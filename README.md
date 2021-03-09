## 基础模板项目

## 版本

    Spring boot 2.2.5
    Mybatis Plus 3.4.1
  
## 介绍

本项目基于Spring Boot项目构建,实现的功能有:
    
- 统一响应结果封装及生成工具
- 统一异常处理
- 使用Druid Spring Boot Starter 集成Druid数据库连接池与监控
- 使用FastJsonHttpMessageConverter，提高JSON序列化速度
- 统一处理返回值中的所有为null的参数替换为空字符串
- 项目启动成功后输出提示信息
- 全局applicationContext内容的获取
- 全局controller层出入参数打印
