package dtos

import ir.ncis.kpjapp.App

data class DataCollection(
    var address: String = "",
    var arrivedAt: String = "",
    var birthdays: String = "",
    var cardCVV: String? = null,
    var cardExpiration: String? = null,
    var cardName: String? = null,
    var cardNumber: String? = null,
    var city: String = "",
    var deductibleIds: String = "",
    var email: String = "",
    var endedAt: String = "",
    var firstNames: String = "",
    var genders: String = "",
    var insuranceCover: Int = 0,
    var isEntry: Int = App.IS_ENTRY,
    var lastNames: String = "",
    var message: String? = null,
    var phone: String? = null,
    var postalCode: String? = null,
    var priceIds: String? = null,
    var provinceId: Int? = 0,
    var startedAt: String = "",
    var transferPassword: String? = null,
)
