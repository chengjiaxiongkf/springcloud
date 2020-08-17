package com.user.common;

import com.TestUserMain;
import com.common.pojo.user.UserInfoPojo;
import com.user.service.UserInfoService;
import org.apache.commons.collections.MapUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author chengjiaxiong
 * @Date 2020/8/17 10:27
 */
@SpringBootTest(classes = TestUserMain.class)
@RunWith(SpringRunner.class)
public class TestCommon {
    @Autowired
    private UserInfoService userInfoService;

    public static void setMap(Map map){
        map.put("a","1");
    }

    interface AddNum {
        int add(int a,int b);
    }

    @Test
    public void add(){
        AddNum addNum = (a,b) -> 1+2;
        System.out.println(addNum.toString());
    }

    @Test
    public void getCommon(){
        Map map = new HashMap();
        List list = new ArrayList();
        System.out.println(MapUtils.getString(map,"a"));
    }

    @Ignore("not ready yet")
    @Test
    public void getUserInfo(){
        System.out.println(userInfoService.getUserInfo(new UserInfoPojo()));
    }

    @Test
    public void ThreadTest(){
        System.out.println("123");
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        for (int a=0;a<100;a++){
            final int num = a;
            threadPoolExecutor.execute(() -> {
                System.out.println(System.currentTimeMillis()+""+Thread.currentThread().getName()+".正在运行"+num);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
