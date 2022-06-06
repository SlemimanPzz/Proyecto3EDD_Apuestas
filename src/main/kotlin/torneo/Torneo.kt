package torneo

import kotlin.Random.nextInt
import kotlin.math.min 
import los.illuminati.colors
import usuario.*

class Torneo(){

    val candidatos: Lista<Candidatos> // Lista de candidatos 
    var apuesta: Int = 0; // ID del canditadato al que se le aposto 
    var apostado: Float = 0F; // la cantidad que se aposto
    var minCandidato Candidatos // el candidato con menor probabilidad de ganar 
    var maxCandidato Candidatos // el candidato con mayor probabilidad de ganar
    var candidatoGanador // Ganador de la partida 

    init{
        var ncandidatos = kotlin.random.nextInt(1...2)
        when(ncandidatos){
            1-> ncandidatos = 16
            2-> ncandidatos = 32
        }
        for(i in 0... ncandidatos-1){
            candidatos.add(Candidatos(0,i,0))
        }
    }

    /**
     * Método para simular todas las partidas del torneo
     */
    fun partidas(){
        for(i in 0 ... ncandidatos-2){
            partida(candidadatos.get(i), candidadatos.get(i+1))
        }
        //hay que agregar el tiempo entre cada partida
    }

    /**
     * Método para simular un partido entre dos candidatos
     */
    fun partida(candidatoA : Candidatos, candidatoB : Candidatos):{

        var cuotaA 
        var coutaB 

        probabilidadA = (candidatoA.habilidad) / (candidatoA.habilidad + candidatoB.habilidad)
        probabilidadB = (candidatoB.habilidad) / (candidatoB.habilidad + candidatoA.habilidad)

        candidadatoA.cuota =  1 / probabilidadA
        candidadatoB.cuota = 1 /probabilidadB

        Colors.println("Empieza Partida", Colors.HIGH_INTENSITY)
        Colors.println("Juega el candidato " + candidatoA.id + " vs " + candidatoB.id, Colors.HIGH_INTENSITY)
        Colors.println("Cuotas de los candidatos ->", Colors.HIGH_INTENSITY)
        Colors.println("Cuota del candidato " + candidatoA.id + " es " + candidadatoA.cuota, Colors.HIGH_INTENSITY)
        Colors.println("Cuota del candidato " + candidatoB.id + " es "  + candidadatoB.cuota, Colors.HIGH_INTENSITY )
        Colors.println("Escribe el id del candidato al que quieres apostar")
        Colors.println(" Tienes " + " segundos para realizar tu apuesta ")

        apostar(candidadatoA,candidatoB)

        // Aquí se le daría tiempo al usuario para realizar la apuesta
        var ganador = kotlin.random.nextInt(1...100)

        //Saca el jugador con menos probablilidad de ganar
        if(kotlin.math.min(probabilidadA,probabilidadB) == probabilidadA){
            minCandidato = candidatoA
            maxCandidato = candidatoB
        }else{
            minCandidato = candidatoB
            maxCandidato = candidatoA
        }

        //Determina al ganador y elimina al perdedor de la lista 
        if(ganador <= minCandidato){
            candidatoGanador = minCandidato
            candidatos.elimina(maxCandidato)
        }else{
            candidatoGanador = maxCandidato
            candidatos.elimina(minCandidato)
        }
    }

    private fun procesaApuesta(){
        if(apuesta == 0) {
            println("Esperamos tu apuesta para la siguiente carrera.")
            return
        }
        println("Procesando apuesta")
        if(apuesta == candidatoGanador.id){
            println("¡Haz Acertado!")
            println("Ha ganado el candidadato ${candidatoGanador.id}")
            println("Su cuota es de ${candidatoGanador.cuota}")
            val ganancia = apostado * candidatoGanador.cuota
            println("Por lo tanto al haber apostado $apostado haz ganado $ganancia")
            usuario.saldo = usuario.saldo - apostado + ganancia
            println("Tu nuevo saldo es de ${usuario.saldo}")
            usuario.historial.historial.add(Apuesta(ganada = true, apostado = apostado, ganancia = ganancia, tipo = TipoApuesta.TORNEO))
            println("Apuesta agregada a tu historial")
        } else {
            println("Pare que perdiste 😥")
            println("Ha ganado el candidadato ${candidatoGanador.id} pero apostaste por $apuesta")
            println("Haz perdido $apostado")
            usuario.saldo -= apostado
            println("Ahora tienes un saldo de ${usuario.saldo}")
            usuario.historial.historial.add(Apuesta(ganada = false, apostado = apostado, ganancia = 0F, tipo =  TipoApuesta.TORNEO))
            println("Apuesta agregada a tu historial")
        }
        println("Tu apuesta ha sido procesada.")
        apuesta = 0
        apostado = 0F
    }

    fun apostar(candidatoA : Candidatos, candidatoB : Candidatos){
        while (true){
            if(apuesta != 0){
                println("Estas seguro que quieres sobreescribir la apuesta?[y/N]")
                val sobre = readln()
                if(sobre == "y" || sobre == "Y") {
                    println("Saliendo")
                    return
                }
                else println("Continuando")
            }
            println("¿Cuanto quieres apostar?(-1 para salir)")
            val x = readln()
            try { x.toFloat() } catch (_ : NumberFormatException) { println("Ingresa un Numero valido"); continue }
            if(x.toFloat() < 0){
                println("Saliendo de las apuestas")
                return
            }
            println("¿A quien se lo quieres apostar?")
            val y = readln()
            try { x.toInt() } catch (_ : NumberFormatException) { println("Ingresa un Numero valido"); continue }
            if(x.toInt() != candidatoA.id && x.toInt() != candidatoB.id ){
                println("Tienes que escoger un corredor valido")
                println("Vuelve a apostar")
                continue
            }
            println("Toma en cuenta que tu apuesta es para la siguiente carrera.")
            apostado = x.toFloat()
            apuesta = y.toInt()
            println("Tu apuesta quedo registrada en la carrera.")
            return
        }
    }
}