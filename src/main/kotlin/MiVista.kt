import javafx.beans.property.SimpleStringProperty
import tornadofx.*

class MiVista : View() {
    override val root = vbox {
        val controller: MyController  = MyController()
        val input = SimpleStringProperty()

        val root = form {
            fieldset {
                field("Input") {
                    textfield(input)
                }

                button("Commit") {
                    action {
                        controller.writeToDb(input.value)
                        input.value = ""
                    }
                }
            }
        }
    }


    class MyController: Controller() {
        fun writeToDb(inputValue: String) {
            println("Writing $inputValue to database!")
        }
    }
}
