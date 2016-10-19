package com.antteam.framework.test;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: StressTestUtil
 * 
 * @Description: TODO
 * @author Mirai
 * @date 2016年10月19日
 */
public class StressTestUtil {

    private ExecutorService threadPool;

    private CompletionService<ExcuteResult> pool;

    private int threadNum;

    public void init(int threadNum) {
        this.threadNum = threadNum;
        // 线程数
        threadPool = Executors.newFixedThreadPool(threadNum);
        pool = new ExecutorCompletionService<ExcuteResult>(threadPool);
    }


    public void submit(Callable<ExcuteResult> call) {
        pool.submit(call);
    }

    protected StatData countStatData(long totalTimes,HandleResultCallBack handle) {
        StatData statData = new StatData();
        statData.setTotalTimes(totalTimes);
        statData.setThreadNum(threadNum);
        // 统计
        for (int i = 0; i < totalTimes; i++) {
            try {
                ExcuteResult result = pool.take().get();
                result=handle.handle(result);
                Long runTime=result.getRuntime();
                statData.setTotalTime(statData.getTotalTime()+runTime);
                if(runTime.compareTo(statData.getRequestMaxTime())>0){
                    statData.setRequestMaxTime(runTime);
                }
                if(runTime.compareTo(statData.getRequestMinTime())<0){
                    statData.setRequestMinTime(runTime);
                }
                if(!result.isSuccess()){
                    statData.setFailTimes(statData.getFailTimes()+1);
                }
            }catch (Exception e) {
                statData.setFailTimes(statData.getFailTimes() + 1);
            }
        }
        System.out.println(statData);
        return statData;

    }
    
    public void destory() {
        threadPool.shutdown();
    }

}
