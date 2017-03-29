package ua.itea.ijavaadv;

/**
 * Created
 * at 19:18
 * on 24.03.17
 * by Iurii Derevianko;
 */
class Fop {
    private String name;
    private String address;
    private String activity;
    private String status;

    Fop(String name, String addres, String activity, String status){
        this.name = name;
        this.address = addres;
        this.activity = activity;
        this.status = status;
    }

    @Override
    public String toString(){
        return (name + "\n" + address + "\n" + activity + "\n" + status+ "\n");
    }
}
