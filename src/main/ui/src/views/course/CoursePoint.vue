<template>
    <div ref="point" class="course-item-point" @click="handleStart">
        <div class="course-item-name">
            <div style="display: flex;align-items: center;">
                <img v-if="finished" src="../../assets/course/finished.png">
                <img v-else src="../../assets/course/play.png">
                <div style="flex: 1;word-break: break-all;padding-left: 2px;">{{ name }}</div>
            </div>
            <div class="address" style="display: flex;align-items: center;">
                <img src="../../assets/detail/coordinate.png">
                <span style="flex: 1;word-break: break-all;padding-left: 2px;">{{ address }}</span>
            </div>
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
            courseId: {
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
        },
        mounted() {
            const scrollWidth = this.$refs.point.scrollWidth;
            const offsetWidth = document.getElementsByClassName('mobile-page')[0].offsetWidth;
            if (scrollWidth > offsetWidth - 8) {
                this.$refs.point.style.maxWidth = `${offsetWidth - 8}px`;
                const strRight = window.getComputedStyle(this.$refs.point).right;
                const numRight = Number(strRight.replace('px', ''));
                if (numRight > 0) {
                    this.$refs.point.style.right = `${numRight + Math.round((scrollWidth - offsetWidth + 8) / 2)}px`
                }
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
            padding-top: 8px;
            font-size: 12px;

            img {
                width: 16px;
                vertical-align: middle;
            }

            span {
                position: relative;
                top: 1px;
            }
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
        right: 132px;
        bottom: 319px;

        .triangle {
            border-width: 10px 60px 10px 60px;
        }
    }

    .point2 {
        right: 123px;
        bottom: 307px;

        .triangle {
            border-width: 10px 80px 10px 80px;
        }
    }

    .point3 {
        right: 178px;
        bottom: 296px;

        .triangle {
            border-width: 10px 32px 10px 32px;
        }
    }

    .point4 {
        right: 104px;
        bottom: 300px;

        .triangle {
            border-width: 10px 50px 10px 120px;
        }
    }

    .point5 {
        right: 142px;
        bottom: 307px;

        .triangle {
            border-width: 10px 60px 10px 60px;
        }
    }

    .point6 {
        right: 122px;
        bottom: 328px;

        .triangle {
            border-width: 10px 70px 10px 70px;
        }
    }

    .point7 {
        right: 142px;
        bottom: 300px;

        .triangle {
            border-width: 10px 60px 10px 60px;
        }
    }

    .point8 {
        right: 121px;
        bottom: 298px;

        .triangle {
            border-width: 10px 70px 10px 70px;
        }
    }

    .point9 {
        right: 270px;
        bottom: 196px;

        .triangle {
            position: relative;
            left: -10px;
            border-width: 13px 130px 10px 2px;
        }
    }

    .point10 {
        right: 115px;
        bottom: 300px;

        .triangle {
            border-width: 10px 50px 10px 120px;
        }
    }

    .point11 {
        right: 148px;
        bottom: 282px;

        .triangle {
            border-width: 10px 70px 10px 70px;
        }
    }

    .point12 {
        right: 187px;
        bottom: 310px;

        .triangle {
            border-width: 10px 60px 10px 60px;
        }
    }

    .point13 {
        right: 65px;
        bottom: 342px;

        .triangle {
            border-width: 10px 90px 10px 190px;
        }
    }

    .point14 {
        right: 85px;
        bottom: 163px;

        .triangle {
            border-width: 10px 100px 10px 150px;
        }
    }
</style>
