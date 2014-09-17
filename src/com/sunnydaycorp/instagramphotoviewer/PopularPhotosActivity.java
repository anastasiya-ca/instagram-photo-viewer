package com.sunnydaycorp.instagramphotoviewer;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ListView;

import com.sunnydaycorp.instagramphotoviewer.InstagramClient.InstagramClientListener;

public class PopularPhotosActivity extends Activity {

	private SwipeRefreshLayout swipeContainer;
	private ListView lvPhotos;
	private InstagramClient instagramClient;

	private InstagramClientListener listener = new InstagramClientListener() {
		@Override
		public void onPopularPhotosFetched(List<InstagramMedia> popularPhotos) {
			PopularPhotosListItemAdapter adapter = new PopularPhotosListItemAdapter(PopularPhotosActivity.this, popularPhotos);
			lvPhotos.setAdapter(adapter);
			swipeContainer.setRefreshing(false);
		}

		@Override
		public void onError() {
			// TODO display relevant message to the user
			swipeContainer.setRefreshing(false);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_popular_photos);

		lvPhotos = (ListView) findViewById(R.id.lvPopularPhotos);

		instagramClient = new InstagramClient(listener);
		instagramClient.fetchPopularPhotos();

		swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
		swipeContainer.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				instagramClient.fetchPopularPhotos();
			}
		});

		// TODO change color scheme
		swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

	}

}
