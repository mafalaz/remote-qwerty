package com.project.remoteqwerty

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.remoteqwerty.databinding.ActivityBluetoothListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BluetoothListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBluetoothListBinding
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private lateinit var pairedDevicesAdapter: PairedDevicesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pairedDevicesAdapter = PairedDevicesAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = pairedDevicesAdapter
        showPairedDevices()
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showPairedDevices() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this device.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isPermissionGranted(Manifest.permission.BLUETOOTH_CONNECT)) {
            Toast.makeText(this, "Permission for Bluetooth is not granted.", Toast.LENGTH_SHORT).show()
            return
        }

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
        if (!pairedDevices.isNullOrEmpty()) {
            val deviceList = pairedDevices.map { it.name to it } // Pair name and device
            pairedDevicesAdapter.submitList(deviceList) // Submit the full pair (name, device)

            // Set up click listener for devices
            pairedDevicesAdapter.setOnDeviceClickListener { deviceName ->
                val device = deviceList.find { it.first == deviceName }?.second
                if (device != null) {
                    connectToDevice(device)
                }
            }
        } else {
            Toast.makeText(this, "No paired Bluetooth devices found.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun connectToDevice(device: BluetoothDevice) {
        GlobalScope.launch(Dispatchers.Main) {
            // Show loading before starting connection
            showLoading(true)

            withContext(Dispatchers.IO) {
                // Perform Bluetooth connection in background
                val uuid = device.uuids?.firstOrNull()?.uuid ?: return@withContext
                val socket = device.createRfcommSocketToServiceRecord(uuid)

                try {
                    socket.connect()
                    runOnUiThread {
                        Toast.makeText(this@BluetoothListActivity, "Connected to ${device.name}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@BluetoothListActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    BluetoothManager.bluetoothSocket = socket
                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@BluetoothListActivity, "Failed to connect to ${device.name}", Toast.LENGTH_SHORT).show()
                    }
                    e.printStackTrace()
                } finally {
                    // Hide loading and close the socket
                    runOnUiThread {
                        showLoading(false)
                    }

                }
            }
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
