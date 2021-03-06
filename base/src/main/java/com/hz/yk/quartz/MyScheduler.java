package com.hz.yk.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

public class MyScheduler {

    private       List<MyJobDetail>  jobList     = new ArrayList<MyJobDetail>();
    private       TreeSet<MyTrigger> triggerList = new TreeSet<MyTrigger>();
    private final Object             lockObj     = new Object();
    SchedulerThread thread = new SchedulerThread();// 任务调度在本此线程执行

    public void schedulerJob(MyJobDetail detail, MyTrigger trigger) {
        synchronized (lockObj) {
            jobList.add(detail);
            trigger.setJobKey(detail.getJobName());
            triggerList.add(trigger);
        }
    }

    public void start() {
        System.out.println("########## run scheduler at :" + new Date() + "##########");
        thread.start();
    }

    public void halt() {
        thread.halt();
    }

    class SchedulerThread extends Thread {
        private boolean shutDown = false;

        public void halt() {//停止线程
            synchronized (lockObj) {
                shutDown = true;
                lockObj.notifyAll();
            }
        }

        @Override
        public void run() {//运行
            while (!shutDown) {
                synchronized (lockObj) {
                    try {
                        final MyTrigger trigger = triggerList.pollFirst();//获取最近执行的作业
                        if (trigger == null) {
                            lockObj.wait(100);
                            continue;
                        }
                        long curr = System.currentTimeMillis();
                        long nextTime = trigger.getNextFireTime();
                        while (nextTime > curr && !shutDown) {
                            curr = System.currentTimeMillis();
                            if (nextTime > curr + 1) {
                                lockObj.wait(nextTime - curr);
                            }
                            if (!shutDown) {
                                int index = jobList.indexOf(new MyJobDetail(trigger.getJobKey(), null));
                                MyJobDetail jobDetail = jobList.get(index);
                                MyJob job = jobDetail.getClazz().newInstance();
                                job.execute(jobDetail.getJobData());
                                trigger.resert();
                                nextTime = trigger.getNextFireTime();
                                if (nextTime != -1) {
                                    triggerList.add(trigger);
                                } else {
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}
