package leevirantala.phonebtserver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button connectbutton, sendbutton;
    TextView display;
    final String TAG = MainActivity.class.getSimpleName();
    
    // Bluetooth related stuff.
    private String mConnectedDeviceName = null;
    private ArrayAdapter<String> mConversationArrayAdapter;
    private StringBuffer mOutStringBuffer;
    private BluetoothAdapter btAdapter = null;
    private BluetoothManager btManager = null;
    
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
        switch(v.getId()){
            case R.id.connectbutton:
                //Do something
                break;
            case R.id.sendbutton:
                //Do something else
                break;
        }
            
    }
}
