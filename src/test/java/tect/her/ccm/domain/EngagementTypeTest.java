package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class EngagementTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EngagementType.class);
        EngagementType engagementType1 = new EngagementType();
        engagementType1.setId(1L);
        EngagementType engagementType2 = new EngagementType();
        engagementType2.setId(engagementType1.getId());
        assertThat(engagementType1).isEqualTo(engagementType2);
        engagementType2.setId(2L);
        assertThat(engagementType1).isNotEqualTo(engagementType2);
        engagementType1.setId(null);
        assertThat(engagementType1).isNotEqualTo(engagementType2);
    }
}
