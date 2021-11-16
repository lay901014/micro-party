<template>
    <div class="header">
        <div class="icon">
            <i v-if="!showList && isAuthority" class="el-icon-arrow-left" @click="$emit('show')" />
        </div>
        <div class="title">一院体检预约</div>
    </div>
</template>

<script>
    export default {
        name: 'Header',
        props: ['showList', 'isAuthority'],
        setup() {}
    }
</script>

<style lang="scss" scoped>
@import "@/styles/variables.scss";

.header {
  padding: 0 12px;
  height: 97px;
  background: $color-bg-header;
  color: #ffffff;
  display: flex;
  align-items: center;

  .icon {
    width: 16px;
    height: 16px;
    flex-shrink: 0;
  }

  .title {
    flex: 1;
    font-size: 18px;
    text-align: center;
  }
}

@media (max-width: 768px) {
  .header {
    display: none;
  }
}
</style>
