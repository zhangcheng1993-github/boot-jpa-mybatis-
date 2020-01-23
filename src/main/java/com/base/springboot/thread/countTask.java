package com.base.springboot.thread;

import java.util.concurrent.Callable;

/**
 * @author zc
 * @version 1.0.0
 * @ClassName countTask.java
 * @Description TODO
 * @createTime 2019/10/25/ 21:24:00
 */
public class countTask implements Callable<Integer> {

    private Integer startNum;

    private Integer endNum;

    public countTask(Integer startNum, Integer endNum) {
        this.startNum = startNum;
        this.endNum = endNum;
    }



    @Override
    public Integer call() throws Exception {
        Integer count=0;
        for (int i=startNum;i<=endNum;i++){
            count+=i;
        }

        System.out.println("当前线程计算:"+startNum+"到"+endNum+"的数据");
        return count;
    }
}
