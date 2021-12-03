<template>
    <div class="mobile-page">
        <div v-if="!activeCourse.isView && second < 30" class="alert fade-away">
            <img src="../../assets/detail/warning.png">
            <span>课程播放1分钟，记录为完成寻访展馆</span>
        </div>
        <div style="padding: 16px 12px;">
            <div style="font-size: 18px;font-weight: 700;"><span>【红色商博之旅】{{ activeCourse.courseName }}</span></div>
            <div style="margin-bottom: 16px;padding: 10px 0 6px;border-bottom: 1px solid #ECECEC;">
                <div class="course-desc-item">
                    <img src="../../assets/detail/location.png">
                    <span>地点：{{ activeCourse.address }}</span>
                </div>
                <div class="course-desc-item">
                    <img src="../../assets/detail/lecturer.png">
                    <span>授课：<span style="margin-right: 8px;">{{ activeCourse.college }}</span><span>{{ activeCourse.teacher }}</span></span>
                </div>
            </div>
            <video
                ref="video"
                preload
                autoplay
                controls
                width="100%"
            >
                <source :src="activeCourse.videoUrl" type="video/mp4">
                <div>您的浏览器不支持Video标签</div>
            </video>
            <div class="introduction">{{ activeCourse.pageContent}}</div>
            <div v-if="activeCourse.imageUrls" class="img-c">
                <img
                    v-for="(item, i) of activeCourse.imageUrls.split(';')"
                    :key="i"
                    :src="item"
                >
            </div>
        </div>
    </div>
</template>

<script>
    import { mapGetters } from 'vuex';
    import { setCourseState } from './api';
    export default {
        data() {
            return {
                time: 0,
                playTimer: null,
                second: 0,
                secondTimer: null
            };
        },
        mounted() {
            if (!this.activeCourse.isView) {
                this.$refs.video.addEventListener('play', this.playEvent);
                this.$refs.video.addEventListener('playing', this.playEvent);
                this.$refs.video.addEventListener('waiting', this.clearPlayInterval);
                this.$refs.video.addEventListener('pause', this.clearPlayInterval);
                this.stopwatch();
            }
        },
        beforeDestroy() {
            this.clearPlayInterval();
            this.clearStopwatch();
        },
        methods: {
            clearStopwatch() {
                clearInterval(this.secondTimer);
                this.secondTimer = null;
            },
            stopwatch() {
                this.secondTimer = setInterval(() => {
                    this.second += 1;
                    if (this.second >= 30) {
                        this.clearStopwatch();
                    }
                }, 1000)
            },
            playEvent(e) {
                if (this.playTimer) {
                    this.clearPlayInterval();
                }
                this.playTimer = setInterval(() => {
                    this.time += 0.01;
                    if (this.time >= 59.5) {
                        // 发送请求
                        const { id } = this.$route.params;
                        if (id) {
                            setCourseState(id).then(res => {
                                const { errno } = res;
                                if (errno === 0) {
                                    this.$store.dispatch('course/getCourses');
                                }
                            })
                        }
                        this.$refs.video.removeEventListener('play', this.playEvent);
                        this.$refs.video.removeEventListener('playing', this.playEvent);
                        this.$refs.video.removeEventListener('waiting', this.clearPlayInterval);
                        this.$refs.video.removeEventListener('pause', this.clearPlayInterval);
                        this.clearPlayInterval();
                    }
                }, 10);
            },
            clearPlayInterval() {
                clearInterval(this.playTimer);
                this.playTimer = null;
            }
        },
        computed: {
            ...mapGetters(['courseList']),
            activeCourse() {
                let result = {};
                for (const item of this.courseList) {
                    if (item.id === this.$route.params.id) {
                        result = item;
                        break;
                    }
                }
                return result;
            }
        }
    };
</script>

<style scoped lang="scss">
    .alert {
        width: 100%;
        line-height: 36px;
        margin: 0 auto;
        padding: 0 6px 0 16px;
        font-size: 14px;
        color: #FFF;
        background: #F55F00;

        &>div {
            background: url('../../assets/detail/emblem.png') no-repeat right center;
            background-size: 34px;
        }

        img {
            width: 20px;
            height: 20px;
            position: relative;
            top: 4px;
        }

    }

    .course-desc-item {
        line-height: 28px;

        img {
            width: 15px;
            margin-right: 4px;
            position: relative;
            top: 2px;
        }

    }

    .introduction {
        line-height: 24px;
        margin-top: 10px;
        text-indent: 34px;
        text-align: justify;
    }

    .img-c {
        text-align: center;

        img {
            max-width: 100%;
            margin-top: 10px;
        }

    }

    .fade-away {
        overflow-y: hidden;
        animation: fade_away 30s;
        animation-fill-mode: forwards;
    }

    @keyframes fade_away {

        97% {
            height: 36px;
        }

        100% {
            height: 0px;
        }

    }

</style>
