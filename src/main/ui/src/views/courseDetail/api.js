import request from '@/utils/request';

export const setCourseState = id => {
    return request({
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        method: 'post',
        url: '/party/my/update/state',
        params: { courseId: id }
    })
}

