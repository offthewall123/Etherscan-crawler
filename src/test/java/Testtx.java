import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

public class Testtx {


    @Test
    public void Testtx() throws IOException {
        String url="https://etherscan.io/tx/0xcff0c8dc400d4a4cb0ea514ca1bdd842497003923b7da53d74836265c0926baf";
        Document document=Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(3000)
                .post();
//        Element element=document.getElementById("inputdata");
//        System.out.println(element.text());

//#ContentPlaceHolder1_maintable > div:nth-child(10) > a
        Element elements=document.getElementById("ContentPlaceHolder1_maintable");

        Elements element1s=elements.select("div.col-sm-9")
                .select("a");
        for(Element e:element1s){
            if(e.attr("href").contains("address")){
                System.out.println(e.text());
            }
        }

//        System.out.println(element1s);


    }
}
