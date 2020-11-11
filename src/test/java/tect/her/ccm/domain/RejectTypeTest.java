package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class RejectTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RejectType.class);
        RejectType rejectType1 = new RejectType();
        rejectType1.setId(1L);
        RejectType rejectType2 = new RejectType();
        rejectType2.setId(rejectType1.getId());
        assertThat(rejectType1).isEqualTo(rejectType2);
        rejectType2.setId(2L);
        assertThat(rejectType1).isNotEqualTo(rejectType2);
        rejectType1.setId(null);
        assertThat(rejectType1).isNotEqualTo(rejectType2);
    }
}
