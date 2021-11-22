import { asyncRoutes, constantRoutes } from '@/router';
import { PERMISSION } from '@/store/mutation-types';

/**
 * Use meta.rights to determine if the current user has permission
 * @param route 判断路由
 * @param rights 用户所有权限列表
 * @param routeRights 用户有效权限列表
 */
function hasPermission(route, rights, routeRights) {
    if (route.meta && route.meta.rights && route.meta.rights.length > 0) {
        return rights.some(right => {
            if (route.meta.rights.includes(right)) {
                routeRights.push(right);
                return true;
            }
            return false;
        });
    } else {
        return true;
    }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param rights 用户所有权限列表
 * @param routeRights 用户有效权限列表
 */
export function filterAsyncRoutes(routes, rights, routeRights) {
    const res = [];

    routes.forEach(route => {
        const currentRoute = { ...route };
        if (hasPermission(currentRoute, rights, routeRights)) {
            if (currentRoute.children) {
                currentRoute.children = filterAsyncRoutes(currentRoute.children, rights, routeRights);
                // 当用权限过滤后还有子路由才push（子路由中至少有一个具有权限）
                if (currentRoute.children && currentRoute.children.length > 0) {
                    res.push(currentRoute);
                }
            } else {
                res.push(currentRoute);
            }
        }
    });

    return res;
}

const state = {
    routes: [],
    addRoutes: [],
    // 路由中要求的且用户也具有的权限列表(用户有效权限列表)
    routeRights: []
};

const mutations = {
    [PERMISSION.SET_ROUTES]: (state, routes) => {
        state.addRoutes = routes;
        state.routes = constantRoutes.concat(routes);
    },

    [PERMISSION.SET_RIGHTS]: (state, rights) => {
        state.routeRights = rights;
    }
};

/**
 * 动态产生路由的重定向
 * @param route 路由
 * @param paths 路径
 */
function generateRedirect(route, paths) {
    if (route.redirect == null) {
        if (route.children) {
            route.redirect = [...paths, route.children[0].path].join('/');
            route.children.forEach(childRoute => {
                generateRedirect(childRoute, [...paths, childRoute.path]);
            });
        }
    }
}

const actions = {
    /**
     * @param rights 用户所有权限
     */
    generateRoutes({ commit }, rights) {
        return new Promise(resolve => {
            // 用户有效权限列表
            const routeRights = [];
            const accessedRoutes = filterAsyncRoutes(asyncRoutes, rights, routeRights);
            accessedRoutes.forEach(route => generateRedirect(route, [route.path]));
            commit(PERMISSION.SET_ROUTES, accessedRoutes);
            commit(PERMISSION.SET_RIGHTS, routeRights);
            resolve(accessedRoutes);
        });
    }
};

export default {
    namespaced: true,
    state,
    mutations,
    actions
};
