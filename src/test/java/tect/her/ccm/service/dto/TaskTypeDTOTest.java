package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class TaskTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskTypeDTO.class);
        TaskTypeDTO taskTypeDTO1 = new TaskTypeDTO();
        taskTypeDTO1.setId(1L);
        TaskTypeDTO taskTypeDTO2 = new TaskTypeDTO();
        assertThat(taskTypeDTO1).isNotEqualTo(taskTypeDTO2);
        taskTypeDTO2.setId(taskTypeDTO1.getId());
        assertThat(taskTypeDTO1).isEqualTo(taskTypeDTO2);
        taskTypeDTO2.setId(2L);
        assertThat(taskTypeDTO1).isNotEqualTo(taskTypeDTO2);
        taskTypeDTO1.setId(null);
        assertThat(taskTypeDTO1).isNotEqualTo(taskTypeDTO2);
    }
}
