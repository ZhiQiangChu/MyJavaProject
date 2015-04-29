package com.hz.yk.yk.statemachine;

/**
 * @author wuzheng.yk
 *         Date: 15/4/29
 *         Time: 16:24
 */
public class Aircon1 {
    IState state= StateImpl.OFF;//private��Ĭ�ϣ�ɾ��getState()��
    //����Action
    public void power(){//��power��
        state.power(this);
    }
    public void cool(){//�������
        state.cool(this);
    }

    public void entry(){
        System.out.println("��" + this.state.name());
    }
    /**
     * ACCtrl�Ĵ��롣
     */
    public static void main(String[] args){
        Aircon1 ac = new Aircon1();
        System.out.println("Current State:" + ac.state.name());
        ac.cool();
        ac.power();
        ac.cool();
        ac.cool();
        ac.power();
        ac.power();
        ac.power();

    }
}
