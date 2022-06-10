
import carrera.Carrera
import kotlinx.coroutines.*
import usuario.Usuario
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit
import java.util.concurrent.LinkedBlockingDeque

fun inicio(): Usuario {
    println("BIENVENIDO")
    println("1. Crear Usuario")
    println("2. Inicia Sesión")
    while (true){
        val sel = readln()
        try {
            when(sel.toInt()){
                1 -> {
                    val nuevoUsuario = Usuario.creaUsuario() ?: continue
                    nuevoUsuario.guarda()
                    println("Usuario creado")
                    println("1. Crear Usuario")
                    println("2. Inicia Sesión")
                }
                2 -> {
                    println("Ingresa tu Usuario")
                    val usr = Usuario.iniciaSesion(readln()) ?: continue
                    println("Section iniciado con usuario ${usr.nombre}")
                    return usr
                }
                else -> println("Ingresa un numero valido")
            }
        } catch (_: NumberFormatException){
            println("Ingresa un numero valido")
        }
    }
}


fun consultaCarreras(car : Carrera){
    while (true){
        println("¿Que quieres consultar?")
        println("1. Todos los corredores")
        println("2. Uno en especifico")
        println("3. Salir de consultas")
        val consulta = readln()
        try { consulta.toInt() } catch (_ : NumberFormatException) { println("Ingresa un Numero valido"); continue }
        when(consulta.toInt()){
            1 -> {
                car.corredores.forEach { println(it) }
                println("Todos los corredores presentados")
            }
            2 -> {
                println("¿Que concursante quieres consultar?")
                val x = readln()
                try { x.toInt() } catch (_ : NumberFormatException) { println("Ingresa un Numero valido"); continue }
                car.consultaParticipante(x.toInt())
                continue
            }
            3 -> {
                println("¿Estas seguro que quieres dejar de consular?[y/N]")
                val salir = readln()
                if(salir == "y" || salir == "Y") return
                else println("Decideste no salir")
                continue
            }
            else -> {
                println("Selecciona una opción valida.")
                continue
            }
        }
    }
}

fun apostar(car : Carrera){
    while (true){
        if(car.apuesta != 0){
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
        println("¿A quien se lo quieres apostar?[1-${car.numCompetidores}]")
        val y = readln()
        try { x.toInt() } catch (_ : NumberFormatException) { println("Ingresa un Numero valido"); continue }
        if(x.toInt() !in 1 .. car.numCompetidores){
            println("Tienes que escoger un corredor valido")
            println("Vuelve a apostar")
            continue
        }
        println("Toma en cuenta que tu apuesta es para la siguiente carrera.")
        car.apostado = x.toFloat()
        car.apuesta = y.toInt()
        println("Tu apuesta quedo registrada en la carrera.")
        return
    }
}

fun menu(car : Carrera, usr : Usuario) {
    while (true){
        println("Menu")
        println("1. Apostar")
        println("2. Consultas de Carrera")
        println("3. Agregar Saldo")
        println("4. Consultar historial")
        println("5. Salir")
        val x = readln()
        try { x.toInt() } catch (_ : NumberFormatException) { println("Ingresa un Numero valido"); continue }
        when (val m = x.toInt()) {
            5 -> {
                println("¿Estas seguro que quieres salir?[y/N]")
                val salir = readln()
                if(salir == "y" || salir == "Y") return
                else println("Decideste no salir")
                continue
            }
            4 -> {
                usr.consultaHistorial()
            }
            3 -> {
                usr.agregarSaldo()
            }
            2 -> {
                consultaCarreras(car)
            }
            1 -> {
                apostar(car)
            }
            else -> {
                println("Escoge una acción valida, $m no es una acción valida.")
                continue
            }
        }
    }
}



fun main() {
    val blockingQueue = LinkedBlockingDeque<String>()
    val input = BufferedReader(InputStreamReader(System.`in`))
    val hilo1 = Thread(Runnable {
        while (true) {
            try {
                blockingQueue.put(input.readLine())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }).start()
    val hilo2 = Thread(Runnable {
        try {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(5)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                var poll: String? = blockingQueue.poll()
                poll = poll ?: "Nada"
                println(poll)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }).start()
}
