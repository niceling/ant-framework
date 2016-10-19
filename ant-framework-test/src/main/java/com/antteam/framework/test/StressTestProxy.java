package com.antteam.framework.test;

import java.util.concurrent.ExecutionException;

/**
 * ClassName: TestProxy 
 * @Description: 压测代理工具
 * @author Mirai
 * @date 2016年10月18日
 */
public abstract class StressTestProxy{
    
    private StressTestUtil stressUtil;
    
    private void before(int threadNum)
    {
        stressUtil=new StressTestUtil();
        stressUtil.init(threadNum);
    }
    
    
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
        before(threadNum);
        for (int index = 0; index <totalTimes; index++) {
            Task task=new Task(runCallBack);
            stressUtil.submit(task);
        }
        after(totalTimes);
    }
    
    
    private void after(long totalTimes)
    {
        stressUtil.countStatData(totalTimes,new HandleResultCallBack() {
            @Override
            public ExcuteResult handle(ExcuteResult result) {
                return handleResult(result);
            }
        });
        stressUtil.destory();
        afterEvent();
    }
    
    protected abstract void afterEvent();
    
    protected abstract ExcuteResult handleResult(ExcuteResult result);
}
