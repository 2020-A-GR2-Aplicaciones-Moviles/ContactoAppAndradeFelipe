package com.andradefelipe.contactoappandradefelipe.ejercicios

class EjemploClases {

    class Outer1 {
        private val bar: Int = 1
        class Nested {
            fun foo() = 2
        }
    }
    val demo1 = Outer1.Nested().foo() // == 2

    class Outer2 {
        private val bar: Int = 1
        inner class Inner {
            fun foo() = bar
        }
    }
    val outer = Outer2()
    val demo2 = outer.Inner().foo() // == 1

    open class Base {
        open fun v() {}
        fun nv() {}
    }
    class Derived() : Base() {
        override fun v() {}
    }

    object DataProviderManager {
        fun registerDataProvider(provider: String) {
            // …
        }
    }


    data class Product(var name: String, var price: Double)
    fun ManageProduct() {
        val productA = Product("Spoon", 30.2)
        println(productA) //toString(), prints: Product(name=Spoon, price=30.2)
        val productB = productA.copy()
        print(productB) // prints: Product(name=Spoon, price=30.2)
        val productC = productA.copy(price = 24.0, name = "Knife")
        print(productB) // prints: Product(name=Knife, price=24.0)
    }

}