package com.atxbai.online.common.textUtils;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.*;

/**
 * @author 小白
 * @version 1.0
 * @create: 2024-01-15 08:22
 * @content: 用于过滤富文本
 */
public class HtmlFilterHelper extends HTMLEditorKit.ParserCallback{
    private StringBuffer buff;

    private static HtmlFilterHelper htmlFilterHelper = new HtmlFilterHelper();

    private void parse(String str) throws IOException {
        InputStream iin = new ByteArrayInputStream(str.getBytes());
        Reader in = new InputStreamReader(iin);
        buff = new StringBuffer();
        ParserDelegator delegator = new ParserDelegator();
        delegator.parse(in, this, Boolean.TRUE);
        iin.close();
        in.close();
    }

    @Override
    public void handleText(char[] text, int pos) {
        buff.append(text);
    }

    private String getText() {
        return buff.toString();
    }


    /**
     * 改用该方法，过滤富文本
     * @param str
     * @return
     */
    public static String getContent(String str) {
        try {
            htmlFilterHelper.parse(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return htmlFilterHelper.getText();
    }

}
