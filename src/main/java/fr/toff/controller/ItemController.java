package fr.toff.controller;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.toff.model.ItemName;
import fr.toff.model.ItemNumber;

@Component("itemController")
public class ItemController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String type = request.getRequestURI().split("/")[request.getRequestURI().split("/").length - 1];
		
		Object result = null;
		
		if ("number".equals(type)) {
			result = new ItemNumber(Integer .valueOf(request.getParameter("value")));
		} else if ("name".equals(type)) {
			result = new ItemName(request.getParameter("value"));
		}
		
		if (result != null) {
			response.setContentType("application/json");
			StreamUtils.copy(mapper.writeValueAsString(result), Charset.forName("UTF-8"), response.getOutputStream());
		}

		return null;
	}

}
