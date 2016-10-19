package com.antteam.framework.test;

import java.util.concurrent.Callable;



/**
 * ClassName: Task 
 * @Description: TODO
 * @author Mirai
 * @date 2016年10月19日
 */
public class Task implements Callable<ExcuteResult>{

    private RunnerCallBack runCallBack;
    
    public Task(RunnerCallBack runCallBack){
       this.runCallBack=runCallBack;
    }
    public ExcuteResult call() throws Exception {
        ExcuteResult result=new ExcuteResult();
        Long beginTime=System.currentTimeMillis();
        Object data=runCallBack.callback();
        Long endTime=System.currentTimeMillis();
        result.setData(data);
        result.setRuntime(endTime-beginTime); 
        return result;
    }

}
