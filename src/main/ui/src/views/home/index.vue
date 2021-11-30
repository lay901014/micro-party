<template>
    <div ref="wrapper" class="mobile-page wrapper">
        <img src="../../assets/home/logo.png" class="logo">
        <img src="../../assets/home/emblem.png" class="emblem">
        <div class="m-center">
            <img ref="system_name1" src="../../assets/home/system_name1.png" class="system_name1">
            <img ref="system_name2" src="../../assets/home/system_name2.png" class="system_name2">
            <img src="../../assets/home/ink.png" class="ink">
        </div>
        <div class="m-bottom">
            <div class="c">
                <img src="../../assets/home/hb.png" class="hb">
                <img src="../../assets/home/great_wall.png" class="wall">
            </div>
            <img src="../../assets/home/btn.png" class="btn" @click="$router.push('/ui/course')">
            <img src="../../assets/home/event_name.png" class="event_name">
        </div>
    </div>
</template>

<script>
    export default {
        mounted() {
            const { offsetHeight } = document.body;
            const style = window.getComputedStyle(this.$refs.wrapper);
            const wrapperWidth = style.width ? parseInt(style.width) : null;
            if (wrapperWidth && offsetHeight) {
                if (wrapperWidth / offsetHeight > 0.6) {
                    const height = Math.ceil(wrapperWidth / 0.56);
                    this.$refs.wrapper.style.height = `${height > 1500 ? 1500 : height}px`;
                }
            }
            const systemNameHeight = Math.ceil(this.$refs.system_name1.width * 0.32);
            this.$refs.system_name2.style.top = `${systemNameHeight}px`;
        }
    };
</script>

<style scoped>
    .emblem {
        width: 30%;
        position: absolute;
        top: 10px;
        right: 10px;
        opacity: 0.4;
    }
    .m-center {
        width: calc(100% - 40px);
        position: absolute;
        top: 24%;
        left: 20px;
    }
    .system_name1 {
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 100;
    }
    .system_name2 {
        width: 50%;
        position: absolute;
        top: 120px;
        right: 0;
        margin-top: 8px;
        z-index: 100;
    }
    .ink {
        width: 99%;
        position: absolute;
        top: 40px;
        right: -110px;
        z-index: 50;
        opacity: 0.8;
    }
    .m-bottom {
        text-align: center;
    }
    .btn {
        width: 90%;
        position: relative;
        z-index: 200;
    }
    .event_name {
        width: calc(90% - 60px);
        position: relative;
    }
    .c {
        position: relative;
    }
    .hb {
        position: absolute;
        left: -20px;
        bottom: -40px;
        width: 45%;
        z-index: 100;
    }
    .wall {
        position: absolute;
        right: -17px;
        bottom: -20px;
        width: 67%;
        z-index: 80;
    }
</style>
