package com.qunar.fresh.after;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.LineProcessor;
import com.qunar.fresh.bean.QThread;

/**   
 * @author liandecai
 * @time Mar 5, 2014
 */
public class ThreadLineProcessor implements LineProcessor<Multimap<String, QThread>>{
    
    public final static String STATE_KEY_WORD = "java.lang.Thread.State: ";
    public final static String WAIT_CONDITION_KEY_WORD = "waiting on condition";
    public final static String TID_KEY_WORD = "tid";
    
    Multimap<String, QThread> multimap = HashMultimap.create();
    QThread nowThread = null;
    @Override
    public boolean processLine(String line) throws IOException {
        line = line.trim();
        if(line.startsWith("\"")) {
            if(nowThread != null && !Strings.isNullOrEmpty(nowThread.getWaitOnConditonId())) {
                multimap.put(nowThread.getWaitOnConditonId(), nowThread);
            }
            nowThread = new QThread();
            paserThreadInfo(line);
        }
        if(line.startsWith(STATE_KEY_WORD)) {
            paserStatue(line);
        }
        return true;
    }
 // 解析含有进程状态的字符串
    private void paserStatue(String line) {
        Preconditions.checkNotNull(line);
        Preconditions.checkNotNull(nowThread);
        if (!line.startsWith(STATE_KEY_WORD))
            return;
        nowThread.setStatue(line.substring(STATE_KEY_WORD.length()));
    }

    // 解析含有进程id name的字符串
    private void paserThreadInfo(String line) {
        Preconditions.checkNotNull(line);
        List<String> ls = Lists.newArrayList(Splitter.on(" ").split(line));

        String name = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
        nowThread.setName(name);

        if (line.contains(WAIT_CONDITION_KEY_WORD)) {
            String waitOnConditonId = ls.get(ls.size() - 1).trim();
            if(!Strings.isNullOrEmpty(waitOnConditonId)) {
                nowThread.setWaitOnConditonId(waitOnConditonId.substring(1, waitOnConditonId.length() - 1));
            }
        }
        
        for (String str : ls) {
            if (str.startsWith(TID_KEY_WORD)) {
                nowThread.setId(str.substring(TID_KEY_WORD.length() + 1));
            }
        }
    }

    @Override
    public Multimap<String, QThread> getResult() {
        
        return multimap;
    }

}

