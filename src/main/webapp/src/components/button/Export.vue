<template>
    <span class="export" @click="exportForm">
        <img :src="require('@/icons/download.png')">
        查看导医单
    </span>
</template>

<script>
    export default {
        name: 'Export',
        props: ['value'],
        setup(props, context) {
            const exportForm = () => {
                if (props.value) {
                    window.open(`/health/appointment/export?id=${props.value}`, 'blank')
                } else {
                    context.root.$message.error('未获取到导医单ID')
                }
            }

            return { exportForm }
        }
    }
</script>

<style lang="scss" scoped>
.export {
  display: flex;
  align-items: center;
  justify-content: center;

  img {
    width: 18px;
    height: 17px;
    margin-right: 4px;
  }
}

.export:hover {
  cursor: pointer;
}
</style>
