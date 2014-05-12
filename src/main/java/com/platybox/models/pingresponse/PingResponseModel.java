package com.platybox.models.pingresponse;

import java.util.ArrayList;
import java.util.HashMap;

public final class PingResponseModel {
		
	public boolean isAvailable() {
		return available;
	}
	
	public String[] getNews() {
		return news;
	}
	
	public static PingResponseModel available() {
		return AVAILABLE_INSTANCE;
	}

	private static final PingResponseModel AVAILABLE_INSTANCE = new PingResponseModel(true, new String[0]);
	
	private boolean available;	
	private String[] news;
	
	private PingResponseModel(boolean available, String[] suggestions) {
		this.available = available;
		this.news = suggestions;
	}
	
}
