package com.qunar.fresh.parser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.qunar.fresh.bean.QThread;

/**
 * @author liandecai
 * @time Mar 4, 2014
 */
public class ThreadCenter {
    private String jStackFile;
    private List<String> lines;
    private List<QThread> qThreads = Lists.newArrayList();
    private Multimap<String, QThread> multimap;

    public final static String STATE_KEY_WORD = "java.lang.Thread.State: ";
    public final static String WAIT_CONDITION_KEY_WORD = "waiting on condition";
    public final static String TID_KEY_WORD = "tid";

    public ThreadCenter(String jStackFileName) throws IOException {
        this.jStackFile = jStackFileName;
        qThreads = Lists.newArrayList();
        multimap = HashMultimap.create();
        getThreadLine();
        parser();
    }

    private void getThreadLine() throws IOException {
        ThreadLineProcessor threadLineProcessor = new ThreadLineProcessor();
        String filePath = Resources.getResource(jStackFile).getPath();
        lines = Files.readLines(new File(filePath), Charsets.UTF_8, threadLineProcessor);
    }

    private void parser() {

        Iterator<String> it = lines.iterator();
        String line1, line2;
        while (it.hasNext()) {
            line1 = it.next();
            QThread qThread = new QThread();
            // 根据条件判断下一行是否有进程状态
            if (line1.startsWith("\"") && line1.endsWith("]")) {
                line2 = it.next();
                paserThreadInfo(line1, qThread);
                paserStatue(line2, qThread);
            } else {
                paserThreadInfo(line1, qThread);
            }
            qThreads.add(qThread);
            // 添加waicondition进程信息
            if (qThread.getWaitOnConditonId() != null && !"".equals(qThread.getWaitOnConditonId())) {
                multimap.put(qThread.getWaitOnConditonId(), qThread);
            }
        }
    }

    // 解析含有进程状态的字符串
    private void paserStatue(String line, QThread qThread) {
        Preconditions.checkNotNull(line);
        Preconditions.checkNotNull(qThread);
        if (!line.startsWith(STATE_KEY_WORD))
            return;
        qThread.setStatue(line.substring(STATE_KEY_WORD.length()));
    }

    // 解析含有进程id name的字符串
    private void paserThreadInfo(String line, QThread qThread) {
        Preconditions.checkNotNull(line);
        Preconditions.checkNotNull(qThread);

        List<String> ls = Lists.newArrayList(Splitter.on(" ").split(line));

        String name = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
        qThread.setName(name);

        if (line.contains(WAIT_CONDITION_KEY_WORD)) {
            String waitOnConditonId = ls.get(ls.size() - 1);
            qThread.setWaitOnConditonId(waitOnConditonId.substring(1, waitOnConditonId.length() - 1));
        }

        for (String str : ls) {
            if (str.startsWith(TID_KEY_WORD)) {
                qThread.setId(str.substring(TID_KEY_WORD.length() + 1));
            }
        }
    }

    // 按wait的id的个数排序
    private List<Entry<String, Collection<QThread>>> sortByWaitCount() {
        List<Entry<String, Collection<QThread>>> counter = Lists.newArrayList(multimap.asMap().entrySet());
        Collections.sort(counter, new Comparator<Entry<String, Collection<QThread>>>() {
            @Override
            public int compare(Entry<String, Collection<QThread>> o1, Entry<String, Collection<QThread>> o2) {
                return o2.getValue().size() - o1.getValue().size();
            }
        });
        return counter;
    }

    // 返回格式化数据
    public String getQThreadSortedString() {
        List<Entry<String, Collection<QThread>>> counter = sortByWaitCount();
        StringBuffer sb = new StringBuffer();
        for (Entry<String, Collection<QThread>> entry : counter) {
            sb.append("condition id:" + entry.getKey() + ",count:" + entry.getValue().size() + ":");
            sb.append(System.lineSeparator());
            for (QThread qThread : entry.getValue()) {
                sb.append(qThread.toString());
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    public void writeQThreadSortedToFile(String filePath) throws IOException {
        Preconditions.checkNotNull(filePath);
        String content = getQThreadSortedString();
        Files.write(content, new File(filePath), Charsets.UTF_8);
    }
}
