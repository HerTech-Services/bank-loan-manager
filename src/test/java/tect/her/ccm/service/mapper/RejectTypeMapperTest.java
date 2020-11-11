package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RejectTypeMapperTest {
    private RejectTypeMapper rejectTypeMapper;

    @BeforeEach
    public void setUp() {
        rejectTypeMapper = new RejectTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rejectTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rejectTypeMapper.fromId(null)).isNull();
    }
}
