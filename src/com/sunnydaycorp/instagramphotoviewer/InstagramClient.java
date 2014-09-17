package com.sunnydaycorp.instagramphotoviewer;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class InstagramClient extends Activity {

	private static final String LOG_TAG = InstagramClient.class.getSimpleName();

	private static final String INSTAGRAM_CLIENT_ID = "ae65b9655a3b4b4eb2704fdc008d658d";
	private static final String INSTAGRAM_POPULAR_PHOTOS_URL = "https://api.instagram.com/v1/media/popular?client_id=" + INSTAGRAM_CLIENT_ID;

	private final AsyncHttpClient client;
	private final InstagramClientListener listener;

	public InstagramClient(InstagramClientListener listener) {
		this.listener = listener;
		this.client = new AsyncHttpClient();
	}

	public interface InstagramClientListener {
		public void onPopularPhotosFetched(List<InstagramMedia> popularPhotos);

		public void onError();
	}

	public void fetchPopularPhotos() {
		client.get(INSTAGRAM_POPULAR_PHOTOS_URL, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				try {
					List<InstagramMedia> popularPhotos = InstagramResponseProcessor.processPopularPhotosResponse(response);
					if (listener != null) {
						listener.onPopularPhotosFetched(popularPhotos);
					}
				} catch (JSONException e) {
					Log.e(LOG_TAG, "Error processing Instagram popular photor JSON response", e);
					onError();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
				Log.e(LOG_TAG, errorResponse.toString(), throwable);
				onError();
			}
		});
	}

	private void onError() {
		if (listener != null) {
			listener.onError();
		}
	}

}
