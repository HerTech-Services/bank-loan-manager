package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EngagementTypeMapperTest {
    private EngagementTypeMapper engagementTypeMapper;

    @BeforeEach
    public void setUp() {
        engagementTypeMapper = new EngagementTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(engagementTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(engagementTypeMapper.fromId(null)).isNull();
    }
}
