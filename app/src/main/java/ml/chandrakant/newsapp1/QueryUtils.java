package ml.chandrakant.newsapp1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public final class QueryUtils {

    public static ArrayList<NewsItem> fetchNewsData(String requestedUrl) {

        // Wait for 1 seconds to display progress bar.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        URL connectionUrl = createUrl(requestedUrl);

        String jsonResponse = createHttpRequest(connectionUrl);

        ArrayList<NewsItem> newsData = extractNewsItem(jsonResponse);

        return newsData;
    }

    private static String createHttpRequest(URL url) {
        String jsonResponse = "";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(3000);
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            jsonResponse = convertStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonResponse;
    }

    private static String convertStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static ArrayList<NewsItem> extractNewsItem(String jsonResponse) {
        ArrayList<NewsItem> newsArrayList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray results = jsonObject.getJSONObject("response")
                                            .getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject news = results.getJSONObject(i);
                String webUrl = news.getString("webUrl");
                String webTitle = news.getString("webTitle");
                String authorName = "";

                //Check for author of the news article
                if (news.has("tags")) {
                    if (news.getJSONArray("tags").length() > 0) {
                        authorName = news.getJSONArray("tags").getJSONObject(0).getString("webTitle");
                    }
                }

                String sectionName = news.getString("sectionName");

                String dateAndTime = "";

                //Checking if the news article has a publication date and time
                if (news.has("webPublicationDate")) {
                    dateAndTime = news.getString("webPublicationDate");
                }

                NewsItem currentNewsItem = new NewsItem(authorName, sectionName, webUrl, webTitle, dateAndTime);
                newsArrayList.add(currentNewsItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsArrayList;
    }

    private static URL createUrl(String urlString) {
        URL connectedUrl = null;

        try {
            connectedUrl = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return connectedUrl;
    }
}