import request from '@/utils/request';

export function getUserInfo() {
    return request({
        url: '/inspect/me/info',
        method: 'get'
    });
}
