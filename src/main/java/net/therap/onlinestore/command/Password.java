package net.therap.onlinestore.command;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

import static net.therap.onlinestore.constant.Constants.PASSWORD_PATTERN;

/**
 * @author nadimmahmud
 * @since 3/8/23
 */
public class Password implements Serializable {

    protected static final long serialVersionUID = 1L;

    private String storedUserPassword;

    @NotNull(message = "{input.password}")
    private String oldPassword;

    @NotNull
    @Pattern(regexp = PASSWORD_PATTERN, message = "{input.password}")
    private String newPassword;

    @NotNull(message = "{input.password}")
    private String confirmPassword;

    public String getStoredUserPassword() {
        return storedUserPassword;
    }

    public void setStoredUserPassword(String storedUserPassword) {
        this.storedUserPassword = storedUserPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
