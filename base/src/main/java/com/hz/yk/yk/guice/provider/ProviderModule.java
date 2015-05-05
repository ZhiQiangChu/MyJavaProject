package com.hz.yk.yk.guice.provider;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.hz.yk.yk.guice.DatabaseTransactionLog;
import com.hz.yk.yk.guice.TransactionLog;

/**
 * ��configure������ʹ��Provider�󶨺�ֱ��д@Provides������ʵ�ֵĹ�����û�в��ģ�����ʹ��Provider�󶨻�ʹ��������������ҵ��ṩ����ķ�����Ҳ��Ҫ���������͵�����ע��ʱ��ʹ��Provider�󶨻��Ǹ��õ�ѡ��
 * @author wuzheng.yk
 *         Date: 15/5/3
 *         Time: 11:09
 */
public class ProviderModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(TransactionLog.class).toProvider(DatabaseTransactionLogProvider.class);
    }

    @Provides
    TransactionLog provideTransactionLog() {
        DatabaseTransactionLog transactionLog = new DatabaseTransactionLog();
        return transactionLog;
    }
}
