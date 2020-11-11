package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoringMapperTest {
    private ScoringMapper scoringMapper;

    @BeforeEach
    public void setUp() {
        scoringMapper = new ScoringMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scoringMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scoringMapper.fromId(null)).isNull();
    }
}
