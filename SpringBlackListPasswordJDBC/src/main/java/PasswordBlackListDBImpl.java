import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PasswordBlackListDBImpl implements PasswordBlackList{
    private JdbcTemplate jdbcTemplate;

    public PasswordBlackListDBImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    private static final String SELECT_ALL_Passwords = "SELECT password FROM BlackPassword";
    private final RowMapper<String> passwordRowMapper = (row, rowNumbers) -> {
        String password = row.getString("password");
        return password;
    };

    @Override
    public boolean contains(String password) {
        List<String> passwords = jdbcTemplate.query(SELECT_ALL_Passwords,passwordRowMapper);
        return passwords.contains(password);
    }

}
