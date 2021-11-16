const path = require('path')

function resolve(dir) {
    return path.join(__dirname, dir)
}

module.exports = {
    outputDir: 'dist',
    assetsDir: 'static',
    productionSourceMap: false,
    filenameHashing: false,
    indexPath: 'index.html',
    devServer: {
        disableHostCheck: true,
        port: 9526,
        open: true,
        overlay: {
            warnings: false,
            errors: true
        },
        // 后端程序运行后使用该段代码，端口号根据实际情况自行修改
        proxy: {
            '/': {
                target: `http://localhost:8526/`,
                changeOrigin: true
            }
        }
    },
    chainWebpack: (config) => {
        config.resolve.alias
            .set('@', resolve('./src'))
    }
}
