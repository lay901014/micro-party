const getters = {
    sidebar: state => state.app.sidebar,
    device: state => state.app.device,
    le1600: state => state.app.le1600,
    layoutLoaded: state => state.app.layoutLoaded,
    token: state => state.user.token,
    account: state => state.user.account,
    name: state => state.user.name,
    dept: state => state.user.dept,
    deptCode: state => state.user.deptCode,
    pageRights: state => state.user.pageRights
};
export default getters;
