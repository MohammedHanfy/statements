package com.mah.statements.wrappers;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ServletInputStreamWrapper extends ServletInputStream {

    private final InputStream inputStream;

    public ServletInputStreamWrapper(byte[] body) {

        this.inputStream = new ByteArrayInputStream(body);
    }

    @Override
    public boolean isFinished() {

        try {

            return inputStream.available() == 0;

        } catch (Exception exception) {

            return false;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {

        // Not used
    }

    @Override
    public int read() throws IOException {
        return this.inputStream.read();
    }
}
