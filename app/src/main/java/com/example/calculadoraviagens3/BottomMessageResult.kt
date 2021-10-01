package com.example.calculadoraviagens3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BottomMessageResult : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottom_message_result, container, false)

        val costMessageView = view.findViewById<TextView>(R.id.cost_result_message)

        var message = arguments?.getString("result_message")

        costMessageView.text = message



        val share_box = view.findViewById<TextView>(R.id.share_text)
        //val save_box = view.findViewById<LinearLayout>(R.id.save_box_layout)

        share_box.setOnClickListener {
            message += " \n\nCálculo realizado pela Calculadora de Viagens. \nBoa viagem !"

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Escolha o aplicativo para compartilhar: "))
        }




        return view


    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }


}