import {COURSE} from '@/store/mutation-types';
import { getCourseList } from '@/api/course';

const state = {
    list: []
};

const mutations = {
    [COURSE.SET_COURSE_LIST]: (state, list) => {
        state.list = list || [];
    }
};

const actions = {
    getCourses({ commit, state }) {
        return new Promise((resolve, reject) => {
            getCourseList().then(response => {
                const { errno, entities } = response;
                if (errno === 0 && entities) {
                    commit(COURSE.SET_COURSE_LIST, entities);
                }
                resolve(response);
            }).catch(error => {
                // const response = {
                //     'errno': 0,
                //     'error': 'success',
                //     'total': 0,
                //     'entities': [
                //         {
                //             'id': 'a18dc8a7-45ed-11ec-8f19-fa163e34a620',
                //             'courseName': '从证券事业的兴衰看马克思主义的发展性',
                //             'longitude': 123.4535340,
                //             'latitude': 211.3243240,
                //             'pageContent': '中国证券博物馆于2018年1月经中央编办批复成立，是我国证券行业唯一一家国家级博物馆。馆内藏品丰富，以改革开放以来证券期货业发展的当代藏品为主，见证了新中国证券市场的诞生、发育和成长。本片借助馆内藏品，透过证券业在中国沉沉浮浮的历史画卷，帮助大家一起来体会马克思主义理论的一个重要特征——马克思主义的发展性，以进一步坚定马克思主义的信仰。\n',
                //             'isView': false,
                //             'courseTag': 'course1',
                //             'address': '中国证券博物馆'
                //         },
                //         {
                //             'id': 'ad744b14-45ed-11ec-8f19-fa163e34a620',
                //             'courseName': '党章诞生地话党章中的经济思想',
                //             'longitude': 231.4353400,
                //             'latitude': 213.4354330,
                //             'pageContent': '目前，我国已成为世界第二大经济体，成为推动世界经济增长的主要动力源。党的经济纲领是党章核心内容的重要组成部分，体现了党的经济理念、理想与追求，中国共产党党章中的经济思想关系着中国现代化建设的成败。本课程就是在中国共产党第一个党章的诞生地——中共二大会址，跟大家一起分享从一大到十九大，历次党章中的主要的经济思想。进而，从党的非凡历程中，感悟到一代代共产党人的初心和使命。',
                //             'isView': true,
                //             'courseTag': 'course2',
                //             'address': '中共二大会址'
                //         }
                //     ]
                // };
                // const { errno, entities } = response;
                // if (errno === 0 && entities) {
                //     commit(COURSE.SET_COURSE_LIST, entities);
                // }
                reject(error);
            });
        });
    },
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};
