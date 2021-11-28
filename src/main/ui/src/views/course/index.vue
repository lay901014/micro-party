<template>
    <div class="mobile-page">
        <img src="../../assets/home/logo.png" class="logo">
        <img src="../../assets/home/emblem.png" class="emblem">
        <div ref="mapWrapper" class="map-wrapper">
            <!--<div ref="map" class="map-c">-->
            <!--<img src="../../assets/course/map.png" class="map">-->
            <!--&lt;!&ndash;<div>虹口区-1姚志鹏-中国证券博物-从证券事业的兴衰看马克思主义的发展性</div>&ndash;&gt;-->
            <!--<div ref="point1" style="position: absolute;top: 43.3%;left: 58%;" class="point" />-->

            <!--&lt;!&ndash;<div>静安区-2李新亮-中共二大会址-党章诞生地话党章中的经济思想</div>&ndash;&gt;-->
            <!--<div style="position: absolute;top: 45.2%;left: 54.4%;" class="point" />-->

            <!--&lt;!&ndash;<div>黄浦区-3沈全-上海市工人文化宫-匠心筑梦</div>&ndash;&gt;-->
            <!--<div style="position: absolute;top: 47.2%;left: 56.8%;" class="point" />-->

            <!--&lt;!&ndash;<div>黄浦区-4王成峰-上海市总工会-在劳动模范风采中感悟人民群众的创造伟力</div>&ndash;&gt;-->
            <!--<div style="position: absolute;top: 46.5%;left: 58%;" class="point" />-->

            <!--&lt;!&ndash;<div>静安区-5施阳九-中共上海地下组织斗争史陈列馆-隐秘战线的无声较量</div>&ndash;&gt;-->
            <!--<div style="position: absolute;top: 45.2%;left: 53.6%;" class="point" />-->

            <!--&lt;!&ndash;<div>虹口区-6付清海-中共“四大”纪念馆-中共四大与早期合作社运动</div>&ndash;&gt;-->
            <!--<div style="position: absolute;top: 41.6%;left: 57.2%;" class="point" />-->

            <!--&lt;!&ndash;<div>黄浦区-7张小红-银行博物馆-金融初心，人民本位</div>&ndash;&gt;-->

            <!--<div>8李志刚-上海市银行博物馆-中国现代金融的起源与发展</div>-->
            <!--<div>9周敏晖-陈云纪念馆博物馆-统一财经，打赢经济战</div>-->
            <!--<div>10万嘉伟-税收的作用与改革</div>-->
            <!--<div>11</div>-->
            <!--<div>12王小平-中国金融中的红色基因</div>-->
            <!--<div>13贺然</div>-->
            <!--<div>14袁美琴、胡学庆、陈小愚</div>-->

            <!--&lt;!&ndash;<div>青浦区-周敏晖-陈云纪念馆博物馆-统一财经，打赢经济战</div>&ndash;&gt;-->

            <!--<div />-->
            <!--</div>-->

            <div ref="map" class="map-c">
                <img src="../../assets/course/map.png" class="map">
                <CourseItem
                    v-for="item of courseList"
                    :key="item.id"
                    :id="item.id"
                    :name="item.courseName"
                    :finished="!!item.isView"
                    :class="item.className"
                />
            </div>

        </div>

        <div class="page-footer">
            <div class="turn-bar">
                <!--<img src="../../assets/course/arrow.png" class="arrow">-->
                <span>开启您的寻访之旅</span>
                <!--<img src="../../assets/course/arrow.png" class="arrow arrow-right arrow-disabled">-->
            </div>
            <div class="progress-bar">
                <span>{{ finished }} / {{ total }}</span>
                <img src="../../assets/course/pagination.png" class="pagination">
            </div>
        </div>
    </div>
</template>

<script>
    import CourseItem from './CourseItem';
    export default {
        components: { CourseItem },
        data() {
            return {
                total: 0,
                finished: 0,
                courseList: [],
                titles: [
                    {
                        name: '从证券事业的兴衰看马克思主义的发展性',
                        address: '中国证券博物',
                        className: 'course1'
                    },
                    {
                        name: '党章诞生地话党章中的经济思想',
                        address: '中共二大会址',
                        className: 'course2'
                    },
                    {
                        name: '匠心筑梦',
                        address: '上海市工人文化宫',
                        className: 'course3',
                        isView: true
                    },
                    {
                        name: '在劳动模范风采中感悟人民群众的创造伟力',
                        address: '上海市总工会',
                        className: 'course4'
                    },
                    {
                        name: '隐蔽战线之无声较量',
                        address: '中共上海地下组织斗争史陈列馆',
                        className: 'course5'
                    },
                    {
                        name: '中共四大与早期合作社运动',
                        address: '中共“四大”纪念馆',
                        className: 'course6'
                    },
                    {
                        name: '金融初心，人民本位',
                        address: '银行博物馆',
                        className: 'course7'
                    },
                    {
                        name: '中国现代金融的起源与发展',
                        // name: '中国现代金融的起源与发展—上海市银行博物馆',
                        address: '上海市银行博物馆',
                        className: 'course8'
                    },
                    {
                        name: '统一财经，打赢经济战',
                        address: '陈云纪念馆博物馆',
                        className: 'course9'
                    },
                    {
                        name: '税收的作用与改革——以人民为中心视角',
                        address: '国家税务总局12366上海（国际）纳税服务中心',
                        className: 'course10'
                    },
                    {
                        name: '初心与使命：上商往事之一',
                        address: '上海商学院',
                        className: 'course11'
                    },
                    {
                        name: '中国金融的红色基因',
                        address: '上海科技金融博物馆',
                        className: 'course12'
                    },
                    {
                        name: '坚守育人初心，传承师道使命',
                        // name: '坚守育人初心，传承师道使命-从教故事，育人情怀',
                        address: '',
                        className: 'course13'
                    }
                ]
            };
        },
        mounted() {
            const res = {
                'errno': 0,
                'error': 'success',
                'total': 0,
                'entities': [
                    {
                        'id': 'a18dc8a7-45ed-11ec-8f19-fa163e34a620',
                        'courseName': '从证券事业的兴衰看马克思主义的发展性',
                        'longitude': 123.4535340,
                        'latitude': 211.3243240,
                        'pageContent': '中国证券博物馆于2018年1月经中央编办批复成立，是我国证券行业唯一一家国家级博物馆。馆内藏品丰富，以改革开放以来证券期货业发展的当代藏品为主，见证了新中国证券市场的诞生、发育和成长。本片借助馆内藏品，透过证券业在中国沉沉浮浮的历史画卷，帮助大家一起来体会马克思主义理论的一个重要特征——马克思主义的发展性，以进一步坚定马克思主义的信仰。\n',
                        'isView': false
                    },
                    {
                        'id': 'ad744b14-45ed-11ec-8f19-fa163e34a620',
                        'courseName': '党章诞生地话党章中的经济思想',
                        'longitude': 231.4353400,
                        'latitude': 213.4354330,
                        'pageContent': '目前，我国已成为世界第二大经济体，成为推动世界经济增长的主要动力源。党的经济纲领是党章核心内容的重要组成部分，体现了党的经济理念、理想与追求，中国共产党党章中的经济思想关系着中国现代化建设的成败。本课程就是在中国共产党第一个党章的诞生地——中共二大会址，跟大家一起分享从一大到十九大，历次党章中的主要的经济思想。进而，从党的非凡历程中，感悟到一代代共产党人的初心和使命。',
                        'isView': true
                    }
                ]
            };
            const { errno, entities } = res;
            if (errno === 0 && entities) {
                this.total = entities.length;
                let finished = 0;
                entities.forEach(item => {
                    if (item.isView) {
                        finished += 1;
                    }
                    for (const obj of this.titles) {
                        if (obj.name === item.courseName) {
                            item.className = obj.className;
                            break;
                        }
                    }
                });
                this.finished = finished;
                this.courseList = entities;
            }

            const mapWidth = this.$refs.mapWrapper.offsetWidth;
            this.$refs.map.style.left = `${Math.ceil((mapWidth - 538) / 2)}px`;
        }
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

    .emblem {
        width: 20%;
        max-width: 100px;
        position: absolute;
        top: 10px;
        right: 10px;
        opacity: 0.4;
    }

    .map-wrapper {
        min-height: 100%;
        padding-bottom: 80px;
        font-size: 0;
        background: #B00111;
        display: flex;
        flex-direction: column;
        justify-content: center;
    }

    .map-c {
        width: 538px;
        height: 583px;
        position: relative;
        top: 30px;
    }

    .map {
        width: 538px;
        height: 583px;
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

    .arrow {
        width: 18px;
        vertical-align: middle;
        cursor: pointer;
    }

    .arrow-right {
        transform: rotate(180deg);
    }

    .arrow-disabled {
        cursor: not-allowed;
        opacity: .4;
    }

    .page-footer {
        margin-top: -80px;
        position: relative;
        z-index: 100;
        text-align: center;
    }

    .turn-bar {
        margin-bottom: 10px;
        font-size: 15px;
        color: #FFF;
        font-weight: 700;

        &>span {
            padding: 0 4px;
            position: relative;
            top: 1px;
        }
    }

    .progress-bar {
        margin-bottom: 10px;
        position: relative;
        font-size: 0;

        &>span {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            font-size: 18px;
            color: #B00111;
            font-weight: 700;
        }
    }

    .course1 {
        top: 154px;
        left: 122px;
    }

    .course2 {
        top: 196px;
        left: 218px;
    }

    .course3 {
        top: 196px;
        left: 124px;
    }

    .course4 {
        top: 238px;
        left: 144px;
    }

    .course5 {
        top: 282px;
        left: 100px;
    }

    .course6 {
        top: 282px;
        left: 260px;
    }

    .course7 {
        top: 327px;
        left: 100px;
    }

    .course8 {
        top: 327px;
        left: 260px;
    }

    .course9 {
        top: 369px;
        left: 94px;
    }

    .course10 {
        top: 413px;
        left: 140px;
    }

    .course11 {
        top: 369px;
        left: 262px;
    }

    .course12 {
        top: 453px;
        left: 96px;
    }

    .course13 {
        top: 453px;
        left: 250px;
    }
</style>
