package com.gec.system.util;



import java.util.concurrent.*;

public class ThreadPoolFactory  {

    public static ThreadPoolExecutor threadPoolFactory;

    //用于影视添加的标记
    public static int Size=0;
    static {
        //创建自定义线程池，参数：核心线程数5，最大线程数10，空闲时间20s，时间单位，阻塞队列，拒绝策略
        threadPoolFactory = new ThreadPoolExecutor(5,10,20, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(10),new ThreadPoolExecutor.AbortPolicy());
    }

    public static void setSize(){
        Size++;
        if (Size > 1000){
            Size = 0;
        }
    }
    public static int getUpSize(){
        return Size;
    }







}
