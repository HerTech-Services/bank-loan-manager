package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserPosteMapperTest {
    private UserPosteMapper userPosteMapper;

    @BeforeEach
    public void setUp() {
        userPosteMapper = new UserPosteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userPosteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userPosteMapper.fromId(null)).isNull();
    }
}
