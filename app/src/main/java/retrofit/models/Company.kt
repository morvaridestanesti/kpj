package retrofit.models

data class Company(
    val id: Int,
    val slug: String,
    val logo: String,
    val url: String,
    val name: String,
    val description: String,
    val title: String? = null
)
