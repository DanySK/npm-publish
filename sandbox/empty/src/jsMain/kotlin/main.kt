import sandbox.GreetingArgs
import sandbox.checkEven
import sandbox.greet

@JsModule("is-odd")
public external object IsOdd

@OptIn(ExperimentalJsExport::class)
@JsExport
public fun empty(): Unit = greet(object : GreetingArgs {
  override val name: String = "Joe"
  override val sureName: String = "Mama"
}).also {
  console.log(IsOdd)
  console.log("is 2 even = ${checkEven(2)}")
}