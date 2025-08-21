package retrofit.models

import com.google.gson.annotations.SerializedName

data class BaseContent(
    @SerializedName("calculator_form_title")
    val calculatorFormTitle: String?,

    @SerializedName("calculator_form_entry")
    val calculatorFormEntry: String?,

    @SerializedName("calculator_form_visiting")
    val calculatorFormVisiting: String?,

    @SerializedName("calculator_form_travel")
    val calculatorFormTravel: String?,

    @SerializedName("calculator_form_birthday")
    val calculatorFormBirthday: String?,

    @SerializedName("calculator_form_cover")
    val calculatorFormCover: String?,

    @SerializedName("calculator_form_start")
    val calculatorFormStart: String?,

    @SerializedName("calculator_form_end")
    val calculatorFormEnd: String?,

    @SerializedName("calculator_form_error_end")
    val calculatorFormErrorEnd: String?,

    @SerializedName("calculator_form_submit")
    val calculatorFormSubmit: String?,

    @SerializedName("calculator_result_deductible")
    val calculatorResultDeductible: String?,

    @SerializedName("calculator_result_unit")
    val calculatorResultUnit: String?,

    @SerializedName("calculator_result_total")
    val calculatorResultTotal: String?,

    @SerializedName("calculator_result_buy")
    val calculatorResultBuy: String?,

    @SerializedName("calculator_result_details")
    val calculatorResultDetails: String?,

    @SerializedName("inquiry_age")
    val inquiryAge: String?,

    @SerializedName("inquiry_cover")
    val inquiryCover: String?,

    @SerializedName("inquiry_days")
    val inquiryDays: String?,

    @SerializedName("inquiry_back")
    val inquiryBack: String?,

    @SerializedName("inquiry_next")
    val inquiryNext: String?,

    @SerializedName("inquiry_step1_title")
    val inquiryStep1Title: String?,

    @SerializedName("inquiry_step1_header")
    val inquiryStep1Header: String?,

    @SerializedName("inquiry_step1_description")
    val inquiryStep1Description: String?,

    @SerializedName("inquiry_step1_agreement")
    val inquiryStep1Agreement: String?,

    @SerializedName("inquiry_step2_title")
    val inquiryStep2Title: String?,

    @SerializedName("inquiry_step2_basic")
    val inquiryStep2Basic: String?,

    @SerializedName("inquiry_step2_family")
    val inquiryStep2Family: String?,

    @SerializedName("inquiry_step2_no")
    val inquiryStep2No: String?,

    @SerializedName("inquiry_step2_yes")
    val inquiryStep2Yes: String?,

    @SerializedName("inquiry_step2_people")
    val inquiryStep2People: String?,

    @SerializedName("inquiry_step2_cover")
    val inquiryStep2Cover: String?,

    @SerializedName("inquiry_step2_address")
    val inquiryStep2Address: String?,

    @SerializedName("inquiry_step2_city")
    val inquiryStep2City: String?,

    @SerializedName("inquiry_step2_province")
    val inquiryStep2Province: String?,

    @SerializedName("inquiry_step2_postalcode")
    val inquiryStep2PostalCode: String?,

    @SerializedName("inquiry_step2_phone")
    val inquiryStep2Phone: String?,

    @SerializedName("inquiry_step2_email")
    val inquiryStep2Email: String?,

    @SerializedName("inquiry_step2_firstname")
    val inquiryStep2FirstName: String?,

    @SerializedName("inquiry_step2_lastname")
    val inquiryStep2LastName: String?,

    @SerializedName("inquiry_step2_gender")
    val inquiryStep2Gender: String?,

    @SerializedName("inquiry_step2_male")
    val inquiryStep2Male: String?,

    @SerializedName("inquiry_step2_female")
    val inquiryStep2Female: String?,

    @SerializedName("inquiry_step2_birthday")
    val inquiryStep2Birthday: String?,

    @SerializedName("inquiry_step2_insured")
    val inquiryStep2Insured: String?,

    @SerializedName("inquiry_step2_beneficiaries")
    val inquiryStep2Beneficiaries: String?,

    @SerializedName("inquiry_step2_alert")
    val inquiryStep2Alert: String?,

    @SerializedName("inquiry_step2_policy")
    val inquiryStep2Policy: String?,

    @SerializedName("inquiry_step2_start")
    val inquiryStep2Start: String?,

    @SerializedName("inquiry_step2_end")
    val inquiryStep2End: String?,

    @SerializedName("inquiry_step2_arrival")
    val inquiryStep2Arrival: String?,

    @SerializedName("inquiry_step2_error_end")
    val inquiryStep2ErrorEnd: String?,

    @SerializedName("inquiry_step2_error_arrival")
    val inquiryStep2ErrorArrival: String?,

    @SerializedName("inquiry_step3_title")
    val inquiryStep3Title: String?,

    @SerializedName("inquiry_step3_fullname")
    val inquiryStep3FullName: String?,

    @SerializedName("inquiry_step3_gender")
    val inquiryStep3Gender: String?,

    @SerializedName("inquiry_step3_birthday")
    val inquiryStep3Birthday: String?,

    @SerializedName("inquiry_step3_insured")
    val inquiryStep3Insured: String?,

    @SerializedName("inquiry_step3_beneficiaries")
    val inquiryStep3Beneficiaries: String?,

    @SerializedName("inquiry_step3_policy")
    val inquiryStep3Policy: String?,

    @SerializedName("inquiry_step3_start")
    val inquiryStep3Start: String?,

    @SerializedName("inquiry_step3_end")
    val inquiryStep3End: String?,

    @SerializedName("inquiry_step3_arrival")
    val inquiryStep3Arrival: String?,

    @SerializedName("inquiry_step3_deductible")
    val inquiryStep3Deductible: String?,

    @SerializedName("inquiry_step3_pay")
    val inquiryStep3Pay: String?,

    @SerializedName("inquiry_step3_methods")
    val inquiryStep3Methods: String?,

    @SerializedName("inquiry_step3_credit")
    val inquiryStep3Credit: String?,

    @SerializedName("inquiry_step3_bank")
    val inquiryStep3Bank: String?,

    @SerializedName("inquiry_step3_card_number")
    val inquiryStep3CardNumber: String?,

    @SerializedName("inquiry_step3_card_cvv")
    val inquiryStep3CardCvv: String?,

    @SerializedName("inquiry_step3_card_name")
    val inquiryStep3CardName: String?,

    @SerializedName("inquiry_step3_card_expiration")
    val inquiryStep3CardExpiration: String?,

    @SerializedName("inquiry_step3_error_card_number")
    val inquiryStep3ErrorCardNumber: String?,

    @SerializedName("inquiry_step3_error_plan")
    val inquiryStep3ErrorPlan: String?,

    @SerializedName("inquiry_step3_transfer")
    val inquiryStep3Transfer: String?,

    @SerializedName("inquiry_step3_message")
    val inquiryStep3Message: String?,

    @SerializedName("inquiry_step4_title")
    val inquiryStep4Title: String?,

    @SerializedName("inquiry_step4_greeting")
    val inquiryStep4Greeting: String?,

    @SerializedName("inquiry_step4_message")
    val inquiryStep4Message: String?,

    @SerializedName("inquiry_step4_thanks")
    val inquiryStep4Thanks: String?,

    @SerializedName("inquiry_step4_back")
    val inquiryStep4Back: String?
)
