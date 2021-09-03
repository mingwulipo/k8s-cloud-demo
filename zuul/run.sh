#!/bin/bash

#install报错，也会往下走，注意看install成功否
mvn clean install -Dmaven.test.skip=true

tag=`head -1 version`
echo "tag:$tag"
img="harbor.fastai.top/java/zuul:$tag"
echo "image:$img"

docker build -t $img ./
docker rm -v -f zuul && docker run -p 8080:8080 -v /etc/localtime:/etc/localtime:ro -v /etc/timezone:/etc/timezone:ro --name zuul -d $img

docker push $img
echo "推送镜像成功!"