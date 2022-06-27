package com.example.mybluetoothrxtx

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_REQUEST_ENABLE
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //private val REQUEST_CODE_ENABLE_BT = 1;

    //Bluetooth adapter
    //lateinit var bluetoothAdapter: BluetoothAdapter
    private val REQUEST_CODE_ENABLE_BT:Int=1;
    private val REQUEST_CODE_DISCOVERABLE_BT:Int=1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init bluetooth adapter
        //bAdapter = BluetoothAdapter.getDefaultAdapter()
        val bluetoothAdapter:BluetoothAdapter?=BluetoothAdapter.getDefaultAdapter()
        //check if bluetooth is on/off
        if (bluetoothAdapter == null) {
            statusBluetooth.text = "Bluetooth is not available"
        } else {
            statusBluetooth.text = "Bluetooth is available"
        }
        //set image according bluetooth status
        if (bluetoothAdapter?.isEnabled==true) {
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
        } else {
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
        //turn bluetooth on
        onBtn.setOnClickListener {
            if(bluetoothAdapter?.isEnabled==true){
                Toast.makeText(this, "Ya esta papu", Toast.LENGTH_LONG).show()
            }else{
                val intent=Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return@setOnClickListener
                }
                startActivityForResult(intent,REQUEST_CODE_ENABLE_BT);
            }
        }
        //turn bluetooth off
        offBtn.setOnClickListener {
            if(bluetoothAdapter?.isEnabled==false) {
                Toast.makeText(this, "No esta papu", Toast.LENGTH_LONG).show()
            }else{
                bluetoothAdapter?.disable()
                bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this,"Ya lo apague", Toast.LENGTH_LONG).show()
            }

        }

        //Discover
        discoverableBtn.setOnClickListener {
            if(bluetoothAdapter?.isDiscovering==false){
                Toast.makeText(this,"Buscando",Toast.LENGTH_LONG).show()
                val intent=Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent,REQUEST_CODE_DISCOVERABLE_BT);
            }
        }
        //get list devices bluetooth
        pairedBtn.setOnClickListener {
            if(bluetoothAdapter?.isEnabled==true){
                pairedTv.text="paired devices"
                //get list devices
                val devices=bluetoothAdapter.bondedDevices
                for (device in devices){
                    val deviceName=device.name
                    val deviceAddress=device
                    pairedTv.append("\nDevice: $deviceName, $deviceAddress")
                }
            }else{
                Toast.makeText(this,"Primero encienda bluetooth",Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode:Int,resultCode:Int,data:Intent?){
        when(requestCode){

            REQUEST_CODE_ENABLE_BT ->
                if(resultCode== Activity.RESULT_OK){
                    bluetoothIv.setImageResource((R.drawable.ic_bluetooth_on))
                    Toast.makeText(this,"Listo ome gonorea ome",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"No se pudo parce", Toast.LENGTH_LONG).show()
                }

        }
        super.onActivityResult(requestCode, resultCode, data) 
    }


}