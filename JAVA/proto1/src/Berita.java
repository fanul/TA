/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fanul
 */
public class Berita {

    
    public static final int END_DIV_POSITIO = 4;
    
    private String title;
    private String link;
    private String description;
    private String city;
    private boolean valid = false;

    public Berita() {
        title = link = description = "";
    }

    public Berita(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;

    }

    public void appendTitle(String data) {
        title += data;
    }

    public void appendLink(String data) {
        link += data;
    }

    public void appendDescription(String data) {
        description += data;
    }

    public void setTitle(String data) {
        this.title = data;
    }

    public void setLink(String data) {
        this.link = data;
    }

    public void setDescription(String data) {
        this.description = data;
    }

    public String getTite() {
        return this.title;
    }

    public String getlink() {
        return this.link;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCity() {
        return this.city;
    }

    public boolean isValid()
    {
        return this.valid;
    }
    
    public String getPureDescription() {
        return description.substring(getStartDescriptionPosition(), description.length());
    }

    public int getStartDescriptionPosition() {
        int endDivPos = getEndDivPosition();
        String rawString = description.substring(endDivPos, description.length());
        for (int i = 0; i < rawString.length(); i++) {
            if (rawString.charAt(i) == '-') {
                if (rawString.charAt(i + 2) != ' ' && rawString.charAt(i + 2) != '-') {
                    return (endDivPos + i + 2);
                }
            } else if (rawString.charAt(i) == '&') {
                    return (endDivPos + i + 8);
            }
        }
        return 0;
    }

    public String getImageFromDescription() {
        String buffString = "";
        int count = 1;
        for (int i = 0; i < this.description.length(); i++) {
            if (count == 2 && this.description.charAt(i) != '>') {
                buffString += this.description.charAt(i);
            } else if (count == 2 && this.description.charAt(i) == '>') {
                break;
            }

            if (this.description.charAt(i) == '>') {
                //System.out.println('>');
                count++;
            }

            //System.out.print(this.description.charAt(i));
        }

        String[] rawString = buffString.split(" ");
        String proceed = rawString[1].substring(5, rawString[1].length() - 1);
        return proceed;
    }

    public int getEndDivPosition() {
        int count = 1, position = 0;
        for (int i = 0; i < this.description.length(); i++) {
            if (this.description.charAt(i) == '>') {
                count++;
            }
            if (count == END_DIV_POSITIO) {
                position = i + 1;
                break;
            }
        }
        return position;
    }

    public String getCityFromDescription() {
        String rawString = this.description.substring(getEndDivPosition(), getEndDivPosition() + 50);
        String[] cekThis1 = rawString.split("-");
        String[] cekThis2 = rawString.split("&ndash;");
        if (cekThis1.length > 1) {
            return cekThis1[0].trim();
        } else if (cekThis2.length > 1) {
            return cekThis2[0].trim();
        } else {
            return null;
        }
    }

    public void setValid() {
        if (getCityFromDescription() != null) {
            this.city = getCityFromDescription();
            this.valid = true;
        } else {
            this.city = null;
            this.valid = false;
        }
    }
}
