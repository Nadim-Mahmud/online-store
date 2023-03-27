package net.therap.onlinestore.filter;

import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.ACTIVE_USER;

/**
 * @author nadimmahmud
 * @since 1/8/23
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private static final String HOME = "/";
    private static final String LOGIN = "/login";
    private static final String LOGIN_PAGE = "/login-page";
    private static final String ITEM_IMAGE_URL = "/item/image";
    private static final String ITEM_DETAILS_URL = "/item/details";
    private static final String SHOPKEEPER_URL = "/shopkeeper/";
    private static final String DELIVERY_URL = "/delivery/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String uri = httpServletRequest.getRequestURI();
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(ACTIVE_USER);

        if (Objects.nonNull(user)) {
            request.setAttribute(ACTIVE_USER, user);

            if (UserType.SHOPKEEPER.equals(user.getType()) && uri.equals(HOME)) {
                request.getRequestDispatcher(SHOPKEEPER_URL).forward(request, response);

                return;
            } else if (UserType.DELIVERYMAN.equals(user.getType()) && uri.equals(HOME)) {
                request.getRequestDispatcher(DELIVERY_URL).forward(request, response);

                return;
            }

            chain.doFilter(request, response);

        } else if (Arrays.asList(HOME, LOGIN, LOGIN_PAGE, ITEM_DETAILS_URL).contains(uri)
                || uri.startsWith(ITEM_IMAGE_URL)
                || uri.startsWith(ITEM_DETAILS_URL)) {

            chain.doFilter(request, response);

        } else {
            ((HttpServletResponse) response).sendRedirect(LOGIN_PAGE);
        }
    }
}
