import defaultSettings from '@/settings';
import { SETTINGS } from '@/store/mutation-types';

const { showSettings } = defaultSettings;

const state = {
    showSettings: showSettings,
};

const mutations = {
    [SETTINGS.CHANGE_SETTING]: (state, { key, value }) => {
        if (state.hasOwnProperty(key)) {
            state[key] = value;
        }
    }
};

const actions = {
    changeSetting({ commit }, data) {
        commit(SETTINGS.CHANGE_SETTING, data);
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};

