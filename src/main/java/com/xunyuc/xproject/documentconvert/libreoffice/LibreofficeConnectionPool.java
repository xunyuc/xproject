package com.xunyuc.xproject.documentconvert.libreoffice;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Xunyuc on 2017/6/18.
 */
public class LibreofficeConnectionPool implements Closeable {

    private String ip;
    private int port;
    private GenericObjectPool<OpenOfficeConnection> internalPool;

    public LibreofficeConnectionPool(String ip, int port) {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        PooledObjectFactory<OpenOfficeConnection> factory = new LibreofficeFactory(ip, port);
        if (this.internalPool != null) {
            try {
                closeInternalPool();
            } catch (Exception e) {
            }
        }

        this.internalPool = new GenericObjectPool<OpenOfficeConnection>(factory, poolConfig);
    }

    protected void closeInternalPool() {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw new RuntimeException("Could not destroy the pool", e);
        }
    }


    public boolean isClosed() {
        return this.internalPool.isClosed();
    }


    public OpenOfficeConnection getConnection() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("Could not get a resource from the pool", e);
        }
    }

    public void returnConnection(final OpenOfficeConnection resource) {
        if (resource == null) {
            return;
        }
        try {
            internalPool.returnObject(resource);
        } catch (Exception e) {
            throw new RuntimeException("Could not return the resource to the pool", e);
        }
    }

    public void close() throws IOException {
        closeInternalPool();
    }
}
