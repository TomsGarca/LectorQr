package com.example.lectorqr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lectorqr.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.integration.android.IntentIntegrator


class User(conteo: Int) {
    var conteo: Int = 0
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnScanner.setOnClickListener { initScanner() }
    }

    private fun initScanner(){
//        IntentIntegrator(this).setPrompt("Alinea el Codigo QR con la pantalla")
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        val refe = FirebaseDatabase.getInstance().getReference("131313/conteo/personas")
        if (result != null)
        {
            when (result.contents)
            {
                null -> {
                    Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
                }
                "https://me-qr.com/mAd6egUk" -> {
                    Log.d("PedAPP", refe.toString())
                    val query = refe.get()
                    ////                Log.d("PedaAPP", query.toString())
                    query.addOnSuccessListener { result ->
                        // El nodo existe
                        var value = result.child("personas").value as String
                        Log.d("PedAPP", "El nombre del usuario es $value")
                        Toast.makeText(this, "QR valido $result", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, "Error al obtener datos de Firebase: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.d("PedAPP", "El nombre del usuario es ")
                    }
                    Toast.makeText(this, "QR valido", Toast.LENGTH_LONG).show()
                }
                else -> {
                    Toast.makeText(this, "QR Invalido", Toast.LENGTH_LONG).show()
                }
            }
//            if (result.contents == null)
//            {
//                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
//            } else if (result.contents == "https://me-qr.com/mAd6egUk")
//            {
//                Log.d("PedAPP", refe.toString())
//                val query = refe.get()
//////                Log.d("PedaAPP", query.toString())
//                query.addOnSuccessListener { result ->
//                    // El nodo existe
//                    var value = result.child("personas").value as String
//                    Log.d("PedAPP", "El nombre del usuario es $value")
//                    Toast.makeText(this, "QR valido $result", Toast.LENGTH_LONG).show()
//                }.addOnFailureListener { e ->
//                    Toast.makeText(this, "Error al obtener datos de Firebase: ${e.message}", Toast.LENGTH_LONG).show()
//                    Log.d("PedAPP", "El nombre del usuario es ")
//                }
//                Toast.makeText(this, "QR valido", Toast.LENGTH_LONG).show()
//            }
//            else
//            {
//                Toast.makeText(this, "QR Invalido", Toast.LENGTH_LONG).show()
//            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
