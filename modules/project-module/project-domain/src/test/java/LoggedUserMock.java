import com.sensilabs.projecthub.commons.LoggedUser;

public class LoggedUserMock implements LoggedUser {

    @Override
    public String getUserId() {
        return "testUser";
    }
}
