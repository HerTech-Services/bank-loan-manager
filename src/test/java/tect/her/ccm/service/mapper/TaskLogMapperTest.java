package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskLogMapperTest {
    private TaskLogMapper taskLogMapper;

    @BeforeEach
    public void setUp() {
        taskLogMapper = new TaskLogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskLogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskLogMapper.fromId(null)).isNull();
    }
}
