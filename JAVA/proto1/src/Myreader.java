
import org.xml.sax.helpers.DefaultHandler;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fanul
 */

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Stack;
import java.util.Vector;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.events.StartDocument;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Myreader extends DefaultHandler{
    
    boolean in_rss = false;
     boolean in_channel = false;
     boolean in_title = false;
     boolean in_description = false;
     boolean in_link = false;
     boolean in_build_date = false;
     boolean in_generator = false;
     boolean in_image = false;
     boolean in_image_url = false;
     boolean in_image_title = false;
     boolean in_image_link = false;
     boolean in_image_description = false;
     boolean in_copyright = false;
     boolean in_item = false;
     boolean in_item_title = false;
     boolean in_item_link = false;
     boolean in_item_description = false;
     boolean in_category = false;
     boolean in_pubdate = false;
     boolean in_url = false;
     public boolean is_finish = false;

     int titleDepth=0;
     int linkDepth=0;
     int descriptionDepth=0;

     int titleLine= 0;
     int newsLine = 0;


     private String tempTextTitle = "";
     private String tempTextLink = "";
     private String tempTextDescription = "";

     private Berita RSS = new Berita();
     private Stack<Berita> Feeds;
    
     public Myreader() {
        Feeds = new Stack<Berita>();
    }

    public void printAll()
    {
        for(int i=0; i<Feeds.size(); i++)
        {
            System.out.println(Feeds.get(i).getTite());
        }
    }

    public String getNewsTitle(int i)
    {
        System.out.println(Feeds.get(i).getTite());
        return Feeds.get(i).getTite();
    }

    public Stack<Berita> getList()
    {
        return Feeds;
    }

    public int getListCount()
    {
        return Feeds.size();
    }

    public void getFeed(String alamat,boolean useproxy, String link) throws ParserConfigurationException, SAXException, IOException
    {
        /* ---- local ----
        SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
        saxParser.parse("/home/fanul/Documents/tes/feed.xml",this);
         */

        /* internasional */
        SAXParserFactory factory = null;
        SAXParser sAXParser = null;
        InputStream urlinputstream = null;
        Properties sysProperties = System.getProperties();

        try {
            URL url = new URL(link);
            if(useproxy)
            {
                sysProperties.put("proxyHost", "10.151.34.14");
                sysProperties.put("proxyPort", "1111");
                System.setProperties(sysProperties);
                System.out.println(System.getProperties().toString());
            }
            else
            {
                sysProperties.remove("proxyHost");
                sysProperties.remove("proxyPort");
            }

            urlinputstream = url.openConnection().getInputStream();
            factory = SAXParserFactory.newInstance();
            if(factory!=null)
            {
                sAXParser = factory.newSAXParser();
                sAXParser.parse(urlinputstream, this);
            }

        } catch (Exception e) {
        }

    }

    @Override
    public void startDocument() throws SAXException{
        System.out.println("Mulai membaca");
    }

    @Override
    public void endDocument() throws SAXException{
        System.out.println("Selesei membaca");
        is_finish = true;
    }

    @Override
    public void startElement(String uri, String localName,String qName,
            Attributes attributes) throws SAXException {

            //System.out.println("Start Element :" + qName);
            if(qName.equalsIgnoreCase("rss"))
            {
                    in_rss = true;

            }
            else if(qName.equalsIgnoreCase("rss"))
            {
                    in_channel = true;
            }
            else if(qName.equalsIgnoreCase("title"))
            {
                    ++titleDepth;
                    if(titleDepth == 1) { in_title=true; /*System.out.print("\nmasuk title\n");*/ }
                    else if(titleDepth == 2) { in_image_title=true; /*System.out.print("\n----->masuk image title\n");*/}
                    else if(titleDepth > 2) { in_item_title=true; /*System.out.print("\n----->----->masuk item title\n");*/}
                    //System.out.print(qName);
            }
            else if(qName.equalsIgnoreCase("description"))
            {
                    ++descriptionDepth;
                    if(descriptionDepth == 1) in_description=true;
                    else if(descriptionDepth == 2) in_image_description=true;
                    else if(descriptionDepth > 2) in_item_description=true;
            }
            else if(qName.equalsIgnoreCase("link"))
            {
                    ++linkDepth;
                    if(linkDepth == 1) in_link=true;
                    else if(linkDepth == 2) in_image_link=true;
                    else if(linkDepth > 2) in_item_link=true;
            }
            else if(qName.equalsIgnoreCase("lastbuilddate"))
            {
                    in_build_date = true;
            }
            else if(qName.equalsIgnoreCase("generator"))
            {
                    in_generator = true;
            }
            else if(qName.equalsIgnoreCase("image"))
            {
                    in_image = true;
            }
            else if(qName.equalsIgnoreCase("item"))
            {
                    in_item = true;
            }
            else if(qName.equalsIgnoreCase("url"))
            {
                    in_url = true;
            }
            else if(qName.equalsIgnoreCase("category"))
            {
                    in_category = true;
            }
            else if(qName.equalsIgnoreCase("pubdate"))
            {
                    in_pubdate = true;
            }

    }

    @Override
    public void endElement(String uri, String localName,
            String qName) throws SAXException {

            //System.out.println("End Element :" + qName);

         if(qName.equalsIgnoreCase("rss"))
            {
                    in_rss = false;

            }
            else if(qName.equalsIgnoreCase("rss"))
            {
                    in_channel = false;
            }
            else if(qName.equalsIgnoreCase("title"))
            {
                    if(titleDepth == 1) { in_title=false; /*System.out.print("\nkeluar title\n");*/ }
                    else if(titleDepth == 2) { in_image_title=false; /*System.out.print("\n----->keluar image title\n");*/}
                    else if(titleDepth > 2) {in_item_title=false; /*System.out.print("\n----->----->keluar item title\n");*/}
                    titleLine=0;
                    //System.out.print(qName);
            }
            else if(qName.equalsIgnoreCase("description"))
            {
                    if(descriptionDepth == 1) in_description=false;
                    else if(descriptionDepth == 2) in_image_description=false;
                    else if(descriptionDepth > 2) in_item_description=false;
                    newsLine=0;
            }
            else if(qName.equalsIgnoreCase("link"))
            {
                    if(linkDepth == 1) in_link=false;
                    else if(linkDepth == 2) in_image_link=false;
                    else if(linkDepth > 2) in_item_link=false;
            }
            else if(qName.equalsIgnoreCase("lastbuilddate"))
            {
                    in_build_date = false;
            }
            else if(qName.equalsIgnoreCase("generator"))
            {
                    in_generator = false;
            }
            else if(qName.equalsIgnoreCase("image"))
            {
                    in_image = false;
            }
            else if(qName.equalsIgnoreCase("item"))
            {
                    in_item = false;
                    if(!tempTextTitle.equalsIgnoreCase("")&&!tempTextLink.equalsIgnoreCase("")&&!tempTextDescription.equalsIgnoreCase(""))
                    Feeds.push(new Berita(tempTextTitle, tempTextLink, tempTextDescription.trim().replaceAll("&quot", "").replaceAll("&ldquo", "")));

//                    System.out.println(Feeds.get(0).getTite());
//                    System.out.println("-----------");
//                    System.out.println(Feeds.get(0).getDescription());

                    tempTextDescription = "";
                    tempTextLink = "";
                    tempTextTitle = "";


            }
            else if(qName.equalsIgnoreCase("url"))
            {
                    in_url = false;
            }
            else if(qName.equalsIgnoreCase("category"))
            {
                    in_category = false;
            }
            else if(qName.equalsIgnoreCase("pubdate"))
            {
                    in_pubdate = false;
            }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if(in_item_title)
        {
           //RSS.appendTitle(new String(ch, start, length));
           //System.out.println(new String(ch, start, length));
            tempTextTitle+=new String(ch, start, length);
        }
        else if(in_item_description)
        {
           //RSS.appendDescription(new String(ch, start, length));
            tempTextDescription+=new String(ch, start, length);
        }
        else if(in_item_link)
        {
            //RSS.appendLink(new String(ch, start, length));
            tempTextLink+=new String(ch, start, length);
        }
    }
     
}
