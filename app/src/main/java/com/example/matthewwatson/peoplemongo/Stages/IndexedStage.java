package com.example.matthewwatson.peoplemongo.Stages;

import com.davidstemmer.screenplay.stage.XmlStage;

/**
 * Created by Matthew.Watson on 11/4/16.
 */

public abstract class IndexedStage extends XmlStage { //superclass for stages
    public final String id;//used to reference stage

    protected IndexedStage(String id) {
        this.id = id;
    }// + UUID if you reuse the same view

    @Override
    public boolean equals(Object o) {//ways to define equalities of stage ids
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexedStage that = (IndexedStage) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
