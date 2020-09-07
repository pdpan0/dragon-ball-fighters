package com.pdpano.dragonballfighters

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DisplayFighterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_fighter)

        val fighterName: String = intent.getStringExtra(FIGHTER_NAME)
        val fighterStrength: Int = intent.getIntExtra(FIGHTER_STRENGTH,0)
        val fighterHealth: Int = intent.getIntExtra(FIGHTER_HEALTH,0)
        val fighterBreed: String = intent.getStringExtra(FIGHTER_BREED)

        if(fighterName.isNullOrEmpty() ||
           fighterStrength==0 ||
           fighterHealth==0 ||
           fighterBreed.isNullOrEmpty()) {

            findViewById<TextView>(R.id.label_fighter).apply {
                setTextColor(Color.parseColor("#d63031"))
                text = "Não foi possível carregar o nível de poder do seu lutador!"
            }

        } else {
            val fighter = Fighter(
                fighterName,
                fighterStrength,
                fighterHealth,
                Breed.valueOf(fighterBreed)
            )
            displayFighter(fighter)
        }
    }

    fun displayFighter(fighter: Fighter) {
        findViewById<TextView>(R.id.label_fighter).apply {
            text = if (fighter.getKi() >= 80.0) {
                setTextColor(Color.parseColor("#0984e3"))
                "Lutador de alto nível melhor tomar cuidado. \n\n $fighter"
            } else {
                setTextColor(Color.parseColor("#d63031"))
                "Lutador ainda precisa de treinamento. \n\n $fighter"
            }
        }
    }
}