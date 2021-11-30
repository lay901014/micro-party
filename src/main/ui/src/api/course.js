import request from '@/utils/request';

export const getCourseList = () => {
    return request({
        method: 'get',
        url: '/party/my/course/list'
    })
}

