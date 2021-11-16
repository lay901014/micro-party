import axios from 'axios'
import { Message } from 'element-ui'

const service = axios.create({
    // baseURL: import.meta.env.VITE_BASE // url = base url + request url
})

service.interceptors.request.use(
    config => {
        return config
    },
    error => {
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        if (response.status === 204) {
            return response.data
        }

        const res = response.data

        if (res.errno === undefined) {
            return Promise.reject(new Error('数据格式错误'))
        }
        if (res.errno !== 0) {
            const error = res.error || 'Error'
            Message({
                message: error,
                type: 'error',
                duration: 5 * 1000
            })

            return Promise.reject(new Error(error))
        } else {
            return res
        }
    },
    error => {
        const res = error.response
        if (res && res.data) {
            const error = res.data.error || 'Error'
            Message({
                message: error,
                type: 'error',
                duration: 5 * 1000
            })
        }
        return Promise.reject(error)
    }
)

export default service
