<template>
    <div class="button" @click="$emit('click')">
        <slot>提交预约</slot>
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
  border: 1px solid $color-btn-base;
  border-radius: 6px;
  font-size: 16px;
  padding: 10px;
  width: 160px;
}

.button:hover {
  cursor: pointer;
}
</style>
