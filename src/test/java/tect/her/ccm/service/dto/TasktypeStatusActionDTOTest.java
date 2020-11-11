package tect.her.ccm.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class TasktypeStatusActionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TasktypeStatusActionDTO.class);
        TasktypeStatusActionDTO tasktypeStatusActionDTO1 = new TasktypeStatusActionDTO();
        tasktypeStatusActionDTO1.setId(1L);
        TasktypeStatusActionDTO tasktypeStatusActionDTO2 = new TasktypeStatusActionDTO();
        assertThat(tasktypeStatusActionDTO1).isNotEqualTo(tasktypeStatusActionDTO2);
        tasktypeStatusActionDTO2.setId(tasktypeStatusActionDTO1.getId());
        assertThat(tasktypeStatusActionDTO1).isEqualTo(tasktypeStatusActionDTO2);
        tasktypeStatusActionDTO2.setId(2L);
        assertThat(tasktypeStatusActionDTO1).isNotEqualTo(tasktypeStatusActionDTO2);
        tasktypeStatusActionDTO1.setId(null);
        assertThat(tasktypeStatusActionDTO1).isNotEqualTo(tasktypeStatusActionDTO2);
    }
}
