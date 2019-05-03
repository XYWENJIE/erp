# erp
ERP系统项目，通过Java实现

可以直接使用Microsoft Visual Studio Code打开编辑，并支持直接使用jetty直接运行。

支持Eclipse，intellij编辑，建议使用Eclipse编辑Activiti流程图，可以通过IDE运行其他Servlet服务，如Tomcat等。

系统采用Wicket来当作MVC控制，Spring实现依赖注入，Hibernate实现对象关系映射（对象指Java类实例化的对象，关系值SQL这类的关系数据库）

Hibernate使用标准的JPA功能，JPA的功能也可以用别的实现比如EclipseLink等都可以，最终采用Spring Data JPA简化JPA代码操作。

非关系数据库也可以采用Spring Data 其他实现框架。