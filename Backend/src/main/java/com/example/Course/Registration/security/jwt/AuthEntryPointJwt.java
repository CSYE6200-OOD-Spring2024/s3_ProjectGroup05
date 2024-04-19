package com.example.Course.Registration.security.jwt;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import com.google.gson.JsonObject;
// import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	private String mapToJsonString(Map<String, Object> map) {
        // Convert the Map to a JSON-like string
        StringBuilder jsonString = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) {
                jsonString.append(",");
            }
            jsonString.append("\"").append(entry.getKey()).append("\":\"").append(entry.getValue()).append("\"");
            first = false;
        }
        jsonString.append("}");
        return jsonString.toString();
    }

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		if(response.getHeader("Error Message")!=null){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> map = new HashMap<>();
			map.put("status", response.getStatus());
			map.put("message", response.getHeader("Error Message"));
			response.getWriter().write(mapToJsonString(map));
			return;
		}
		else{
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Map<String, Object> map = new HashMap<>();
			map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
			map.put("message", "Error: Unauthorized");
			response.getWriter().write(mapToJsonString(map));
			logger.error("Unauthorized error: {}", authException.getMessage());
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
			return;
		}
		// response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}
