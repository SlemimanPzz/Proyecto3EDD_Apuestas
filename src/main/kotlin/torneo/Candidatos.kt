package torneo 

import kotlin.random.nextInt

class Candidatos(var habilidad : Int, var id : Int, var cuota: Double, var probabilidad: Double){

    init{
        habilidad = kotlin.random.Random.nextInt(50 .. 400)
    }
}