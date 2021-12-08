<template>
    <div class="mobile-page preface-c">
        <div class="content">
            <div class="title">前言</div>
            <div class="part">上海商学院深入学习贯彻党的十九届六中全会精神，在推进党史学习教育中发挥商科学研优势，践行和弘扬伟大建党精神，坚持用活上海作为党的“初心之地”的红色资源，聚焦商业博物馆的“红色富矿”，遴选荣誉教师和思政课教师代表带领学生开展实景互动体验式教学，进一步发挥课堂主渠道育人功能，形成寻访商业博物馆红色基因主题党史学习教育系列微课。
                学校党委坚持通过开展红色寻访，让党史学习教育更有感染力；讲授红色故事，让党史学习教育更有穿透力；传承红色基因，让党史学习教育更有影响力。</div>
            <div class="part">学校党委号召广大师生在伟大建党精神激励下，以史为鉴，开创未来，继续秉持“厚德博学 经世济民”校训精神，一如既往抢抓机遇、攻坚克难，爬坡奋进、开拓创新，为早日建成特色鲜明的高水平应用型商科大学而努力奋斗！</div>
        </div>
        <img src="../../assets/preface/footer.png" class="footer-img">
        <div class="arrow-c" @click="handleGo">
            <img src="../../assets/preface/preface_arrow.png" class="arrow">
        </div>
    </div>
</template>

<script>
    export default {
        methods: {
            handleGo() {
                this.$router.push({ path: '/ui/course' });
            }
        }
    };
</script>

<style scoped lang="scss">
    .preface-c {
        display: flex;
        flex-direction: column;
        background: url('../../assets/preface/preface_bg.jpg') no-repeat;
        background-size: 100% 100%;
    }

    .content {
        flex: 1;
        padding-top: 35.4%;
        color: #FFF;
    }

    .title {
        font-size: 24px;
        font-weight: 600;
        letter-spacing: 2px;
        text-align: center;
    }

    .part {
        line-height: 22px;
        padding: 16px 36px 0;
        font-size: 13px;
        text-align: justify;
        text-indent: 2em;
    }

    .footer-img {
        width: 100%;
    }

    .arrow-c {
        line-height: 80px;
        position: absolute;
        right: 8px;
        top: calc(50% - 40px);
        cursor: pointer;
        animation: handle 0.5s 1 ;
        animation-iteration-count: infinite;
        animation-direction: alternate;

        .arrow {
            width: 20px;
        }

    }

    @keyframes handle {
        0% {
            opacity: 1;
        }

        100% {
            right: 4px;
            opacity: 0.5;
        }
    }

</style>
