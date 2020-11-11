package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class TaskLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskLog.class);
        TaskLog taskLog1 = new TaskLog();
        taskLog1.setId(1L);
        TaskLog taskLog2 = new TaskLog();
        taskLog2.setId(taskLog1.getId());
        assertThat(taskLog1).isEqualTo(taskLog2);
        taskLog2.setId(2L);
        assertThat(taskLog1).isNotEqualTo(taskLog2);
        taskLog1.setId(null);
        assertThat(taskLog1).isNotEqualTo(taskLog2);
    }
}
