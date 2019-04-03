package app.collector;

import app.model.BuildDetailsModel;
import app.model.GitDetails;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XMLToObjectConvertor {


    public static BuildDetailsModel buildJenkinsDetails(String xmlString) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        BuildDetailsModel model = new BuildDetailsModel();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8)));
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        XPathExpression expr = xpath.compile("//flow-build/timestamp/text()");
        Object result = expr.evaluate(doc, XPathConstants.NODE);
        Node node = (Node) result;
        System.out.println("timeStamp : "+node.getNodeValue());
        model.setTimestamp(Long.valueOf(node.getNodeValue()));

        expr = xpath.compile("//flow-build/result/text()");
        result = expr.evaluate(doc, XPathConstants.NODE);
        node = (Node) result;
        System.out.println("result : "+node.getNodeValue());
        model.setResult(node.getNodeValue());

        expr = xpath.compile("//flow-build/duration/text()");
        result = expr.evaluate(doc, XPathConstants.NODE);
        node = (Node) result;
        System.out.println("duration : "+node.getNodeValue());
        model.setDuration(Long.valueOf(node.getNodeValue()));

        expr = xpath.compile("//flow-build/actions/hudson.plugins.git.util.BuildData/remoteUrls/string/text()");
        result = expr.evaluate(doc, XPathConstants.NODESET);
        GitDetails gitDetails = new GitDetails();
        NodeList nodeList = (NodeList) result;
        if (nodeList.item(0)!=null) {
            System.out.println("Repo name : " + nodeList.item(0).getNodeValue());
            gitDetails.setRepo(nodeList.item(0).getNodeValue());
        }
        else{
            gitDetails.setRepo("unknown repo");
        }

        model.setGitDetails(gitDetails);

        return model;
    }
}
