package com.weatherinfo.model;

public class PresenterResponse<Response> {

    private Response response;
    private Throwable error;

    public PresenterResponse(Response response) {
        this.response = response;
    }

    public PresenterResponse(Throwable error) {
        this.error = error;
    }

    public Response getResponse() {
        return response;
    }

    public Throwable getError() {
        return error;
    }

}
