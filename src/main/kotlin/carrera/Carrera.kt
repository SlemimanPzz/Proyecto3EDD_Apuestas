package carrera

import kotlin.random.Random

class Carrera(private val numCompetidores : Int) {
    private val corredores = Array(numCompetidores) {Corredor(it, MutableList<Int>(0){0}, 0f)}
    private var long = 0

    init {
        corredores.shuffle()
        corredores.forEachIndexed() {index, corredor ->  corredor.historial.add(index+1)}
        long += 1
        corredores.forEach {probabilidades(it)}
        corredores.forEach { println(it) }


        repeat(5){
            lightOutAndAwayWeGo()
            corredores.forEach { println(it) }
        }

    }

    private fun lightOutAndAwayWeGo(){
        corredores.sortBy { corredor ->  corredor.chance}
        val numGanador = Random.nextFloat()
        var ganador : Corredor =corredores.first()
        var acumulado = 0f
        for( x in corredores){
            acumulado += x.chance
            if(acumulado > numGanador){
                ganador = x
                break
            }
        }
        corredores.shuffle()
        val inter = corredores.first()
        val indexWinner = corredores.indexOf(element = ganador)
        corredores[indexWinner] = inter
        corredores[0] = ganador
        corredores.forEachIndexed() { index, corredor -> corredor.historial.add(index + 1) }
        corredores.forEach() {corredor -> probabilidades(corredor) }
        long+=1
    }

    private fun probabilidades(corredor : Corredor){
        val loDeArriba = (long * (numCompetidores + 1)) - corredor.historial.sum()
        val loDeAbajo = long * ((numCompetidores * (numCompetidores + 1))/2)
        val pc = loDeArriba.toFloat()/loDeAbajo.toFloat()
        corredor.chance = pc
    }

    fun apuesta(idCorredor : Int): Pair<Boolean, Float> {
        lightOutAndAwayWeGo()
        return if(corredores[0].id == idCorredor)
            true to (1/corredores[0].chance)
        else
            false to (1/corredores[0].chance)
    }

}