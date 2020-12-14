package university.innopolis.madgroup3.onboarding.network.responses

data class ResponseWrapper<T>(
    val response: T?,
    val error: Throwable?,
)
