工程说明：
    父工程的pom定义公共的jar包资源
    下面有4个子工程
         ferrari-base定义基本的pojo、vo、util、还有对外接口
         ferrari-core定义接口的实现、业务逻辑代码、DAO
         ferrari-logistics定义物流相关服务
         ferrari-pay定义第三方支付相关服务
         ferrari-server定义配置文件，spring和mybatis
         ferrari-web定义相关struts2的action
配置说明：
    所有的配置项单独抽放一个外部配置文件ferrari.properties，放在系统用户下面
升级说明：
    发布车码头1.0.0，工程从零开始搭建，包括淘宝TOP接口接入、远程监控     






     
     