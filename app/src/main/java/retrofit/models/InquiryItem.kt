package retrofit.models

data class InquiryItem(
    val company: Company,
    val deductibles: List<Deductible>,
    val plans: List<PlanWrapper>
)
