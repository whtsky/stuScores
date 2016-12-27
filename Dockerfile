FROM java:8

RUN apt-get update
RUN apt-get install -y maven

WORKDIR /stuScores

ADD pom.xml /stuScores/pom.xml  
RUN ["mvn", "dependency:resolve"]  
RUN ["mvn", "verify"]

ADD src /stuScores/src  
RUN ["mvn", "package"]

EXPOSE 8788
CMD ["mvn", "exec:java"]
