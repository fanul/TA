
import java.io.*;
import java.net.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// This class downloads a file from a URL.
class Download {

    public void getRSS(String inputURL, String outputFile) {
        try {
            URL downloadLink = new URL(inputURL);
            ReadableByteChannel rbc = Channels.newChannel(downloadLink.openStream());
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.getChannel().transferFrom(rbc, 0, 1 << 24);
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public static void main(String args[]) throws MalformedURLException, IOException {

        URL google = new URL("http://www.jpnn.com/index.php?mib=rss&id=215");
        ReadableByteChannel rbc = Channels.newChannel(google.openStream());
        FileOutputStream fos = new FileOutputStream("tes.xml");
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);

    }
}
