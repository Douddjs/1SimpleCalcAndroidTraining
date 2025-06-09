package com.example.simplecalculator

import android.Manifest
import android.os.Bundle
import android.util.Log
//import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.simplecalculator.databinding.ActivityMainBinding
import android.os.Vibrator
import android.os.VibrationEffect
//import android.content.Context
import android.os.Build
import androidx.annotation.RequiresPermission


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var result = 00.0



    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator

        // For API 26+ use VibrationEffect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            // Deprecated in API 26
            vibrator.vibrate(50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resultView.text = result.toString()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets -> // Or findViewById(R.id.main) if it's not the direct root of binding
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        @RequiresPermission(allOf = [Manifest.permission.VIBRATE, Manifest.permission.VIBRATE])
        fun inputsChecker(): Pair<Double, Double> {
            val in1 = binding.input1.text.toString().toDoubleOrNull()
            val in2 = binding.input2.text.toString().toDoubleOrNull()
            vibrate()

//            return when {
//                in1?.isNaN() == true && in2?.isNaN() == true -> Pair(00.0, 00.0)
//
//                in1?.isNaN() == true -> {
//                    Pair(00.0, in2.toDouble())
//                }
//
//                in2.isNaN() -> {
//                    Pair(in1.toDouble(), 00.0)
//                }
//
//                else -> {
//                    Pair(in1.toDouble(), in2.toDouble())
//                }
//            }

            if (in1 == null || in2 == null || in1.isNaN() || in2.isNaN()) {
                return Pair(0.0, 0.0)
            }

            return Pair(in1, in2)
        }

        binding.addBtn.setOnClickListener {
            val (num1, num2) = inputsChecker()

            result = num1 + num2
            binding.resultView.text = result.toString()

            Log.d("SimpleCalculator", "Calculated value: $result") // 4. Check calculation

//            Toast.makeText(this, "${binding.input1}", Toast.LENGTH_SHORT).show()
//            Toast.makeText(this, "Result: $result", Toast.LENGTH_SHORT).show()

        }

        binding.subBtn.setOnClickListener {
            val (num1, num2) = inputsChecker()

            result = num1 - num2
            binding.resultView.text = result.toString()
        }

        binding.mulBtn.setOnClickListener {
            val (num1, num2) = inputsChecker()

            result = num1 * num2
            binding.resultView.text = result.toString()
        }

        binding.divBtn.setOnClickListener {
            val (num1, num2) = inputsChecker()

            if (num2.toInt() == 0) {
                Toast.makeText(this, "Math Error: Denomerator Can't Be 0", Toast.LENGTH_SHORT).show()
            } else {
                result = num1 / num2
                binding.resultView.text = result.toString()
            }


        }












    }
}


