package torneo

import kotlin.Random.nextInt
import kotlin.math.min 
import los.illuminati.colors

class Torneo(val candidatos: Lista<Candidatos>){

    init{
        var ncandidatos = kotlin.random.nextInt(1...2)
        when(ncandidatos){
            1-> ncandidatos = 16
            2-> ncandidatos = 32
        }
        for(i in 0... ncandidatos-1){
            candidatos.add(Candidatos(0,i))
        }
    }

    fun partidas(){
        for(i in 0 ... ncandidatos-2){
            partida(candidadatos.get(i), candidadatos.get(i+1))
        }
        //hay que agregar el tiempo entre cada partida
        if(ncandidatos ? 2 == 1){

        }
    }

    fun partida(candidatoA : Candidatos, candidatoB : Candidatos):{

        val probabilidadA
        val probabilidadB
        var cuotaA 
        var coutaB 

        probabilidadA = (candidatoA.habilidad) / (candidatoA.habilidad + candidatoB.habilidad)
        probabilidadB = (candidatoB.habilidad) / (candidatoB.habilidad + candidatoA.habilidad)

        coutaA = 1 / probabilidadA
        coutaB = 1 / probabilidadB


        Colors.println("Empieza Partida", Colors.HIGH_INTENSITY)
        Colors.println("Juega el candidato " + candidatoA.id + " vs " + candidatoB.id)

        var ganador = kotlin.random.nextInt(1...100)

        //Para determinar el ganador 
        if(ganador <= kotlin.math.min(probabilidadA,probabilidadB)){

        }
    }
}