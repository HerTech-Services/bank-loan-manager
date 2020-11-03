package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DowngradingStepMapperTest {
    private DowngradingStepMapper downgradingStepMapper;

    @BeforeEach
    public void setUp() {
        downgradingStepMapper = new DowngradingStepMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(downgradingStepMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(downgradingStepMapper.fromId(null)).isNull();
    }
}
