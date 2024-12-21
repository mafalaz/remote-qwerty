package com.project.remoteqwerty

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.remoteqwerty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val PERMISSION_REQUEST_CODE = 123
    private val REQUEST_ENABLE_BT = 456
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.enterTextButton.setOnClickListener {
            val intent = Intent(this, EnterTextActivity::class.java)
            startActivity(intent)
        }

        binding.setTimerButton.setOnClickListener {
            sendCharacter("j")
            val intent = Intent(this, EnterTextActivity::class.java)
            startActivity(intent)
        }

        binding.bluetoothButton.setOnClickListener {
            checkAndEnableBluetooth()
        }

        binding.buttonArrowLeft.setOnClickListener {
            sendCharacter("W")
        }

        binding.buttonFoulLeft.setOnClickListener {
            sendCharacter("D")
        }

        binding.buttonBrightnessUp.setOnClickListener {
            sendCharacter("U")
        }

        binding.buttonRound.setOnClickListener {
            sendCharacter("k")
        }

        binding.buttonFoulRight.setOnClickListener {
            sendCharacter("K")
        }

        binding.buttonArrowRight.setOnClickListener {
            sendCharacter("O")
        }

        binding.buttonTimeOutLeft.setOnClickListener {
            sendCharacter("S")
        }

        binding.buttonTimerUp.setOnClickListener {
            sendCharacter("b")
        }

        binding.buttonBrightnessDown.setOnClickListener {
            sendCharacter("R")
        }

        binding.buttonReset.setOnClickListener {
            sendCharacter("l")
        }

        binding.buttonTimerDown.setOnClickListener {
            sendCharacter("f")
        }

        binding.buttonTimeOutRight.setOnClickListener {
            sendCharacter("L")
        }

        binding.buttonBuzzer.setOnClickListener {
            sendCharacter("A")
        }

        binding.buttonSelectMode.setOnClickListener {
            sendCharacter("k")
        }

        binding.buttonAnimation.setOnClickListener {
            sendCharacter("T")
        }

        binding.buttonMode.setOnClickListener {
            sendCharacter("Z")
        }

        binding.buttonSC24.setOnClickListener {
            sendCharacter("P")
        }

        binding.buttonSetSCVar.setOnClickListener {
            sendCharacter("X")
            val intent = Intent(this, EnterTextActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSCPlay.setOnClickListener {
            sendCharacter("M")
        }

        binding.buttonSCVariasi.setOnClickListener {
            sendCharacter("Q")
        }

        binding.buttonSC14.setOnClickListener {
            sendCharacter("i")
        }

        binding.buttonSkorKiriUp.setOnClickListener {
            sendCharacter("c")
        }

        binding.buttonSkorKiriDown.setOnClickListener {
            sendCharacter("d")
        }

        binding.buttonSkorKananUp.setOnClickListener {
            sendCharacter("g")
        }

        binding.buttonSkorKananDown.setOnClickListener {
            sendCharacter("h")
        }
    }

    private fun sendCharacter(sendCharacter: String) {
        try {
            val outputStream = BluetoothManager.bluetoothSocket?.outputStream
            if (outputStream != null) {
                outputStream.write(sendCharacter.toByteArray())
                outputStream.flush()
            } else {
                Toast.makeText(this, "Bluetooth not connected.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send data.", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // Android 12+
            if (!isPermissionGranted(Manifest.permission.BLUETOOTH_CONNECT)) {
                requestPermission(Manifest.permission.BLUETOOTH_CONNECT)
            }
        }
    }

    private fun checkAndEnableBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not supported on this device.", Toast.LENGTH_SHORT).show()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else {
            val intent = Intent(this, BluetoothListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(permission),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Bluetooth permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Bluetooth enabled.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, BluetoothListActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Bluetooth not enabled.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
