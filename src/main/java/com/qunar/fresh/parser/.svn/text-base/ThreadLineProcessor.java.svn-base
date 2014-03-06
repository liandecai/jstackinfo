package com.qunar.fresh.parser;

import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.io.LineProcessor;

/**
 * @author liandecai
 * @time Mar 4, 2014
 */
public class ThreadLineProcessor implements LineProcessor<List<String>> {
    private final String EFFECTLINE_KEY_WORD = "java";
    private List<String> lines = Lists.newArrayList();

    @Override
    public boolean processLine(String line) throws IOException {
        // 过滤有效行
        line = line.trim();
        if (line.startsWith("\"") || line.startsWith(EFFECTLINE_KEY_WORD)) {
            lines.add(line);
        }
        return true;
    }

    @Override
    public List<String> getResult() {
        return lines;
    }
}
