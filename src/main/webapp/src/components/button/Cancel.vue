<template>
    <div class="button" @click="$emit('click')">
        <slot>取消</slot>
    </div>
</template>

<script>
    export default {
        setup() {}
    }
</script>

<style lang="scss" scoped>
@import "@/styles/variables.scss";

.button {
  text-align: center;
  color: $color-text;
  border-radius: 6px;
  font-size: 16px;
  padding: 18px 0 27px 0;
}

.button:hover{
    cursor: pointer;
}
</style>
