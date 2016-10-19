package com.antteam.framework.test;

/**
 * ClassName: StatData 
 * @Description: 统计数据
 * @author Mirai
 * @date 2016年10月18日
 */
public class StatData {
    
    
    /**
     * 最小响应时间
     */
    private long requestMinTime = Long.MAX_VALUE;

    /**
     *  最大响应时间
     */
    private long requestMaxTime = 0L;

    /**
     * 总时间
     */
    private long totalTime;

    /**
     * 总次数
     */
    private long totalTimes;

    /**
     * 失败次数
     */
    private long failTimes=0L;

    /**
     * 线程数
     */
    private long threadNum;
    
    
    /**
     * 失败率
     * @return
     */
    public double getFailRate()
    {
        double dfailTimes = failTimes;
        double dtotalTimes = totalTimes;
        return dfailTimes / dtotalTimes;
    }

    /**
     * qps
     * @return
     */
    public double getQps()
    {
        if(totalTime==0){
            throw new RuntimeException("总用时为 0，qps无限大");
        }
        return (totalTimes * 1000) / totalTime;
    }

    public long getRequestMinTime()
    {
        return requestMinTime;
    }

    public void setRequestMinTime(long requestMinTime)
    {
        this.requestMinTime = requestMinTime;
    }

    public long getRequestMaxTime()
    {
        return requestMaxTime;
    }

    public void setRequestMaxTime(long requestMaxTime)
    {
        this.requestMaxTime = requestMaxTime;
    }

    public long getRequestAvgTime()
    {
        long avgTime =this.totalTime/totalTimes;
        return avgTime;
    }

    public long getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(long totalTime)
    {
        this.totalTime = totalTime;
    }

    public long getTotalTimes()
    {
        return totalTimes;
    }

    public void setTotalTimes(long totalTimes)
    {
        this.totalTimes = totalTimes;
    }

    public long getFailTimes()
    {
        return failTimes;
    }

    public void setFailTimes(long failTimes)
    {
        this.failTimes = failTimes;
    }

    public long getThreadNum()
    {
        return threadNum;
    }

    public void setThreadNum(long threadNum)
    {
        this.threadNum = threadNum;
    }

    @Override
    public String toString()
    {
        return "线程数：" + threadNum + " 总次数：" + totalTimes + " 总时间：" + totalTime + " 平均响应时间：" + getRequestAvgTime() + " 最小响应时间：" + requestMinTime
                + " 最大响应时间：" + requestMaxTime + " 失败次数：" + failTimes + " 失败率：" + getFailRate() + " qps:" + getQps();
    }
    
}
