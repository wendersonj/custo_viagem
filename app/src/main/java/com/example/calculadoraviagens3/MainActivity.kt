package com.example.calculadoraviagens3

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.calculadoraviagens3.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.buttonCalculate.setOnClickListener {
            calculateTravel(this)
            hideKeyboard(it)
        }

        binding.buttonReset.setOnClickListener {
            resetFields(this)
            showKeyboard(it)
        }

        binding.shareButton.setOnClickListener {
            hideKeyboard(it)
            Toast.makeText(
                this,
                "Em breve !\nCobrem dos nossos desenvolvedores.\n=)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun showKeyboard(view: View) {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
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

            //invalidateAll() //nao uso gone... nao preciso fazer rebind

            mensagemCusto.visibility = View.INVISIBLE
            custoText.visibility = View.INVISIBLE

            Toast.makeText(
                activity.applicationContext,
                "Limpando ...",
                Toast.LENGTH_SHORT
            ).show()

            //leva o usuário para o inicio do form
            precoGasEdit.requestFocus()
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

                var custo = ((((distancia / autonomia) * precoGas) + adicional) / qtdPessoas)

                //R$ (valor com 2 decimais)
                custoText.text = "R$ " + String.format("%.2f", custo)
                //valorTotal.text =


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