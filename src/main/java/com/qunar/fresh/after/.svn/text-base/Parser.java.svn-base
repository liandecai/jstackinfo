package com.qunar.fresh.after;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.google.common.io.Resources;
import com.qunar.fresh.bean.QThread;

/**   
 * @author liandecai
 * @time Mar 5, 2014
 */
public class Parser {
    
    private LineProcessor<Multimap<String, QThread>> processor; 
    
    private Parser(LineProcessor<Multimap<String, QThread>> processor) {
        this.processor = processor;
    }
    
    public static Parser create(LineProcessor<Multimap<String, QThread>> processor) {
        return new Parser(processor);
    }
    
    public Multimap<String, QThread> parser(String filename) throws IOException {
        Preconditions.checkNotNull(filename);
        String filePath = Resources.getResource(filename).getPath();
        return Files.readLines(new File(filePath), Charsets.UTF_8, processor); 
    }
}

