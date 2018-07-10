package com.weatherinfo.model;


public class LiveDataResponse<Response> {

    private Response response;

    private String error;

    public LiveDataResponse(Response response) {
        this.response = response;
    }

    public LiveDataResponse(String error) {
        this.error = error;
    }

    public Response getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "PresenterResult{" +
                "response=" + response +
                ", error='" + error + '\'' +
                '}';
    }
}
