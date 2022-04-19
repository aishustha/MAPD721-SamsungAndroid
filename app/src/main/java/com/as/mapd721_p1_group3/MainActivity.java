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

    TextView mStatusBlueTv, mPairedTv;
    ImageView mBlueTv;
    Button mOnBtn, mOffBtn, mDiscoverBtn, mPairedBtn;

    BluetoothAdapter mBlueAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mStatusBlueTv = findViewById(R.id.statusBluetoonthTv);
        mPairedTv = findViewById(R.id.pairedTv);
        mBlueTv = findViewById(R.id.bluetoothTv);
        mOnBtn = findViewById(R.id.onBtn);
        mOffBtn = findViewById(R.id.offBtn);
        mDiscoverBtn = findViewById(R.id.discoverableBtn);
        mPairedBtn = findViewById(R.id.pairedBtn);


        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBlueAdapter == null) {
            mStatusBlueTv.setText("Bluetooth is not available");
        } else{
            mStatusBlueTv.setText("Bluetooth is available");
        }

        if(mBlueAdapter.isEnabled()) {
            mBlueTv.setImageResource(R.drawable.ic_action_on);
        } else {
            mBlueTv.setImageResource(R.drawable.ic_action_off);
        }

        mOnBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick (View v){
                    if (!mBlueAdapter.isEnabled()) {
                        showToast("Turning On Bluetooth");
                        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(intent, REQUEST_ENABLE_BT);
                    }

                    else {
                        showToast("Bluetooth is already on");
                    }
            }
        });

        mOffBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                if (mBlueAdapter.isEnabled()) {
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth Off");
                    mBlueTv.setImageResource(R.drawable.ic_action_off);
                }
                else {
                    showToast("Bluetooth is already off");
                }
        }
        });

        mDiscoverBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
                if (!mBlueAdapter.isDiscovering()) {
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
            }
        });

        mPairedBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick (View v){
            if (mBlueAdapter.isEnabled()) {
                mPairedTv.setText("Paired Devices");
                Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                for (BluetoothDevice device: devices) {
                    mPairedTv.append("\nDevice:" + device.getName() + "," + device);
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
                    mBlueTv.setImageResource(R.drawable.ic_action_on);
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