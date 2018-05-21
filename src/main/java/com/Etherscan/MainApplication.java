package com.Etherscan;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainApplication {
    public static void main(String[] args) throws IOException {
        String txh="transactionHash";
        String inputdata="inputdata";
        String filepath="src\\main\\result\\result.txt";
        //相对路径要从src开始写！！！！！！！！！！!!!!!!!!!!!
        File file=new File(filepath);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedOutputStream out=new BufferedOutputStream(new FileOutputStream(filepath,true));

        for(int pageNumber=1;pageNumber<=1000;pageNumber++){
            String MAINURL="https://etherscan.io/txs?p="+pageNumber;
            Document document=Jsoup.connect(MAINURL)
                    .userAgent("Mozilla/5.0")
                    .timeout(50000000)
                    .get();
            Elements elements=document.select("div.row");
            Elements elements1=elements.select("span.address-tag").select("a");
            for(Element e:elements1) {
                String url = e.attr("abs:href");
                if (url.contains("tx")) {
                    Document doc = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0")
                            .timeout(50000000)
                            .get();
                    Element element = doc.getElementById("inputdata");//input data字段


                    Elements elements2 = doc.getElementById("ContentPlaceHolder1_maintable")
                            .select("div.col-sm-9")
                            .select("a");


                    int count = 0;
                    String from = "";
                    String to = "";
                    for (Element element1 : elements2) {
                        if (element1.attr("href").contains("address")) {
                            if (count == 0) {
                                from = element1.text();
                                count++;
                            } else {
                                to = element1.text();
                            }
                        }
                    }

                    out.write("\n".getBytes());
                    out.write(url.getBytes());
                    out.write("\n".getBytes());

                    out.write("from :".getBytes());
                    out.write(from.getBytes());
                    out.write("\n".getBytes());

                    out.write("to   :".getBytes());
                    out.write(to.getBytes());
                    out.write("\n".getBytes());

                    out.write("inputdata: ".getBytes());
                    out.write(element.text().getBytes());
                    out.write("\n".getBytes());

//                    System.out.println(url);
//                    System.out.println("from :"+from);
//                    System.out.println("to   :"+to);
//                    System.out.println("inputdata: "+element.text());
                }

            }
            System.out.println(pageNumber+"finished");
        }
        out.close();
    }
}
