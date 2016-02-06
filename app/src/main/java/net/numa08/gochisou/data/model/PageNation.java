package net.numa08.gochisou.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public abstract class PageNation<T> {
    private List<T> list;
    @SerializedName("prev_page")
    private int prevPage;
    @SerializedName("next_page")
    private int nextPage;
    @SerializedName("total_count")
    private int totalCount;

    public List<T> getList() {
        return list;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public static class TeamPageNation extends PageNation<Team> {
        @SerializedName("teams")
        private List<Team> teams;

        @Override
        public List<Team> getList() {
            return teams;
        }
    }
}
