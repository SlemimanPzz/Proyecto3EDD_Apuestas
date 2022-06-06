package torneo 

import kotlin.random.nextInt

data class Candidatos(var habilidad, var id, var cuota){

    init{
        habilidad = kotlin.Random.nextInt(50 ... 400)
    }
}