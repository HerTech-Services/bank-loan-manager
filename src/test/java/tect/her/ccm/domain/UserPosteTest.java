package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class UserPosteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPoste.class);
        UserPoste userPoste1 = new UserPoste();
        userPoste1.setId(1L);
        UserPoste userPoste2 = new UserPoste();
        userPoste2.setId(userPoste1.getId());
        assertThat(userPoste1).isEqualTo(userPoste2);
        userPoste2.setId(2L);
        assertThat(userPoste1).isNotEqualTo(userPoste2);
        userPoste1.setId(null);
        assertThat(userPoste1).isNotEqualTo(userPoste2);
    }
}
