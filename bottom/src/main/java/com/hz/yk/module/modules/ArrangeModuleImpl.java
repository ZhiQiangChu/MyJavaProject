package com.hz.yk.module.modules;

import com.hz.yk.module.IcService;
import com.hz.yk.module.ItemDO;
import com.hz.yk.module.plugin.TagPlugin;
import com.hz.yk.module.plugin.PromotionPlugin;
import com.hz.yk.module.router.PluginRouter;
import com.hz.yk.module.router.RouterInfo;
import com.hz.yk.module.util.PluginUtil;

/**
 * @author wuzheng.yk
 *         Date: 15/5/7
 *         Time: 14:47
 */
public class ArrangeModuleImpl implements ArrangeModule{
    IcService icService;

    @Override
    public void publish(ItemDO itemDO) {
        System.out.println("start publish ,,,,");
        //����ic�ķ���ȥ������Ʒ
        icService.doPublish();
        RouterInfo routerInfo =PluginRouter.getInfo(itemDO);
        //�����Żݲ��ȥ�����Ż�
        PromotionPlugin promotionPlugin = PluginUtil.getInstance(PromotionPlugin.class,routerInfo);
        promotionPlugin.promotion();
        //���ô����ȥ���
        TagPlugin tagPlugin = PluginUtil.getInstance(TagPlugin.class,routerInfo);
        tagPlugin.tag();
        System.out.println("publish success,,,,");
    }

    public void setIcService(IcService icService) {
        this.icService = icService;
    }
}
