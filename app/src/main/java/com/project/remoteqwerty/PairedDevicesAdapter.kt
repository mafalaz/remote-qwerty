package com.project.remoteqwerty

import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PairedDevicesAdapter : RecyclerView.Adapter<PairedDevicesAdapter.DeviceViewHolder>() {

    private val devices = mutableListOf<Pair<String, BluetoothDevice>>() // Use Pair to store both name and address
    private var onDeviceClickListener: ((String) -> Unit)? = null

    fun submitList(newDevices: List<Pair<String, BluetoothDevice>>) {
        devices.clear()
        devices.addAll(newDevices)
        notifyDataSetChanged()
    }

    fun setOnDeviceClickListener(listener: (String) -> Unit) {
        onDeviceClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent, false)
        return DeviceViewHolder(view, onDeviceClickListener)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        holder.bind(device.first, device.second) // Passing both device name and address
    }

    override fun getItemCount(): Int = devices.size

    class DeviceViewHolder(
        itemView: View,
        private val onDeviceClickListener: ((String) -> Unit)?
    ) : RecyclerView.ViewHolder(itemView) {

        private val deviceNameTextView: TextView = itemView.findViewById(R.id.deviceNameTextView)
        private val deviceAddressTextView: TextView = itemView.findViewById(R.id.deviceAddressTextView)

        fun bind(deviceName: String, deviceAddress: BluetoothDevice) {
            deviceNameTextView.text = deviceName
            deviceAddressTextView.text = deviceAddress.address // Display the Bluetooth device address

            itemView.setOnClickListener {
                onDeviceClickListener?.invoke(deviceName)
            }
        }
    }
}


