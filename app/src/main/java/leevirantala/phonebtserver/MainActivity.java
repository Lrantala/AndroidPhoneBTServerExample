package leevirantala.phonebtserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int DISCOVERABLE_REQUEST_CODE = 0x1;
    Button connectbutton, sendbutton;
    TextView display;
    final String TAG = MainActivity.class.getSimpleName();
    
    // Bluetooth related stuff.
    private String mConnectedDeviceName = null;
    private ArrayAdapter<String> mConversationArrayAdapter;
    private StringBuffer mOutStringBuffer;
    private BluetoothAdapter btAdapter = null;
    private BluetoothManager btManager = null;
    private BTServer btServer = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    
    private void initialize() {
        Log.d(TAG, "initialize");
        display = (TextView)findViewById(R.id.display);
        connectbutton = (Button)findViewById(R.id.connectbutton);
        connectbutton.setOnClickListener(this);
        sendbutton = (Button)findViewById(R.id.sendbutton);
        sendbutton.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
        switch(v.getId()){
            case R.id.connectbutton:
                if (btServer == null){
                    Toast.makeText (this, "Making server, connecting", Toast.LENGTH_SHORT).show();
                    /*if(btAdapter != null && btAdapter.isDiscovering()){
                        btAdapter.cancelDiscovery();
                    }
                    btAdapter.startDiscovery();*/
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(discoverableIntent, DISCOVERABLE_REQUEST_CODE);
                    btServer = new BTServer();
                    btServer.connect();
                }
                else if (btServer != null){
                    Toast.makeText(this, "Nulling server, disconnecting", Toast.LENGTH_SHORT).show();
                    if(btAdapter != null && btAdapter.isDiscovering()){
                        btAdapter.cancelDiscovery();
                    }
                    btServer.disconnect();
                    btServer = null;
                }
                break;
            case R.id.sendbutton:
                //Do something else
                Toast.makeText(this, "Doesn't do anything yet", Toast.LENGTH_SHORT).show();
                break;
        }
            
    }
}
