package cn.edu.nju.software.jzs.service.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;

public class DefaultHostHttpService implements HttpService {

    private BasicHttpService httpService;

    private URLGetter urlGetter;

    public DefaultHostHttpService(BasicHttpService httpService, String host) {
        this.httpService = httpService;
        this.urlGetter = new URLGetter(host);
    }

    @Override
    public JSONResponse postForJSONResponse(String url, Object request, Object...urlVariables) {
        return this.httpService.postForJSONResponse(urlGetter.getFullUrl(url), request, urlVariables);
    }

    @Override
    public JSONResponse postForJSONResponse(URLInfo url, Object request, Object...urlVariables) {
        return this.httpService.postForJSONResponse(urlGetter.getFullUrl(url), request, urlVariables);
    }

    @Override
    public HttpClient getClient() {
        return this.httpService.getClient();
    }

    @Override
    public JSONObject getForObject(String url, Object request) {
        return httpService.getForObject(urlGetter.getFullUrl(url), request);
    }

}
