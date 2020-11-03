package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PosteMapperTest {
    private PosteMapper posteMapper;

    @BeforeEach
    public void setUp() {
        posteMapper = new PosteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(posteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(posteMapper.fromId(null)).isNull();
    }
}
