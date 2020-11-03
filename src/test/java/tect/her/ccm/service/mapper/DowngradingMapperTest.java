package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DowngradingMapperTest {
    private DowngradingMapper downgradingMapper;

    @BeforeEach
    public void setUp() {
        downgradingMapper = new DowngradingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(downgradingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(downgradingMapper.fromId(null)).isNull();
    }
}
