<template>
    <div style="padding-top: 36px;">
        <div class="alert">
            <div>
                <img src="../../assets/detail/warning.png">
                <span>课程播放1分钟，记录为完成寻访展馆</span>
            </div>
        </div>
        <div style="padding: 16px 12px;">
            <div style="font-size: 18px;font-weight: 700;"><span>【红色商博之旅】{{ info.title }}</span></div>
            <div style="margin-bottom: 16px;padding: 10px 0 6px;border-bottom: 1px solid #ECECEC;">
                <div class="course-desc-item">
                    <img src="../../assets/detail/location.png">
                    <span>地点：{{ info.location }}</span>
                </div>
                <div class="course-desc-item">
                    <img src="../../assets/detail/lecturer.png">
                    <span>授课：{{ info.lecturer }}</span>
                </div>
            </div>
            <video
                ref="video"
                preload
                autoplay
                controls
                width="100%"
            >
                <source src="http://10.119.2.216:8081/video1.mp4" type="video/mp4">
                <div>您的浏览器不支持Video标签</div>
            </video>
            <div class="introduction">陈云先后是以毛泽东同志为核心的党的第一代中央领导集体、以邓小平同志为核心的党的第二代中央领导集体的重要成员，是改革开放初期中央决策层中起关键作用的人物，是中国社会主义经济建设的开创者和奠基人之一。中华人民共和国建立之初，我国国内的经济形势非常严峻，党和政府面临多方面的压力和挑战。陈云作为一位伟大的革命家，充分展现了他在“两白一黑”战役和“银元之战”中的果敢和智慧，保卫了上海，打赢了这场经济战，从而巩固了新生的政权。</div>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Index',
        data() {
            return {
                hasChecked: false,
                time: 0,
                playTimer: null,
                info: {
                    title: '统一财经，打赢经济战',
                    location: '陈云纪念博物馆',
                    lecturer: '周敏慧'
                }
            };
        },
        mounted() {
            if (!this.hasChecked) {
                this.$refs.video.addEventListener('play', this.playEvent);
                this.$refs.video.addEventListener('playing', this.playEvent);
                this.$refs.video.addEventListener('waiting', this.clearPlayInterval);
                this.$refs.video.addEventListener('pause', this.clearPlayInterval);
            }
        },
        methods: {
            playEvent(e) {
                if (this.playTimer) {
                    this.clearPlayInterval();
                }
                this.playTimer = setInterval(() => {
                    this.time += 0.01;
                    if (this.time >= 59.5) {
                        // 发送请求
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
        }
    };
</script>

<style scoped lang="scss">
    .alert {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        line-height: 36px;
        padding: 0 6px 0 16px;
        font-size: 14px;
        color: #FFF;
        background: #F55F00;
        z-index: 50;

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

</style>
