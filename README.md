# Java Server for HTML5 Routing

![example workflow](https://github.com/bishoybasily/server/actions/workflows/release.yml/badge.svg)

### Usage

```dockerfile
#############
### build ###
#############
FROM node:14.16.1 as builder
WORKDIR /workspace
COPY . /workspace
RUN npm install
ARG configuration=''
RUN npx ng build -c ${configuration} --output-path=dist

###############
### package ###
###############
FROM bishoybasily/server:latest
COPY --from=builder /workspace/dist/ /web/content/
```

Copy the above `Dockerfile` file into your application root directory and make sure that docker is installed and running
then run,

```shell
docker build -t my-angular-application:1.0 .
```

to build an image from your application with name `my-angular-application` and tag `1.0`. this sever by default uses &
exposes port `80`.

Now you can run the application by running,

```shell
docker run --rm -p 8080:8080 my-angular-application:1.0
```

finally, access it from your browser [http://127.0.0.1:8080/](http://127.0.0.1:8080/)

### Configuration

##### Environment variables

* `WEB_CONTENT_PATH` sets the directory for web application content, default value is `/web/content/`
* `WEB_CONTENT_FILES_EXTENSION` sets the file's extension value, default value is `.html`

