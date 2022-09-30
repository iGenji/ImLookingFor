package bf.digital.mobile.imlookingfor.dal.entities

import java.time.LocalDate

class Company {
    var idCompany: String? = null
    var nameCompany: String? = null
    var mail: String? = null
    var instagram: String? = null
    var descriptionCompany: String? = null
    var picture: String? = null
    var dateJoined: String? = null
    var imageUrl: String? = null

    constructor(
        idCompany: String?,
        nameCompany: String?,
        mail: String?,
        instagram: String?,
        descriptionCompany: String?,
        picture: String?,
        imageUrl: String?
    ) {
        this.idCompany = idCompany
        this.nameCompany = nameCompany
        this.mail = mail
        this.instagram = instagram
        this.descriptionCompany = descriptionCompany
        this.picture = picture
        dateJoined = LocalDate.now().toString()
        this.imageUrl = imageUrl;
    }

    constructor() {}

    override fun toString(): String {
        return "Company{" +
                "idCompany='" + idCompany + '\'' +
                ", name='" + nameCompany + '\'' +
                ", mail='" + mail + '\'' +
                ", instagram='" + instagram + '\'' +
                ", descriptionCompany='" + descriptionCompany + '\'' +
                ", picture='" + picture + '\'' +
                ", dateJoined=" + dateJoined +
                '}'
    }
}