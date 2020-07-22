package com.andradefelipe.contactoappandradefelipe.ejercicios

class EjemploIf {

    fun EjemploIf() {
        val age = 18
        val message = "You are ${if (age < 18) "a young " else "an adult"} person"
        println(message) // Prints: You are an adult person
    }

}