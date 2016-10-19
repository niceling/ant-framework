package com.antteam.framework.test;

/**
 * ClassName: Result 
 * @Description: TODO
 * @author Mirai
 * @date 2016年10月19日
 */
public class ExcuteResult {
    /**
     * 运行时间
     */
    private long runtime;

    /**
     * 是否成功
     */
    private boolean isSuccess;
    
    private Object data;
    
    public boolean isSuccess()
    {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess)
    {
        this.isSuccess = isSuccess;
    }

    public long getRuntime()
    {
        return runtime;
    }

    public void setRuntime(long runtime)
    {
        this.runtime = runtime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
