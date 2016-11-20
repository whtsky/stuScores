# 安装
先改一下 Maven 的源地址…… 不然国内访问慢死了
```bash
> cat ~/.m2/settings.xml
<settings>
  <mirrors>
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
</settings>
```

# 编译 && 运行
```bash
mvn clean package
mvn exec:java
```

# 参考资料

+ Maven: [Maven in 5 minutes](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
+ Jersey: [Jersey User Guide](https://jersey.java.net/documentation/latest/index.html)