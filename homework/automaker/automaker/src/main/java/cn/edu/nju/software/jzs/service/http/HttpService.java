package cn.edu.nju.software.jzs.service.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.HttpClient;

public interface HttpService {

    public JSONResponse postForJSONResponse(String url, Object request, Object... urlVariables);

    public JSONResponse postForJSONResponse(URLInfo url, Object request, Object... urlVariables);

    public HttpClient getClient();

    public JSONObject getForObject(String url, Object request);

}