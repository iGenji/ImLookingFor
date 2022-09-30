package bf.digital.mobile.imlookingfor.dal.entities

import java.time.LocalDate

class Like {
    private var idLike: String? = null
    private var idCompany: String? = null
    private var idModel: String? = null
    private var creationDate: String? = null
    private var matchDate: String? = null

    constructor(idLike: String?, idCompany: String?, idModel: String?, matchDate: String?) {
        this.idLike = idLike
        this.idCompany = idCompany
        this.idModel = idModel
        creationDate = LocalDate.now().toString()
        this.matchDate = matchDate
    }

    constructor() {}
}