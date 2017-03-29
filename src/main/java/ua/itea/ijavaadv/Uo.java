package ua.itea.ijavaadv;

/**
 * Created
 * at 0:47
 * on 26.03.17
 * by Iurii Derevianko;
 */

class Uo {

    private String fullName;
    private String shortName;
    private String code;
    private String address;
    private String chiefName;
    private String activity;
    private String status;



    Uo(String fullName, String shortName, String code, String address, String chiefName, String activity, String status){
        this.fullName = fullName;
        this.shortName = shortName;
        this.code = code;
        this.address = address;
        this.chiefName = chiefName;
        this.activity = activity;
        this.status = status;
    }

    @Override
    public String toString(){
        return (fullName + "\n" + shortName  + "\n" + code + "\n" + address  + "\n" + chiefName
                + "\n" + activity + "\n" + status+ "\n");
    }
}
