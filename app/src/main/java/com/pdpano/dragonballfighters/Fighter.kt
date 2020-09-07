package com.pdpano.dragonballfighters

data class Fighter(val name: String, val strength: Int, val health: Int, val breed: Breed) {

    fun getKi(): Int = (strength+breed.baseStrength)+(health+breed.baseHealth)

    override fun toString(): String {
        return "Lutador: $name \n Raça: $breed \n Poder de Luta: ${getKi()}"
    }
}