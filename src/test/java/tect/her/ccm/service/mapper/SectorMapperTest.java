package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SectorMapperTest {
    private SectorMapper sectorMapper;

    @BeforeEach
    public void setUp() {
        sectorMapper = new SectorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sectorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sectorMapper.fromId(null)).isNull();
    }
}
