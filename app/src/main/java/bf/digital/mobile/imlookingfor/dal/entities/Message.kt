package bf.digital.mobile.imlookingfor.dal.entities

class Message {
    var idMessage: String? = null
    var idCompany: String? = null
    var idModel: String? = null
    var textMessage: String? = null

    constructor(idMessage: String?, idCompany: String?, idModel: String?, textMessage: String?) {
        this.idMessage = idMessage
        this.idCompany = idCompany
        this.idModel = idModel
        this.textMessage = textMessage
    }

    constructor() {}

    override fun toString(): String {
        return "Message{" +
                "idMessage='" + idMessage + '\'' +
                ", idCompany='" + idCompany + '\'' +
                ", idModel='" + idModel + '\'' +
                ", textMessage='" + textMessage + '\'' +
                '}'
    }
}