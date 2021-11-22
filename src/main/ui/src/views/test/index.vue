<template>
    <div style="width: 100%;height: 100%;position: relative">
        <div style="position: absolute;top: 180px;left: 40px;width: 2px;height: 2px;border-radius: 1px;background: #000;" />
        <div style="position: absolute;top: 480px;right: 70px;width: 2px;height: 2px;border-radius: 1px;background: red;" />
        <div id="line" />
    </div>
</template>

<script>
    export default {
        name: 'Index',
        mounted() {
            var c = document.getElementById('line');
            var ctx = c.getContext('2d');
            ctx.beginPath();
            ctx.moveTo(0, 0);
            ctx.lineTo(300, 150);
            ctx.stroke();
        }
    };
</script>

<style scoped>
#line {
    height: 1px;
    background: #000;
    position: absolute;
    top: 180px;
    left: 40px;
    width: 80px;
    background: red;
    transform: rotate(30deg);
}
</style>
