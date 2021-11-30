import { APP } from '@/store/mutation-types';

const state = {
    sidebar: {
        opened: true,
        withoutAnimation: false
    },
    layoutLoaded: false,
    device: 'desktop',
    le1600: true // 窗口宽度小于等于1600px
};

const mutations = {
    [APP.SET_LAYOUT_LOADED]: state => {
        state.layoutLoaded = true;
    },
    [APP.TOGGLE_DEVICE]: (state, device) => {
        state.device = device;
    },
    [APP.LE1600]: (state, boolean) => {
        state.le1600 = boolean;
    }
};

const actions = {
    toggleDevice({ commit }, device) {
        commit(APP.TOGGLE_DEVICE, device);
    },
    le1600({ commit }, boolean) {
        commit(APP.LE1600, boolean);
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};
