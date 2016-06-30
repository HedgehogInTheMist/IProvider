package test;

import com.epam.inet.provider.dao.DaoFactory;
import com.epam.inet.provider.dao.UserDao;
import com.epam.inet.provider.dao.exception.DaoException;
import com.epam.inet.provider.entity.Role;
import com.epam.inet.provider.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * Created by Hedgehog on 17.06.2016.
 */
public class UserDaoTest {

    private static DaoFactory daoFactory = DaoFactory.getDaoFactory();
    private static UserDao userDao = (UserDao) daoFactory.getDao(DaoFactory.DaoType.USER_DAO);

    private static User user1 = null;
    private static User user2 = null;
    private static User user3 = null;
    private static User delUser = new User();
    private static Role role = new Role();

    @BeforeClass
    public static void init() {
        user1 = new User();
        user2 = new User();
        user3 = new User();
        user1.setUsername("testUser1");
        user1.setPassword("12345");
        role.setRolename("admin");
        user1.setRole(role);
        user2.setUsername("testUser2");
        user2.setPassword("54321");
        user3.setRole(role);
    }

    @After
    public void tearDown() throws Exception {
        userDao.delete(user3.getId());
    }

    @Test
    public void testAddUser() throws DaoException {
        user3.setUsername("testUser3");
        user3.setPassword("qwerty");
        assert role !=null;
        if(role != null) {
            role.setId(2);
        }
        role.setRolename("user");
        userDao.create(user3);
        User actUser3 = userDao.findUserByLogin(user3.getUsername());
        assertEquals("They seems should be same", user3.getUsername(), actUser3.getUsername());
    }

    @Test
    public void deleteUserTest() throws DaoException {
        User actualUser = userDao.findUserByLogin(user3.getUsername());
        assertEquals("Two users should be the same", user3.getUsername(), actualUser.getUsername());
        userDao.delete(actualUser.getId());
        assertNull("User is null", userDao.findUserByLogin(user3.getUsername()));
    }

    @Test
    public void testFindUserByLogin() {
        try {
            user1 = userDao.findUserByLogin("admin");
            user2 = userDao.findUserByLogin("administrator");
            assertNotNull(user1);
            assertNull(user2);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    @After
    public void last() throws DaoException {
        user1 = null;
        user2 = null;
    }
}
