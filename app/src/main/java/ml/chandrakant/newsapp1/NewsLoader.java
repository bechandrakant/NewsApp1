package ml.chandrakant.newsapp1;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {
    private String urlString;

    public NewsLoader(Context context, String urlString) {
        super(context);
        this.urlString = urlString;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {
        return QueryUtils.fetchNewsData(urlString);
    }
}