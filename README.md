# stuScores

基于 Jersey + Vue 2 的 C/S 学生成绩管理系统

## API Server

### 编译 && 运行

#### Docker way
WIP

#### 手动
需要 Java 8 + Maven 3
```bash
brew cask install java
brew install maven
````

然后：
```bash
mvn clean package
mvn exec:java
```
之后再浏览器里打开 [http://127.0.0.1:8788/api/](http://127.0.0.1:8788/api/)

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