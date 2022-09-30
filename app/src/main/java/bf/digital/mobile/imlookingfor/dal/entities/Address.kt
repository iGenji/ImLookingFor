package bf.digital.mobile.imlookingfor.dal.entities

class Address {
    var idAddress: String? = null
    var street: String? = null
    var numberStreet = 0
    var zipCode = 0
    var city: String? = null
    var country: String? = null
    var ownerModel: String? = null
    var ownerCompany: String? = null

    constructor(
        idAddress: String?,
        street: String?,
        numberStreet: Int,
        zipCode: Int,
        city: String?,
        country: String?,
        ownerModel: String?,
        ownerCompany: String?
    ) {
        this.idAddress = idAddress
        this.street = street
        this.numberStreet = numberStreet
        this.zipCode = zipCode
        this.city = city
        this.country = country
        this.ownerModel = ownerModel
        this.ownerCompany = ownerCompany
    }

    constructor() {}

    override fun toString(): String {
        return "Address{" +
                "idAddress='" + idAddress + '\'' +
                ", street='" + street + '\'' +
                ", numberStreet=" + numberStreet +
                ", zipCode=" + zipCode +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", ownerModel='" + ownerModel + '\'' +
                ", ownerCompany='" + ownerCompany + '\'' +
                '}'
    }
}