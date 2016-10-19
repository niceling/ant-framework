package com.antteam.test.framework.demo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.antteam.framework.test.RunnerCallBack;
import com.antteam.test.framework.demo.constants.EnvironmentConstans;

/**
 * ClassName: IndexStressTest2
 * 
 * @Description: TODO
 * @author Mirai
 * @date 2016年10月18日
 */
public class IndexControllerStressTest extends ControllerStressBaseTest {

    @Test
    public void test() {
        setHost(EnvironmentConstans.GetHost(EnvironmentConstans.DEV));
        submitTestTask(10, 100, new RunnerCallBack() {
            @Override
            public Object callback(){
                try {
                    Map<String, String> paramsMap = new HashMap<>();
                    paramsMap.put("id", String.valueOf(633576461689819136L));
                    String data = postRequest(false, "/v1/test", paramsMap);
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
    
    
    @Test
    public void test2() {
        setHost(EnvironmentConstans.GetHost(EnvironmentConstans.DEV));
        submitTestTask(10, 100, new RunnerCallBack() {
            @Override
            public Object callback(){
                try {
                    Map<String, String> paramsMap = new HashMap<>();
                    paramsMap.put("id", String.valueOf(633576461689819136L));
                    String data = postRequest(false, "/v1/test", paramsMap);
                    return data;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

}
