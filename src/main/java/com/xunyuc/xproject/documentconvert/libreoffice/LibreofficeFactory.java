package com.xunyuc.xproject.documentconvert.libreoffice;

import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.net.ConnectException;

/**
 * Created by Xunyuc on 2017/6/18.
 */
public class LibreofficeFactory implements PooledObjectFactory<OpenOfficeConnection> {

    private String ip = "localhost";
    private int port = 8100;

    public LibreofficeFactory(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public LibreofficeFactory(int port) {
        this.port = port;
    }

    /**
     * 这个方法是用来创建一个对象，
     * 当在GenericObjectPool类中调用borrowObject方法时，如果当前对象池中没有空闲的对象，
     * GenericObjectPool会调用这个方法，创建一个对象，并把这个对象封装到PooledObject类中，并交给对象池管理。
     * @return
     * @throws Exception
     */
    public PooledObject<OpenOfficeConnection> makeObject() throws Exception {
        OpenOfficeConnection connection = new SocketOpenOfficeConnection(ip, port);
        try {
            connection.connect();
        } catch (ConnectException e) {
            throw new RuntimeException("Could not connect to libreoffice, libreoffice service may not start", e);
        }
        return new DefaultPooledObject<OpenOfficeConnection>(connection);
    }

    /**
     * 销毁对象，
     * 当对象池检测到某个对象的空闲时间(idle)超时，或使用完对象归还到对象池之前被检测到对象已经无效时，就会调用这个方法销毁对象。
     * 对象的销毁一般和业务相关，但必须明确的是，当调用这个方法之后，对象的生命周期必须结果。
     * 如果是对象是线程，线程必须已结束，如果是socket，socket必须已close，如果是文件操作，文件数据必须已flush，且文件正常关闭。
     * @param pooledObject
     * @throws Exception
     */
    public void destroyObject(PooledObject<OpenOfficeConnection> pooledObject) throws Exception {
        OpenOfficeConnection connection = pooledObject.getObject();
        if (connection.isConnected()) {
            connection.disconnect();
        }
    }

    /**
     * 检测一个对象是否有效。
     * 在对象池中的对象必须是有效的，这个有效的概念是，从对象池中拿出的对象是可用的。
     * 比如，如果是socket,那么必须保证socket是连接可用的。
     * 在从对象池获取对象或归还对象到对象池时，会调用这个方法，判断对象是否有效，如果无效就会销毁。
     * @param pooledObject
     * @throws Exception
     */
    public boolean validateObject(PooledObject<OpenOfficeConnection> pooledObject) {
        OpenOfficeConnection connection = pooledObject.getObject();
        return connection.isConnected();
    }

    /**
     * 激活一个对象或者说启动对象的某些操作。
     * 比如，如果对象是socket，如果socket没有连接，或意外断开了，可以在这里启动socket的连接。
     * 它会在检测空闲对象的时候，如果设置了测试空闲对象是否可以用，就会调用这个方法，在borrowObject的时候也会调用。
     * 另外，如果对象是一个包含参数的对象，可以在这里进行初始化。让使用者感觉这是一个新创建的对象一样。
     * @param pooledObject
     * @throws Exception
     */
    public void activateObject(PooledObject<OpenOfficeConnection> pooledObject) throws Exception {

    }

    /**
     * 钝化一个对象。在向对象池归还一个对象是会调用这个方法。
     * 这里可以对对象做一些清理操作。比如清理掉过期的数据，下次获得对象时，不受旧数据的影响。
     * @param pooledObject
     * @throws Exception
     */
    public void passivateObject(PooledObject<OpenOfficeConnection> pooledObject) throws Exception {

    }
}
