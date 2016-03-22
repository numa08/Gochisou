package net.numa08.gochisou.data.entity.mapper

import net.numa08.gochisou.data.model.PageNation
import org.apache.commons.io.IOUtils
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.IOException

class PostEntitiesMapperTest {

    @Test
    @Throws(IOException::class)
    fun parseTeams() {
        PostEntitiesMapperTest::class.java.classLoader.getResourceAsStream("teams.json").use { inputStream ->
            val json = IOUtils.toString(inputStream)

            val gson = PostEntitiesMapper().gson
            val pageNation = gson.fromJson(json, PageNation.TeamPageNation::class.java)

            assertThat(pageNation.totalCount, `is`(1))
            assertThat(pageNation.prevPage, `is`(0))
            assertThat(pageNation.nextPage, `is`(0))

            val list = pageNation.list!!
            assertThat(list.size, `is`(1))
            val team = list[0]
            assertThat(team.name, `is`("docs"))
            assertThat(team.privacy, `is`("open"))
            assertThat(team.description, `is`("esa.io official documents"))
            assertThat(team.icon, `is`("https://img.esa.io/uploads/production/teams/105/icon/thumb_m_0537ab827c4b0c18b60af6cdd94f239c.png"))
            assertThat(team.url, `is`("https://docs.esa.io/"))
        }
    }

    @Test
    fun parsePosts() {
        PostEntitiesMapperTest::class.java.classLoader.getResourceAsStream("post.json").use { inp ->
            val json = IOUtils.toString(inp)

            val gson = PostEntitiesMapper().gson
            val pageNation = gson.fromJson(json, PageNation.PostPageNation::class.java)

            assertThat(pageNation.totalCount, `is`(1))
            assertThat(pageNation.prevPage, `is`(0))
            assertThat(pageNation.nextPage, `is`(0))

            val list = pageNation.list!!
            assertThat(list.size, `is`(1))
            val post = list[0]
            assertThat(post.number, `is`(1L))
            assertThat(post.name, `is`("hi!"))
        }
    }
}