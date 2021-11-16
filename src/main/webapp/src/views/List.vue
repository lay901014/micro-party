<template>
    <div class="list">
        <div class="content">
            <div v-for="i in data" :key="i.id" class="card">
                <Card :data="i" />
                <el-divider class="divider" />
                <div class="option">
                    <Export :value="i.id" />
                    <span>
                        <span slot="reference" @click="messageBox(i.id)">取消预约</span>
                        <Primary class="primary" @click="editClick(i)">修改预约</Primary>
                    </span>
                </div>
            </div>
        </div>
        <div class="footer">
            <Base
                v-if="!hiddenCreate && isAuthority"
                class="base"
                @click="$emit('create')"
            >增加新的预约</Base>
        </div>
    </div>
</template>

<script>
    import Card from './components/Card.vue'
    import Primary from '@/components/button/Primary.vue'
    import Base from '@/components/button/Base.vue'
    import Export from '@/components/button/Export.vue'
    import { cancelData } from './api'
    import { computed } from '@vue/composition-api'

    export default {
        name: 'List',
        components: { Card, Primary, Base, Export },
        props: {
            data: { type: Array, default: () => [] },
            userInfo: { type: Object, default: () => null },
            isAuthority: { type: Boolean, default: false }
        },
        setup(props, context) {
            const hiddenCreate = computed(
                () => props.data.length > 0 && !props.userInfo.canEntrust
            )

            const cancelClick = (value) => {
                cancelData({ id: value }).then((res) => {
                    context.emit('update')
                })
            }

            const editClick = (value) => context.emit('edit', value)

            const messageBox = (value) => {
                context.root.$alert('确认取消体检预约？', '取消预约', {
                    confirmButtonText: '确定',
                    callback: action => {
                        if (action === 'confirm') {
                            cancelClick(value)
                        }
                    }
                })
            }

            return { hiddenCreate, cancelClick, messageBox, editClick }
        }
    }
</script>

<style lang="scss" scoped>
@import "@/styles/variables.scss";

.list {
  box-sizing: border-box;
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;

  .content {
    width: 100%;
  }

  .card {
    box-sizing: border-box;
    padding: 20px 14px 12px 16px;
    background-color: #fff;
    margin-top: 12px;
    width: 100%;
  }

  .divider {
    margin: 20px 0 12.5px 0;
  }

  .option {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 32px;

    span {
      color: $color-btn-base;
    }

    span:hover {
      cursor: pointer;
    }

    .primary {
      display: inline;
      padding: 5px 17px;
      margin-left: 8px;
      border-radius: 4px;
    }
  }

  .footer {
    margin: 12px 0 20px 0;
    flex-shrink: 0;
  }
}
</style>
