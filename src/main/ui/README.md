# 智慧科研
## 使用vue-admin-template搭建
- 模板地址 https://github.com/PanJiaChen/vue-admin-template.git


## Build Setup

```
# 克隆项目
git clone https://github.com/PanJiaChen/vue-admin-template.git

# 进入项目目录
cd vue-admin-template

# 安装依赖
npm install

## 快速安装
npm install --registry=https://registry.npm.taobao.org

# 启动服务
npm run dev
```

浏览器访问 [http://localhost:9528](http://localhost:9528)

## 发布

```
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```

## 其它

```
# 预览发布环境效果
npm run preview

# 预览发布环境效果 + 静态资源分析
npm run preview -- --report

# 代码格式检查
npm run lint

# 代码格式检查并自动修复
npm run lint -- --fix

# 复制打包出来的代码到webapp目录，这步一般不用做，npm run build:prod时候会自动做
npm run copy
```

