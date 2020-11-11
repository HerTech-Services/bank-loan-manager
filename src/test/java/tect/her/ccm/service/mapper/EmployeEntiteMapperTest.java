package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeEntiteMapperTest {
    private EmployeEntiteMapper employeEntiteMapper;

    @BeforeEach
    public void setUp() {
        employeEntiteMapper = new EmployeEntiteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(employeEntiteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(employeEntiteMapper.fromId(null)).isNull();
    }
}
