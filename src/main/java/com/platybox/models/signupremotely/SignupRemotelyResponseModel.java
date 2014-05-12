package com.platybox.models.signupremotely;

public final class SignupRemotelyResponseModel {

	private String response;
	
	public void setResponse (String response) {
		AVAILABLE_INSTANCE.response = response;
	}

	public String getResponse() {
		return response;
	}
	
	private static final SignupRemotelyResponseModel AVAILABLE_INSTANCE = 
			new SignupRemotelyResponseModel(new String ("There has been an error with your request."));
	
	private SignupRemotelyResponseModel(String response) {
		this.response = response;
	}
	
	/*Methods*/
	public static SignupRemotelyResponseModel createResponse (String response) {
		return new SignupRemotelyResponseModel (response);		
	}
	
	public static SignupRemotelyResponseModel error() {
		return AVAILABLE_INSTANCE;
	}
	
}
