import store from '@/store';

const { body } = document;
const WIDTH = 992; // refer to Bootstrap's responsive design

export default {
    beforeMount() {
        window.addEventListener('resize', this.$_resizeHandler);
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.$_resizeHandler);
    },
    mounted() {
        const isMobile = this.$_isMobile();
        if (isMobile) {
            store.dispatch('app/toggleDevice', 'mobile');
        }
        store.dispatch('app/le1600', body.getBoundingClientRect().width <= 1600);
    },
    methods: {
    // use $_ for mixins properties
    // https://vuejs.org/v2/style-guide/index.html#Private-property-names-essential
        $_isMobile() {
            const rect = body.getBoundingClientRect();
            return rect.width - 1 < WIDTH;
        },
        $_resizeHandler() {
            if (!document.hidden) {
                const isMobile = this.$_isMobile();
                store.dispatch('app/toggleDevice', isMobile ? 'mobile' : 'desktop');
                store.dispatch('app/le1600', body.getBoundingClientRect().width <= 1600);
            }
        }
    }
};
