package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FilelistMapperTest {
    private FilelistMapper filelistMapper;

    @BeforeEach
    public void setUp() {
        filelistMapper = new FilelistMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(filelistMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(filelistMapper.fromId(null)).isNull();
    }
}
