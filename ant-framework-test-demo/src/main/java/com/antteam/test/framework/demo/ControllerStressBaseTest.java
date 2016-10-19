package com.antteam.test.framework.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.antteam.framework.test.ExcuteResult;
import com.antteam.framework.test.StressTestProxy;
import com.antteam.test.framework.demo.constants.EnvironmentConstans;
import com.antteam.test.framework.demo.utils.HttpClientUtil;

/**
 * ClassName: ControllerStressBaseTest 
 * @Description: TODO
 * @author Mirai
 * @date 2016年10月18日
 */
public class ControllerStressBaseTest extends StressTestProxy{

    String host=EnvironmentConstans.GetHost(EnvironmentConstans.TEST);
    String loginUrl=host+EnvironmentConstans.PROJECT_NAME+"/auth/getLoginToken";
    
    public String postRequest(boolean needLogin,String url,Map<String,String> params){
        try {
            String post_url=host+EnvironmentConstans.PROJECT_NAME+url;
            String token="";
            if(needLogin){
                String data=HttpClientUtil.getInstance().postHttpSinglePramas(loginUrl,"openid=o1cCewX6xBBOsay2yJZy0B2J7j4M");
                String dataStr=JSONObject.parseObject(data).getString("data");
                token=JSONObject.parseObject(dataStr).getString("token");
            }
            Map<String,String> paramsMap=new HashMap<>();
            paramsMap.put("channel", EnvironmentConstans.CHANNEL);
            paramsMap.put("client_id", EnvironmentConstans.CLIENT_ID);
            paramsMap.put("client_secret", EnvironmentConstans.CLIENT_SECRET);
            paramsMap.put("version", EnvironmentConstans.VERSION);
            if(StringUtils.isNotBlank(token)){
                //模拟cookie
                paramsMap.put("token", token);
            }
            if(params!=null && !params.isEmpty()){
                paramsMap.putAll(params);
            }
            String result=HttpClientUtil.getInstance().postHttpMultiPramas(post_url, paramsMap);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public void setHost(String host) {
        this.host = host;
        loginUrl=host+EnvironmentConstans.PROJECT_NAME+"/auth/getLoginToken";
    }


    @Override
    protected void afterEvent() {
      System.exit(0);
    }

    /**
     * @Description: 对结果的处理，如果不处理，返回result即可 
     * 
     * 
     * @param @param result
     * @param @return   
     * @throws
     * @author Mirai
     * @date 2016年10月19日
     */
    @Override
    protected ExcuteResult handleResult(ExcuteResult result) {
       String data=(String)result.getData();
       JSONObject obj=JSONObject.parseObject(data);
       if(Long.parseLong((String) obj.get("code"))==200){
           result.setSuccess(true);
       }else {
           System.out.println("失败结果："+data);
       }
       return result;
    }
}
