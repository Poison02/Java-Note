# 模拟Servlet本质

- 充当SUN公司的角色，制定Servlet规范
  - `javax.servlet.Servlet`接口
- 充当Tomcat服务器开发团队
- 充当Webapp的开发者
  - BankServlet implements Servlet
  - UserLoginServlet implements Servlet
  - UserLoginServlet implements Servlet
- 通过我们的分析：
  - 对于我们javaweb程序员，我们之需要做两件事：
    - 白那些一个类实现Servlet接口
    - 将编写的类配置到配置文件中，在配置文件中：指定请求路径和类名的关系
- 注意：
  - 这个配置文件的文件名不能乱来。固定的。
  - 这个配置文件的存放路径不能乱来。固定的。
  - 文件名、文件路径都是SUN公司指定的Servlet规范中的明细。
- 严格意思上来说Servlet其实并不是一个简单的一个接口：
  - Servlet规范中规定了：
    - 一个合格的webapp应该是一个怎样的目录结构
    - 一个合格的webapp应该有一个怎样的配置文件
    - 一个合格的webapp配置文件路径放在哪里
    - 一个合格的webapp中Java程序应该放在哪里
    - 这些都是Servlet规范中规定的
- Tomcat服务器要遵循Servlet规范。JavaWeb程序员也要遵循这个Servlet规范，这样Tomcat服务器才能和webapp解耦合。