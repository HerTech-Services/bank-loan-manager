package tect.her.ccm.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParametersMapperTest {
    private ParametersMapper parametersMapper;

    @BeforeEach
    public void setUp() {
        parametersMapper = new ParametersMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(parametersMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(parametersMapper.fromId(null)).isNull();
    }
}
