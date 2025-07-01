//package org.primefaces.mirage.filter;
//
//import jakarta.servlet.*;
//
//import java.io.IOException;
//
//public class CharacterEncodingFilter1 implements Filter {
//
//	private String encoding = "UTF-8"; // Default encoding is UTF-8
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// Get the encoding from the filter initialization parameters
//		String encodingParam = filterConfig.getInitParameter("encoding");
//		if (encodingParam != null) {
//			encoding = encodingParam;
//		}
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		// Set the character encoding for request and response
//		if (request.getCharacterEncoding() == null) {
//			request.setCharacterEncoding(encoding); // Set encoding for the request
//		}
//		if (response.getCharacterEncoding() == null) {
//			response.setCharacterEncoding(encoding); // Set encoding for the response
//		}
//
//		// Continue the chain of filters
//		chain.doFilter(request, response);
//	}
//
//	@Override
//	public void destroy() {
//		// Cleanup, if necessary (not required in this case)
//	}
//}
