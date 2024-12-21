package com.project.remoteqwerty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.remoteqwerty.databinding.ActivityEnterTextBinding

class EnterTextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEnterTextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.button1.setOnClickListener {
            sendCharacter("1`")
        }

        binding.button2.setOnClickListener {
            sendCharacter("2`")
        }

        binding.button3.setOnClickListener {
            sendCharacter("3`")
        }

        binding.button4.setOnClickListener {
            sendCharacter("4`")
        }

        binding.button5.setOnClickListener {
            sendCharacter("5`")
        }

        binding.button6.setOnClickListener {
            sendCharacter("6`")
        }

        binding.button7.setOnClickListener {
            sendCharacter("7`")
        }

        binding.button8.setOnClickListener {
            sendCharacter("8`")
        }

        binding.button9.setOnClickListener {
            sendCharacter("9`")
        }

        binding.button0.setOnClickListener {
            sendCharacter("0`")
        }

        binding.buttonQ.setOnClickListener {
            sendCharacter("Q")
        }

        binding.buttonW.setOnClickListener {
            sendCharacter("W")
        }

        binding.buttonE.setOnClickListener {
            sendCharacter("E")
        }

        binding.buttonR.setOnClickListener {
            sendCharacter("R")
        }

        binding.buttonT.setOnClickListener {
            sendCharacter("T")
        }

        binding.buttonY.setOnClickListener {
            sendCharacter("Y")
        }

        binding.buttonU.setOnClickListener {
            sendCharacter("U")
        }

        binding.buttonI.setOnClickListener {
            sendCharacter("I")
        }

        binding.buttonO.setOnClickListener {
            sendCharacter("O")
        }

        binding.buttonP.setOnClickListener {
            sendCharacter("P")
        }

        binding.buttonA.setOnClickListener {
            sendCharacter("A")
        }

        binding.buttonS.setOnClickListener {
            sendCharacter("S")
        }

        binding.buttonD.setOnClickListener {
            sendCharacter("D")
        }

        binding.buttonF.setOnClickListener {
            sendCharacter("F")
        }

        binding.buttonG.setOnClickListener {
            sendCharacter("G")
        }

        binding.buttonH.setOnClickListener {
            sendCharacter("H")
        }

        binding.buttonJ.setOnClickListener {
            sendCharacter("J")
        }

        binding.buttonK.setOnClickListener {
            sendCharacter("K")
        }

        binding.buttonL.setOnClickListener {
            sendCharacter("L")
        }

        binding.buttonDelete.setOnClickListener {
            sendCharacter("i")
        }

        binding.buttonTeamA.setOnClickListener {
            sendCharacter("a")
        }

        binding.buttonZ.setOnClickListener {
            sendCharacter("Z")
        }

        binding.buttonX.setOnClickListener {
            sendCharacter("X")
        }

        binding.buttonC.setOnClickListener {
            sendCharacter("C")
        }

        binding.buttonV.setOnClickListener {
            sendCharacter("V")
        }

        binding.buttonB.setOnClickListener {
            sendCharacter("B")
        }

        binding.buttonN.setOnClickListener {
            sendCharacter("N")
        }

        binding.buttonM.setOnClickListener {
            sendCharacter("M")
        }

        binding.buttonSpace.setOnClickListener {
            sendCharacter("k")
        }

        binding.buttonTeamB.setOnClickListener {
            sendCharacter("e")
        }

        binding.buttonSetTimer.setOnClickListener {
            sendCharacter("j")
        }

        binding.buttonSetSCVar.setOnClickListener {
            sendCharacter("X")
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
}