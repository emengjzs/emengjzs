package jzs.test.base.http;

import org.springframework.http.ResponseEntity;

public interface HTTPRequestListener {

    void beforeRequest(String url, Object request);

    void afterResponse(String url, Object request, ResponseEntity<?> response);
}
