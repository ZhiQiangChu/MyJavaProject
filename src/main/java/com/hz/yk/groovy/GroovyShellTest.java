package com.hz.yk.groovy;/**
 * @author wuzheng.yk
 * Date: 14-2-7
 * Time: ����4:40
 */

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import java.math.BigDecimal;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * @author wuzheng.yk
 *         Date: 14-2-7
 *         Time: ����4:40
 */
public class GroovyShellTest {

    @Test
    public void testCallStatement() {
        // 1.��Java�����е���Groovy���
        Binding binding = new Binding();
        binding.setVariable("price", new Integer(200));
        GroovyShell shell = new GroovyShell(binding);
        Object value = shell.evaluate("println 'Hello World!'; site = \"qiangpai\"; return price * 0.1");
        assertTrue(value.equals(new BigDecimal("20.0"))); // ������С����ԭ��,groovyĬ��С������ΪBigDecimal(ͨ�����������ж�Ǯ������)
        assertTrue(binding.getVariable("site").equals("qiangpai"));
    }
}
