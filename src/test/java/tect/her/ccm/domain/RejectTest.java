package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class RejectTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reject.class);
        Reject reject1 = new Reject();
        reject1.setId(1L);
        Reject reject2 = new Reject();
        reject2.setId(reject1.getId());
        assertThat(reject1).isEqualTo(reject2);
        reject2.setId(2L);
        assertThat(reject1).isNotEqualTo(reject2);
        reject1.setId(null);
        assertThat(reject1).isNotEqualTo(reject2);
    }
}
