package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntiteMapperTest {
    private EntiteMapper entiteMapper;

    @BeforeEach
    public void setUp() {
        entiteMapper = new EntiteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(entiteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(entiteMapper.fromId(null)).isNull();
    }
}
