/** When your routing table is too long, you can split it into small modules **/

import Layout from '@/layout';

const baseRoute = process.env.VUE_APP_CONTEXT_BASE_URL + '/ui';

const userRouter = {
    path: `${baseRoute}`,
    component: Layout,
    redirect: `${baseRoute}/home`,
    meta: {
        breadcrumb: false
    },
    children: [
        {
            name: 'home',
            path: 'home',
            component: () => import(/* webpackChunkName: "user" */'@/views/home')
        },
        {
            name: 'preface',
            path: 'preface',
            component: () => import(/* webpackChunkName: "user" */'@/views/preface')
        },
        {
            name: 'course',
            path: 'course',
            component: () => import(/* webpackChunkName: "user" */'@/views/course'),
            meta: {
                courseList: true
            }
        },
        {
            name: 'course_detail',
            path: 'course/:id',
            component: () => import(/* webpackChunkName: "user" */'@/views/courseDetail'),
            meta: {
                courseList: true
            }
        }
    ]
};

export default userRouter;
