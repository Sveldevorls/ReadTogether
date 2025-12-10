import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import PrimeVue from 'primevue/config';
import ToastService from 'primevue/toastservice';
import Aura from '@primeuix/themes/aura'
import { definePreset } from '@primeuix/themes';
import { createPinia } from 'pinia';

const app = createApp(App);

const pinia = createPinia();
const auraNoTransition = definePreset(Aura, {
    semantic: {
        transitionDuration: '0s'
    }
})

app.use(router);
app.use(PrimeVue, {
    theme: {
        preset: auraNoTransition
    }
});
app.use(ToastService);
app.use(pinia);
app.mount('#app');
