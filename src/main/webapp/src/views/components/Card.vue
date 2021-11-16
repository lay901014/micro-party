<template>
    <div class="base">
        <div class="row">
            <span class="label">预约人：</span>
            <span class="text">{{ data.userName }}</span>
        </div>
        <div class="row">
            <span class="label">医院：</span>
            <span class="text">{{ data.hospotalArea }}</span>
        </div>
        <div class="row">
            <span class="label">预约时间：</span>
            <span class="text">{{
                data.appointmentDate + ' ' + data.appointmentTime
            }}</span>
        </div>
    </div>
</template>

<script>
    export default {
        name: 'Card',
        props: { data: { type: Object, required: true }},
        setup() {}
    }
</script>

<style lang="scss" scoped>
@import "@/styles/variables.scss";

.base {

  .row {
    margin: 8px 0;

    .label {
      font-size: 16px;
      color: $color-text-label;
      line-height: 16px;
    }

    .text {
      font-size: 16px;
      color: $color-text;
    }
  }
}
</style>
