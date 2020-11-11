package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TypeEntiteMapperTest {
    private TypeEntiteMapper typeEntiteMapper;

    @BeforeEach
    public void setUp() {
        typeEntiteMapper = new TypeEntiteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeEntiteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeEntiteMapper.fromId(null)).isNull();
    }
}
