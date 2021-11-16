import request from '@/utils/request.js'

export const getUserData = (params) => {
    return request({ url: '/party/appointment/profile', method: 'GET', params })
}

export const getListData = () => {
    return request({ url: '/party/appointment/my', method: 'GET' })
}

export const getHospitalData = () => {
    return request({ url: '/party/appointment/hospital/list', method: 'GET' })
}

export const saveData = params => {
    return request({ headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, url: '/party/appointment/save', method: 'POST', params })
}

export const cancelData = params => {
    return request({ headers: { 'Content-Type': 'application/x-www-form-urlencoded' }, url: '/party/appointment/cancel', method: 'POST', params })
}
