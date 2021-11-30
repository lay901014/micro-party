import Vue from 'vue';
import 'normalize.css/normalize.css'; // A modern alternative to CSS resets
import '@/styles/index.scss'; // global css
import App from './App';
import store from './store';
import router from './router';
import './permission'; // permission control

Vue.config.productionTip = false;

if (!window.location.origin) {
    const { protocol, hostname, port } = window.location
    window.location.origin = `${protocol}//${hostname}${port ? `:${port}` : ''}`;
}

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
});

