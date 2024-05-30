package br.com.wjsouza.dev.calculadora_viagens.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_travel_table")
class DataTravel(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val descricao: String = "",
    @NonNull
    val precoGas: Float,
    @NonNull
    val autonomia: Float,
    @NonNull
    val distancia: Float,

    val valor_adicional: Float = 0f,
    @NonNull
    val qtd_pessoas: Int = 1
) {


    fun calcularCusto(): Float {
        return ((((distancia / autonomia) * precoGas) + valor_adicional) / qtd_pessoas)
    }

}
