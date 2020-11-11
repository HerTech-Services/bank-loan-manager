package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class TasktypeStatusActionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TasktypeStatusAction.class);
        TasktypeStatusAction tasktypeStatusAction1 = new TasktypeStatusAction();
        tasktypeStatusAction1.setId(1L);
        TasktypeStatusAction tasktypeStatusAction2 = new TasktypeStatusAction();
        tasktypeStatusAction2.setId(tasktypeStatusAction1.getId());
        assertThat(tasktypeStatusAction1).isEqualTo(tasktypeStatusAction2);
        tasktypeStatusAction2.setId(2L);
        assertThat(tasktypeStatusAction1).isNotEqualTo(tasktypeStatusAction2);
        tasktypeStatusAction1.setId(null);
        assertThat(tasktypeStatusAction1).isNotEqualTo(tasktypeStatusAction2);
    }
}
