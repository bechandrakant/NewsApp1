package ml.chandrakant.newsapp1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<NewsItem>> {

    public NewsAdapter newsAdapter;
    public ListView newsListView;
    private TextView statusTextView;
    private ProgressBar progressBar;

    private static final String GUARDIAN_URL_STRING =
            "https://content.guardianapis.com/search?api-key=test&show-tags=contributor";

    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this, GUARDIAN_URL_STRING);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        statusTextView.setText(R.string.news_unavailable);
        newsAdapter.clear();

        progressBar.setVisibility(View.GONE);

        if (data != null && !data.isEmpty()) {
            newsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        newsAdapter.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

        progressBar = findViewById(R.id.loading_progress_bar);

        newsAdapter = new NewsAdapter(this, new ArrayList<NewsItem>());
        newsListView = findViewById(R.id.news_list);
        newsListView.setAdapter(newsAdapter);

        statusTextView = findViewById(R.id.status_text_view);
        newsListView.setEmptyView(statusTextView);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsItem currentNews = newsAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getWebUrl()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, currentNews.getWebUrl(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (isConnected) {
            getSupportLoaderManager().initLoader(0, null, this);
        } else {
            progressBar.setVisibility(View.GONE);
            statusTextView.setText(R.string.connection_unavailable);
        }
    }
}
