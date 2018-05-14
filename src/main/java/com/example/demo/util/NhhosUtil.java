package com.example.demo.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class NhhosUtil {

    private static final Logger logger = LoggerFactory.getLogger(NhhosUtil.class);

    @Value("${serviceDesk.url}")
    private String nhhosUrl;

    private Map<String, HttpInvokerProxyFactoryBean> httpInvokerProxyFactoryBeans = new HashMap<>();

    private SystemBookProxy getRemoteSystemBookProxy(String systemBookCode) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("centerId", systemBookCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<Object>(headers);
        try {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseEntity = restTemplate.exchange(nhhosUrl
                    + "api/systemBookProxy/get/{centerId}", org.springframework.http.HttpMethod.GET, requestEntity, String.class, map);
            if (responseEntity.getBody() == null) {
                logger.info(systemBookCode);
                return null;
            }
            String result = new String(responseEntity.getBody().getBytes("ISO-8859-1"), "utf-8");
            Gson gson = new Gson();
            SystemBookProxy systemBookProxy = gson.fromJson(result, SystemBookProxy.class);
            return systemBookProxy;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public String getUrl(String systemBookCode) {
       /* SystemBookProxy systemBookProxy = getRemoteSystemBookProxy(systemBookCode);
        if (systemBookProxy == null) {
            return null;
        }
        String url = systemBookProxy.getUrl();
        String serviceUrl = url.replace("pos3Server", "amazonCenter") + "/remoting/";
        return serviceUrl;*/
        return "http://192.168.0.125:55121/amazonCenter/remoting/";
    }

    public  <T> T createCenterObject(Class<T> cls, String serviceUrl) {
        HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean = httpInvokerProxyFactoryBeans.get(cls.getSimpleName());
        if (httpInvokerProxyFactoryBean == null) {
            httpInvokerProxyFactoryBean = new HttpInvokerProxyFactoryBean();
            httpInvokerProxyFactoryBean.setServiceInterface(cls);
            String className = cls.getSimpleName();
            String pre = className.substring(0, 1);
            String last = className.substring(1);
            httpInvokerProxyFactoryBean.setServiceUrl(serviceUrl + pre.toLowerCase() + last);
            httpInvokerProxyFactoryBean.afterPropertiesSet();
            httpInvokerProxyFactoryBeans.put(cls.getSimpleName(), httpInvokerProxyFactoryBean);
        }
        return (T) httpInvokerProxyFactoryBean.getObject();
    }





}