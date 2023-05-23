package com.tiktok.bean;

/**
 *@functon 多线程学习
 *@author 林炳文
 *@time 2015.3.9
 */
class Thread2 implements Runnable{
    private String name;

    public Thread2(String name) {
        this.name=name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(name + "运行  :  " + i);
            try {
                Thread.sleep((int) Math.random() * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
public class Main {

    public static void main(String[] args) {
        new Thread(new Thread2("C")).start();
        new Thread(new Thread2("D")).start();
    }
}
/*
输出：
C运行  :  0
D运行  :  0
D运行  :  1
C运行  :  1
D运行  :  2
C运行  :  2
D运行  :  3
C运行  :  3
D运行  :  4
C运行  :  4

实际上所有的多线程代码都是通过运行Thread的start()方法来运行的。
因此，不管是扩展Thread类还是实现Runnable接口来实现多线程，最终还是通过Thread的对象的API来控制线程的，熟悉Thread类的API是进行多线程编程的基础。
 */