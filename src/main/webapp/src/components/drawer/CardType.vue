<template>
    <el-drawer :visible.sync="visible" :direction="direction" v-bind="$attrs" :with-header="false" :size="278">
        <Options :data="options" v-bind="$attrs" v-on="$listeners" @input="inputEvent" />
        <Cancel @click="visible = false" />
    </el-drawer>
</template>

<script>
    import { onMounted } from '@vue/composition-api'
    import { useDrawer } from './useDrawer'
    import Options from '@/components/option/Options.vue'
    import Cancel from '@/components/button/Cancel.vue'

    export default {
        name: 'CardType',
        components: { Options, Cancel },
        setup(props, context) {
            const drawer = useDrawer(props, context)

            const OPTIONS = [
                { label: '身份证', value: '身份证' },
                { label: '护照', value: '护照' },
                { label: '港澳台居民大陆居住证', value: '港澳台居民大陆居住证' },
                { label: '其他证件', value: '其他证件' }
            ]

            onMounted(() => {
                drawer.setOptions(OPTIONS)
            })

            return { ...drawer }
        }
    }
</script>
