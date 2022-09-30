package bf.digital.mobile.imlookingfor.dal.entities

class Interest(var idInterest: String, var nameInterest: String, var model: String) {

    override fun toString(): String {
        return "Interest{" +
                "idInterest='" + idInterest + '\'' +
                ", nameInterest='" + nameInterest + '\'' +
                ", model='" + model + '\'' +
                '}'
    }
}