package jp.co.axa.apidemo.application.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jp.co.axa.apidemo.domain.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

@Slf4j
@Component
public class TokenUtils {
    /**
     * Generate token if username and password are correct
     *
     * @param user User
     * @return token
     */
    public static String genToken(User user) {
        return JWT.create().withExpiresAt(dateAddOne(new Date())).withAudience("1")
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    /**
     * Set token expiration date as 1 day
     *
     * @param date Date
     * @return token
     */
    private static Date dateAddOne(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
        return date;

    }

    /**
     * Initialize token from user
     *
     * @param user User
     */
    public static void initToken(User user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String aud = JWT.decode(genToken(user)).getAudience().get(0);
        Integer userId = Integer.valueOf(aud);
        // Add token identify in session
        request.getSession().setAttribute("token", userId);
    }


    /**
     * Get user
     *
     * @return User
     */
    public static User getUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            // todo: uncomment out if using non in-memory database storing multiple user
            // String token = request.getHeader("token");
            // String aud = JWT.decode(token).getAudience().get(0);
            Object userId = request.getSession().getAttribute("token");
            if (userId != null) {
                return admin();
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error("token error", e);
            return null;
        }
    }

    /**
     * Set login username and password as "admin"
     * todo: change to get name and password from non-in-memory database
     */
    static User admin() {
        User admin = new User();
        // todo: change to get name and password from non-in-memory database
        admin.setName("admin");
        admin.setPassword("admin");
        return admin;
    }
}
