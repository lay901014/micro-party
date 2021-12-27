<template>
    <div class="map-course-item" @click="handleClick">
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
            courseId: {
                type: String,
                required: true
            },
            finished: {
                type: Boolean,
                required: true
            }
        },
        data() {
            return {
                mark: null
            }
        },
        methods: {
            handleClick() {
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
                this.$emit('update:activeId', this.courseId)
            },
            hideMark(e) {
                this.$emit('update:activeId', null);
                e.target.className += ' fade-out';
                e.target.removeEventListener('click', this.hideMark);
                this.mark = null;
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
                let mark = document.createElement('div');
                mark.className = 'mark';
                mark.addEventListener('click', this.hideMark);
                this.mark = mark;
                mark = null;
                document.body.appendChild(this.mark);
            }
        },
        beforeDestroy() {
            if (this.mark) {
                this.mark.remove();
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
            top: 1px;
        }

    }

    .fade-out {
        animation: fade_out 0.8s 1;
        animation-fill-mode: forwards;
    }

    .fade-in {
        animation: fade_in 0.8s 1;
        animation-fill-mode: forwards;
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
        left: 700px;
    }

    @keyframes right_out {
        /*0% {*/
            /*left: inherit;*/
        /*}*/
        100% {
            left: 700px;
            transform: none;
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
