const getters = {
    sidebar: state => state.app.sidebar,
    device: state => state.app.device,
    le1600: state => state.app.le1600,
    layoutLoaded: state => state.app.layoutLoaded,
    token: state => state.user.token,
    account: state => state.user.account,
};
export default getters;
