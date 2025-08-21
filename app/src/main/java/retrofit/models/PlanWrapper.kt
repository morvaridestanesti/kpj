package retrofit.models

data class PlanWrapper(
    val plan: Plan,
    val prices: List<Price>
)
