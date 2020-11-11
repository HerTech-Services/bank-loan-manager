package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilelistEgagementTypeMapperTest {
    private FilelistEgagementTypeMapper filelistEgagementTypeMapper;

    @BeforeEach
    public void setUp() {
        filelistEgagementTypeMapper = new FilelistEgagementTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(filelistEgagementTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(filelistEgagementTypeMapper.fromId(null)).isNull();
    }
}
