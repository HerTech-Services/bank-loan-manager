package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TasktypeStatusActionMapperTest {
    private TasktypeStatusActionMapper tasktypeStatusActionMapper;

    @BeforeEach
    public void setUp() {
        tasktypeStatusActionMapper = new TasktypeStatusActionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tasktypeStatusActionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tasktypeStatusActionMapper.fromId(null)).isNull();
    }
}
