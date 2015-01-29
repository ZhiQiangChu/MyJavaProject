package com.hz.yk.classload;

/**
 * @author wuzheng.yk
 *         Date: 15/1/29
 *         Time: 11:26
 */
public class CaseHsf {
    public static void main(String[] args) {
        MyClassLoader loader1 = new MyClassLoader(null, "loader1");// ʹ�ø�������������࣬�������ռ����
        try {
            System.out.println(loader1.getParent());
            Class<RfqService> rfqServiceClass = RfqService.class;// ֱ�Ӵ���ӿ��࣬����ģ��HSF��ȡӦ����
            Class<RfqRequestDTO> rfqRequestDTOClass = RfqRequestDTO.class;// ֱ�Ӵ���ӿ��࣬����ģ��HSF��ȡӦ����
            Class<?> hsfTest = loader1.loadClass("com.hz.yk.classload.HSFTest");// Ӧ�û�ȡHSF�࣬����ģ�⵼����Ӧ��
            RfqService rfqService = (RfqService)   hsfTest.getDeclaredMethod("getObject", new Class[]{Class.class, Class.class})
                    .invoke(hsfTest.newInstance(),new Class[]    {rfqServiceClass,rfqRequestDTOClass });
            System.out.println("rfqService class load = "+rfqService.getClass().getClassLoader());
            System.out.println(rfqService.getRfq().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
