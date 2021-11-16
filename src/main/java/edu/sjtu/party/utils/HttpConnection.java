package edu.sjtu.party.utils;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

@SuppressWarnings("deprecation")
public class HttpConnection {

    private static Log logger = LogFactory.getLog(HttpConnection.class);

    //请求配置，设置链接超时和读取超时
    private static final RequestConfig config = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();

    //https策略，绕过安全检查
    private static CloseableHttpClient getSingleSSLConnection() throws Exception {
        //CloseableHttpClient httpClient = null;
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        //return HttpClients.custom().setDefaultRequestConfig(config).build();
        //2017 12 14修改，忘了绕过安全检查设置了，哈哈
        return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(config).build();
    }

    public static JSONObject doGet(String url, Map<String, String> parameters) throws Exception {
        StringBuilder newUrl=new StringBuilder();
        newUrl.append(url);
        if (parameters != null && parameters.size() != 0) {
            newUrl.append("?");
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                newUrl.append(entry.getKey());
                newUrl.append("=");
                newUrl.append(entry.getValue());
                newUrl.append("&");
            }
            newUrl.setLength(newUrl.length() - 1);
        }
        //获取绕过安全检查的httpClient，以便发送https请求
        CloseableHttpClient client = getSingleSSLConnection();
        try {
            HttpGet httpget = new HttpGet(new URI(newUrl.toString()));
            httpget.setHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(httpget);
            int status = response.getStatusLine().getStatusCode();
            String entityStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (status != 200) {
                throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", status, entityStr));
            }
            return JSONObject.parseObject(entityStr);
//            return entityStr;
        } catch (Exception t) {
            String msg = String.format("Failed to call api '%s'", url);
            throw t;
        }
    }

    public static String doPut(String url, String body, String token) throws Exception {
        try {
            CloseableHttpClient httpclient = getSingleSSLConnection();
            HttpPut httpput = new HttpPut(url);
            httpput.setHeader("Accept", "application/json");
            httpput.setHeader("Content-Type", "application/json;charset=utf-8");
            if(StringUtils.isNotEmpty(token)) {
                httpput.setHeader("Authorization", "Bearer ".concat(token));
            }
            BasicHttpEntity httpEntity = new BasicHttpEntity();
            httpEntity.setContent(new java.io.ByteArrayInputStream(body.getBytes("UTF-8")));
            httpEntity.setContentLength(body.getBytes("UTF-8").length);
            httpput.setEntity(httpEntity);
            CloseableHttpResponse response = httpclient.execute(httpput);
            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", response.getStatusLine().getStatusCode(), ""));
                }
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resp = EntityUtils.toString(entity, "utf-8");
                    return resp;
                } else {
                    throw new RuntimeException("http status=" + response.getStatusLine());
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String doDelete(String url, String token) throws Exception {
        try {
            CloseableHttpClient httpclient = getSingleSSLConnection();
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeader("Accept", "application/json");
            httpDelete.setHeader("Content-Type", "application/json;charset=utf-8");
            if(StringUtils.isNotEmpty(token)) {
                httpDelete.setHeader("Authorization", "Bearer ".concat(token));
            }
            CloseableHttpResponse response = httpclient.execute(httpDelete);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                } else {
                    throw new RuntimeException("http status=" + response.getStatusLine());
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * https
     *
     * @param httpclient Http客户端
     */
    private static void enableSSL(HttpClient httpclient) {
//        // 调用ssl
//        try {
//            SSLContext sslcontext = SSLContext.getInstance("TLS");
//            sslcontext.init(null, new TrustManager[] { truseAllManager }, null);
//            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            Scheme https = new Scheme("https", sf, 443);
//            httpclient.getConnectionManager().getSchemeRegistry()
//                    .register(https);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

//    public static String httpGet(String url, String charset) throws HttpException, IOException {
//        String json = null;
//        HttpGet httpGet = new HttpGet();
//        // 设置参数
//        try {
//            httpGet.setURI(new URI(url));
//        } catch (URISyntaxException e) {
//            throw new HttpException("请求url格式错误。" + e.getMessage());
//        }
//        // 发送请求
//        HttpClient client = new DefaultHttpClient();
//        HttpResponse httpResponse = client.execute(httpGet);
//        // 获取返回的数据
//        HttpEntity entity = httpResponse.getEntity();
//        byte[] body = EntityUtils.toByteArray(entity);
//        StatusLine sL = httpResponse.getStatusLine();
//        int statusCode = sL.getStatusCode();
//        if (statusCode == 200) {
//            json = new String(body, charset);
//            entity.consumeContent();
//        } else {
//            throw new HttpException("statusCode=" + statusCode);
//        }
//        return json;
//    }

//    public static JSONObject doGet(String url, Map<String, String> parameters) throws Exception {
//        if (parameters.size() != 0) {
//            url = url + "?";
//            for (String key : parameters.keySet()) {
//                url = url + key + "=" + parameters.get(key) + "&";
//            }
//        }
//        url = url.substring(0, url.length() - 1);
//
//        try {
//            HttpClient client = new DefaultHttpClient();
//            if (url.startsWith("https")) {
//                enableSSL(client);
//            }
//            HttpGet httpget = new HttpGet(new URI(url));
//            httpget.setHeader("Content-Type", "application/json");
//            HttpResponse response = client.execute(httpget);
//            int status = response.getStatusLine().getStatusCode();
//            String entityStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//            if (status != 200) {
//                throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", status, entityStr));
//            }
//            return JSONObject.fromObject(entityStr);
//        } catch (Exception t) {
//            String msg = String.format("Failed to call api '%s'", url);
//            logger.error(msg, t);
//            throw t;
//        }
//    }

//    public static JSONObject doPut(String url, Map<String, String> parameters) throws Exception {
//        Map<String, String> params = new LinkedHashMap<>();
//        if (parameters != null) {
//            params.putAll(parameters);
//        }
//        try {
//            HttpClient client = new DefaultHttpClient();
//            if (url.startsWith("https")) {
//                enableSSL(client);
//            }
//            HttpPut httpput = new HttpPut(new URI(url));
//            List<NameValuePair> nvps = new ArrayList<>();
//            for (Entry<String, String> entry : params.entrySet()) {
//                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            httpput.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//            HttpResponse response = client.execute(httpput);
//            int status = response.getStatusLine().getStatusCode();
//            String entityStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//            if (status != 200) {
//                throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", status, entityStr));
//            }
//            return JSONObject.fromObject(entityStr);
//        } catch (Exception t) {
//            String msg = String.format("Failed to call api '%s'", url);
//            logger.error(msg, t);
//            throw t;
//        }
//    }
//
//    public static JSONObject doPost(String url, Map<String, String> parameters) throws Exception {
//        @SuppressWarnings("unused")
//        Map<String, String> params = new LinkedHashMap<>();
//        if (parameters != null) {
//            params.putAll(parameters);
//        }
//        try {
//            HttpClient client = new DefaultHttpClient();
//            if (url.startsWith("https")) {
//                enableSSL(client);
//            }
//            HttpPost httppost = new HttpPost(new URI(url));
//            List<NameValuePair> nvps = new ArrayList<>();
//            for (Entry<String, String> entry : params.entrySet()) {
//                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//            httppost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
//            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//            HttpResponse response = client.execute(httppost);
//            int status = response.getStatusLine().getStatusCode();
//            String entityStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//            if (status != 200) {
//                throw new RuntimeException(String.format("api return error http code %d, detail: \n%s", status,
//                        entityStr));
//            }
//            return JSONObject.fromObject(entityStr);
//        } catch (Exception t) {
//            String msg = String.format("Failed to call api '%s'", url);
//            logger.error(msg, t);
//            throw t;
//        }
//    }
//
//    /**
//     * @param params 请求参数
//     * @return 构建query
//     */
//    public static String buildQuery(Map<String, String> params, String charset) {
//        if (params == null || params.isEmpty()) {
//            return null;
//        }
//        StringBuilder sb = new StringBuilder();
//        boolean first = true;
//        for (Entry<String, String> entry : params.entrySet()) {
//            if (first) {
//                first = false;
//            } else {
//                sb.append("&");
//            }
//            String key = entry.getKey();
//            String value = entry.getValue();
//            if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
//                try {
//                    sb.append(key).append("=")
//                            .append(URLEncoder.encode(value, charset));
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return sb.toString();
//
//    }
//
//    private static TrustManager truseAllManager = new X509TrustManager() {
//
//        public X509Certificate[] getAcceptedIssuers() {
//            return new X509Certificate[]{};
//        }
//
//        public void checkClientTrusted(X509Certificate[] cert, String oauthType)
//                throws java.security.cert.CertificateException {
//        }
//
//        public void checkServerTrusted(X509Certificate[] cert, String oauthType)
//                throws java.security.cert.CertificateException {
//        }
//    };
//
//    /**
//     * https
//     *
//     * @param httpclient Http客户端
//     */
//    private static void enableSSL(HttpClient httpclient) {
//        // 调用ssl
//        try {
//            SSLContext sslcontext = SSLContext.getInstance("TLS");
//            sslcontext.init(null, new TrustManager[]{truseAllManager}, null);
//            SSLSocketFactory sf = new SSLSocketFactory(sslcontext);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//            Scheme https = new Scheme("https", sf, 443);
//            httpclient.getConnectionManager().getSchemeRegistry()
//                    .register(https);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}





