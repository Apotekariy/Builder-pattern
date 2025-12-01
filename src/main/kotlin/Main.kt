package org.example

//Builder паттерн Kotlin DSL
data class Car(
    val brand: String,
    val model: String,
    val engineType: String
) {
    class Builder {
        var brand: String = ""
        var model: String = ""
        var engineType: String = ""

        fun build(): Car {
            require(brand.isNotBlank()) { "Brand cannot be empty" }
            require(model.isNotBlank()) { "Model cannot be empty" }
            require(engineType.isNotBlank()) { "Engine type cannot be empty" }
            return Car(brand, model, engineType)
        }
    }

    companion object {
        inline fun build(block: Builder.() -> Unit): Car {
            return Builder().apply(block).build()
        }
    }

    override fun toString(): String {
        return "Car(brand='$brand', model='$model', engineType='$engineType')"
    }
}

// Abstract Factory паттерн
interface CarFactory {
    fun createCar(model: String, engineType: String): Car
}

class BMWFactory : CarFactory {
    override fun createCar(model: String, engineType: String): Car {
        return Car.build {
            brand = "BMW"
            this.model = model
            this.engineType = engineType
        }
    }
}

class MercedesFactory : CarFactory {
    override fun createCar(model: String, engineType: String): Car {
        return Car.build {
            brand = "Mercedes"
            this.model = model
            this.engineType = engineType
        }
    }
}

// Конкретная фабрика для Toyota
class ToyotaFactory : CarFactory {
    override fun createCar(model: String, engineType: String): Car {
        return Car.build {
            brand = "Toyota"
            this.model = model
            this.engineType = engineType
        }
    }
}

// Пример использования
fun main() {
    println("Builder Pattern")
    // Создание через Builder с DSL
    val customCar = Car.build {
        brand = "Custom"
        model = "Roadster"
        engineType = "Electric"
    }
    println(customCar)

    println("Abstract Factory Pattern")
    val factories: List<CarFactory> = listOf(
        BMWFactory(),
        MercedesFactory(),
        ToyotaFactory()
    )

    val bmwCar = factories[0].createCar("X5", "Diesel")
    val mercedesCar = factories[1].createCar("E-Class", "Petrol")
    val toyotaCar = factories[2].createCar("Camry", "Hybrid")

    println(bmwCar)
    println(mercedesCar)
    println(toyotaCar)
}