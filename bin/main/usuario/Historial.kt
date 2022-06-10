package usuario

import kotlinx.serialization.*

@Serializable
data class Historial(val historial : MutableList<Apuesta>) {
    
}