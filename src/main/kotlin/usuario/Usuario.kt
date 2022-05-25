package usuario

import kotlinx.serialization.*


@Serializable
data class Usuario(val name : String, var saldo : Double){

}
