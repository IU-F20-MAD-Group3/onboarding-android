package university.innopolis.madgroup3.onboarding.network.responses

import university.innopolis.madgroup3.onboarding.data.models.Token

data class TokenResponse(
    val token: Token?,
    val error: Throwable?
)