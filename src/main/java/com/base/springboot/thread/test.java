package com.base.springboot.thread;

import java.util.concurrent.*;

/**
 * @author zc
 * @version 1.0.0
 * @ClassName test.java
 * @Description TODO
 * @createTime 2019/10/25/ 21:29:00
 */
public class test {

    public static void main(String[] args) {

        //获取本机线程数
        Integer threadNum=Runtime.getRuntime().availableProcessors();
        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);
        //创建CompletionService多线程服务
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
        //开始
        Long startTime=System.currentTimeMillis();
        for(int i = 1;i<=10;i++ ){
            //传入Callable,执行多线程任务
            completionService.submit(new countTask((i-1)*10,(i-1)*10+10));
        }

        int sum=0;
        for(int i = 1;i<=10;i++ ){
            try {
                //非阻塞队列,那个任务先完成,就取出那个任务结果。
                Future<Integer> future =completionService.take();
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


        Integer count=0;
        //开始(单线程)
        Long singleStartTime=System.currentTimeMillis();
        for (int k=1;k<=100;k++){
            try {
                count+=k;
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //结束(单线程)
        Long singleEndTime=System.currentTimeMillis();
        System.out.println("单线程合计为:"+sum+",耗时:"+(singleEndTime-singleStartTime));
    }
}
