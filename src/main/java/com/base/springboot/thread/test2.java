package com.base.springboot.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zc
 * @version 1.0.0
 * @ClassName test2.java
 * @Description TODO
 * @createTime 2019/10/25/ 22:16:00
 */
public class test2 {
    public static void main(String[] args) {
        //获取本机线程数
        Integer threadNum=Runtime.getRuntime().availableProcessors();
        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        //开始
        Long startTime=System.currentTimeMillis();
        //声明一个Future集合
        List<Future<Integer>> futureList=new ArrayList<Future<Integer>>();
        for(int i = 1;i<=10;i++ ){
            //将执行结果装到Future集合
            futureList.add(executor.submit(new countTask((i-1)*10,(i-1)*10+10)));
        }

        int sum=0;
        //遍历Future集合
        for (Future<Integer> future: futureList) {
            try {
                //依次取出结果,就算后面的任务结果已经完成了,也要排队取出
                Integer futureVal=future.get();
                System.out.println(futureVal);
                sum+=futureVal;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

          //结束
        Long endTime=System.currentTimeMillis();
        System.out.println("多线程合计为:"+sum+",耗时:"+(endTime-startTime));

    }
}
