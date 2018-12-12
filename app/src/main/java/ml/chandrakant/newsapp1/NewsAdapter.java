package ml.chandrakant.newsapp1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    NewsAdapter(Context context, ArrayList<NewsItem> newsItemList) {
        super(context, 0, newsItemList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,@NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        final NewsItem latestNews = getItem(position);

        if (latestNews != null) {
            // Title of news.
            TextView titleTextView = listItemView.findViewById(R.id.title_text_view);
            titleTextView.setText(latestNews.getWebTitle());

            // Section
            TextView sectionTextView = listItemView.findViewById(R.id.section_text_view);
            sectionTextView.setText(latestNews.getSectionName());

            // Author
            TextView authorTextView = listItemView.findViewById(R.id.author_text_view);
            if (!latestNews.getAuthorName().equals("")) {
                authorTextView.setText(latestNews.getAuthorName());
            } else {
                authorTextView.setVisibility(View.GONE);
            }

            // Date and Time
            TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
            TextView timeTextView = listItemView.findViewById(R.id.time_text_view);

            if (!latestNews.getDateAndTime().equals("")) {
                // Date and Time are separated by alphabets 'T' and 'Z'
                int indexOfT = latestNews.getDateAndTime().indexOf('T');
                int indexOfZ = latestNews.getDateAndTime().indexOf('Z');

                // Date is ranging  from index 0 to indexOfT.
                dateTextView.setText(latestNews.getDateAndTime().substring(0, indexOfT));

                // Time is ranging  from (indexOfT + 1) to indexOfZ excluding letter T and Z and seconds.
                timeTextView.setText(latestNews.getDateAndTime().substring(indexOfT + 1, indexOfZ - 3));
            } else {
                // If Date and Time not available then free up layout space.
                dateTextView.setVisibility(View.GONE);
                timeTextView.setVisibility(View.GONE);
            }
        } else {
            listItemView.setVisibility(View.GONE);
        }

        return listItemView;

    }
}