import kotlinx.serialization.json.Json
import usuario.Usuario
import java.io.FileWriter
import kotlinx.serialization.*
import java.io.FileReader

fun main(){
    println("Nombre:")
    val usr  = Usuario(saldo = 70.0, name = readln())
    usr.saldo = readln().toDouble()
    val archivo = FileWriter("./Usuarios/${usr.name}.json")
    val archivo2 = FileReader("./Usuarios/${usr.name}.json")
    archivo.write(Json.encodeToString(usr))
    archivo.close()
    val usr2 = Json.decodeFromString<Usuario>(archivo2.readText())

    println(usr)
    println(usr == usr2)
}