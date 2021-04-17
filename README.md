# Java Server for HTML5 Routing

### Usage

Sample Dockerfile to containerize an angular application

```dockerfile
#############
### build ###
#############
FROM node:14.16.1 as builder
WORKDIR /workspace
COPY . /workspace
RUN npm install
ARG configuration=''
RUN npx ng build -c ${configuration} --output-path=target

###############
### package ###
###############
FROM bishoybasily/server:1.0
COPY --from=builder /workspace/target/ /web/content/
```

Now to build this image, copy the above file into your application root director and make sure that docker is installed
then run,

```shell
docker build -t my-angular-application:1.0 .
```

to build an image from your application with name `my-angular-application` and tag `1.0`, this sever by default uses &
exposes port `80`.

Now you can run the application by running,

```shell
docker run --rm -p 8080:80 my-angular-application:1.0
```

finally, access it from your browser [http://127.0.0.1:8080/](http://127.0.0.1:8080/)
