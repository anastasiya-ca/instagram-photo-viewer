package com.sunnydaycorp.instagramphotoviewer;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;

public class InstagramResponseProcessor extends Activity {

	public static List<InstagramMedia> processPopularPhotosResponse(JSONObject response) throws JSONException {
		List<InstagramMedia> popularPhotos = new ArrayList<InstagramMedia>();

		JSONArray photosJSON = response.getJSONArray("data");
		for (int i = 0; i < photosJSON.length(); i++) {
			JSONObject photoJSON = photosJSON.getJSONObject(i);
			InstagramMedia photo = new InstagramMedia();

			if (!photoJSON.isNull("user")) {
				JSONObject user = photoJSON.getJSONObject("user");
				photo.setUserName(user.optString("username"));
				photo.setProfilePicUrl(user.optString("profile_picture"));
			}
			if (!photoJSON.isNull("caption")) {
				photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
			}
			if (!photoJSON.isNull("images")) {
				JSONObject images = photoJSON.getJSONObject("images");
				if (!images.isNull("standard_resolution")) {
					JSONObject standardResolution = images.getJSONObject("standard_resolution");
					photo.setImageUrl(standardResolution.optString("url"));
					photo.setImageHeight(standardResolution.optInt("height"));
					photo.setImageWidth(standardResolution.optInt("width"));
				}
			}
			if (!photoJSON.isNull("likes")) {
				photo.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));
			}

			if (!photoJSON.isNull("location")) {
				photo.setLocationName(photoJSON.getJSONObject("location").optString("name"));
			}

			if (!photoJSON.isNull("created_time")) {
				photo.setPostedDateTime(new DateTime(photoJSON.getLong("created_time") * 1000));
			}

			if (!photoJSON.isNull("comments")) {
				List<InstagramMediaComment> comments = new ArrayList<InstagramMediaComment>();
				JSONArray commentsJSON = photoJSON.getJSONObject("comments").getJSONArray("data");
				for (int k = 0; k < commentsJSON.length(); k++) {
					JSONObject commentJSON = commentsJSON.getJSONObject(k);
					DateTime createdTime = new DateTime(commentJSON.getLong("created_time") * 1000);
					String text = commentJSON.getString("text");
					String authorName = commentJSON.getJSONObject("from").getString("username");
					comments.add(new InstagramMediaComment(createdTime, text, authorName));
				}
				photo.setComments(comments);
			}

			popularPhotos.add(photo);
		}
		return popularPhotos;
	}

}
