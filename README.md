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

# 运行
```bash
mvn exec:java
```