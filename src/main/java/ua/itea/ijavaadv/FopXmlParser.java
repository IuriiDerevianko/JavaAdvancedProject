package ua.itea.ijavaadv;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


/**
 * Created
 * at 19:31
 * on 11.03.17
 * by Iurii Derevianko;
 */

class FopXmlParser extends DefaultHandler {

    private final int FOP_LIST_MAX_SIZE = 999_999;
    private int counter = 0;

    private boolean searchNameMark = false;
    private boolean searchAddressMark = false;
    private boolean searchActivityMark = false;
    private boolean searchStatusMark = false;

    private String thisElement = "";

    private String name = "";
    private String address = "";
    private String activity = "";
    private String status = "";

    private String searchName = "";
    private String searchAddress = "";
    private String searchActivity = "";
    private String searchStatus = "";

    private List<Fop> fopList = new ArrayList<>();


    void searchData(String searchName, String searchAddress, String searchActivity, String searchStatus) {
        this.searchName = searchName;
        this.searchAddress = searchAddress;
        this.searchActivity = searchActivity;
        this.searchStatus = searchStatus;
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse FopXML...");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElement = "";
    }


    boolean searchFop (String field, String searchField){
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


        if (thisElement.equals("ПІБ")) {
            name += new String(ch, start, length) + "; ";
            searchNameMark = searchFop(name, searchName);
        }

        if (thisElement.equals("Місце_проживання")) {
            address += new String(ch, start, length) + "; ";
            searchAddressMark = searchFop(address, searchAddress);
        }

        if (thisElement.equals("Основний_вид_діяльності")) {
            activity += new String(ch, start, length) + "; ";
            searchActivityMark = searchFop(activity, searchActivity);
        }

        if (thisElement.equals("Стан")) {
            status += new String(ch, start, length) + "; ";
            searchStatusMark = searchFop(status, searchStatus);
        }


        if(thisElement.equals("ROW") && searchNameMark && searchAddressMark
                && searchActivityMark && searchStatusMark) {
            System.out.println(name);
            counter++;
            if((fopList.size() < FOP_LIST_MAX_SIZE) && (counter !=1)){
                fopList.add(new Fop(name, address, activity, status));
            }
            if (fopList.size() == FOP_LIST_MAX_SIZE) {
                name = "";
                address = "";
                activity = "";
                status = "FOP_LIST_MAX_SIZE = 1 000 000;";
                fopList.add(new Fop(name, address, activity, status));
                searchName = "";
                searchAddress = "";
                searchActivity = "";
                searchStatus = "";
                System.out.println("FOP_LIST_MAX_SIZE = 1 000 000;");
            }

            name = "";
            address = "";
            activity = "";
            status = "";
            searchNameMark = false;
            searchAddressMark = false;
            searchActivityMark = false;
            searchStatusMark = false;
        } else {
            if(thisElement.equals("ROW")) {
                counter++;
                name = "";
                address = "";
                activity = "";
                status = "";
                searchNameMark = false;
                searchAddressMark = false;
                searchActivityMark = false;
                searchStatusMark = false;
            }
        }
    }

    @Override
    public void endDocument() {
        if(searchNameMark && searchAddressMark && searchActivityMark
                && searchStatusMark && (fopList.size() < FOP_LIST_MAX_SIZE)) {
            fopList.add(new Fop(name, address, activity, status));
        }
        name = "";
        address = "";
        activity = "";
        status = "";
        searchNameMark = false;
        searchAddressMark = false;
        searchActivityMark = false;
        searchStatusMark = false;
        System.out.println("Stop parse FopXML.");
    }

    List<Fop> getFopList() {
        return fopList;
    }

    int getCounter() {
        return counter;
    }
}