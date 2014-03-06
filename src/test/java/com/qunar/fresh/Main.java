package com.qunar.fresh;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import javax.sound.sampled.Line;

import com.google.common.base.Strings;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import com.qunar.fresh.after.Parser;
import com.qunar.fresh.after.ThreadLineProcessor;
import com.qunar.fresh.after.ThreadPrinter;
import com.qunar.fresh.bean.QThread;
import com.qunar.fresh.parser.ThreadCenter;

/**
 * Unit test for simple App.
 */
public class Main {
    public static void main(String[] args) throws IOException {
//        for(Entry<String, Collection<QThread>> ls :Parser.create(new ThreadLineProcessor()).parser("j.stack").asMap().entrySet()) {
//            System.out.println(ls.getKey() + " : " + ls.getValue().size());
//        }
//        ThreadPrinter.create().writeSortedInfoToFile("aa");
        ThreadPrinter.create(Parser.create(new ThreadLineProcessor()).parser("j.stack")).writeSortedInfoToFile("aa");
    }
    
}
