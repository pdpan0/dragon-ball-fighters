package com.pdpano.dragonballfighters

data class Fighter(val name: String, val strength: Int, val health: Int, val breed: Breed) {

    fun getPower(): Double = (strength*breed.baseStrength)+(health*breed.baseHealth)/4

    override fun toString(): String {
        return "Lutador: $name \n Raça: $breed \n Poder de Luta: ${getPower()}"
    }
}