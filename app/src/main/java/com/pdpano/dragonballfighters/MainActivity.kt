package com.pdpano.dragonballfighters

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      Checa quando o usuário da foco no campo, para validação.
        input_fighter_name.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) validateFields(input_fighter_name) else null
        }
        input_fighter_strength.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) validateFields(input_fighter_strength) else null
        }
        input_fighter_health.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) validateFields(input_fighter_health) else null
        }

        if (input_fighter_health.error.isNullOrBlank() &&
            input_fighter_name.error.isNullOrBlank() &&
            input_fighter_strength.error.isNullOrBlank()) {
            // Permitir o botão
        }
    }

    /*
        Foi feito desta forma para fins de aprendizado, utilizando principios de
        responsabilidade única, creio que o melhor e ideal seria uma função
        (addTextChangedListener) de validação para cada campo.
     */
    fun validateFields(field: EditText) {
        field.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()) {
                    when (field) {
                        input_fighter_name ->
                            field.error = validateName(s)
                        input_fighter_strength ->
                            field.error = validateStrength(s.toString().toInt())
                        input_fighter_health ->
                            field.error = validateHealth(s.toString().toInt())
                    }
                }
            }
        })
    }

    /*
        Regra de válidação para os campos correspondentes.
     */
    fun validateName(s: CharSequence?): String? {
        val pattern = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]".toRegex()
        return if(!pattern.containsMatchIn(s.toString())) null
            else "Não pode conter caracteres especiais"
    }

    fun validateStrength(strength: Int): String? {
        return if(strength <= 0) "Força do seu guerreiro não pode ser negativa"
            else if (strength >= 100) "Guerreiro não pode ter força maior que 100"
            else null
    }

    fun validateHealth(health: Int): String? {
        return if (health <= 0) "Guerreiro não se encontra vivo"
            else if (health >= 100) "Guerreiro não pode ter vida maior que 100"
            else null
    }

    /*
        Estima o poder do Guerreiro.
     */
    fun calculateEstimatePower(view: View) {
        val fighter = Fighter(
            findViewById<EditText>(R.id.input_fighter_name)
                .text
                .toString(),
            findViewById<EditText>(R.id.input_fighter_strength)
                .text
                .toString()
                .toInt(),
            findViewById<EditText>(R.id.input_fighter_health)
                .text
                .toString()
                .toInt()
        )

        val fighterPower = fighter.getPower()

        findViewById<TextView>(R.id.result_text).apply {
            if (fighterPower in 0..50) {
                setTextColor(Color.parseColor("#d63031"))
                text = "Lutador ainda precisa de treinamento. Seu poder é de ${fighterPower}"
            }
            setTextColor(Color.parseColor("#0984e3"))
            text = "Lutador de alto nível melhor tomar cuidado. Seu poder é de ${fighterPower}"
        }
    }
}