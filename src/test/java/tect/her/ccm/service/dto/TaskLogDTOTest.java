package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class TaskLogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskLogDTO.class);
        TaskLogDTO taskLogDTO1 = new TaskLogDTO();
        taskLogDTO1.setId(1L);
        TaskLogDTO taskLogDTO2 = new TaskLogDTO();
        assertThat(taskLogDTO1).isNotEqualTo(taskLogDTO2);
        taskLogDTO2.setId(taskLogDTO1.getId());
        assertThat(taskLogDTO1).isEqualTo(taskLogDTO2);
        taskLogDTO2.setId(2L);
        assertThat(taskLogDTO1).isNotEqualTo(taskLogDTO2);
        taskLogDTO1.setId(null);
        assertThat(taskLogDTO1).isNotEqualTo(taskLogDTO2);
    }
}
