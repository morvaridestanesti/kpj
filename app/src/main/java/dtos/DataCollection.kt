package dtos

import ir.ncis.kpjapp.App

data class DataCollection(
    var address: String = "",
    var arrivedAt: String = "",
    var birthdays: String = "",
    var cardCVV: String = "",
    var cardExpiration: String = "",
    var cardName: String = "",
    var cardNumber: String = "",
    var city: String = "",
    var deductibleIds: String = "",
    var email: String = "",
    var endedAt: String = "",
    var firstNames: String = "",
    var genders: String = "",
    var insuranceCover: Int = 0,
    var isEntry: Int = App.IS_ENTRY,
    var lastNames: String = "",
    var message: String = "Inquiry",
    var phone: String = "",
    var postalCode: String = "",
    var priceIds: String = "",
    var provinceId: Int = 0,
    var startedAt: String = "",
    var transferPassword: String = "",
)
