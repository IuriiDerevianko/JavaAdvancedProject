package ua.itea.ijavaadv;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;


/**
 * Created
 * at 0:29
 * on 18.03.17
 * by Iurii Derevianko;
 */

public class Controller {

    //----------------------Fop-----------------------

    @FXML
    private TextField textFieldFopName;

    @FXML
    private TextField textFieldFopAddress;

    @FXML
    private TextField textFieldFopActivity;

    @FXML
    private TextField textFieldFopStatus;

    @FXML
    private Button buttonFopSearch;

    @FXML
    private Button buttonFopStop;

    @FXML
    private ListView<Fop> listViewFop;

    @FXML
    private TextField textFieldFopFound;

    @FXML
    private TextField textFieldFopAll;

    @FXML
    private TextField textFieldFopTimeSearch;

    @FXML
    private ProgressBar progressBarFopSearch;


    //----------------------Uo-----------------------

    @FXML
    private TextField textFieldUoFullName;

    @FXML
    private TextField textFieldUoShortName;

    @FXML
    private TextField textFieldUoCode;

    @FXML
    private TextField textFieldUoAddress;

    @FXML
    private TextField textFieldUoChiefName;

    @FXML
    private TextField textFieldUoActivity;

    @FXML
    private TextField textFieldUoStatus;

    @FXML
    private Button buttonSearchUo;

    @FXML
    private Button buttonStopUo;

    @FXML
    private ListView<Uo> listViewUo;

    @FXML
    private TextField textFieldUoFound;

    @FXML
    private TextField textFieldUoAll;

    @FXML
    private TextField textFieldUoTimeSearch;


    @FXML
    private void initialize() {
        textFieldFopName.setText("");
        textFieldFopAddress.setText("Київ");
        textFieldFopActivity.setText("Комп'ютерне програмування");
        textFieldFopStatus.setText("зареєстровано");

        textFieldUoFullName.setText("");
        textFieldUoShortName.setText("");
        textFieldUoCode.setText("");
        textFieldUoAddress.setText("Київ");
        textFieldUoChiefName.setText("");
        textFieldUoActivity.setText("Комп'ютерне програмування");
        textFieldUoStatus.setText("зареєстровано");
    }


    @FXML
    public void searchFop() throws SAXException, ParserConfigurationException, IOException {

        //progressBarFopSearch.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

        long firstTime = System.currentTimeMillis();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        saxParser.getXMLReader().setFeature("http://apache.org/xml/features/allow-java-encodings",true);
        XMLReader xmlReader = saxParser.getXMLReader();

        FopXmlParser fopXmlParser = new FopXmlParser();
        xmlReader.setContentHandler(fopXmlParser);

        fopXmlParser.searchData(textFieldFopName.getText(), textFieldFopAddress.getText(),
                textFieldFopActivity.getText(), textFieldFopStatus.getText());

        //xmlReader.parse("/media/derevianko/3C4EAFE44EAF94E4/ITEA/Java_advanced_project/src/main/resources/fopTest.xml");
        xmlReader.parse("/media/derevianko/3C4EAFE44EAF94E4/ITEA/Java_advanced_project_xml/15-UFOP/FOP_1.xml");
        xmlReader.parse("/media/derevianko/3C4EAFE44EAF94E4/ITEA/Java_advanced_project_xml/15-UFOP/FOP_2.xml");
        xmlReader.parse("/media/derevianko/3C4EAFE44EAF94E4/ITEA/Java_advanced_project_xml/15-UFOP/FOP_3.xml");


        ObservableList<Fop> fopList = FXCollections.observableList(fopXmlParser.getFopList());
        listViewFop.setItems(fopList);

        long secondTime = System.currentTimeMillis() - firstTime;

        textFieldFopFound.setText(fopXmlParser.getFopList().size() + "");
        textFieldFopAll.setText(fopXmlParser.getCounter() + "");
        textFieldFopTimeSearch.setText((double)(secondTime/1000) + " s");
    }



    public void searchUo() throws SAXException, ParserConfigurationException, IOException {

        long firstTime = System.currentTimeMillis();

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        saxParser.getXMLReader().setFeature("http://apache.org/xml/features/allow-java-encodings",true);
        XMLReader xmlReader = saxParser.getXMLReader();

        UoXmlParser uoXmlParser = new UoXmlParser();
        xmlReader.setContentHandler(uoXmlParser);

        uoXmlParser.searchData(textFieldUoFullName.getText(), textFieldUoShortName.getText(),
                textFieldUoCode.getText(), textFieldUoAddress.getText(), textFieldUoChiefName.getText(),
                textFieldUoActivity.getText(), textFieldUoStatus.getText());

        //xmlReader.parse("/media/derevianko/3C4EAFE44EAF94E4/ITEA/Java_advanced_project/src/main/resources/uoTest.xml");
        xmlReader.parse("/media/derevianko/3C4EAFE44EAF94E4/ITEA/Java_advanced_project_xml/15-UFOP/UO.xml");


        ObservableList<Uo> uoList = FXCollections.observableList(uoXmlParser.getUoList());
        listViewUo.setItems(uoList);

        long secondTime = System.currentTimeMillis() - firstTime;

        textFieldUoFound.setText(uoXmlParser.getUoList().size() + "");
        textFieldUoAll.setText(uoXmlParser.getCounter() + "");
        textFieldUoTimeSearch.setText((double)(secondTime/1000) + " s");
    }
}
