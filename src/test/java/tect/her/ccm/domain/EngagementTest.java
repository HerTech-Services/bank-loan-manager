package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class EngagementTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Engagement.class);
        Engagement engagement1 = new Engagement();
        engagement1.setId(1L);
        Engagement engagement2 = new Engagement();
        engagement2.setId(engagement1.getId());
        assertThat(engagement1).isEqualTo(engagement2);
        engagement2.setId(2L);
        assertThat(engagement1).isNotEqualTo(engagement2);
        engagement1.setId(null);
        assertThat(engagement1).isNotEqualTo(engagement2);
    }
}
