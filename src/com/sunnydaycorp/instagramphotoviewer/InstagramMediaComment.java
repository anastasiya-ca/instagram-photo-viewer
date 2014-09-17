package com.sunnydaycorp.instagramphotoviewer;

import org.joda.time.DateTime;

public class InstagramMediaComment implements Comparable<InstagramMediaComment> {

	private DateTime createdDateTime;
	private String text;
	private String authorName;

	public InstagramMediaComment(DateTime createdDateTime, String text, String authorName) {
		this.createdDateTime = createdDateTime;
		this.text = text;
		this.authorName = authorName;
	}

	public DateTime getCreatedDateTime() {
		return createdDateTime;
	}

	public String getText() {
		return text;
	}

	public String getAuthorName() {
		return authorName;
	}

	@Override
	public int compareTo(InstagramMediaComment otherComment) {
		if (createdDateTime.getMillis() < otherComment.getCreatedDateTime().getMillis()) {
			return 1;
		} else if (createdDateTime.getMillis() > otherComment.getCreatedDateTime().getMillis()) {
			return -1;
		}
		return 0;
	}
}