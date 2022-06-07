import torneo.*
import usuario.Usuario

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
                    println("Seción iniciado con usuario ${usr.nombre}")
                    return usr
                }
                else -> println("Ingresa un numero valido")
            }
        } catch (_: NumberFormatException){
            println("Ingresa un numero valido")
        }
    }
}

fun main() {
    val usr = inicio()

    val tor = Torneo(usr)
    tor.partidas()
    usr.guarda()
    println("Usurario guardado")
}
