package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EngagementMapperTest {
    private EngagementMapper engagementMapper;

    @BeforeEach
    public void setUp() {
        engagementMapper = new EngagementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(engagementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(engagementMapper.fromId(null)).isNull();
    }
}
