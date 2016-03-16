package yelp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

// utility class (连接yelpAPI的class)
public class YelpAPI {
    private static final String API_HOST = "api.yelp.com";
    // search content
    // YelpAPI(): initialize the connection with your keys, will be used by our rpc methods
    private static final String DEFAULT_TERM = "dinner";
    private static final int SEARCH_LIMIT = 20;
    private static final String SEARCH_PATH = "/v2/search";
    private static final String CONSUMER_KEY = "SPMYsxD5jPDkn4mn7C8vSw";
    private static final String CONSUMER_SECRET = "qsKivbXOk_C-l7IdB5bne3pT_jY";
    private static final String TOKEN = "9KrQI9DRsMRVsa61CoIonjpXzKvyXVhQ";
    private static final String TOKEN_SECRET = "0SlBhIQB4J0tg_1LRF6ARbWOmFo";

    OAuthService service;
    Token accessToken;

    /**
     * Setup the Yelp API OAuth credentials.
     */
    // initialize the connection with your keys, will be used by our rpc methods
    public YelpAPI() {
   	 this.service = new ServiceBuilder().provider(TwoStepOAuth.class)
   			 .apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
   	 this.accessToken = new Token(TOKEN, TOKEN_SECRET);
    }

    /**
     * Creates and sends a request to the Search API by term and location.
     */
    // the most important
    // we will convert string to JSON object finally
    public String searchForBusinessesByLocation(double lat, double lon) {
   	 OAuthRequest request = new OAuthRequest(Verb.GET, "http://" + API_HOST
   			 + SEARCH_PATH);
   	 // 拼起来，类似于URL里？ &&加query一样
   	 request.addQuerystringParameter("term", DEFAULT_TERM);
   	 request.addQuerystringParameter("ll", lat + "," + lon);
   	 request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
   	 // 发送请求
   	 return sendRequestAndGetResponse(request);
    }

    /**
     * Sends an {@link OAuthRequest} and returns the {@link Response} body.
     */
    private String sendRequestAndGetResponse(OAuthRequest request) {
   	 System.out.println("Querying " + request.getCompleteUrl() + " ...");
   	 this.service.signRequest(this.accessToken, request);
   	 Response response = request.send();
   	 return response.getBody();
    }

    /**
     * Queries the Search API based on the command line arguments and takes the
     * first result to query the Business API.
     */
    private static void queryAPI(YelpAPI yelpApi, double lat, double lon) {
   	 String searchResponseJSON = yelpApi.searchForBusinessesByLocation(lat,
   			 lon);
   	 JSONObject response = null;
   	 try {
   		 response = new JSONObject(searchResponseJSON);
   		 JSONArray businesses = (JSONArray) response.get("businesses");
   		 for (int i = 0; i < businesses.length(); i++) {
   			 JSONObject business = (JSONObject) businesses.get(i);
   			 System.out.println(business);
   		 }
   	 } catch (Exception e) {
   		 e.printStackTrace();
   	 }
    }

    /**
     * Main entry for sample Yelp API requests.
     */
    // test if we have the right key
    public static void main(String[] args) {
   	 YelpAPI yelpApi = new YelpAPI();
   	 queryAPI(yelpApi, 37.38, -122.08);
    }
}
