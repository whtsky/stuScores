FROM node:7.2

RUN npm set progress=false && \
    npm install -g --progress=false yarn

WORKDIR /frontend

ADD package.json /frontend/package.json
ADD yarn.lock /frontend/yarn.lock
RUN ["yarn", "--no-progress"]

ADD src /stuScores/src
ADD build /stuScores/build
ADD config /stuScores/config
RUN ["yarn", "build"]

WORKDIR /frontend/dist
