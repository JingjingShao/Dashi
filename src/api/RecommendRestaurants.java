package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import db.DBConnection;

/**
 * Servlet implementation class RecommendRestaurants
 */
@WebServlet(name = "recommendation", urlPatterns = { "/recommendation" })
public class RecommendRestaurants extends HttpServlet {
	private static final DBConnection connection = new DBConnection();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecommendRestaurants() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JSONArray array = new JSONArray();
		if (request.getParameterMap().containsKey("user_id")) {
			String userId = request.getParameter("user_id");
			array = connection.RecommendRestaurants(userId);

		}
		RpcParser.writeOutput(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		JSONArray array = new JSONArray();
		try {
			// 转成map，看是否有'lat','lon'
			if (request.getParameterMap().containsKey("user_id")) {
				String userId = request.getParameter("user_id");
				// return some fake restaurants
				array.put(new JSONObject().put("name", "Panda Express").put("location", "downtown").put("country",
						"united states"));
				array.put(new JSONObject().put("name", "Hong Kong Express").put("location", "downtown").put("country",
						"united states"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// 写到helper func中
		RpcParser.writeOutput(response, array);
	}
}
