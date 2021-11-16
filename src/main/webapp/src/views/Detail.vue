<template>
    <div v-if="showCompletion" class="content">
        <img :src="require('@/icons/bg.png')">
        <div class="success">预约成功！</div>
        <Export :value="exportId" style="margin-bottom: 30px;" />
        <Primary class="back" @click="backClick"> 回到首页 </Primary>
    </div>
    <div v-else>
        <div class="row">
            <span class="label">选择医院：</span>
            <Choose
                v-model="data.hospotalArea"
                :is-edit="false"
                @click="hospitalClick"
            />
            <Hospital
                ref="hospital"
                v-model="data.hospotalArea"
                :data="hospitalData"
            />
        </div>
        <div v-if="!!addressText" class="explain" style="justify-content: flex-end">
            {{ addressText }}
        </div>
        <el-divider class="divider" />
        <div class="row">
            <span class="label">预约时间：</span>
            <span style="display: flex; align-items: center">
                <span v-if="typeof dateMost === 'boolean'" style="margin-right: 4px">
                    <el-tag v-if="dateMost" effect="plain">可预约</el-tag>
                    <el-tag v-else type="danger" effect="plain">已约满</el-tag>
                </span>
                <Choose v-model="dateText" :is-edit="false" @click="dateSelectClick" />
            </span>
            <DateSelect
                ref="dateSelect"
                v-model="data.appointmentDateId"
                :data="dateOptions"
            />
        </div>

        <el-divider v-if="showOthers" class="divider" />
        <div v-if="showOthers" class="row">
            <span class="label">预约人：</span>
            <Choose v-model="othersText" :is-edit="isEdit" @click="memberTypeClick" />
            <MemberType
                ref="memberType"
                v-model="data.others"
                @input="memberChange"
            />
        </div>
        <el-divider v-if="showOthers && !data.others" class="divider" />
        <div v-if="showOthers && !data.others" class="row">
            <span class="label">工号：</span>
            <span v-if="isEdit" class="option">{{ data.userId }}</span>
            <el-input
                v-else
                v-model="data.userId"
                size="small"
                class="component"
                @input="userChange"
            />
        </div>
        <div class="title">确认个人信息</div>
        <div class="title tip">个人信息如有问题，请联系sjtuhr@sjtu.edu.cn</div>
        <div class="row">
            <span class="label">姓名：</span>
            <span class="option">{{ data.userName }}</span>
        </div>
        <el-divider v-if="!(showOthers && !data.others)" class="divider" />
        <div v-if="!(showOthers && !data.others)" class="row">
            <span class="label">工号：</span>
            <span class="option">{{ data.userId }}</span>
        </div>
        <el-divider class="divider" />
        <div class="row">
            <span class="label">性别：</span>
            <span class="option">{{ genderText }}</span>
        </div>
        <el-divider class="divider" />
        <div class="row">
            <span class="label">部门：</span>
            <span class="option">{{ data.college }}</span>
        </div>
        <el-divider class="divider" />

        <div class="row">
            <span class="label">出生日期：</span>
            <span class="option">{{ data.birthDate }}</span>
            <!-- <el-date-picker
                v-model="data.birthDate"
                size="small"
                value-format="yyyy-MM-dd"
                class="component"
            /> -->
        </div>
        <el-divider class="divider" />
        <div class="row">
            <span class="label">婚姻状态：</span>
            <Choose v-model="marriageText" :is-edit="false" @click="marriageClick" />
            <MarriageStatus ref="marriageStatus" v-model="data.isMarried" />
        </div>
        <el-divider class="divider" />
        <div class="row">
            <span class="label">证件类型：</span>
            <span class="option">{{ data.identityType }}</span>
            <!-- <Choose v-model="data.identityType" :is-edit="false" @click="cardTypeClick" /> -->
            <!-- <CardType ref="cardType" v-model="data.identityType" @input="cardTypeChange" /> -->
        </div>
        <el-divider class="divider" />
        <div class="row">
            <span class="label">证件号码：</span>
            <span class="option">{{ data.idNumber }}</span>
            <!-- <el-input v-model="data.idNumber" size="small" class="component" /> -->
        </div>
        <el-divider class="divider" />
        <div class="row" style="padding-bottom: 10px">
            <span class="label">手机号码：</span>
            <el-input v-model="data.mobile" size="small" class="component" />
        </div>
        <!-- <div class="explain">
            <i class="el-icon-warning-outline" />
            手机号码用于通知您体检信息
        </div> -->
        <div class="footer">
            <Primary v-if="isAuthority" @click="submitClick" />
        </div>
    </div>
</template>

<script>
    import Hospital from '@/components/drawer/Hospoital.vue'
    import DateSelect from '@/components/drawer/DateSelect.vue'
    import MemberType from '@/components/drawer/MemberType.vue'
    import MarriageStatus from '@/components/drawer/MarriageStatus.vue'
    import Export from '@/components/button/Export.vue'
    // import CardType from '@/components/drawer/CardType.vue'
    import Primary from '@/components/button/Primary.vue'
    import Choose from '@/components/button/Choose.vue'
    import { getUserData, getHospitalData, saveData } from './api'
    import { onMounted, reactive, toRefs, computed } from '@vue/composition-api'
    import { cloneDeep } from 'lodash'
    import { Form, Member } from './config'

    export default {
        name: 'Detail',
        components: {
            Hospital,
            MemberType,
            // CardType,
            MarriageStatus,
            Primary,
            Choose,
            DateSelect,
            Export
        },
        props: ['userInfo', 'list', 'isAuthority'],
        setup(props, context) {
            const state = reactive({
                data: cloneDeep(Form),
                hospitalData: [],
                showCompletion: false,
                exportId: ''
            })

            const isEdit = computed(() => !!state.data.id)

            const dateOptions = computed(() => {
                const value = state.hospitalData.find(
                    (i) => i.value === state.data.hospotalArea
                )
                if (value) {
                    return value.children.map((i) => ({
                        label: i.appointmentDate + '  ' + i.appointmentTime,
                        value: i.id,
                        isMost: i.canAppoint
                    }))
                } else {
                    return []
                }
            })

            const showOthers = computed(() => props.userInfo.canEntrust)

            const addressText = computed(() => {
                const value = state.hospitalData.find(
                    (i) => i.value === state.data.hospotalArea
                )
                return value ? value.address : ''
            })

            const othersText = computed(() => {
                if (state.data.others === true) {
                    return '本人'
                } else if (state.data.others === false) {
                    return '代他人'
                } else {
                    ('')
                }
            })

            const marriageText = computed(() => {
                if (state.data.isMarried === true) {
                    return '已婚'
                } else if (state.data.isMarried === false) {
                    return '未婚'
                } else {
                    ('')
                }
            })

            const genderText = computed(() => {
                if (state.data.gender === 'male') {
                    return '男性'
                } else if (state.data.gender === 'female') {
                    return '女性'
                } else {
                    ('')
                }
            })

            const dateText = computed(() => {
                const value = dateOptions.value.find(
                    (i) => i.value === state.data.appointmentDateId
                )
                return value ? value.label : ''
            })

            const dateMost = computed(() => {
                const value = dateOptions.value.find(
                    (i) => i.value === state.data.appointmentDateId
                )
                return value ? value.isMost : ''
            })

            const hospitalClick = () => context.refs['hospital'].openDrawer()

            const dateSelectClick = () => context.refs['dateSelect'].openDrawer()

            const memberTypeClick = () => context.refs['memberType'].openDrawer()

            const cardTypeClick = () => context.refs['cardType'].openDrawer()

            const cardTypeChange = (value) => {
                state.data.idNumber = ''
            }

            const marriageClick = () => context.refs['marriageStatus'].openDrawer()

            const memberChange = (value) => {
                if (value) {
                    state.data = { ...state.data, ...props.userInfo }
                } else {
                    state.data = { ...state.data, ...cloneDeep(Member) }
                }
            }

            const userChange = (value) => {
                if (!value) {
                    state.data = { ...state.data, ...cloneDeep(Member) }
                    return
                }
                getUserData({ userId: value }).then((res) => {
                    if (res.entities && res.entities.length > 0 && res.entities[0]) {
                        state.data = { ...state.data, ...res.entities[0] }
                    } else {
                        if (value) {
                            state.data = { ...state.data, ...cloneDeep(Member), userId: value }
                        }
                    }
                })
            }

            const getHospital = () => {
                getHospitalData().then((res) => {
                    state.hospitalData =
                        res.entities.map((i) => ({
                            label: i.hospitalArea + i.address,
                            value: i.hospitalArea,
                            address: i.address,
                            children: i.dateList
                        })) || []
                })
            }

            const submitClick = () => {
                const params = {
                    id: state.data.id,
                    appointmentDateId: state.data.appointmentDateId,
                    userId: state.data.userId,
                    mobile: state.data.mobile,
                    identityType: state.data.identityType,
                    idNumber: state.data.idNumber,
                    isMarried: state.data.isMarried,
                    birthDate: state.data.birthDate
                }
                if (!checkParams()) {
                    return
                }
                if (checkExist()) {
                    return context.root.$message.error('已预约人员，不可重复预约')
                }
                saveData(params).then((res) => {
                    const existId = res.entities && res.entities.length > 0
                    state.exportId = existId ? res.entities[0] : ''
                    state.showCompletion = true
                })
            }

            const checkParams = () => {
                // else if (!state.data.identityType) {
                //                     context.root.$message.error('请选择证件类型')
                //                     return false
                //                 } else if (!state.data.idNumber) {
                //                     context.root.$message.error('请输入证件号码')
                //                     return false
                //                 }
                //  else if (!state.data.birthDate) {
                //     context.root.$message.error('请选择出生日期')
                //     return false
                // }

                if (!state.data.appointmentDateId) {
                    context.root.$message.error('请选择预约时间')
                    return false
                } else if (!state.data.userId) {
                    context.root.$message.error('请输入工号')
                    return false
                } else if (![true, false].includes(state.data.isMarried)) {
                    context.root.$message.error('请选择婚姻状态')
                    return false
                } else if (!state.data.mobile) {
                    context.root.$message.error('请输入手机号码')
                    return false
                }
                return true
            }

            const checkExist = () => {
                return (
                    !!props.list.find((i) => i.userId === state.data.userId) &&
                    !state.data.id
                )
            }

            const backClick = () => {
                context.emit('update')
            }

            const initDetail = (value) => {
                state.showCompletion = false
                const data = value || cloneDeep(Form)
                state.data = {
                    ...state.data,
                    ...data,
                    others: value ? props.userInfo.userId === value.userId : ''
                }
            }

            onMounted(() => {
                getHospital()
            })

            return {
                isEdit,
                addressText,
                showOthers,
                genderText,
                othersText,
                marriageText,
                dateOptions,
                dateText,
                dateMost,
                ...toRefs(state),
                hospitalClick,
                dateSelectClick,
                memberTypeClick,
                cardTypeClick,
                memberChange,
                userChange,
                marriageClick,
                cardTypeChange,
                submitClick,
                backClick,
                initDetail
            }
        }
    }
</script>

<style lang="scss" scoped>
@import "@/styles/variables.scss";

.content {
  text-align: center;
  padding: 20px;
  background-color: #fff;

  img {
    width: 171px;
    height: 212px;
  }

  .success {
    color: $color-text;
    padding: 30px 0;
    font-size: 16px;
    text-align: center;
  }

  div {
    margin: 0 auto;
  }
}

.row {
  padding: 20px 14px 20px 16px;
  background-color: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .label {
    flex-shrink: 0;
    font-size: 16px;
    color: $color-text-label;
    line-height: 16px;
  }

  .option {
    font-size: 16px;
  }

  .component {
    width: 160px;

    ::v-deep.el-input__inner {
      font-size: 16px;
      text-align: right;
    }
  }
}

.divider {
  margin: 0;
}

.title {
  font-size: 16px;
  display: flex;
  justify-content: space-between;
  color: $color-text-explain;
  padding: 12px 16px;
}

.title.tip {
  font-size: 14px;
  padding: 0 16px 12px 16px;
}

.explain {
  padding: 0 14px 20px 16px;
  font-size: 14px;
  background-color: #fff;
  color: $color-text-explain;
  display: flex;
  align-items: center;

  .el-icon-warning-outline {
    color: $color-icon-point;
  }
}

.footer {
  padding: 20px 0;

  div {
    margin: 0 auto;
  }
}
</style>
