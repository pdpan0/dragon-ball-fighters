package com.pdpano.dragonballfighters

import android.content.Intent
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

const val FIGHTER_NAME = "com.pdpano.dragonballfighters.FIGHTER_NAME"
const val FIGHTER_STRENGTH = "com.pdpano.dragonballfighters.FIGHTER_STRENGTH"
const val FIGHTER_HEALTH = "com.pdpano.dragonballfighters.FIGHTER_HEALTH"
const val FIGHTER_BREED = "com.pdpano.dragonballfighters.FIGHTER_BREED"

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
                if(!s?.trim().isNullOrEmpty()) {
                    val pattern = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]".toRegex()
                    when {
                        count <= 2 -> {
                            input.error = "Não pode ter menos de 2 caracteres"
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
                } else {
                    input.error = "Campo obrigatório"
                    inputNameIsValid = false
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
                    input.error = "Campo obrigatório"
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
                    input.error = "Campo obrigatório"
                    inputHealthIsValid = false
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
        val fighterName = findViewById<EditText>(R.id.input_fighter_name)
            .text
            .toString()

        val fighterStrength = findViewById<EditText>(R.id.input_fighter_strength)
            .text
            .toString()
            .toInt()

        val fighterHealth = findViewById<EditText>(R.id.input_fighter_health)
            .text
            .toString()
            .toInt()

        val fighterBreed = findViewById<Spinner>(R.id.spinner_fighter_breed)
            .selectedItem
            .toString()
            .toUpperCase()

        val intent = Intent(this, DisplayFighterActivity::class.java).apply {
            putExtra(FIGHTER_NAME, fighterName)
            putExtra(FIGHTER_STRENGTH, fighterStrength)
            putExtra(FIGHTER_HEALTH, fighterHealth)
            putExtra(FIGHTER_BREED, fighterBreed)
        }

        startActivity(intent)
    }
}