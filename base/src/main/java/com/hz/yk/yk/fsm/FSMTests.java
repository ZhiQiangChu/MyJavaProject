package com.hz.yk.yk.fsm;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.Maps;

public class FSMTests {
	String STATE_LOCKED = "locked"; // ����״̬
	String STATE_UNLOCKED = "unlocked";// ����״̬

	// �¼�����
	String EVENT_COIN = "coin"; // Ͷ��
	String EVENT_PASS = "pass";// ͨ��


	@Test
	public void test() {
		Transtable table = new Transtable();

		Map<String, Object> ctx = Maps.newHashMap();
		// ctx init
		 table.addTranstion(STATE_LOCKED, EVENT_COIN, STATE_UNLOCKED);
		 table.addTranstion(STATE_UNLOCKED, EVENT_PASS, STATE_LOCKED);

		FSM fsm = new FSM(STATE_LOCKED, table, ctx);

		System.out.println("current state :" + fsm.frie(EVENT_COIN));
		System.out.println("current state :" + fsm.frie(EVENT_PASS));
		
		System.out.println("current state :" + fsm.frie(EVENT_COIN));
		System.out.println("current state :" + fsm.frie(EVENT_PASS));

	}

}
