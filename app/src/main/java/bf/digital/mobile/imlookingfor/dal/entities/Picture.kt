package bf.digital.mobile.imlookingfor.dal.entities

class Picture {
    var idPicture: String? = null
    var pathPicture: String? = null
    var ownerId: String? = null
    var isMainPicture = false

    constructor(
        idPicture: String?,
        pathPicture: String?,
        ownerId: String?,
        isMainPicture: Boolean
    ) {
        this.idPicture = idPicture
        this.pathPicture = pathPicture
        this.ownerId = ownerId
        this.isMainPicture = isMainPicture
    }

    constructor() {}

    override fun toString(): String {
        return "Picture{" +
                "idPicture='" + idPicture + '\'' +
                ", pathPicture='" + pathPicture + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", isMainPicture=" + isMainPicture +
                '}'
    }
}