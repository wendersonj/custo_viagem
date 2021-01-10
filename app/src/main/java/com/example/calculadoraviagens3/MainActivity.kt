package com.example.calculadoraviagens3

import android.app.Activity
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.calculadoraviagens3.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonCalculate.setOnClickListener {
            calculateTravel(this)
        }

        binding.buttonReset.setOnClickListener {
            resetFields(this)
        }

        binding.shareButton.setOnClickListener {
            Toast.makeText(
                this,
                "Em breve !\nCobrem dos nossos desenvolvedores.\n=)",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun resetFields(activity: Activity) {
        binding.apply {

            val campos = arrayListOf<TextInputEditText>(
                precoGasEdit,
                adicionalEdit,
                autonomiaEdit,
                distanciaEdit,
                qtdPessoasEdit
            )

            //limpando campos
            for (campo in campos) {
                campo.text?.clear()
                campo.error = null
            }

            mensagemCusto.visibility = View.INVISIBLE
            custoText.visibility = View.INVISIBLE

            Toast.makeText(
                activity.applicationContext,
                "Limpando ...",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun calculateTravel(activity: MainActivity) {
        binding.apply {

            var emptyField: Boolean = false
            var wrongValue: Boolean = false
            var precoGas: Double = 0.0
            var autonomia: Double = 0.0
            var distancia: Double = 0.0
            var adicional: Double = 0.0
            var qtdPessoas: Int = 0


//Se fizer um array de valores [], dá pra iterar entre o array campos


            if (precoGasEdit.text.toString().isNotEmpty()) {
                precoGas = precoGasEdit.text.toString().toDouble()

                if (precoGas == 0.0) {
                    wrongValue = true
                    precoGasEdit.error = "Valor inadequado."
                }

            } else {
                precoGasEdit.requestFocus()
                precoGasEdit.error = "Campo vazio."
                emptyField = true
            }

            if (autonomiaEdit.text.toString().isNotEmpty()) {
                autonomia = autonomiaEdit.text.toString().toDouble()

                if (autonomia == 0.0) {
                    wrongValue = true
                    autonomiaEdit.error = "Valor inadequado."
                }

            } else {
                autonomiaEdit.requestFocus()
                autonomiaEdit.error = "Campo vazio."
                emptyField = true
            }

            if (adicionalEdit.text.toString().isNotEmpty()) {
                adicional = adicionalEdit.text.toString().toDouble()
            }



            if (distanciaEdit.text.toString().isNotEmpty()) {
                distancia = distanciaEdit.text.toString().toDouble()

                if (distancia == 0.0) {
                    wrongValue = true
                    distanciaEdit.error = "Valor inadequado."
                }

            } else {
                distanciaEdit.requestFocus()
                distanciaEdit.error = "Campo vazio."
                emptyField = true
            }

            if (qtdPessoasEdit.text.toString().isNotEmpty()) {
                qtdPessoas = qtdPessoasEdit.text.toString().toInt()

                if (qtdPessoas == 0) {
                    wrongValue = true
                    qtdPessoasEdit.error = "Valor inadequado."
                }

            } else {
                qtdPessoasEdit.requestFocus()
                qtdPessoasEdit.error = "Campo vazio."
                emptyField = true
            }

            if (emptyField or wrongValue) {
                RaiseFillToast(activity)
            } else {

                custoText.text =
                    "R$ " + ((((distancia / autonomia) * precoGas) + adicional) / qtdPessoas).toString()

                mensagemCusto.visibility = View.VISIBLE
                custoText.visibility = View.VISIBLE
            }
        }
    }

    private fun RaiseFillToast(activity: Activity) {
        Toast.makeText(
            activity.applicationContext,
            "Preencha todos os campos",
            Toast.LENGTH_SHORT
        ).show()
    }

}