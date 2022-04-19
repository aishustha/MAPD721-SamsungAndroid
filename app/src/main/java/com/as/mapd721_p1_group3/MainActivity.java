package com.as.mapd721_p1_group3;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView StatusBlueTv, PairedTv;
    ImageView BlueTv;
    Button OnBtn, OffBtn, DiscoverBtn, PairedBtn;

    BluetoothAdapter BlueAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StatusBlueTv = findViewById(R.id.statusBluetoothTv);
        PairedTv = findViewById(R.id.pairedTv);
        BlueTv = findViewById(R.id.bluetoothTv);
        OnBtn = findViewById(R.id.onBtn);
        OffBtn = findViewById(R.id.offBtn);
        DiscoverBtn = findViewById(R.id.discoverBtn);
        PairedBtn = findViewById(R.id.pairedBtn);


        BlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (BlueAdapter == null) {
            StatusBlueTv.setText("Bluetooth is not available");
        } else{
            StatusBlueTv.setText("Bluetooth is available");
        }

        if(BlueAdapter.isEnabled()) {
            BlueTv.setImageResource(R.drawable.ic_action_on);
        } else {
            BlueTv.setImageResource(R.drawable.ic_action_off);
        }

        OnBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick (View v){
                    if (!BlueAdapter.isEnabled()) {
                        showToast("Turning On Bluetooth");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                    }

                    else {
                        showToast("Bluetooth is already on");
                    }
            }
        });

        OffBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                if (BlueAdapter.isEnabled()) {
                    BlueAdapter.disable();
                    showToast("Turning Bluetooth Off");
                    BlueTv.setImageResource(R.drawable.ic_action_off);
                }
                else {
                    showToast("Bluetooth is already off");
                }
        }
        });

        DiscoverBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                if (!BlueAdapter.isDiscovering()) {
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        PairedBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
            if (BlueAdapter.isEnabled()) {
                PairedTv.setText("Paired Devices");
                Set<BluetoothDevice> devices = BlueAdapter.getBondedDevices();
                for (BluetoothDevice device: devices) {
                    PairedTv.append("\nDevice:" + device.getName() + "," + device);
                }
            }
            else {
                showToast("Turn on bluetooth to get paired devices");
            }
        }
        });
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    BlueTv.setImageResource(R.drawable.ic_action_on);
                    showToast("Bluetooth is on");
                }

                else {
                    showToast("Couldn't on Bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showToast(String msg) {
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }


}