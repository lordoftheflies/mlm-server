/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam;

/**
 * Cross-site-resource-forgery header filter.
 *
 * @author lordoftheflies
 */
public class CsrfHeaderFilter { //extends OncePerRequestFilter {

//    /**
//     * Internally filtering. Set Cross-Site Request Forgery header to a cookie
//     * for the base URL.
//     *
//     * @param request
//     * @param response
//     * @param filterChain
//     * @throws ServletException
//     * @throws IOException
//     */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//        if (csrf != null) {
//            Cookie cookie = WebUtils.getCookie(request, XSRFTOKEN);
//            String token = csrf.getToken();
//            if (cookie == null || token != null && !token.equals(cookie.getValue())) {
//                cookie = new Cookie(XSRFTOKEN, token);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    /**
//     * Cross-Site Request Forgery
//     */
//    private static final String XSRFTOKEN = "XSRF-TOKEN";
}
