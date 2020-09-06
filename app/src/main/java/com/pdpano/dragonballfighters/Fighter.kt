package com.pdpano.dragonballfighters

data class Fighter(val name: String, val strength: Int, val health: Int) {

    fun getPower(): Int = ((strength+health)/2)
}