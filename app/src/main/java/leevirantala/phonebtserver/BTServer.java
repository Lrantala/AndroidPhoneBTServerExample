package leevirantala.phonebtserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by eewijet on 31.10.2017.
 */

public class BTServer implements Runnable {
    private static BluetoothAdapter bluetoothAdapter;
    private boolean running = true;
    
    private final UUID uuid = UUID.fromString("ee2133aa-b8a2-11e7-abc4-cec278b6b50a");
    private static String TAG = BTServer.class.getSimpleName();
    
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private DataInputStream dataInputStream;
    private OutputStream outputStream;
    private InputStream inputStream;
    private byte[] buffer = new byte[1024];
    private int bytesRead;
    private long fileSize;
    private ByteArrayOutputStream readResult;
    private int streamLength;
    private boolean reading;
    
    
    @Override
    public void run() {
        try {
            serverSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord("PulseServer", uuid);
            socket = serverSocket.accept();
            dataInputStream = (DataInputStream) socket.getInputStream();
            String fileName = dataInputStream.readUTF();
            fileSize = dataInputStream.readLong();
            outputStream = new FileOutputStream(fileName);
            while (fileSize > 0 && (bytesRead = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                outputStream.write(buffer, 0, bytesRead);
                fileSize -= bytesRead;
            }
            outputStream.close();
        } catch (IOException e) {
            Log.e(TAG, "Thread IOException error", e);
        }
    }
    
    public void startReading(){
        Log.d(TAG, "startReading");
        reading = true;
    }
    
    public void stopReading(){
        Log.d(TAG, "stopReading");
        reading = false;
    }
}
