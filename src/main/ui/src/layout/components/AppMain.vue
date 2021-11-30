<template>
    <transition name="fade-transform" mode="out-in">
        <router-view :key="key" />
    </transition>
</template>

<script>
    export default {
        name: 'AppMain',
        computed: {
            key() {
                return this.$route.path;
            }
        }
    };
</script>

<style scoped lang="scss">
    @import "~@/styles/variables.scss";

    .app-main {
        /*50 = navbar  */
        height: 100%;
        width: 100%;
        padding-top: 30px;
        position: relative;
        overflow: hidden;
    }
</style>
