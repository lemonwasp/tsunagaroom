package controller;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dto.LocationDTO;
import logic.LocationLogic;

/**
 * Servlet implementation class LocationServlet
 */
@WebServlet("/LocationServlet")
public class LocationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String sendKind = request.getParameter("sendKind");
		HttpSession session = request.getSession();

		if ("FootPrint".equals(sendKind)) {

			int userId = Integer.parseInt(request.getParameter("userId"));

			LocationLogic logic = new LocationLogic();

			ArrayList<LocationDTO> locationList = logic.getLocation(userId);

			session.setAttribute("locationList", locationList);

			request.getRequestDispatcher("/locationTop.jsp")
					.forward(request, response);

		} else if ("FootPrintMap".equals(sendKind)) {

			int locationId = Integer.parseInt(request.getParameter("locationId"));

			ArrayList<LocationDTO> locationList = (ArrayList<LocationDTO>) session.getAttribute("locationList");

			LocationDTO selectedLocation = null;

			if (locationList != null) {
				for (LocationDTO location : locationList) {
					if (location.getId() == locationId) {
						selectedLocation = location;
						break;
					}
				}
			}

			session.setAttribute("selectedLocation", selectedLocation);

			request.getRequestDispatcher("/locationMap.jsp")
					.forward(request, response);

		} else if ("FootPrintTop".equals(sendKind)) {

			request.getRequestDispatcher("/locationTop.jsp")
					.forward(request, response);
		}
	}

}
