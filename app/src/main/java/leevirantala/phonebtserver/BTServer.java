package leevirantala.phonebtserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

/**
 * Created by eewijet on 31.10.2017.
 */

public class BTServer extends Thread implements Runnable {
    private static BluetoothAdapter bluetoothAdapter;
    private BTConnectionReader btConnectionReader = null;
    
    private final UUID uuid = UUID.fromString("ee2133aa-b8a2-11e7-abc4-cec278b6b50a");
    private static String TAG = BTServer.class.getSimpleName();
    
    private class BTConnectionReader implements Runnable {
        private BluetoothServerSocket serverSocket;
        private BluetoothSocket clientSocket;
        private DataInputStream dataInputStream;
        private OutputStream outputStream;
        private InputStream inputStream;
        private BufferedReader bufferedReader;
        private byte[] buffer = new byte[1024];
        private int bytesRead;
        private long fileSize;
        private ByteArrayOutputStream readResult;
        private int streamLength;
        private boolean reading;
        String message = "";
        
        @Override
        public void run() {
            try {
                serverSocket = BluetoothAdapter.getDefaultAdapter().listenUsingRfcommWithServiceRecord("PulseServer", uuid);
                clientSocket = serverSocket.accept();
                serverSocket.close();
                inputStream = clientSocket.getInputStream();
                Log.d(TAG, "Run entered");
                    //bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while (reading){
                    dataInputStream = new DataInputStream(inputStream);
                    message = dataInputStream.readUTF();
                    Log.d(TAG, message);
                
                    /*dataInputStream = (DataInputStream) socket.getInputStream();
                    String fileName = dataInputStream.readUTF();
                    fileSize = dataInputStream.readLong();
                    outputStream = new FileOutputStream(fileName);
                    while (fileSize > 0 && (bytesRead = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                        fileSize -= bytesRead;
                    }*/
            }
            } catch (IOException e) {
                Log.d(TAG, "Thread IOException error", e);
                e.printStackTrace();
            }
        }
    
    
        public void startReading() {
            Log.d(TAG, "startReading");
            Log.d(TAG, "Starting server");
            reading = true;
            run();
        }
    
        public void stopReading() {
            Log.d(TAG, "stopReading");
            Log.d(TAG, "Stopping server");
            reading = false;
        }
    }

    public void connect() {
        //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        //bluetoothAdapter.enable();
        Log.d(TAG, "connect");
        btConnectionReader = new BTConnectionReader();
        btConnectionReader.startReading();
    }

    public void disconnect() {
        //bluetoothAdapter.disable();
        Log.d(TAG, "disconnect");
        btConnectionReader.stopReading();
    }
}