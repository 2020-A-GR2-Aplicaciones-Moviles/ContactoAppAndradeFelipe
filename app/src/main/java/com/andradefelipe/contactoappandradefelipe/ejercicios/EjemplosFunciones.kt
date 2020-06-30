package com.andradefelipe.contactoappandradefelipe.ejercicios

class EjemplosFunciones {

    fun EjemploFuncionesTipo1()
    {
        lateinit var a: (Int) -> Int
        lateinit var b: ()->Int
        lateinit var c: (String)->Unit

        a = fun(i: Int) = i * 2
        b = fun(): Int { return 4 }
        c = fun(s: String){ println(s) }

        // Usage
        println(a(10)) // Prints: 20
        println(b()) // Prints: 4
        c("Kotlin rules") // Prints: Kotlin rules
    }

}