<template>
    <div class="course-item-point" @click="handleStart">
        <div class="course-item-name">
            <div>
                <img v-if="finished" src="../../assets/course/finished.png">
                <img v-else src="../../assets/course/play.png">
                <span>{{ name }}</span>
            </div>
            <div class="address">{{ address }}</div>
        </div>
        <!--<div :class="['triangle', name.length > 10 ? 'lg10' : 'lt10']" :style="{ borderWidth: activeBorderWidth }">-->
        <div class="triangle">
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
            },
            finished: {
                type: Boolean,
                required: true
            },
            address: {
                type: String
            }
        },
        computed: {
            // activeBorderWidth() {
            //     if (this.name.length < 11) {
            //         return `8px ${this.name.length * 6}px 8px ${this.name.length * 6}px`;
            //     }
            //     return `8px 100px 8px 50px`;
            // }
        },
        methods: {
            handleStart() {
                this.$router.push(`/ui/course/${this.id}`)
            }
        }
    };
</script>

<style scoped lang="scss">
    .course-item-point {
        position: absolute;
        cursor: pointer;
        z-index: 1000;
    }
    .course-item-name {
        font-size: 14px;
        color: #FFF;
        padding: 8px 10px;
        background-image: linear-gradient(to right, rgb(49, 54, 61), rgb(5, 0, 0));
        border: 3px solid #E39821;
        border-radius: 20px;

        img {
            width: 16px;
            vertical-align: middle;
        }

        span {
            position: relative;
        }

        .address {
            padding: 6px 0 0 2px;
            font-size: 12px;
        }

    }
    .triangle {
        width: 0;
        height: 0;
        border-style: solid;
        border-color: rgba(0, 0, 0, .4) transparent transparent;
        position: relative;
        margin: 0 auto;
    }
    /*.lt10 {*/
        /*margin: 0 auto;*/
    /*}*/
    /*.lg10 {*/
        /*left: 14px;*/
    /*}*/
    .point {
        width: 8px;
        height: 8px;
        background: rgba(255, 255, 255, .7);
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
    .point1 {
        top: 185px;
        right: 110px;

        .triangle {
            border-width: 10px 50px 10px 120px;
        }
    }

    .point2 {
        top: 196px;
        right: 122px;

        .triangle {
            border-width: 10px 80px 10px 80px;
        }
    }

    .point3 {
        top: 208px;
        right: 168px;

        .triangle {
            border-width: 10px 30px 10px 30px;
        }
    }

    .point4 {
        top: 204px;
        right: 103px;

        .triangle {
            border-width: 10px 50px 10px 120px;
        }
    }

    .point5 {
        top: 199px;
        right: 152px;

        .triangle {
            border-width: 10px 60px 10px 60px;
        }
    }

    .point6 {
        top: 175px;
        right: 121px;

        .triangle {
            border-width: 10px 70px 10px 70px;
        }
    }

    .point7 {
        top: 204px;
        right: 141px;

        .triangle {
            border-width: 10px 60px 10px 60px;
        }
    }

    .point8 {
        top: 204px;
        right: 120px;

        .triangle {
            border-width: 10px 70px 10px 70px;
        }
    }

    .point9 {
        top: 302px;
        right: 267px;

        .triangle {
            position: relative;
            left: -10px;
            border-width: 13px 130px 10px 2px;
        }
    }

    .point10 {
        top: 208px;
        right: 120px;

        .triangle {
            border-width: 10px 50px 10px 120px;
        }
    }

    .point11 {
        top: 221px;
        right: 147px;

        .triangle {
            border-width: 10px 70px 10px 70px;
        }
    }

    .point12 {
        top: 193px;
        right: 179px;

        .triangle {
            border-width: 10px 60px 10px 60px;
        }
    }

    .point13 {
        top: 221px;
        right: 140px;

        .triangle {
            border-width: 10px 70px 10px 70px;
        }
    }
</style>
