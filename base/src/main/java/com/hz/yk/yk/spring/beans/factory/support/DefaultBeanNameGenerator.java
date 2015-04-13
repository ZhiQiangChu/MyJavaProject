package com.hz.yk.yk.spring.beans.factory.support;

import com.hz.yk.yk.spring.beans.factory.config.BeanDefinition;

/**
 * @author wuzheng.yk
 *         Date: 13-3-15
 *         Time: ����7:31
 */
public class DefaultBeanNameGenerator implements BeanNameGenerator {

    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        return BeanDefinitionReaderUtils.generateBeanName(definition, registry);
    }

}
