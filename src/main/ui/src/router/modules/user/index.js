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
            component: () => import(/* webpackChunkName: "user" */'@/views/home'),
            meta: {
                title: '红色商博微党课'
            }
        },
        {
            name: 'course',
            path: 'course',
            component: () => import(/* webpackChunkName: "user" */'@/views/course'),
            meta: {
                title: '红色商博微党课',
                courseList: true
            }
        },
        {
            name: 'course_detail',
            path: 'course/:id',
            component: () => import(/* webpackChunkName: "user" */'@/views/courseDetail'),
            meta: {
                title: '红色商博微党课',
                courseList: true
            }
        }
    ]
};

export default userRouter;
