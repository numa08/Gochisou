package net.numa08.gochisou.data.entity.mapper

import net.numa08.gochisou.data.model.PageNation
import org.apache.commons.io.IOUtils
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Test
import java.io.IOException
import java.util.*

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
            assertThat(post.tags!![0].value, `is`("api"))
            assertThat(post.tags!![1].value, `is`("dev"))
            assertThat(post.category, `is`("日報/2015/05/09"))
            assertThat(post.fullName, `is`("日報/2015/05/09/hi! #api #dev"))
            assertThat(post.isWip, `is`(true))
            assertThat(post.bodyMD, `is`("# Getting Started"))
            assertThat(post.bodyHTML, `is`("<h1 id=\"1-0-0\" name=\"1-0-0\">\n<a class=\"anchor\" href=\"#1-0-0\"><i class=\"fa fa-link\"></i><span class=\"hidden\" data-text=\"Getting Started\"> &gt; Getting Started</span></a>Getting Started</h1>\n"))
            post.createdAt!!.let { createdAt ->
                val actual = Calendar.getInstance(Locale.JAPAN).let { it.time = createdAt; it }
                assertThat(actual.get(Calendar.YEAR), `is`(2015))
                assertThat(actual.get(Calendar.MONTH), `is`(Calendar.MAY))
                assertThat(actual.get(Calendar.DAY_OF_MONTH), `is`(9))
                assertThat(actual.get(Calendar.HOUR_OF_DAY), `is`(11))
                assertThat(actual.get(Calendar.MINUTE), `is`(54))
                assertThat(actual.get(Calendar.SECOND), `is`(50))
            }
            post.updatedAt!!.let { updateAt ->
                val actual = Calendar.getInstance(Locale.JAPAN).let { it.time = updateAt; it }
                assertThat(actual.get(Calendar.YEAR), `is`(2015))
                assertThat(actual.get(Calendar.MONTH), `is`(Calendar.MAY))
                assertThat(actual.get(Calendar.DAY_OF_MONTH), `is`(9))
                assertThat(actual.get(Calendar.HOUR_OF_DAY), `is`(11))
                assertThat(actual.get(Calendar.MINUTE), `is`(54))
                assertThat(actual.get(Calendar.SECOND), `is`(51))
            }
            assertThat(post.message, `is`("Add Getting Started section"))
            assertThat(post.revisionNumber, `is`(1L))
            val createdBy = post.createdBy!!
            assertThat(createdBy.icon, `is`("http://img.esa.io/uploads/production/users/1/icon/thumb_m_402685a258cf2a33c1d6c13a89adec92.png"))
            assertThat(createdBy.name, `is`("Atsuo Fukaya"))
            assertThat(createdBy.screenName, `is`("fukayatsu"))
            val updateBy = post.updatedBy!!
            assertThat(updateBy.icon, `is`("http://img.esa.io/uploads/production/users/1/icon/thumb_m_402685a258cf2a33c1d6c13a89adec92.png"))
            assertThat(updateBy.name, `is`("Atsuo Fukaya"))
            assertThat(updateBy.screenName, `is`("fukayatsu"))
            assertThat(post.kind, `is`("flow"))
            assertThat(post.commentsCount, `is`(1L))
            assertThat(post.tasksCount, `is`(1L))
            assertThat(post.doneTasksCount, `is`(1L))
            assertThat(post.stargazersCount, `is`(1L))
            assertThat(post.watchersCount, `is`(1L))
            assertThat(post.isStar, `is`(true))
            assertThat(post.isWatch, `is`(true))
        }
    }
}