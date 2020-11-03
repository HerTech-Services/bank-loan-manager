package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionMapperTest {
    private ActionMapper actionMapper;

    @BeforeEach
    public void setUp() {
        actionMapper = new ActionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(actionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(actionMapper.fromId(null)).isNull();
    }
}
