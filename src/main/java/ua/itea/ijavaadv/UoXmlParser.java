package ua.itea.ijavaadv;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created
 * at 16:05
 * on 17.03.17
 * by Iurii Derevianko;
 */
class UoXmlParser extends DefaultHandler {


    private final int UO_LIST_MAX_SIZE = 499_999;
    private int counter = 0;

    private boolean searchFullNameMark = false;
    private boolean searchShortNameMark = false;
    private boolean searchCodeMark = false;
    private boolean searchAddressMark = false;
    private boolean searchChiefNameMark = false;
    private boolean searchActivityMark = false;
    private boolean searchStatusMark = false;

    private String thisElement = "";

    private String fullName = "";
    private String shortName = "";
    private String code = "";
    private String address = "";
    private String chiefName = "";
    private String activity = "";
    private String status = "";

    private String searchFullName = "";
    private String searchShortName = "";
    private String searchCode = "";
    private String searchAddress = "";
    private String searchChiefName = "";
    private String searchActivity = "";
    private String searchStatus = "";

    private List<Uo> uoList = new ArrayList<>();


    void searchData(String searchFullName, String searchShortName, String searchCode, String searchAddress,
                    String searchChiefName, String searchActivity, String searchStatus) {
        this.searchFullName = searchFullName;
        this.searchShortName = searchShortName;
        this.searchCode = searchCode;
        this.searchChiefName = searchChiefName;
        this.searchAddress = searchAddress;
        this.searchActivity = searchActivity;
        this.searchStatus = searchStatus;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse UoXML...");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElement = "";
    }


    boolean searchUo (String field, String searchField){
        if(searchField.equals("")) {
            return true;
        } else {
            if(searchField.equalsIgnoreCase(field)) {
                return true;
            } else {
                char[] fieldArray = field.toUpperCase().toCharArray();
                char[] searchFieldArray = searchField.toUpperCase().toCharArray();
                int counter = 0;
                int j = 0;
                for (int i = 0; (i < fieldArray.length) && (j < searchFieldArray.length); i++) {
                    if(fieldArray[i] == searchFieldArray[j]) {
                        counter++;
                        j++;
                        if(counter == searchFieldArray.length) {
                            return true;
                        }
                    } else {
                        counter = 0;
                        j = 0;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public synchronized void characters(char[] ch, int start, int length) throws SAXException {

        // fullName (Найменування),
        // shortName (Скорочена_назва),
        // code (Код_ЄДРПОУ),
        // address (Місцезнаходження),
        // chiefName (ПІБ_керівника),
        // activity (Основний_вид_діяльності),
        // status (Стан).


        if (thisElement.equals("Найменування")) {
            fullName += new String(ch, start, length) + "; ";
            searchFullNameMark = searchUo(fullName, searchFullName);
        }

        if (thisElement.equals("Скорочена_назва")) {
            shortName = new String(ch, start, length);
            searchShortNameMark = searchUo(shortName, searchShortName);
        }

        if (thisElement.equals("Код_ЄДРПОУ")) {
            code = new String(ch, start, length);
            searchCodeMark = searchUo(code, searchCode);
        }

        if (thisElement.equals("Місцезнаходження")) {
            address = new String(ch, start, length);
            searchAddressMark = searchUo(address, searchAddress);
        }

        if (thisElement.equals("ПІБ_керівника")) {
            chiefName = new String(ch, start, length);
            searchChiefNameMark = searchUo(chiefName, searchChiefName);
        }

        if (thisElement.equals("Основний_вид_діяльності")) {
            activity = new String(ch, start, length);
            searchActivityMark = searchUo(activity, searchActivity);
        }

        if (thisElement.equals("Стан")) {
            status = new String(ch, start, length);
            searchStatusMark = searchUo(status, searchStatus);
        }


        if(thisElement.equals("ROW") && searchFullNameMark && searchShortNameMark && searchCodeMark
                && searchAddressMark && searchChiefNameMark && searchActivityMark && searchStatusMark) {
            System.out.println(shortName);
            counter++;
            if ((uoList.size() < UO_LIST_MAX_SIZE) && (counter != 1)) {
                uoList.add(new Uo(fullName, shortName, code, address, chiefName, activity, status));
            }
            if ((uoList.size() == UO_LIST_MAX_SIZE)) {

                fullName = "";
                shortName = "";
                code = "";
                address = "";
                chiefName = "";
                activity = "";
                status = "UO_LIST_MAX_SIZE = 500 000;";

                uoList.add(new Uo(fullName, shortName, code, address, chiefName, activity, status));

                searchFullName = "";
                searchShortName = "";
                searchCode = "";
                searchAddress = "";
                searchChiefName = "";
                searchActivity = "";
                searchStatus = "";
                System.out.println("UO_LIST_MAX_SIZE = 500 000;");
            }

            fullName = "";
            shortName = "";
            code = "";
            address = "";
            chiefName = "";
            activity = "";
            status = "";
            searchFullNameMark = false;
            searchShortNameMark = false;
            searchCodeMark = false;
            searchAddressMark = false;
            searchChiefNameMark = false;
            searchActivityMark = false;
            searchStatusMark = false;
        } else {
            if(thisElement.equals("ROW")) {
                counter++;
                fullName = "";
                shortName = "";
                code = "";
                address = "";
                chiefName = "";
                activity = "";
                status = "";
                searchFullNameMark = false;
                searchShortNameMark = false;
                searchCodeMark = false;
                searchAddressMark = false;
                searchChiefNameMark = false;
                searchActivityMark = false;
                searchStatusMark = false;
            }
        }
    }

    @Override
    public void endDocument() {
        if(thisElement.equals("ROW") && searchFullNameMark && searchShortNameMark && searchCodeMark
                && searchAddressMark && searchChiefNameMark && searchActivityMark && searchStatusMark
                && (uoList.size() < UO_LIST_MAX_SIZE)) {
            uoList.add(new Uo(fullName, shortName, code, address, chiefName, activity, status));
        }
        fullName = "";
        shortName = "";
        code = "";
        address = "";
        chiefName = "";
        activity = "";
        status = "";
        searchFullNameMark = false;
        searchShortNameMark = false;
        searchCodeMark = false;
        searchAddressMark = false;
        searchChiefNameMark = false;
        searchActivityMark = false;
        searchStatusMark = false;
        System.out.println("Stop parse UoXML.");
    }


    List<Uo> getUoList() {
        return uoList;
    }


    int getCounter() {
        return counter;
    }
}
