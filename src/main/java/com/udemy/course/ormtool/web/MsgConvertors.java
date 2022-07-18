package com.udemy.course.ormtool.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Consumes("application/json")
@Produces("application/json")
public class MsgConvertors<T> implements MessageBodyReader<T>, MessageBodyWriter<T> {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper;

    public MsgConvertors() {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Override
    public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public T readFrom(Class<T> aClass, Type type, Annotation[] annotations, MediaType mediaType,
                      MultivaluedMap<String, String> multivaluedMap, InputStream inputStream) throws IOException, WebApplicationException {

        int dataLength = Integer.parseInt( multivaluedMap.get(HttpHeaders.CONTENT_LENGTH).get(0));

        byte[] data = readNetworkData(inputStream, dataLength);

        return objectMapper.readValue(data, aClass);
    }

    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(T t, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> multivaluedMap, OutputStream outputStream) throws IOException, WebApplicationException {

        byte[] data = objectMapper.writeValueAsBytes(t);

        multivaluedMap.put(HttpHeaders.CONTENT_LENGTH, List.of(String.valueOf(data.length)));

        outputStream.write(data);
    }

    private byte[] readNetworkData(InputStream is, int dataLength) throws IOException {
        final byte[] buffer = new byte[dataLength];
        int totalReadSize = 0;

        while (totalReadSize < dataLength) {
            int readSize = is.read(buffer, totalReadSize, dataLength - totalReadSize);
            if (readSize < 0) {
                throw new IOException("Could not read network data, unexpected End of Data Stream");
            }
            totalReadSize += readSize;
        }
        return buffer;
    }
}
