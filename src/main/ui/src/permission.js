import router from './router';
import store from './store';
import NProgress from 'nprogress'; // progress bar
import 'nprogress/nprogress.css'; // progress bar style
import getPageTitle from '@/utils/get-page-title';

NProgress.configure({ showSpinner: false }); // NProgress Configuration

router.beforeEach(async(to, from, next) => {
    // start progress bar
    NProgress.start();
    document.title = getPageTitle(to.meta.title);

    if (store.getters.account !== '') {
        next();
    } else {
        try {
            // get user info
            // note: rights must be a object array! such as: ['admin'] or ,['developer','editor']
            await store.dispatch('user/getInfo');
            // generate accessible routes map based on rights
            // const accessRoutes = await store.dispatch('permission/generateRoutes', pageRights);
            // router.addRoutes(accessRoutes);

            // dynamically add accessible routes
            // router.addRoutes(router);

            // hack method to ensure that addRoutes is complete
            // set the replace: true, so the navigation will not leave a history record
            next({ ...to, replace: true });
        } catch (error) {
            NProgress.done();
        }
    }
});

router.afterEach(() => {
    // finish progress bar
    NProgress.done();
});
