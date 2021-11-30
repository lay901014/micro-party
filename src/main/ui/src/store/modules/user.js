import { getUserInfo } from '@/api/auth';
import { removeToken } from '@/utils/auth';
import { resetRouter } from '@/router';
import { USER } from '@/store/mutation-types';

const state = {
    token: '',
    account: '',
    name: '',
    pageRights: []
};

const mutations = {
    [USER.SET_TOKEN]: (state, token) => {
        state.token = token;
    },
    [USER.SET_ACCOUNT]: (state, account) => {
        state.account = account;
    },
    [USER.SET_NAME]: (state, name) => {
        state.name = name;
    },
    [USER.SET_PAGE_RIGHTS]: (state, rights) => {
        state.pageRights = rights;
    }
};

const actions = {

    // get user info
    getInfo({ commit, state }) {
        return new Promise((resolve, reject) => {
            getUserInfo().then(response => {
                commit(USER.SET_TOKEN, '123455');
                commit(USER.SET_ACCOUNT, 'jhge');
                commit(USER.SET_NAME, '测试');
                resolve(response);
            }).catch(error => {
                reject(error);
            });
        });
    },
    // user logout
    logout({ commit, state }) {
        return new Promise((resolve, reject) => {
            commit(USER.SET_TOKEN, '');
            commit(USER.SET_PAGE_RIGHTS, []);
            commit(USER.SET_HANDLE_RIGHTS, []);
            removeToken();
            resetRouter();
            resolve();
        });
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};

