@file:Suppress("PackageDirectoryMismatch")

package playground.kotlinx.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToMap
import playground.shouldBe

/**
 * Kotlin/kotlinx.serialization : Kotlin multiplatform / multi-format serialization
 *
 * - [GitHub](https://github.com/Kotlin/kotlinx.serialization)
 * - [Kotlin Serialization Guide](https://github.com/Kotlin/kotlinx.serialization/blob/master/docs/serialization-guide.md)
 * - [CHANGELOG](https://github.com/Kotlin/kotlinx.serialization/blob/master/CHANGELOG.md)
 */
fun main() {
    println()
    println("# Kotlin/kotlinx.serialization : Kotlin multiplatform / multi-format serialization")
    val user = User(name = "Robert", age = 42)
    val json = """{"name":"Robert","age":42}"""

    Json.encodeToString(user) shouldBe json
    Json.decodeFromString<User>(json) shouldBe user

    serializeWithProperties()
}

@OptIn(ExperimentalSerializationApi::class)
fun serializeWithProperties() {
    val user = User(name = "Robert", age = 42)
    val users = Users(2, listOf(user, user.copy(name = "Richard")))
    val map = Properties.encodeToMap(users)
    map shouldBe mapOf(
        "size" to 2,
        "users.0.name" to "Robert",
        "users.0.age" to 42,
        "users.1.name" to "Richard",
        "users.1.age" to 42
    )
}


@Serializable
internal data class User(
    val name: String,
    val age: Int
)

@Serializable
internal data class Users(
    val size: Int,
    val users: List<User> = emptyList()
)

@Serializable
data class HttpBinGet(
    val args: Map<String, String> = emptyMap(),
    val headers: Map<String, String> = emptyMap(),
    val origin: String,
    val url: String
)
