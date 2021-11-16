<template>
    <el-drawer :visible.sync="visible" :direction="direction" v-bind="$attrs" :with-header="false" :size="172">
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
        name: 'MemberType',
        components: { Options, Cancel },
        setup(props, context) {
            const drawer = useDrawer(props, context)

            const OPTIONS = [
                { label: '本人', value: true },
                { label: '代他人', value: false }
            ]

            onMounted(() => {
                drawer.setOptions(OPTIONS)
            })

            return { ...drawer }
        }
    }
</script>
