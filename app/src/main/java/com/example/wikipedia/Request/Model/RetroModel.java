package com.example.wikipedia.Request.Model;


public class RetroModel
{
    private String batchcomplete;

    private Warnings warnings;

    private Query query;

    public String getBatchcomplete ()
    {
        return batchcomplete;
    }

    public void setBatchcomplete (String batchcomplete)
    {
        this.batchcomplete = batchcomplete;
    }

    public Warnings getWarnings ()
    {
        return warnings;
    }

    public void setWarnings (Warnings warnings)
    {
        this.warnings = warnings;
    }

    public Query getQuery ()
    {
        return query;
    }

    public void setQuery (Query query)
    {
        this.query = query;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [batchcomplete = "+batchcomplete+", warnings = "+warnings+", query = "+query+"]";
    }
}