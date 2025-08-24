package retrofit.models

data class SupportedPlan(
    val company: Company,
    val deductibles: List<Deductible>,
    val plans: List<PlanWrapper>,
)