/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fanul
 */
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AutoUpdater extends Thread {

    private static final int WAIT_LENGTH = 1000;
    private static final int UPDATE_TIME = 3600;
    
    private main _main;
    private int _perupdate;
    private String _link;
    private String _file;
    private Lzw _prepare;
    private boolean _stop;

    public AutoUpdater(main initFrame, int updateTime) {
        this._main = initFrame;
        this._perupdate = updateTime;
        this._prepare = new Lzw(12);
        this._stop = false;
    }

    public AutoUpdater(main initFrame) {
        this._main = initFrame;
    }

    public void setTime(int time) {
        _perupdate = time;
    }

    public void setsomething(String text) {
        this._main.jButton1.setText(text);
        if (this._main.useproxy.isSelected()) {
            this._main.useproxy.setSelected(false);
        } else {
            this._main.useproxy.setSelected(true);
        }
    }

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

    public void getAllRSS() {
        getRSS(_main.BERITA_TERKINI, "xml/berita_terkini.xml");
        getRSS(_main.EKONOMI, "xml/ekonomi.xml");
        getRSS(_main.HIBURAN, "xml/hiburan.xml");
        getRSS(_main.KRIMINAL, "xml/kriminal.xml");
        getRSS(_main.NUSANTARA, "xml/nusantara.xml");
        getRSS(_main.OLAHRAGA, "xml/olahraga.xml");
        getRSS(_main.POLITIK, "xml/politik.xml");
        getRSS(_main.TEKNOLOGI, "xml/teknologi.xml");
    }

    public void compressAllFile() {
        _prepare.compressFile("xml/berita_terkini.xml", "compress/berita_terkini.compress");
        _prepare.compressFile("xml/ekonomi.xml", "compress/ekonomi.compress");
        _prepare.compressFile("xml/hiburan.xml", "compress/hiburan.compress");
        _prepare.compressFile("xml/kriminal.xml", "compress/kriminal.compress");
        _prepare.compressFile("xml/nusantara.xml", "compress/nusantara.compress");
        _prepare.compressFile("xml/olahraga.xml", "compress/olahraga.compress");
        _prepare.compressFile("xml/politik.xml", "compress/politik.compress");
        _prepare.compressFile("xml/teknologi.xml", "compress/teknologi.compress");
    }

    public void requestStop() {
        _stop = true;
    }

    @Override
    public void run() {
        System.out.println("Thread Start");
        if (_stop) {
            _stop = false;
        }
        int index = 0;
        int countUpdate = 0;
        String txtUpdate;
        while (!_stop) {
            try {
                //setsomething(Integer.toString(index++));
                index++;
                if ((index % Integer.parseInt(_main.boxUpdateTIme.getText())) == 0) {

                    txtUpdate = Integer.toString(++countUpdate);
                    // _main.getNews();
                    //_main.downloadXML();


                    //==== ndapetin RSS ====//
                    //getAllRSS();

                    //==== ngompress semua file ====//
                    compressAllFile();

                    _main._statusUpdate.setText("Updating - " + txtUpdate + " Time");
                    System.out.println("From Updater Thread: updating++");

                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            } finally {
                try {
                    Thread.sleep(WAIT_LENGTH);
                } catch (InterruptedException ex) {
                    Logger.getLogger(AutoUpdater.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (_stop) {
            System.out.println("stop requested");
            System.out.println(_perupdate);
        }

    }
}
