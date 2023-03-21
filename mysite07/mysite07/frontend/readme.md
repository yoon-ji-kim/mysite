### 1. 의존성 설치
```bash
$ npm init -y
$ npm i -D webpack webpack-cli webpack-dev-server style-loader css-loader sass-loader node-sass babel-loader @babel/core @babel/cli @babel/preset-env @babel/preset-react case-sensitive-paths-webpack-plugin
$ npm i react react-dom prop-types styled-components react-modal react-addons-update react-router react-router-dom
```


### 2. 설정
1.  config/babel.config.json
2.  config/webpack.config.js



### 3. npm 스크립팅
```json
"scripts": {
    "build": "npm install && npx webpack --config config/webpack.config.js --progress --mode production",
    "start": "npx webpack serve --config config/webpack.config.js --progress --mode development"
}
```


### 4. 실행
1.  development
    ```bash
    $ npm start
    ```

2.  production
    ```bash
    $ npm run build
    ```