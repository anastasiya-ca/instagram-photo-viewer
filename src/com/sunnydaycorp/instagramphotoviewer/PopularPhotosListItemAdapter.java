package com.sunnydaycorp.instagramphotoviewer;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PopularPhotosListItemAdapter extends ArrayAdapter<InstagramMedia> {

	private Drawable locationIcon;

	private static class ViewHolder {
		ImageView ivProfilePic;
		TextView tvUserName;
		TextView tvLocationName;
		TextView tvPostedTime;
		ImageView ivPhoto;
		TextView tvCaption;
		TextView tvLikes;
		TextView tvComments;
	}

	public PopularPhotosListItemAdapter(Context context, List<InstagramMedia> photos) {
		super(context, R.layout.popular_photos_list_item, photos);

		locationIcon = context.getResources().getDrawable(R.drawable.ic_location_pointer);
		locationIcon.setBounds(0, 0, locationIcon.getIntrinsicHeight(), locationIcon.getIntrinsicWidth());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		InstagramMedia photo = getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.popular_photos_list_item, parent, false);

			ViewHolder viewHolder = new ViewHolder();

			viewHolder.ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
			viewHolder.tvLocationName = (TextView) convertView.findViewById(R.id.tvLocationName);
			viewHolder.tvPostedTime = (TextView) convertView.findViewById(R.id.tvPostedTime);
			viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
			viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
			viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
			viewHolder.tvComments = (TextView) convertView.findViewById(R.id.tvComments);
			convertView.setTag(viewHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();

		// set user name
		viewHolder.tvUserName.setText(photo.getUserName());

		// if any set location name with location icon
		if (photo.getLocationName() != null && !photo.getLocationName().isEmpty()) {
			viewHolder.tvLocationName.setCompoundDrawables(locationIcon, null, null, null);
			viewHolder.tvLocationName.setText(photo.getLocationName());
		}

		// set descriptive elapsed time since posted
		viewHolder.tvPostedTime.setText(DateUtils.getRelativeTimeSpanString(photo.getPostedDateTime().getMillis(), System.currentTimeMillis(),
				DateUtils.SECOND_IN_MILLIS, 0));

		// calculate and set photo height to be displayed
		int imageHeight = photo.getImageHeight();
		int imageWidth = photo.getImageWidth();
		float imageRatio = imageHeight / imageWidth;

		DisplayMetrics outMetrics = new DisplayMetrics();
		((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(outMetrics);
		int targetWidth = outMetrics.widthPixels;
		int targetHeight = (int) imageRatio * targetWidth;

		viewHolder.ivPhoto.getLayoutParams().height = targetHeight;
		viewHolder.ivPhoto.getLayoutParams().width = targetWidth;

		// reset image from recycled view
		viewHolder.ivPhoto.setImageResource(0);
		// asynch send newtwork request, download, convert to bitmap
		Picasso.with(getContext()).load(photo.getImageUrl()).resize(targetHeight, targetWidth).centerInside().into(viewHolder.ivPhoto);

		// load user profile picture
		viewHolder.ivProfilePic.setImageResource(0);
		Picasso.with(getContext()).load(photo.getProfilePicUrl()).resize(85 * targetHeight / imageHeight, 85 * targetHeight / imageHeight)
				.transform(new CircleTransformation()).into(viewHolder.ivProfilePic);

		// create, format and populate caption if any
		if (photo.getCaption() != null) {
			SpannableStringBuilder captionString = new SpannableStringBuilder(photo.getUserName() + " " + photo.getCaption());

			// Highlight user name in caption string
			ForegroundColorSpan fcs = new ForegroundColorSpan(getContext().getResources().getColor(R.color.custom_username_text_color));
			StyleSpan ss = new StyleSpan(android.graphics.Typeface.BOLD);
			captionString.setSpan(fcs, 0, photo.getUserName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			captionString.setSpan(ss, 0, photo.getUserName().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

			viewHolder.tvCaption.setText(captionString);
		}

		// set likes count
		viewHolder.tvLikes.setText(photo.getLikesCount() + " likes");

		// create, format and populate latest two comments if any
		SpannableStringBuilder commentsString = new SpannableStringBuilder();
		List<InstagramMediaComment> comments = photo.getLatestComments();
		if (comments != null) {
			int startPos = 0;
			for (InstagramMediaComment c : comments) {

				if (commentsString.length() > 0) {
					startPos = commentsString.length();
				}
				commentsString.append(c.getAuthorName());
				int endPos = commentsString.length();

				// Highlight user name in comments string
				ForegroundColorSpan fcs = new ForegroundColorSpan(getContext().getResources().getColor(R.color.custom_username_text_color));
				StyleSpan ss = new StyleSpan(android.graphics.Typeface.BOLD);
				commentsString.setSpan(fcs, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				commentsString.setSpan(ss, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

				commentsString.append(" ");
				commentsString.append(c.getText());
				commentsString.append("\n");
			}
			// remove last line break if any
			if (commentsString.length() > 0) {
				commentsString.delete(commentsString.length() - 1, commentsString.length());
			}
			viewHolder.tvComments.setText(commentsString);
		}
		return convertView;

	}
}
