<template>
    <div class="map-course-item" style="top: 300px;left: 121px;">
        <div class="course-name">
            <img src="../../assets/course/play.png">
            <span>上海科技金融博物馆</span>
        </div>
        <div class="triangle">
            <div class="point">
                <div class="solid-point"/>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            name: {
                type: String,
                required: true
            }
        }
    }
</script>

<style scoped lang="scss">
    .map-course-item {
        position: absolute;
    }
    .course-name {
        font-size: 11px;
        color: #FFF;
        padding: 4px 8px;
        background: rgba(0, 0, 0, .6);
        border: 3px solid #E39821;
        border-radius: 50px;
        text-align: center;

        img {
            width: 16px;
            vertical-align: middle;
        }

    }
    .triangle {
        width: 0;
        height: 0;
        border-width: 8px 100px 8px 50px;
        border-style: solid;
        border-color: rgba(176, 1, 17, .3) transparent transparent;
        position: relative;
    }
    .point {
        width: 8px;
        height: 8px;
        background: rgba(176, 1, 17, .4);
        border-radius: 50%;
        position: absolute;
        left: -4px;
        bottom: -4px;
    }
    .solid-point {
        width: 4px;
        height: 4px;
        position: relative;
        top: 2px;
        left: 2px;
        border-radius: 50%;
        background: rgb(176, 1, 17);
    }

</style>
