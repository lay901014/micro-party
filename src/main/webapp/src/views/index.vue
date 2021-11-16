<template>
    <div class="page">
        <Header :show-list="showList" class="header" :is-authority="isAuthority" @show="showList = true" />
        <List
            v-show="showList"
            :data="data"
            :user-info="userInfo"
            :is-authority="isAuthority"
            @edit="editEvent"
            @update="getList"
            @create="createEvent"
        />
        <Detail
            v-show="!showList"
            ref="detail"
            :user-info="userInfo"
            :list="data"
            :is-authority="isAuthority"
            @update="getList"
        />
    </div>
</template>

<script>
    import { onMounted, reactive, toRefs } from '@vue/composition-api'
    import { getUserData, getListData } from './api'
    import { Form } from './config'
    import { cloneDeep } from 'lodash'
    import Header from './Header.vue'
    import Detail from './Detail.vue'
    import List from './List.vue'

    export default {
        name: 'Medical',
        components: { Header, Detail, List },
        setup(props, context) {
            const state = reactive({
                userInfo: cloneDeep(Form),
                showList: false, // true: show list ,false: show detail
                loading: true,
                isAuthority: false,
                data: []
            })

            const getUser = () => {
                getUserData()
                    .then((res) => {
                        if (res.entities && res.entities.length > 0) {
                            state.userInfo = res.entities[0]
                            state.isAuthority = true
                        } else {
                            state.userInfo = cloneDeep(Form)
                        }
                        if (!state.isAuthority) return
                        state.showList = state.userInfo.isAppointmented
                        if (state.showList) {
                            getList()
                        } else {
                            state.loading = false
                        }
                        if (!state.userInfo.canEntrust) {
                            context.refs['detail'].initDetail(state.userInfo)
                        }
                    })
                    .catch((e) => {
                        state.loading = false
                    })
            }

            const getList = () => {
                state.showList = true
                getListData()
                    .then((res) => {
                        state.data = res.entities || []
                        state.loading = false
                    })
                    .catch((e) => {
                        state.loading = false
                    })
            }

            const createEvent = () => {
                const value = state.userInfo.canEntrust ? undefined : state.userInfo
                context.refs['detail'].initDetail(value)
                state.showList = false
            }

            const editEvent = (value) => {
                context.refs['detail'].initDetail(value)
                state.showList = false
            }

            onMounted(() => {
                getUser()
            })

            return { ...toRefs(state), getList, editEvent, createEvent }
        }
    }
</script>

<style lang="scss" scoped>
@import "@/styles/index.scss";
</style>
