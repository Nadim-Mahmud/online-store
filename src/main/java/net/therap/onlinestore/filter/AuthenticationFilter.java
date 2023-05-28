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

import static net.therap.onlinestore.constant.Constants.*;

/**
 * @author nadimmahmud
 * @since 1/8/23
 */
@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        HttpSession httpSession = httpServletRequest.getSession();
        User user = (User) httpSession.getAttribute(ACTIVE_USER);

        if (Objects.nonNull(user)) {
            request.setAttribute(ACTIVE_USER, user);

            if (UserType.SHOPKEEPER.equals(user.getType()) && HOME_URL.equals(uri)) {
                request.getRequestDispatcher(SHOPKEEPER_URL).forward(request, response);

                return;
            } else if (UserType.DELIVERYMAN.equals(user.getType()) && HOME_URL.equals(uri)) {
                request.getRequestDispatcher(DELIVERY_URL).forward(request, response);

                return;
            }

            chain.doFilter(request, response);

        } else if (Arrays.asList(HOME_URL, LOGIN_URL, SEARCH_URL, LOGIN_PAGE_URL, ITEM_DETAILS_URL, REGISTRATION_URL, REGISTER_URL).contains(uri)
                || uri.startsWith(ITEM_IMAGE_URL)
                || uri.startsWith(ITEM_DETAILS_URL)
                || uri.startsWith(ASSETS_URL)) {

            chain.doFilter(request, response);

        } else {
            ((HttpServletResponse) response).sendRedirect(LOGIN_PAGE_URL);
        }
    }
}
