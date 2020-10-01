package org.example.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequestWrapper extends HttpServletRequestWrapper {

    private HttpServletRequest original;
    private byte[] reqBytes;
    private boolean firstTime = true;

    public MyRequestWrapper(HttpServletRequest request) {
        super(request);
        original = request;
    }

    @Override
    public BufferedReader getReader() throws IOException{

    	System.out.println("First time? "+firstTime);
        if(firstTime)
            firstTime();

        System.out.println("reqBytes: "+new String(reqBytes));
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(reqBytes));
        return new BufferedReader(isr);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        if(firstTime)
            firstTime();

        ServletInputStream sis = new ServletInputStream() {
            private int i;

            @Override
            public int read() throws IOException {
                byte b;
                if(reqBytes.length > i)
                    b = reqBytes[i++];
                else
                    b = -1;

                return b;
            }

			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isReady() {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// TODO Auto-generated method stub
				
			}
        };

        return sis;
    }

    private void firstTime() throws IOException{
        firstTime = false;
        String line = original.getReader().lines().collect(Collectors.joining(System.lineSeparator())).trim();
        reqBytes = line.getBytes();
    }
}