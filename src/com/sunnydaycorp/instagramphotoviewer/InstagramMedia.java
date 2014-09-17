package com.sunnydaycorp.instagramphotoviewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

public class InstagramMedia {

	private static final int LATEST_COMMENT_MAX_COUNT = 2;

	private String userName;
	private String caption;
	private String imageUrl;
	private String profilePicUrl;
	private int imageHeight;
	private int imageWidth;
	private int likesCount;
	private String locationName;
	private DateTime postedDateTime;
	private List<InstagramMediaComment> comments;
	private List<InstagramMediaComment> latestComments;

	public List<InstagramMediaComment> getComments() {
		return comments;
	}

	public void setComments(List<InstagramMediaComment> comments) {
		if (comments != null) {
			this.comments = comments;
		} else {
			this.comments = new ArrayList<InstagramMediaComment>();
		}
		Collections.sort(comments);
		populateLatestComments();
	}

	private void populateLatestComments() {
		if (comments.size() > 0) {
			latestComments = comments.subList(0, comments.size() > LATEST_COMMENT_MAX_COUNT ? LATEST_COMMENT_MAX_COUNT : comments.size());
		} else {
			latestComments = new ArrayList<InstagramMediaComment>();
		}
	}

	public List<InstagramMediaComment> getLatestComments() {
		return latestComments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public DateTime getPostedDateTime() {
		return postedDateTime;
	}

	public void setPostedDateTime(DateTime postedDateTime) {
		this.postedDateTime = postedDateTime;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

}
