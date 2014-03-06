jstack 命令 jstack <op> <pin> 
pin为java进程

给出jstack文件 解析出线程名，线程id，线程状态，以及哪些线程wait在同一个condition上 。就是说匹配“waiting on condition”的线程进行统计。

输出结果按照等待同一个condition的线程数从大到小排序。

输出格式如下：
condition id,count:
线程id|线程名|线程状态
线程id|线程名|线程状态
线程id|线程名|线程状态
condition id,count:
线程id|线程名|线程状态
线程id|线程名|线程状态
