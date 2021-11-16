import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import compositionAPI from '@vue/composition-api'
import 'element-ui/lib/theme-chalk/index.css'
import 'normalize.css/normalize.css'
import './styles/index.scss'

Vue.config.productionTip = false

Vue.use(ElementUI)
Vue.use(compositionAPI)

document.title = '一院体检预约'

new Vue({
    render: h => h(App)
}).$mount('#app')
