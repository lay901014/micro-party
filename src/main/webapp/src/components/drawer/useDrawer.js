import { reactive, toRefs } from '@vue/composition-api'

export function useDrawer(props, context) {
    const state = reactive({
        visible: false,
        direction: 'btt',
        options: []
    })

    const setOptions = value => {
        state.options = value
    }

    const openDrawer = () => {
        state.visible = true
    }

    const closeDrawer = () => {
        state.visible = false
    }

    const inputEvent = value => {
        context.emit('input', value)
        closeDrawer()
    }

    return { ...toRefs(state), setOptions, openDrawer, closeDrawer, inputEvent }
}
