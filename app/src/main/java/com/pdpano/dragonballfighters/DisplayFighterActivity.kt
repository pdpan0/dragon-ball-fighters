package com.pdpano.dragonballfighters

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class DisplayFighterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_fighter)

        val fighterName: String = intent.getStringExtra(FIGHTER_NAME)
        val fighterStrength: Int = intent.getIntExtra(FIGHTER_STRENGTH, 0)
        val fighterHealth: Int = intent.getIntExtra(FIGHTER_HEALTH, 0)
        val fighterBreed: String = intent.getStringExtra(FIGHTER_BREED)

        if (fighterName.isNullOrEmpty() ||
            fighterStrength == 0 ||
            fighterHealth == 0 ||
            fighterBreed.isNullOrEmpty()
        ) {

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

    private fun displayFighter(fighter: Fighter) {
        if (fighter.getKi() >= 80.0) {
            findViewById<TextView>(R.id.label_fighter).apply {
                setTextColor(Color.parseColor("#0984e3"))
                text = "Lutador de alto nível melhor tomar cuidado. \n\n $fighter"
            }
            setImage("@drawable/instinto")
        } else {
            findViewById<TextView>(R.id.label_fighter).apply {
                setTextColor(Color.parseColor("#d63031"))
                text = "Lutador ainda precisa de treinamento. \n\n $fighter"
            }
            setImage("@drawable/kid")
        }
    }

    private fun setImage(imagePath: String) {
        val imageResource: Int = resources.getIdentifier(imagePath, null, packageName)
        val image: Drawable? = ContextCompat.getDrawable(applicationContext, imageResource)

        findViewById<ImageView>(R.id.image_fighter).apply {
            setImageDrawable(image)
        }
    }
}