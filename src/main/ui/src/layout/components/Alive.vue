<template>
    <iframe id="alive" :src="src"/>
</template>
<script>
    export default {
        name: 'Alive',
        data() {
            return {
                src: process.env.VUE_APP_CONTEXT_BASE_URL + '/alive'
            };
        }
    };
</script>
<style lang="scss" scoped>
    #alive {
        display: none;
        border: 0
    }
</style>
