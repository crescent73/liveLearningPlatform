初步搭建了一个springboot集成的ssm后端框架，里面代码都是测试代码，到时候写的时候再自己改
环境配置：
JDK：1.8
Spring boot版本：2.4.4
mysql版本：8
maven

开发平台：
idea / eclipse

运行：
1. 直接运行 \src\main\java\com\java\MainApplication.java 中的MainApplication文件
2. 使用maven运行，在terminal中输入mvn spring-boot:run

测试：
1. 先把项目运行起来（导入项目，配置环境，修改mysql数据库的配置）
2. 在浏览器输入：
    localhost：8088 -> 显示index.html 内容
    localhost:8080/index.html -> 全路径访问index.html，显示index.html 内容
    localhost:8088/student/hello -> 显示hello world
    localhost:8088/student/searchStudent -> 返回从mysql数据库中查询到的学生数据，数据格式为json
    localhost:8088/student/findStudent -> 返回从mysql数据库中查询到的学生数据，数据格式为json