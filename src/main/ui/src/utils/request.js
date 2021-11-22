import axios from 'axios';
import { Message, MessageBox } from 'element-ui';
import store from '@/store';
import { startsWith } from 'lodash';

import { USER } from '@/store/mutation-types';

// create an axios instance
const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
    // withCredentials: true, // send cookies when cross-domain requests
    timeout: 20000 // request timeout
});

// request interceptor
service.interceptors.request.use(
    config => {
        // do something before request is sent
        // 增加ajax请求头，便于后端判断是否是ajax请求
        config.headers['X-Requested-With'] = 'XMLHttpRequest';

        if (store.getters.token) {
            config.headers['x-csrf-token'] = store.getters.token;
        }
        return config;
    },
    error => {
        // do something with request error
        return Promise.reject(error);
    }
);

// response interceptor
service.interceptors.response.use(
    response => {
        if (response.status === 204) {
            return response.data;
        }
        const res = response.data;
        // if (res.errno === undefined) {
        //     return Promise.reject(new Error('数据格式错误'));
        // }

        if (res.errno && res.errno > 0) {
            const error = res.error || 'Error';
            Message({
                message: error,
                type: 'error',
                duration: 5 * 1000
            });

            // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
            // if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
            //     // to re-login
            //     MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
            //         confirmButtonText: 'Re-Login',
            //         cancelButtonText: 'Cancel',
            //         type: 'warning'
            //     }).then(() => {
            //         store.dispatch('user/resetToken').then(() => {
            //             location.reload();
            //         });
            //     });
            // }
            return Promise.reject(new Error(error));
        } else {
            return res;
        }
    },
    error => {
        if (error.response && error.response.data && (error.response.status === 403 || error.response.status === 401)) {
            if (error.response.data.errno === 10004) {
                // store.commit(`app/${APP.OPEN_RELOGIN}`);
                return new Promise(resolve => {
                    MessageBox({
                        title: '本次登录已经超时',
                        message: '请重新登录，并在登录窗口关闭后继续先前的操作。',
                        confirmButtonText: '重新登录',
                        showClose: false
                    }).then(() => {
                        const RELOGIN_PATH = (process.env.NODE_ENV === 'production' ? window.location.origin : 'http://localhost:8085') + process.env.VUE_APP_CONTEXT_BASE_URL + '/relogin';
                        window.open(RELOGIN_PATH + '?' + (new Date().getTime()));
                        const focusEvent = () => {
                            // this.$store.commit(`app/${APP.CLOSE_RELOGIN}`);
                            window.removeEventListener('focus', focusEvent);
                            if (!store.getters.layoutLoaded) {
                                window.location.reload();
                            } else {
                                resolve();
                            }
                        };
                        window.addEventListener('focus', focusEvent);
                    });
                }).then(() => {
                    const config = error.config;
                    const retryConfig = { ...config };
                    if (startsWith(config.url, config.baseURL)) {
                        retryConfig.url = config.url.substr(config.baseURL.length);
                    }
                    return service(retryConfig);
                });
            }
            if (error.response.data.errno === 10005) {
                const config = error.config;
                const retryConfig = { ...config };
                if (startsWith(config.url, config.baseURL)) {
                    retryConfig.url = config.url.substr(config.baseURL.length);
                }
                // 设置返回的csrfToken
                store.commit(`user/${USER.SET_TOKEN}`, error.response.data.entities[0]);
                return service(retryConfig);
            }
            if (error.response.data.errno === 10006) {
                Message({
                    message: '您无权访问，请联系管理员',
                    type: 'error',
                    duration: 5 * 1000
                });
                return Promise.reject(error);
            }
        }
        const message = (error.response && error.response.data && error.response.data.error) ? error.response.data.error : error.message;
        if (message !== '取消请求') {
            Message({
                message,
                type: 'error',
                duration: 5 * 1000
            });
        }
        return Promise.reject(error);
    }
);

export default service;
