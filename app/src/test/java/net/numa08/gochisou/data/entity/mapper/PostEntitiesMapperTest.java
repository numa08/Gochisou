package net.numa08.gochisou.data.entity.mapper;

import com.google.gson.Gson;

import net.numa08.gochisou.data.model.PageNation;
import net.numa08.gochisou.data.model.Team;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PostEntitiesMapperTest {

    @Test
    public void fromJSON() throws IOException {
        try(final InputStream inputStream = PostEntitiesMapperTest.class.getClassLoader().getResourceAsStream("teams.json")) {
            final String json = IOUtils.toString(inputStream);

            final Gson gson = new PostEntitiesMapper().getGson();
            final PageNation.TeamPageNation pageNation = gson.fromJson(json, PageNation.TeamPageNation.class);

            assertThat(pageNation.getTotalCount(), is(1));
            assertThat(pageNation.getPrevPage(), is(0));
            assertThat(pageNation.getNextPage(), is(0));

            final List<Team> list = pageNation.getList();
            assertThat(list.size(), is(1));
            final Team team = list.get(0);
            assertThat(team.getName(), is("docs"));
            assertThat(team.getPrivacy(), is("open"));
            assertThat(team.getDescription(), is("esa.io official documents"));
            assertThat(team.getIcon(), is("https://img.esa.io/uploads/production/teams/105/icon/thumb_m_0537ab827c4b0c18b60af6cdd94f239c.png"));
            assertThat(team.getUrl(), is("https://docs.esa.io/"));
        }

    }
}