FROM kkarczmarczyk/node-yarn:7.2

WORKDIR /frontend

ADD package.json /frontend/package.json
ADD yarn.lock /frontend/yarn.lock
RUN ["yarn", "--no-progress"]

ADD src /stuScores/src
ADD build /stuScores/build
ADD config /stuScores/config
RUN ["yarn", "build"]

WORKDIR /frontend/dist
