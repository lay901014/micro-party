<template>
    <div class="mobile-page">
        <img src="../../assets/home/logo.png" class="logo">
        <div style="min-height: 100%;padding-bottom: 80px;font-size: 0;background: #B00111;">
            <img src="../../assets/course/map.png" class="map">
            <!--<div style="position: absolute;top: 10%;right: 10%;">虹口区-姚志鹏-中国证券博物-从证券事业的兴衰看马克思主义的发展性</div>-->
            <div style="position: absolute;top: 42.9%;right: 55.7%;" class="point"/>

            <!--<div>静安区-李新亮-中共二大会址-党章诞生地话党章中的经济思想</div>-->
            <div style="position: absolute;top: 44.5%;right: 61.4%;" class="point"/>

            <!--<div>黄浦区-沈全-上海市工人文化宫-匠心筑梦</div>-->
            <div style="position: absolute;top: 46%;right: 57.3%;" class="point"/>

            <!--<div>黄浦区-王成峰-上海市总工会-在劳动模范风采中感悟人民群众的创造伟力</div>-->
            <div style="position: absolute;top: 45.5%;right: 56%;" class="point"/>

            <!--<div>静安区-施阳九-中共上海地下组织斗争史陈列馆-隐秘战线的无声较量</div>-->
            <div style="position: absolute;top: 44.5%;right: 62.6%;" class="point"/>

            <!--<div>虹口区-付清海-中共“四大”纪念馆-中共四大与早期合作社运动</div>-->
            <div style="position: absolute;top: 41.5%;right: 57%;" class="point"/>

            <!--<div>黄浦区-张小红-银行博物馆-金融初心，人民本位</div>-->

            <!--<div>青浦区-周敏晖-陈云纪念馆博物馆-统一财经，打赢经济战</div>-->
        </div>

        <div style="text-align: center;margin-top: -80px;position: relative;z-index: 100;">
            <div style="margin-bottom: 10px;color: #FFF;font-size: 15px;font-weight: 700;">
                点击观看视频，开启您的寻访之旅
            </div>
            <div style="margin-bottom: 10px;position: relative;font-size: 0;">
                <span style="position: absolute;top: 50%;left: 50%;transform: translate(-50%, -50%);color: #B00111;font-size: 18px;font-weight: 700;">1 / 13</span>
                <img src="../../assets/course/pagination.png" class="pagination">
            </div>
        </div>

        <!--<div class="cell">-->
            <!--<div>123456</div>-->
        <!--</div>-->
    </div>
</template>

<script>
    export default {
        name: 'Index',
    };
</script>

<style scoped lang="scss">
    .mobile-page {
        font-size: 0;
        background: #B00111;
        overflow-x: hidden;
    }

    .logo {
        position: absolute;
        top: 20px;
        left: 20px;
        z-index: 100;
    }

    .map {
        width: 538px;
        height: 583px;
        position: relative;
        top: 30px;
        left: calc(-15% + 8px);
    }

    .pagination {
        width: 223px;
        height: 40px;
    }

    .cell {
        height: 40px;
        width: 80px;
        padding: 0 6px;
        line-height: 36px;
        border: 2px solid;
        border-top-color: #E29723;
        border-bottom-color: #E29723;
        border-left-color: #FFF;
        border-right-color: #FFF;
        border-radius: 20px;

    }

    .point {
        width: 4px;
        height: 4px;
        background: #FFF;
        border-radius: 50%;
    }
</style>
