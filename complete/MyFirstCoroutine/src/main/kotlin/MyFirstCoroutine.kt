import kotlinx.coroutines.*
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext


fun main() {


    val job1: Job = Job()

    val scope = CoroutineScope(Dispatchers.Default + job1)
    //scope.logContext(scope.toString())

    val job2 = scope.launch(start = CoroutineStart.LAZY) {

        //logContext(toString())
        logMessage("Hello")
        delay(1000)

    }

    scope.launch {

        val jobs: List<Job> = (1..5).map {
            launch(start = CoroutineStart.LAZY) {
                logMessage("Started Coroutine $it")
                delay(100)
                logMessage("Ended Coroutine $it")
            }
        }

        logMessage("Created all Coroutines")
        jobs.joinAll()
        logMessage("Finished all Coroutines")


    }


    Thread.sleep(2000)
    logMessage("world")


}


fun logMessage(msg: String) {
    println("Running on: [${Thread.currentThread().name}] | $msg")
}


fun CoroutineScope.logContext(id: String) {
    coroutineContext.logDetails(id)
}


fun CoroutineContext.logDetails(id: String) {
    sequenceOf(
        Job,
        ContinuationInterceptor,
        CoroutineExceptionHandler,
        CoroutineName
    )
        .mapNotNull { key -> this[key] }
        .forEach { logMessage("id: $id ${it.key} = ${it}") }
}