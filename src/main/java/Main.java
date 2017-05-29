import org.apache.commons.validator.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by mc on 5/25/2017.
 */

public class Main {

    public static void main(String[] args) throws Exception {
        String url ;//= "https://www.pucmm.edu.do";
        System.out.println("Escriba una url valida: ");
        Scanner in = new Scanner(System.in);
        url = in.next();

        String[] schemes = {"http","https"};
        UrlValidator urlValidator = new UrlValidator(schemes);

        while (!urlValidator.isValid(url)) {
            System.out.println("Escriba una url valida: ");
            in = new Scanner(System.in);
            url = in.next();
        }
        Document doc = Jsoup.connect(url).get();
        int cantlienas = doc.html().split("\n").length;
        System.out.println("El documento fuente tiene: "+cantlienas+" lineas");

        Elements parrafos = doc.getElementsByTag("p");

        System.out.println("El documento fuente tiene: "+ parrafos.size()+" parrafos");

        System.out.println("El documento fuente tiene: "+parrafos.select("img").size()+" imagenes dentro de parrafos");

        Elements posts = doc.select("form[method= POST]");
        System.out.println("El documento fuente tiene: "+posts.size()+" Forms con el metodo POST");
        Elements gets = doc.select("form[method= GET]");
        System.out.println("El documento fuente tiene: "+gets.size()+" Forms con el metodo GET");

        for (Element po: posts) {
            for(Element e : po.getElementsByAttribute("type").select("input"))
                System.out.println( " " +e.attr("type"));
        }

        for (Element ge: gets) {
            for(Element e : ge.getElementsByAttribute("type").select("input"))
                System.out.println( " " +e.attr("type"));
        }

        for(Element post : posts){
            int l = post.attr("action").length();
            String action = post.attr("action") ;
            if(Character.toString(post.attr("action").charAt(0)).matches("."))
                action = post.attr("action").toString().substring(1,l);

            Document d = Jsoup.connect(url + action).data("asignatura","practica1").post();
            // Connection.Response d = Jsoup.connect(url + action).data("asignatura","practica1").method(Connection.Method.POST).execute();
            System.out.println(d);
        }
    }

}

