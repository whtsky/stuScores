# stuScores

基于 Jersey + Vue 2 的 C/S 学生成绩管理系统。
采用前后端分离设计， 部署时需要运行 API Server 、 编译前端代码并配置 Nginx 。
部署过程稍微有些繁琐， 所以我们写好了 `docker-compose.yml` 文件。 最简单的部署方法是安装 [Docker](https://docs.docker.com/engine/installation/) 和 [Docker Compose](https://docs.docker.com/compose/install/) ，然后运行
```bash
docker-compose up
```

然后在浏览器里打开 [http://127.0.0.1:8080/api/](http://127.0.0.1:8080/api/) 。


## API Server

### 编译 && 运行

#### Docker way
WIP

#### 手动
需要 Java 8 + Maven 3
```bash
brew cask install java
brew install maven
```

然后：
```bash
mvn clean package
mvn exec:java
```
之后在｀浏览器里打开 [http://127.0.0.1:8788/api/](http://127.0.0.1:8788/api/)

## 前端
```bash
# 安装 Nodejs 与 yarn
brew install yarn
# 安装依赖
cd frontend
yarn

# 运行开发服务器
yarn dev
# build
yarn build
```

# 参考资料

+ Maven: [Maven in 5 minutes](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html)
+ Jersey: [Jersey User Guide](https://jersey.java.net/documentation/latest/index.html)