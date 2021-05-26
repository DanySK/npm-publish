package test.sandbox

import io.ktor.http.HttpMethod

fun main(args: Array<String>) {
  println(args)
}

@JsName("direct")
@JsExport
fun direct() {
  println("Ktor method: ${HttpMethod.Get}")
}
