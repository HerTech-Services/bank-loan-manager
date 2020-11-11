package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTypeMapperTest {
    private TaskTypeMapper taskTypeMapper;

    @BeforeEach
    public void setUp() {
        taskTypeMapper = new TaskTypeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(taskTypeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(taskTypeMapper.fromId(null)).isNull();
    }
}
