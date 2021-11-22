<template>
    <div class="lost-container">
        <img class="pic-404" src="@/assets/404_images/404.png" alt="404">
        <div class="bullshit">
            <div class="bullshit__oops">OOPS!</div>
            <div class="bullshit__headline">您请求的页面不存在...</div>
            <div class="bullshit__info">请检查您输入的地址是否正确，或按下面的按钮返回首页</div>
            <a href="/" class="bullshit__return-home">返回首页</a>
        </div>
    </div>
</template>

<script>

    export default {
        name: 'Page404'
    };
</script>

<style lang="scss" scoped>
    .lost-container {
        width: 90%;
        max-width: 1200px;
        margin: 0 auto;
        position: relative;
        top: 50%; /*偏移*/
        font-size: 0;
        transform: translateY(-50%);

        .pic-404 {
            width: 50%;
            vertical-align: middle;
        }

        .bullshit {
            display: inline-block;
            position: relative;
            width: 50%;
            max-width: 400px;
            padding: 30px 0;
            vertical-align: middle;
            overflow: hidden;

            &__oops {
                font-size: 32px;
                font-weight: bold;
                line-height: 40px;
                color: #1482f0;
                opacity: 0;
                margin-bottom: 20px;
                animation-name: slideUp;
                animation-duration: 0.5s;
                animation-fill-mode: forwards;
            }

            &__headline {
                font-size: 20px;
                line-height: 24px;
                color: #222;
                font-weight: bold;
                opacity: 0;
                margin-bottom: 10px;
                animation-name: slideUp;
                animation-duration: 0.5s;
                animation-delay: 0.1s;
                animation-fill-mode: forwards;
            }

            &__info {
                font-size: 13px;
                line-height: 21px;
                color: grey;
                opacity: 0;
                margin-bottom: 30px;
                animation-name: slideUp;
                animation-duration: 0.5s;
                animation-delay: 0.2s;
                animation-fill-mode: forwards;
            }

            &__return-home {
                display: block;
                float: left;
                width: 110px;
                height: 36px;
                background: #1482f0;
                border-radius: 100px;
                text-align: center;
                color: #ffffff;
                opacity: 0;
                font-size: 14px;
                line-height: 36px;
                cursor: pointer;
                animation-name: slideUp;
                animation-duration: 0.5s;
                animation-delay: 0.3s;
                animation-fill-mode: forwards;
            }

            @keyframes slideUp {
                0% {
                    transform: translateY(60px);
                    opacity: 0;
                }
                100% {
                    transform: translateY(0);
                    opacity: 1;
                }
            }
        }

    }

    @media screen and (max-width: 768px) {
        .lost-container {

            text-align: center;

            .pic-404 {
                width: 70%;
            }

            .bullshit {
                display: block;
                width: 100%;
                max-width: 340px;
                margin: 0 auto;

                &__oops {
                    display: none;
                }

                &__return-home {
                    float: none;
                    margin: 0 auto;
                }

            }

        }
    }
</style>
