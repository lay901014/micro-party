<template>
    <div class="map-course-item" @click="handleStart">
        <div class="course-name">
            <img v-if="finished" src="../../assets/course/finished.png">
            <img v-else src="../../assets/course/play.png">
            <span>{{ name }}</span>
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
                const elements = document.getElementsByClassName('map-course-item');
                const length = elements.length;
                for (var i = 0; i< length; i++) {
                    if (elements[i].className.indexOf('right-in') > -1) {
                        elements[i].className = elements[i].className.replace('right-in', 'right-out')
                    } else {
                        elements[i].className += ' right-out'
                    }
                }
                this.createMark();
                this.$emit('update:activeId', this.id)
            },
            hideMark(e) {
                e.target.className += ' fade-out';
                e.target.removeEventListener('click', this.hideMark);
                setTimeout(function() {
                    e.target.remove();
                }, 100)
                const elements = document.getElementsByClassName('map-course-item');
                const length = elements.length;
                for (var i = 0; i< length; i++) {
                    if (elements[i].className.indexOf('right-out') > -1) {
                        elements[i].className = elements[i].className.replace('right-out', 'right-in')
                    } else {
                        elements[i].className += ' right-in'
                    }
                }
            },
            createMark() {
                const mark = document.createElement('div');
                mark.className = 'mark';
                mark.addEventListener('click', this.hideMark)
                document.body.appendChild(mark);
            }
        }
    };
</script>

<style scoped lang="scss">
    .map-course-item {
        position: absolute;
        cursor: pointer;
        white-space: nowrap;
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

        span {
            position: relative;
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

    .fade-out {
        animation: fade_out 0.8s 1;
        animation-fill-mode: forwards;
    }

    .fade-in {
        animation: fade_in 0.8s 1;
        animation-fill-mode: forwards;
        animation-fill-mode: both;
    }

    @keyframes fade_out {

        0% {
            opacity: 1;
        }

        100% {
            height: 0;
            opacity: 0;
            visibility: hidden;
            display: none;
        }
    }

    @keyframes fade_in {

        0% {
            opacity: 0;
        }

        100% {
            opacity: 1;
        }
    }

    .right-out {
        animation: right_out 0.7s 1;
        animation-fill-mode: forwards;
    }

    @keyframes right_out {
        /*0% {*/
            /*left: inherit;*/
        /*}*/
        100% {
            left: 700px;
        }
    }

    .right-in {
        animation: right_in 0.7s 1;
        animation-fill-mode: forwards;
    }
    @keyframes right_in {
        0% {
            left: 700px;
        }
    }
</style>
