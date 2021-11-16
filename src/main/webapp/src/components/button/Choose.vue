<template>
    <span>
        <span :class="['option', { hover: !isEdit }]" @click="optionClick">
            <span v-if="!!value" class="text">{{ value }}</span>
            <span v-else class="holder">请选择</span>
            <img v-if="!isEdit" :src="require('@/icons/option.png')">
        </span>
    </span>
</template>

<script>
    export default {
        name: 'Choose',
        props: ['value', 'isEdit'],
        setup(props, context) {
            const optionClick = () => {
                if (!props.isEdit) {
                    context.emit('click')
                }
            }

            return { optionClick }
        }
    }
</script>

<style lang="scss" scoped>
@import "@/styles/variables.scss";

.option {
  display: flex;
  align-items: center;
  font-size: 16px;

  .text {
    font-size: 16px;
    color: $color-text;
    text-align: right;
    line-height: 24px;
  }

  .holder {
    font-size: 16px;
    color: $color-text-holder;
    line-height: 16px;
  }

  img {
    width: 12px;
    margin-left: 10px;
  }
}
.option.hover {
  cursor: pointer;
}
</style>
