package tect.her.ccm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tect.her.ccm.web.rest.TestUtil;

public class TypeEntiteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeEntite.class);
        TypeEntite typeEntite1 = new TypeEntite();
        typeEntite1.setId(1L);
        TypeEntite typeEntite2 = new TypeEntite();
        typeEntite2.setId(typeEntite1.getId());
        assertThat(typeEntite1).isEqualTo(typeEntite2);
        typeEntite2.setId(2L);
        assertThat(typeEntite1).isNotEqualTo(typeEntite2);
        typeEntite1.setId(null);
        assertThat(typeEntite1).isNotEqualTo(typeEntite2);
    }
}
