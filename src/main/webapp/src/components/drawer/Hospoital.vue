<template>
    <el-drawer :visible.sync="visible" :direction="direction" v-bind="$attrs" :with-header="false" :size="172">
        <Options v-bind="$attrs" v-on="$listeners" @input="inputEvent" />
        <Cancel @click="visible = false" />
    </el-drawer>
</template>

<script>
    import { useDrawer } from './useDrawer'
    import Options from '@/components/option/Options.vue'
    import Cancel from '@/components/button/Cancel.vue'

    export default {
        name: 'Hospital',
        components: { Options, Cancel },
        setup(props, context) {
            const drawer = useDrawer(props, context)
            return { ...drawer }
        }
    }
</script>
