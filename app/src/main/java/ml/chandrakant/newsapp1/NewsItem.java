package ml.chandrakant.newsapp1;

public class NewsItem {
    private String authorName;
    private String sectionName;
    private String webUrl;
    private String webTitle;
    private String dateAndTime;

    public NewsItem(String authorName, String sectionName, String webUrl, String webTitle, String dateAndTime) {
        this.authorName = authorName;
        this.sectionName = sectionName;
        this.webUrl = webUrl;
        this.webTitle = webTitle;
        this.dateAndTime = dateAndTime;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}
