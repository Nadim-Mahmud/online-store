package net.therap.onlinestore.filter;

import net.therap.onlinestore.constant.Constants;
import net.therap.onlinestore.entity.User;
import net.therap.onlinestore.entity.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

import static net.therap.onlinestore.constant.Constants.ACTIVE_USER;

/**
 * @author nadimmahmud
 * @since 1/8/23
 */
@WebFilter(value = "/")
public class GuestFilter implements Filter {

    private static final String HOME = "/";
    private static final String SHOPKEEPER = "/shopkeeper/";
    private static final String DELIVERY = "/delivery/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        User user = (User) httpSession.getAttribute(Constants.ACTIVE_USER);

        if (Objects.nonNull(user)) {
            request.setAttribute(ACTIVE_USER, user);

            if (UserType.SHOPKEEPER.equals(user.getType())) {
                httpServletResponse.sendRedirect(SHOPKEEPER);

                return;
            } else if (UserType.DELIVERYMAN.equals(user.getType())) {
                httpServletResponse.sendRedirect(DELIVERY);

                return;
            }
        }

        request.getRequestDispatcher(HOME).forward(request, response);
    }
}
