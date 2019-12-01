package com.example.wikipedia.Request.Model;

public class Query
{
    private String[] pageids;

    private String pages;

    public String[] getPageids ()
    {
        return pageids;
    }

    public void setPageids (String[] pageids)
    {
        this.pageids = pageids;
    }

    public String getPages ()
    {
        return pages;
    }

    public void setPages (String pages)
    {
        this.pages = pages;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pageids = "+pageids+", pages = "+pages+"]";
    }
}