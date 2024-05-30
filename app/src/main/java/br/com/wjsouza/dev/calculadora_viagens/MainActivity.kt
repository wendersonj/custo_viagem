package br.com.wjsouza.dev.calculadora_viagens

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.wjsouza.dev.calculadora_viagens.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //private val unityAdsManager = UnityAdsManager()
    //val testLabSetting = Settings.System.getString(contentResolver, "firebase.test.lab")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        println("Comecou")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //codigo soente dps da binding

        //unityAdsManager.initializedAd(this, testLabSetting)
        //unityAdsManager.showBannerAd(binding.adViewBottom, this, unityAdsManager.BANNER_ID_1)
        //unityAdsManager.showBannerAd(binding.adViewBottom2, this, unityAdsManager.BANNER_ID_2)



        binding.buttonCalculate.setOnClickListener {
            val cost = calculateTravel(this)
            hideKeyboard(it)

            if (cost != null) {
                binding.costResultContainer.visibility = View.VISIBLE
                //Navigation.findNavController(this, R.id.main_fragment).navigate(R.id.action_mainActivity2_to_cost_result_fragment)

                var message: String =
                    "O custo por pessoa será de R$ ${cost}. A viagem será de ${binding.distanciaEdit.text} km com " +
                            "a gasolina custando R$ ${binding.precoGasEdit.text}," +
                            " o veículo fazendo ${binding.autonomiaEdit.text} km/L e" +
                            " tendo um custo adicional de R$ ${binding.adicionalEdit.text} para" +
                            " ${binding.qtdPessoasEdit.text} pessoa"
                message = message.run {
                    if (binding.qtdPessoasEdit.text.toString().toInt() > 1) {
                        println("deu")
                        this.plus("s.")
                    } else {
                        this.plus('.')
                    }
                }

                val data = Bundle()
                val bottomMessageResult = BottomMessageResult()
                val fragment_manager = getSupportFragmentManager().beginTransaction()
                data.putString("result_message", message)

                binding.costResultContainer.setBackgroundColor(Color.TRANSPARENT)
                bottomMessageResult.arguments = data
                bottomMessageResult.show(fragment_manager, "cost_result_bottom_message")

                //fragment_manager.replace(R.id.cost_result_container, bottomMessageResult).commit()
            }

            binding.costResultContainer.visibility = View.INVISIBLE
        }

        binding.buttonReset.setOnClickListener {
            resetFields(this)
            showKeyboard(it)
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

            //mensagemCusto.visibility = View.INVISIBLE
            //custoText.visibility = View.INVISIBLE
            //shareFab.visibility = View.INVISIBLE

            Toast.makeText(
                activity.applicationContext,
                "Limpando ...",
                Toast.LENGTH_SHORT
            ).show()

            //leva o usuário para o inicio do form
            precoGasEdit.requestFocus()
        }

    }

    private fun calculateTravel(activity: MainActivity): Double? {
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

                return ((((distancia / autonomia) * precoGas) + adicional) / qtdPessoas)
            }
        }

        return null
    }

    private fun RaiseFillToast(activity: Activity) {
        Toast.makeText(
            activity.applicationContext,
            "Preencha todos os campos",
            Toast.LENGTH_SHORT
        ).show()
    }


}