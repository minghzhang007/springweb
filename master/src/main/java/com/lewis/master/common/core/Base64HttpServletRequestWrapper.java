package com.lewis.master.common.core;


import org.apache.commons.codec.binary.Base64;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by zhangminghua on 2017/2/20.
 */
public class Base64HttpServletRequestWrapper extends HttpServletRequestWrapper {

    private boolean firstTime = true;

    private byte[] bytes;

    private static final String encoding ="utf-8";
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public Base64HttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            request.setCharacterEncoding(encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getQueryString() {
        try {
            String str = super.getQueryString();
            String queryString = null;
            if(null != str) {
                //queryString = URLDecoder.decode(str,"UTF-8");
                queryString = URLdecode(str,encoding);
            }
            if (queryString != null) {
                if (Base64.isBase64(queryString)) {
                    return new String(Base64.decodeBase64(queryString.getBytes(encoding)), encoding);
                } else {
                    return queryString;
                }
            }
            return null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
    }

    private void firstTime() throws IOException {
        firstTime = false;
        BufferedReader reader = super.getReader();
        StringBuilder sb = new StringBuilder();
        String line ;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        byte[] orignalBytes = sb.toString().getBytes(encoding);
        if (Base64.isBase64(orignalBytes)) {
            bytes =Base64.decodeBase64(orignalBytes);
        }else{
            bytes = orignalBytes;
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (firstTime) {
            firstTime();
        }
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(bytes), encoding);
        return new BufferedReader(isr);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (firstTime) {
            firstTime();
        }
        ServletInputStream sis =new ServletInputStream(){
            private int i;
            @Override
            public int read() throws IOException {
                byte b;
                if (bytes.length > i)
                    b = bytes[i++];
                else
                    b = -1;

                return b;
            }
        };
        return sis;
    }

    /**
     * 请求串的url安全处理 Base64编码一定是要URL安全的--(不能包含 + 和 / 若包含把 + 替换为 - ,把 / 替换为 _ )
     * 实现代码雷同于 URLDecoder.decode
     * @param s
     * @param enc
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String URLdecode(String s, String enc)
            throws UnsupportedEncodingException{

        boolean needToChange = false;
        int numChars = s.length();
        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
        int i = 0;

        if (enc.length() == 0) {
            throw new UnsupportedEncodingException ("URLDecoder: empty string enc parameter");
        }

        char c;
        byte[] bytes = null;
        while (i < numChars) {
            c = s.charAt(i);
            switch (c) {
                case '+':
                    sb.append('-');
                    i++;
                    needToChange = true;
                    break;
                case '/':
                    sb.append('_');
                    i++;
                    needToChange = true;
                    break;
                case '%':
		/*
		 * Starting with this instance of %, process all
		 * consecutive substrings of the form %xy. Each
		 * substring %xy will yield a byte. Convert all
		 * consecutive  bytes obtained this way to whatever
		 * character(s) they represent in the provided
		 * encoding.
		 */

                    try {

                        // (numChars-i)/3 is an upper bound for the number
                        // of remaining bytes
                        if (bytes == null)
                            bytes = new byte[(numChars-i)/3];
                        int pos = 0;

                        while ( ((i+2) < numChars) &&
                                (c=='%')) {
                            bytes[pos++] =
                                    (byte)Integer.parseInt(s.substring(i+1,i+3),16);
                            i+= 3;
                            if (i < numChars)
                                c = s.charAt(i);
                        }

                        // A trailing, incomplete byte encoding such as
                        // "%x" will cause an exception to be thrown

                        if ((i < numChars) && (c=='%'))
                            throw new IllegalArgumentException(
                                    "URLDecoder: Incomplete trailing escape (%) pattern");

                        sb.append(new String(bytes, 0, pos, enc));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException(
                                "URLDecoder: Illegal hex characters in escape (%) pattern - "
                                        + e.getMessage());
                    }
                    needToChange = true;
                    break;
                default:
                    sb.append(c);
                    i++;
                    break;
            }
        }

        return (needToChange? sb.toString() : s);
    }
}
