<template>
    <div class="map-course-item" @click="handleStart">
        <div class="course-name">
            <img src="../../assets/course/play.png">
            <span>{{ name }}</span>
        </div>
        <div :class="['triangle', name.length > 10 ? 'lg10' : 'lt10']" :style="{ borderWidth: activeBorderWidth }">
            <div class="point">
                <div class="solid-point" />
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        props: {
            name: {
                type: String,
                required: true,
                default: ''
            },
            id: {
                type: String,
                required: true
            }
        },
        computed: {
            activeBorderWidth() {
                if (this.name.length < 11) {
                    return `8px ${this.name.length * 6}px 8px ${this.name.length * 6}px`;
                }
                return `8px 100px 8px 50px`;
            }
        },
        methods: {
            handleStart() {
                this.$router.push(`/ui/course/${this.id}`)
            }
        }
    };
</script>

<style scoped lang="scss">
    .map-course-item {
        position: absolute;
    }
    .course-name {
        font-size: 11px;
        color: #FFF;
        padding: 4px 8px;
        background-image: linear-gradient(to right, rgba(0, 0, 0, .55), rgba(0, 0, 0, .85));
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
        border-style: solid;
        border-color: rgba(176, 1, 17, .3) transparent transparent;
        position: relative;
    }
    .lt10 {
        /*border-width: 8px 50px 8px 50px;*/
        margin: 0 auto;
    }
    .lg10 {
        /*border-width: 8px 100px 8px 50px;*/
        left: 14px;
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
