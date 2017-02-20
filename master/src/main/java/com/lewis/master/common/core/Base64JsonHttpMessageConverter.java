package com.lewis.master.common.core;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

/**
 * Created by zhangminghua on 2017/2/20.
 */
public class Base64JsonHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public Base64JsonHttpMessageConverter(){
        super();
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        byte[] bytes = getObjectMapper().writeValueAsBytes(object);
        FileCopyUtils.copy(Base64.encodeBase64(bytes),outputMessage.getBody());
    }
}
