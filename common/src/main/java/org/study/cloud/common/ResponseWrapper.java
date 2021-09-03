package org.study.cloud.common;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * @desc: TODO
 * @author: lipo
 * @date: 2019-09-25 15:38
 * @version: v1.0
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
    private ServletOutputStream outputStream = null;
    private ByteArrayOutputStream baos = null;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        baos = new ByteArrayOutputStream();
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void responseMessage(String result) {
        try {
            PrintWriter out = getWriterForResponse();
            out.print(result);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (outputStream == null) {
            outputStream = new ServletOutputStream() {
                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener listener) {

                }

                @Override
                public void write(int b) throws IOException {
                    baos.write(b);
                }
            };
        }
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    baos.write(b);
                }
            });
    }

    public PrintWriter getWriterForResponse() throws IOException {
        return new PrintWriter(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        });
    }

    public void closeWriter(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public String getResponseContent() {
        return new String(baos.toByteArray());
    }
}
