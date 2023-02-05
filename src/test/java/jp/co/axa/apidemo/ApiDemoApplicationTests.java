package jp.co.axa.apidemo;

import jp.co.axa.apidemo.domain.entities.Employee;
import jp.co.axa.apidemo.domain.repositories.EmployeeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDemoApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private EmployeeRepository repository;

    @Test
    public void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }

    /**
     * Test get proper Employee info
     */
    @Test
    @Commit
    public void testRepository() {
        Employee A = new Employee();
        A.setName("1001");
        A.setDepartment("b");
        A.setSalary(1);

        repository.save(A);

        Assert.assertNotNull(A.getId());
        repository.deleteAll();

        Assert.assertNotNull(repository.findAll());
    }
}