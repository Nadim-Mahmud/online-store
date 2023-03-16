package net.therap.onlinestore.filter;

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
@WebFilter("/shopkeeper/*")
public class DeliveryManFilter implements Filter {

    private static final String HOME = "/";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        User user = (User) httpSession.getAttribute(ACTIVE_USER);

        if (Objects.nonNull(user) && UserType.CUSTOMER.equals(user.getType())) {
            request.setAttribute(ACTIVE_USER, user);
            chain.doFilter(request, response);

            return;
        }

        ((HttpServletResponse) response).sendRedirect(HOME);
    }
}
