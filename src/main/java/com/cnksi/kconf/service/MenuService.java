package com.cnksi.kconf.service;

import com.cnksi.kconf.model.Menu;
import com.cnksi.kcore.utils.CollectionKit;

import java.util.List;

/**
 * Created by Joey on 2017/2/13.
 * 菜单相关Service
 */
public class MenuService {
    public static MenuService service = new MenuService();

    /**
     * 根据role 查询返回该角色的菜单结构
     * <p>
     * 三级结构：module->parent->sub
     *
     * @param roleid
     * @return
     */
    public List<Menu> findMenuAndChidrenByRole(Long roleid) {
        // 获取角色配置的菜单
        List<Menu> menus = Menu.me.findByRole(roleid);
        if (CollectionKit.empty(menus)) {
            return menus;//返回的是空
        } else {
            //1、先构建module menu
            List<Menu> modules = Menu.me.findModuleMenuByRole(roleid);

            modules.forEach(module -> {
                //2、构建每个module下的parent menu
                List<Menu> parents = Menu.me.findMenu("parent", module.getPkVal());
                parents.forEach(parent -> {
                    //3、构建每个parent下的child menu
                    List<Menu> children = Menu.me.findChildMenuByRole(parent.getPkVal(), roleid);
                    parent.put("children", children);
                });
                module.put("parents", parents);
            });
            return modules;
        }
    }
}
