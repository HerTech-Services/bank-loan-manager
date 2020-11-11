package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RejectMapperTest {
    private RejectMapper rejectMapper;

    @BeforeEach
    public void setUp() {
        rejectMapper = new RejectMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rejectMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rejectMapper.fromId(null)).isNull();
    }
}
