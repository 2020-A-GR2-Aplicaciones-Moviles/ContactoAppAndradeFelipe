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

    fun sum(a: Int, b:Int) : Int {
        return a + b
    }
    // forma simplificada.
    fun sum1(a: Int, b: Int) = a + b

    fun presentGently(v: Any) {
        println("Hello. I would like to present you: $v")
    }

    fun imprimir() {
        presentGently("World") // Hello. I would like to present you: World
        presentGently(42) // Hello. I would like to present you: 42

        printValue("str", suffix = "!") // Prints: (str)!
    }

    fun printValue(value: String, inBracket: Boolean = true,
                   prefix: String = "", suffix: String = "") {
        print(prefix)
        if (inBracket) {
            print("(${value})")
        } else {
            print(value)
        }
        println(suffix)
    }
//funciones anidadas
    fun printTwoThreeTimes() {
        fun printThree() {
            print(3)
        }
        printThree()
        printThree()
    }








}