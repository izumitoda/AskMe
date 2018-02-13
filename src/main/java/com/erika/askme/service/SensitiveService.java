package com.erika.askme.service;


import org.apache.commons.lang.CharUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-13 14:20
 **/
@Service
public class SensitiveService implements InitializingBean {
    private TrieNode rootNode = new TrieNode();
    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);
    //这是InitializingBean中的函数，表示在属性等设置完之后，调用这个函数，来实现一些初始化的操作

    public void addword(String word) {
        TrieNode tmp = rootNode;
        for (int i = 0; i < word.length(); i++) {
            if (isSymbol(word.charAt(i)))
                continue;
            if (tmp.getTrieNode(word.charAt(i)) != null) ;
            else {
                TrieNode tt = new TrieNode(word.charAt(i));
                tmp.addTrieNode(tt);
            }
            tmp = tmp.getTrieNode(word.charAt(i));
            if (i == word.length() - 1) {
                tmp.setend(true);
            }
        }
    }

    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
                || ub == Character.UnicodeBlock.VERTICAL_FORMS)
            return true;
        // 0x2E80-0x9FFF 东亚文字范围
        return Character.isSpaceChar(c)||(!CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF));
    }

    public String filterword(String content) {
        TrieNode root = rootNode;
        TrieNode tmp = root;
        StringBuilder result = new StringBuilder();
        int head = 0;
        int current = 0;
        while (head < content.length()) {
            Character c = content.charAt(head);
            tmp = root;
            if (isSymbol(c)) {
                head++;
                current++;
                result.append(c);
                continue;
            }
            boolean flag = false;
            while (current < content.length() && tmp.getTrieNode(c) != null) {
                if (tmp.getTrieNode(c).getend()) {
                    result.append("**");
                    flag = true;
                    break;
                }
                current++;
                tmp = tmp.getTrieNode(c);
                while(current<content.length() && isSymbol(content.charAt(current))) {
                    current++;
                    continue;
                }
                c = content.charAt(current);

            }
            if (flag) {
                current++;
                head = current;
            } else {
                result.append(content.charAt(head));
                head++;
                current = head;
            }
        }
        return result.toString();
    }

    private class TrieNode {
        private boolean end = false;
        private Map<Character, TrieNode> map = new HashMap<Character, TrieNode>();
        private Character c;

        TrieNode() {
        }

        TrieNode(Character ch) {
            c = ch;
        }

        public Character getch() {
            return c;
        }

        public void setend(boolean a) {
            this.end = a;
        }

        public boolean getend() {
            return end;
        }

        public void addTrieNode(TrieNode tn) {
            map.put(tn.getch(), tn);
        }

        public TrieNode getTrieNode(Character key) {
            return map.get(key);
        }

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rootNode = new TrieNode();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\Users\\erika\\Videos\\offer\\askme\\src\\main\\resources\\SensitiveWords.txt")),
                    "UTF-8"));
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {
                addword(lineTxt.trim());

                System.out.println(lineTxt);
            }
            br.close();
        } catch (Exception e) {
            logger.error("读取文件失败" + e);
            System.out.println("111");
        }

    }


}
