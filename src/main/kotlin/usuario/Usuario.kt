package usuario

import com.google.common.hash.Hashing
import kotlinx.serialization.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.FileWriter
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets

/**
 * Función extendida de [String] que regresa el hash SHA-512 en [String] de la misma.
 * Utilizada principalmente en usuario
 * @return Hash SHA-512 de [this]
 */
fun String.hash512() = Hashing.sha512().hashString(this, StandardCharsets.UTF_8).toString()

/**
 * Lee de la consola una contraseña y la devuelve.
 * @return La contraseña suministrada.
 */
fun leeContrasena(): String {
    val prov = System.console().readPassword("Contraseña:")
    println(String(prov))
    return String(prov)
}

/**
 * Usuario de la aplicación
 *
 * @property nombre Nombre del usuario.
 * @property saldo Saldo del usuario.
 * @property historial Historial del usuario.
 * @property hashPassword Hash de la contraseña del usuario.
 */
@Serializable
data class Usuario(val nombre: String, var saldo: Float, val historial: Historial, val hashPassword :String){


    companion object {

        /**
         * Crea un usuario, con los datos recolectaos.
         */
        fun creaUsuario(): Usuario? {
            println("Creación de usuario")
            println("Para salir ^D en cualquier momento")
            var password : String
            try {
                val consola = System.console()
                println("Nombre de Usuario:")
                var username : String
                while (true){
                    username = readln()
                    val file = File("./.Usuarios/$username.json")
                    if(file.exists()){
                        println("El Usuario ya existe")
                        return null
                    }
                    println("¿Estas seguro que te quieres llamar $username?[y/N]")
                    val yn = readln()
                    if(yn == "y" || "Y" == yn) break
                    println("Vuelve a ingresar al nombre.")
                }

                while (true){
                    password = leeContrasena()
                    println("Vuelve a ingresar la contraseña:")
                    val passwordConfirmation = leeContrasena()
                    if(password == passwordConfirmation){
                        println("Contraseña ingresadas correctamente.")
                        break
                    } else{
                        println("Vuelve a ingresar las contraseñas.")
                    }
                }


                var saldo : Float
                println("Saldo Inicial")
                while (true){
                    val saldoLectura = consola.readLine()
                    try{
                         saldo = saldoLectura.toFloat()
                    } catch (nfe : NumberFormatException){
                        println("Ingresa un saldo valido.")
                        continue
                    }
                    println("¿Estas seguro que tienes este saldo inicial: $saldo?[y/N]")
                    val yn = readln()
                    if(yn == "y" || "Y" == yn) break
                    println("Vuelve a ingresar tu saldo.")
                }
                println("Todo listo para crear usuario")
                println("Creando usuario")
                val hash = password.hash512()
                return Usuario(nombre =  username, saldo = saldo, historial = Historial(mutableListOf()), hashPassword = hash)
            } catch (ex : RuntimeException){
                println("Cancelando creación de usuario.")
                println(ex.message)
                return null
            }
        }


        /**
         * Inicia sesión de un usuario.
         */
        fun iniciaSesion(nombre : String): Usuario? {
            val archivoUsuario = File("./.Usuarios/$nombre.json")
            if(!archivoUsuario.exists()) {
                println("El usuario no existe")
                return null
            }
            val file = FileReader("./.Usuarios/$nombre.json")
            val prov = Json.decodeFromString<Usuario>(archivoUsuario.readText())
            var intentos = 0
            while(intentos < 3){
                println("Ingresa la contraseña")
                val password = leeContrasena()
                if(password.hash512() == prov.hashPassword) {
                    file.close()
                    return prov
                }
                println("Contraseña Incorrecta, Vuelve a intentar")
                intentos += 1
            }
            file.close()
            println("Limite de intentos alcanzados")
            return null
        }
    }


    /**
     * Guarda el usuario en el directorio de los usuarios.
     */
    fun guarda(){
        val archivoGuarda = FileWriter("./.Usuarios/${this.nombre}.json")
        archivoGuarda.write(Json.encodeToString(this))
        archivoGuarda.close()
    }

    fun agregarSaldo() {
        TODO("Not yet implemented")
    }

    fun consultaHistorial() {
        TODO("Not yet implemented")
    }
}
