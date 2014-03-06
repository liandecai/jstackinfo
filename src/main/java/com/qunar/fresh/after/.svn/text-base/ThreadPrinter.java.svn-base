package com.qunar.fresh.after;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.base.Charsets;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;
import com.qunar.fresh.bean.QThread;

/**
 * @author liandecai
 * @time Mar 5, 2014
 */
public class ThreadPrinter {
    private Multimap<String, QThread> multimap;

    public static final Ordering<Entry<String, Collection<QThread>>> defaultOrdering = Ordering
            .from(new Comparator<Entry<String, Collection<QThread>>>() {
                @Override
                public int compare(Entry<String, Collection<QThread>> o1, Entry<String, Collection<QThread>> o2) {
                    return Ints.compare(o2.getValue().size(), o1.getValue().size());
                }
            });

    private Ordering<Entry<String, Collection<QThread>>> ordering = null;

    public static ThreadPrinter create(Multimap<String, QThread> multimap2) {
        return new ThreadPrinter(multimap2);
    }

    private ThreadPrinter(Multimap<String, QThread> multimap) {
        this.multimap = multimap;
    }

    public void setOrdering(Ordering<Entry<String, Collection<QThread>>> ordering) {
        this.ordering = ordering;
    }

    private List<Entry<String, Collection<QThread>>> sort(Iterable<Entry<String, Collection<QThread>>> it) {
        return ordering != null ? ordering.sortedCopy(it) : defaultOrdering.sortedCopy(it);
    }

    public String getSortFomatString() {
        StringBuilder sb = new StringBuilder();
        List<Entry<String, Collection<QThread>>> ls = sort(multimap.asMap().entrySet());
        for (Entry<String, Collection<QThread>> entry : ls) {
            sb.append(String.format("condition id:%s,count:%d:\n", entry.getKey(), entry.getValue().size()));
            for (QThread qThread : entry.getValue()) {
                sb.append(String.format("%s|%s|%s\n", qThread.getId(), qThread.getName(), qThread.getStatue()));
            }
        }
        return sb.toString();
    }

    public void writeSortedInfoToFile(String path) throws IOException {
        Files.write(getSortFomatString(), new File(path), Charsets.UTF_8);
    }
}
