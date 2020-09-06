package com.pdpano.dragonballfighters

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var inputNameIsValid: Boolean = false
    private var inputStrengthIsValid: Boolean = false
    private var inputHealthIsValid: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_estimate_power.isEnabled = false

        validateInputName(input_fighter_name)
        validateInputStrength(input_fighter_strength)
        validateInputHealth(input_fighter_health)
    }

//  Validações dos campos
    private fun validateInputName(input: EditText) {
        input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pattern = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]".toRegex()
                when {
                    count <= 0 -> {
                        input.error = "Não pode ser vazio"
                        inputNameIsValid = false
                    }
                    count >= 50 -> {
                        input.error = "Não pode ter mais que 50 caracteres"
                        inputNameIsValid = false
                    }
                    !pattern.containsMatchIn(s.toString()) -> {
                        inputNameIsValid = true
                    }
                    else -> {
                        input.error = "Não pode conter caracteres especiais"
                        inputNameIsValid = false
                    }
                }
                enableButton()
            }
        })
    }

    private fun validateInputStrength(input: EditText) {
        input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()) {
                    val strengthValue: Int = s.toString().toInt()
                    when {
                        strengthValue <= 0 -> {
                            input.error = "Força do seu guerreiro não pode ser negativa"
                            inputStrengthIsValid = false
                        }
                        strengthValue >= 100 -> {
                            input.error = "Guerreiro não pode ter força maior que 100"
                            inputStrengthIsValid = false
                        }
                        else -> {
                            inputStrengthIsValid = true
                        }
                    }
                } else {
                    inputStrengthIsValid = false
                }
                enableButton()
            }
        })
    }

    private fun validateInputHealth(input: EditText) {
        input.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty()) {
                    val healthValue: Int = s.toString().toInt()
                    when {
                        healthValue <= 0 -> {
                            input.error = "Guerreiro não se encontra vivo"
                            inputHealthIsValid = false
                        }
                        healthValue >= 100 -> {
                            input.error = "Guerreiro não pode ter vida maior que 100"
                            inputHealthIsValid = false
                        }
                        else -> {
                            inputHealthIsValid = true
                        }
                    }
                } else {
                    inputStrengthIsValid = false
                }
                enableButton()
            }
        })
    }

//  Ativar o botão
    private fun enableButton() {
        button_estimate_power.isEnabled = inputNameIsValid && inputStrengthIsValid && inputHealthIsValid
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
                .toInt(),
            Breed.valueOf(
                findViewById<Spinner>(R.id.spinner_fighter_breed)
                    .selectedItem
                    .toString()
                    .toUpperCase()
            )
        )

        findViewById<TextView>(R.id.result_text).apply {
            if (fighter.getPower() >= 50.0) {
                setTextColor(Color.parseColor("#0984e3"))
                text =
                    "Lutador de alto nível melhor tomar cuidado. \n${fighter.toString()}"
            } else {
                setTextColor(Color.parseColor("#d63031"))
                text = "Lutador ainda precisa de treinamento. \n ${fighter.toString()}"
            }
        }
    }
}