## day09

### Handler

接受子线程发送的数据, 更新主线程UI。使用时继承handler并实现handleMessage方法。Handler的处理过程运行在创建Handler的线程里。

### Message

通过Handler对象的obtainMessage()获取一个Message实例。尽量少new节省资源。

### MessageQueue

消息队列，用来存放Handler发送过来的消息，等待Looper的抽取。

### Looper

消息泵，从MessageQueue中抽取Message执行。在非ui线程里，如果创建Handler时不传入Looper对象，这个Handler将不能接收处理消息。创建looper方式：Looper.prepare();进行准备工作，最后执行 Looper.loop();

### AsyncTask

AsyncTask主要用来更新UI线程，不需要子线程和Handler就可以完成异步操作并且刷新用户界面。

![image-20200515191334112](C:\Users\uncle drew\AppData\Roaming\Typora\typora-user-images\image-20200515191334112.png)asyntask线程模型图

其中，Task的实例必须在UI 线程中创建， execute方法必须在UI 线程中调用。创建一个task只能被执行一次，多次调用时将会出现异常 。

