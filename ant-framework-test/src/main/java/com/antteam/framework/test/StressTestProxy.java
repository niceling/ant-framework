package com.antteam.framework.test;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ClassName: TestProxy 
 * @Description: 压测代理工具
 * @author Mirai
 * @date 2016年10月18日
 */
public abstract class StressTestProxy{
    
    private StatData statData=null;
    
    private void before(int threadNum, long totalTimes)
    {
        statData=new StatData();
        statData.setThreadNum(threadNum);
        statData.setTotalTimes(totalTimes);
    }
    
    private void countData(Long runTime,boolean isSuccess){
        statData.setTotalTime(statData.getTotalTime()+runTime);
        if(runTime.compareTo(statData.getRequestMaxTime())>0){
            statData.setRequestMaxTime(runTime);
        }
        if(runTime.compareTo(statData.getRequestMinTime())<0){
            statData.setRequestMinTime(runTime);
        }
        if(!isSuccess){
            statData.setFailTimes(statData.getFailTimes()+1);
        }
    }
    
    private void after()
    {
        System.out.println(statData);
        afterEvent();
    }
    
    protected abstract void afterEvent();
    
    protected abstract ExcuteResult handleResult(ExcuteResult result);

    
    /**
     * @Description: 压测方法
     * @param @param threadNum 线程总数
     * @param @param totalTimes 总次数，模拟客户端个数
     * @param @param callback   回调函数
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @throws Exception 
     * @throws
     * @author Mirai
     * @date 2016年10月18日
     */
    public void submitTestTask(int threadNum,int totalTimes,RunnerCallBack runCallBack){
        before(threadNum,totalTimes);
        ExecutorService exec = Executors.newFixedThreadPool(threadNum);
        CompletionService<ExcuteResult> pool=new ExecutorCompletionService<ExcuteResult>(exec);
        for (int index = 0; index <totalTimes; index++) {
            Task task=new Task(runCallBack);
            Future<ExcuteResult> future= pool.submit(task);
            try {
                ExcuteResult result=handleResult(future.get());
                countData(result.getRuntime(), result.isSuccess());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        exec.shutdown();
        after();
    }
    
}
