package bf.digital.mobile.imlookingfor.dal.entities

import java.time.LocalDate

class Model {
    var idModel: String? = null
    var mail: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var instagram: String? = null
    var weight : String? = null
    var height : String? = null
    var eyeColor: String? = null
    var ethnicity: String? = null
    var skinColor: String? = null
    var age : String? = null
    var gender: String? = null
    var descriptionModel: String? = null
    var dateJoined //not java.time but org.threeten.bp
            : String? = null
    var imageUrl: String? = null;
    constructor(
        idModel: String?,
        mail: String?,
        firstName: String?,
        lastName: String?,
        instagram: String?,
        weight: String,
        height: String,
        eyeColor: String?,
        ethnicity: String?,
        skinColor: String?,
        age: String,
        gender: String?,
        descriptionModel: String?,
        imageUrl: String?
    ) {
        this.idModel = idModel
        this.mail = mail
        this.firstName = firstName
        this.lastName = lastName
        this.instagram = instagram
        this.weight = weight
        this.height = height
        this.eyeColor = eyeColor
        this.ethnicity = ethnicity
        this.skinColor = skinColor
        this.age = age
        this.gender = gender
        this.descriptionModel = descriptionModel
        dateJoined = LocalDate.now().toString()
        this.imageUrl = imageUrl;
    }

    constructor() {}

    override fun toString(): String {
        return "Model{" +
                "idModel='" + idModel + '\'' +
                ", mail='" + mail + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", instagram='" + instagram + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", eyeColor='" + eyeColor + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", skinColor='" + skinColor + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", descriptionModel='" + descriptionModel + '\'' +
                ", date joined=" + dateJoined +
                '}'
    }
}