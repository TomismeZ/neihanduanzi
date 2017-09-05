package com.bmob.im.demo.bean;

import java.util.List;

public class Image {
	public String message;
	public ImageData data;

	public class ImageData {
		public Boolean has_more;
		public String tip;
		public List<ListData> data;

	}

	public class ListData {
		public Group group;

	}

	public class Group {
		public User user;
		public String text;
		public int favorite_count;
		public String share_url;
		public int comment_count;
		public LargeImage large_image;
		public int is_gif;
		public int bury_count;
		public long group_id;
		public MiddleImage middle_image;

	}

	public class User {
		public String name;
		public String avatar_url;
		public long user_id;

	}

	public class LargeImage {
		public String uri;

	}

	public class MiddleImage {
		public String uri;
	}

}