package net.therap.onlinestore.filter;

import net.therap.estaurant.constant.Constants;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.entity.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/8/23
 */
@WebFilter(value = "/")
public class AuthenticationFilter implements Filter {

    private static final String HOME = "/";
    private static final String ADMIN = "/admin/";
    private static final String CHEF = "/chef/";
    private static final String WAITER = "/waiter/";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        User user = (User) httpSession.getAttribute(Constants.ACTIVE_USER);

        if (Objects.isNull(user)) {
            request.getRequestDispatcher(HOME).forward(request, response);

            return;
        }

        if (UserType.ADMIN.equals(user.getType())) {
            httpServletResponse.sendRedirect(ADMIN);
        }

        if (UserType.CHEF.equals(user.getType())) {
            httpServletResponse.sendRedirect(CHEF);
        }

        if (UserType.WAITER.equals(user.getType())) {
            httpServletResponse.sendRedirect(WAITER);
        }
    }
}
