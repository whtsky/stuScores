FROM kkarczmarczyk/node-yarn:7.2

WORKDIR /frontend

ADD package.json /frontend/package.json
ADD yarn.lock /frontend/yarn.lock
RUN ["yarn", "--no-progress"]

ADD src /frontend/src
ADD build /frontend/build
ADD config /frontend/config
RUN ["yarn", "build"]

WORKDIR /frontend/dist
