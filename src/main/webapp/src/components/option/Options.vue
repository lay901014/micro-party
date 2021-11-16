<template>
    <div>
        <div v-for="(i, index) in data" :key="i.value">
            <div
                :class="['label', { select: value === i.value }]"
                @click="valueChange(i.value)"
            >
                <img v-if="value === i.value" :src="require('@/icons/select.png')">
                {{ i.label }}
            </div>
            <el-divider v-if="index !== data.length - 1" class="divider" />
        </div>
        <el-divider class="divider thick" />
    </div>
</template>

<script>
    export default {
        name: 'Options',
        props: {
            data: { type: Array, default: () => [] },
            value: { type: null }
        },
        setup(props, context) {
            const valueChange = (value) => context.emit('input', value)

            return { valueChange }
        }
    }
</script>

<style lang="scss" scoped>
@import "@/styles/variables.scss";

.label {
  padding: 18px 0;
  font-size: 16px;
  color: $color-text;
  line-height: 16px;
  text-align: center;

  img {
    height: 14px;
    width: 14px;
  }
}

.label:hover {
  cursor: pointer;
}

.label.select {
  color: $color-text-select;
}

.divider {
  margin: 0;
  color: #ececec;
}

.divider.thick {
  color: #f6f6f6;
  height: 4px;
}
</style>
