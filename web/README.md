# web

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
多环境配置步骤：
1、编写环境文件 .env.dev/.env.prod
2、在编译启动命令更改：
        "serve-dev": "vue-cli-service serve --mode dev",
        "build-dev": "vue-cli-service build --mode build" ,
        "serve-prod": "vue-cli-service serve --mode prod",
        "build-prod": "vue-cli-service build --mode prod",