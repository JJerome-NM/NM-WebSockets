package com.jjerome.core;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RequestRepository {

    private static final ThreadLocal<Request<UndefinedBody>> request = new ThreadLocal<>();
    private static final ThreadLocal<Request<?>> requestWithRealBody = new ThreadLocal<>();

    public static Request<UndefinedBody> getRequest() {
        return request.get();
    }

    public static Optional<Request<UndefinedBody>> getOptionalRequest() {
        return Optional.of(request.get());
    }

    public static Request<?> getRequestWithRealBody() {
        return requestWithRealBody.get();
    }

    public static Optional<Request<?>> getOptionalRequestWithRealBody() {
        return Optional.of(requestWithRealBody.get());
    }

    public static void setRequest(Request<UndefinedBody> request) {
        RequestRepository.request.set(request);
    }

    public static void setRequestWithRealBody(Request<?> request) {
        requestWithRealBody.set(request);
    }

    public void unloadRequest() {
        request.remove();
    }

    public void unloadWithRealBody() {
        request.remove();
    }
}
