<template>
    <div class="app-wrapper">
        <app-main />
        <alive />
    </div>
</template>

<script>
    import { AppMain, Alive } from './components';

    export default {
        name: 'Layout',
        components: {
            AppMain,
            Alive
        }
    };
</script>
